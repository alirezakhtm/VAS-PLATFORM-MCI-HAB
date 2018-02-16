/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumerothermsg;

import com.fidar.database.handler.DatabaseHandler;
import com.fidar.database.object.ServicesUser;
import com.fidar.json.handler.JsonHandler;
import com.fidar.queue.handler.QueueHandler;
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
 * @author alireza
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
            Destination destination = session.createQueue(QueueHandler.Q_OTHER_MSG);
            MessageConsumer consumer = session.createConsumer(destination);
            ExecutorService executorService = Executors.newFixedThreadPool(50);
            while(true){
                Message message = consumer.receive(TIME_OUT);
                if(message != null){
                    if(message instanceof TextMessage){
                        String strJson = ((TextMessage) message).getText();
                        Thread th_subuser = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new GsonBuilder().create();
                                ReceiveMsgObject rmo = gson.fromJson(strJson, ReceiveMsgObject.class);
                                db.open();
                                int iServiceCode = db.getServiceCode(rmo);
                                db.close();
                                String strTxtMsg = rmo.getText();
                                switch(strTxtMsg){
                                    case "":
                                        db.open();
                                        String strHelpMT = db.getHelpMT(iServiceCode);
                                        db.close();
                                        QHandler.InsertToSMSQueue(new SMSObject(iServiceCode+"", rmo.getFrom(), strHelpMT));
                                        break;
                                    case " ":
                                        db.open();
                                        String strHelpMT_ = db.getHelpMT(iServiceCode);
                                        db.close();
                                        QHandler.InsertToSMSQueue(new SMSObject(iServiceCode+"", rmo.getFrom(), strHelpMT_));
                                        break;
                                    default:
                                        db.open();
                                        String strNotValidMT = db.getNotValidMT(iServiceCode);
                                        db.close();
                                        QHandler.InsertToSMSQueue(new SMSObject(iServiceCode+"", rmo.getFrom(), strNotValidMT));
                                        break;
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
            System.err.println("ConsumerOtherMsg - LogicApp - DoProccess : " + e);
        }
        bProcessCurrent = false;
    }
}
