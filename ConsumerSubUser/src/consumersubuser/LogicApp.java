/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumersubuser;

import com.fidar.database.handler.DatabaseHandler;
import com.fidar.database.object.ServicesUser;
import com.fidar.json.handler.JsonHandler;
import com.fidar.queue.handler.QueueHandler;
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
            Destination destination = session.createQueue(QueueHandler.Q_SubUser);
            MessageConsumer consumer = session.createConsumer(destination);
            ExecutorService executorService = Executors.newFixedThreadPool(50);
            while(true){
                Message message = consumer.receive(TIME_OUT);
                if(message != null){
                    if(message instanceof TextMessage){
                        String strJson = ((TextMessage) message).getText();
                        Gson gson = new GsonBuilder().create();
                        SubUserObject subUserObject = gson.fromJson(strJson, SubUserObject.class);
                        Thread th_subuser = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                
                                
                                System.out.println("[*] " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                                                format(Calendar.getInstance().getTime()) +
                                                " User <" + subUserObject.getReceiveMsgObject().getFrom() +
                                                "> have been registered.");
                                // insert SMS object to Q-SMS & save record on tbl_serviceusers
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
                                
                                
                                
                                /*
                                // call web service for subscribe user
                                WSXMLGenerator wsxmlg = new WSXMLGenerator();
                                // subscribe new user to service
                                String strOrder = wsxmlg.getPushOTP(subUserObject, 5);
                                String WSResponce = WSHandler.sendToWebServiceHUB(strOrder);
                                // Analysis webservice response
                                PushOTPResponse potpr = wsxmlg.getPushOTPResponse(WSResponce);
                                if(potpr.getCode().getValue().toLowerCase().equals("ok")){
                                    System.out.println("[*] " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                                            format(Calendar.getInstance().getTime()) +
                                            " User <" + subUserObject.getReceiveMsgObject().getFrom() +
                                            "> have been registered.");
                                    // insert response of web service into Q-PushOTPResponse
                                    QHandler.InsertPushOTPResponse(WSResponce);
                                    // insert SMS object to Q-SMS
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
                                }else{
                                    // insert response of web service into Q-ErrorPushOTP
                                    QHandler.InsertToErrorPushOTP(WSResponce);
                                }
                                */
                                
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
            System.err.println("ConsumerSubUser - LogicApp - DoProccess : " + e);
        }
        bProcessCurrent = false;
    }
    
}
