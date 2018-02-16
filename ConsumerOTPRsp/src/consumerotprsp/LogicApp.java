/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumerotprsp;

import com.fidar.database.handler.DatabaseHandler;
import com.fidar.database.object.OTPReqObject;
import com.fidar.database.object.ServicesUser;
import com.fidar.json.handler.JsonHandler;
import com.fidar.queue.handler.QueueHandler;
import com.fidar.queue.object.OTPObject;
import com.fidar.queue.object.ReceiveMsgObject;
import com.fidar.queue.object.SMSObject;
import com.fidar.queue.object.SubUserObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author alirezakhtm
 */
public class LogicApp {
    // create guard timer for protection of consumer conection
    private final Timer GuardTimer = new Timer();
    // read setting from json
    private final JsonHandler jHandler = new JsonHandler();
    // create database object to access DB
    private final DatabaseHandler db = new DatabaseHandler();
    // create object for managing of Queue in ActiveMQ
    private final QueueHandler QHandler = new QueueHandler();
    // felag used by guard timer
    private static boolean bProcessCurrent = false;
    // define constence
    private final int TIME_OUT = 2000;
    /**
     * this constructor, run DoProccess() function automatically.
     */
    public LogicApp() {
        GuardTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(!bProcessCurrent){
                    bProcessCurrent = true;
                    Thread th = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DoProcess();
                        }
                    });
                    th.start();
                }
            }
        }, 0, (long)jHandler.getDelay());
    }
    
    public void DoProcess(){
        ActiveMQConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(jHandler.getActiveMQUsername(),
                        jHandler.getActiveMQPassword(),
                        "tcp://localhost:61616");
        Connection connection = null;
        try{
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(QueueHandler.Q_OTP_BUFFER);
            MessageConsumer consumer = session.createConsumer(destination);
            ExecutorService executorService = Executors.newFixedThreadPool(50);
            while(true){
                Message message = consumer.receive(TIME_OUT);
                if(message != null){
                    if(message instanceof TextMessage){
                        String strJson = ((TextMessage) message).getText();
                        Gson gson = new GsonBuilder().create();
                        OTPObject otpo = gson.fromJson(strJson, OTPObject.class);
                        Thread th_subuser = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                
                                
                                System.out.println("[*] " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                                                format(Calendar.getInstance().getTime()) +
                                                " User <" + otpo.getRecipient() +
                                                ">, with Status <"+ otpo.getStatusId() +"> have been registered.");
                                db.open();
                                OTPReqObject otpro = db.getOTPRequest(otpo.getOtpId());
                                db.close();
                                if(otpro.getMsisdn() != null){
                                    // insert SMS object to Q-SMS & save record on tbl_serviceusers
                                    SubUserObject subUserObject = new SubUserObject(
                                            Integer.parseInt(otpro.getServiceCode()),
                                            new ReceiveMsgObject("register from OTP push", otpro.getMsisdn(), otpro.getShortCode(), "0", "0")
                                    );
                                    db.open();
                                    String welcomeMT = db.getWelcomeMT(subUserObject.getServiceCode());
                                    db.saveUserOnServiceUser(new ServicesUser(
                                                    subUserObject.getServiceCode(),                 // service code
                                                    subUserObject.getReceiveMsgObject().getFrom(),  // msisdn
                                                    1                                               // subscribe user
                                    ));
                                    db.close();
                                    QHandler.InsertToSMSQueue(new SMSObject(
                                                    subUserObject.getServiceCode()+"",
                                                    subUserObject.getReceiveMsgObject().getFrom(),
                                                    welcomeMT)
                                    );
                                }
                            }
                        });
                        executorService.execute(th_subuser);
                    }
                }else{
                    break;
                }
            }
            executorService.shutdown();
            while(!executorService.isTerminated()){}
            // close connections of JMS
            consumer.close();
            session.close();
            connection.close();
        }catch(JMSException e){
            System.err.println("ConsumerOTPRsp - LogicApp - DoProccess : " + e);
        }
        bProcessCurrent = false;
    }
    
}
