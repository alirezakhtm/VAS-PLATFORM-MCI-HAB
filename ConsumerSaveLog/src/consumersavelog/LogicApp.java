/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumersavelog;

import com.fidar.database.handler.DatabaseHandler;
import com.fidar.database.object.MOMTLogObject;
import com.fidar.database.object.OTPLogObject;
import com.fidar.json.handler.JsonHandler;
import com.fidar.queue.handler.QueueHandler;
import com.fidar.queue.object.NotificationObject;
import com.fidar.queue.object.OTPObject;
import com.fidar.queue.object.ReceiveMsgObject;
import com.fidar.queue.object.SubUserObject;
import com.fidar.queue.object.UnSubUserObject;
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
    
    private final Timer guardTimer = new Timer();
    private final JsonHandler jHandler = new JsonHandler();
    private static final Integer TIME_OUT = 2000;
    private final String TXT_SUB = "subscription";
    private final String TXT_UNSUB = "unsubscription";
    
    private final QueueHandler qHandler = new QueueHandler();
    private final DatabaseHandler db = new DatabaseHandler();
    
    private boolean bProccessCurrent = false;
    
    public LogicApp() {
        guardTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(!bProccessCurrent){
                    bProccessCurrent = true;
                    Thread th = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DoProccess();
                        }
                    });
                    th.start();
                }
            }
        }, 0, (int)jHandler.getDelay());
    }
    
    public void DoProccess(){
        // start logic proccess
        ActiveMQConnectionFactory connectionFactory = 
                new ActiveMQConnectionFactory(
                        jHandler.getActiveMQUsername(),
                        jHandler.getActiveMQPassword(),
                        "tcp://localhost:61616"
                );
        Connection connection_ReceivedMsg = null;
        try{
            // create consumer for Received Message and pass them to Subscription and
            // unsubscription Queue
            connection_ReceivedMsg = connectionFactory.createConnection();
            connection_ReceivedMsg.start();
            Session session_ReceivedMsg = connection_ReceivedMsg.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination_ReceiveMsg = session_ReceivedMsg.createQueue(QueueHandler.Q_ReceiveMsg);
            MessageConsumer consumer_ReceiveMsg = session_ReceivedMsg.createConsumer(destination_ReceiveMsg);
            ExecutorService executorService_receivedMsg = Executors.newFixedThreadPool(50);
            while(true){
                Message message_ReceiveMsg = consumer_ReceiveMsg.receive(TIME_OUT);
                if(message_ReceiveMsg != null){
                    if(message_ReceiveMsg instanceof TextMessage){
                        String strReceiveMsg = ((TextMessage) message_ReceiveMsg).getText();
                        Gson gson = new GsonBuilder().create();
                        ReceiveMsgObject rmo = gson.fromJson(strReceiveMsg, ReceiveMsgObject.class);
                        // Detect user actione : Subscribe or Unsubscribe
                        String strTxtMsg = rmo.getText().toLowerCase();
                        // pass logic of parssing to thread for more speed
                        Thread th_ReceivedMsg = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.open();
                                int iServiceCode = db.getServiceCode(rmo);
                                db.close();
                                System.out.println("[*] " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                                        format(Calendar.getInstance().getTime()) +
                                        " ServiceCode : " + iServiceCode +
                                        " ReceMsg : " + strReceiveMsg +
                                        " From : " + rmo.getFrom());
                                if(iServiceCode != 0){
                                    switch(strTxtMsg){
                                        case TXT_SUB:
                                            // put data on Q-SubUser
                                            qHandler.InsertToSubUserQueue(new SubUserObject(iServiceCode, rmo));
                                            break;
                                        case TXT_UNSUB:
                                            // put data on Q-UnSubUser
                                            qHandler.InsertUnSubUserQueue(new UnSubUserObject(iServiceCode, rmo));
                                            break;
                                        default:
                                            // put data on Q-OtherMsg
                                            qHandler.InsertToOtherMsgQueue(rmo);
                                            break;
                                    }
                                }
                                // save data on the tbl_mo_mt_log
                                db.open();
                                db.saveMOMTLog(new MOMTLogObject(
                                        iServiceCode, 
                                        rmo.getFrom(), 
                                        rmo.getText(), 
                                        "null", 
                                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                                                format(Calendar.getInstance().getTime()))
                                );
                                // save data on tbl_receive_msg
                                db.saveReceiveSMS(
                                        new com.fidar.database.object.ReceiveMsgObject(
                                                rmo.getText(), 
                                                rmo.getFrom(), 
                                                rmo.getTo(), 
                                                rmo.getSmsId(), 
                                                rmo.getUserid()
                                        )
                                );
                                db.close();
                            }
                        });
                        executorService_receivedMsg.execute(th_ReceivedMsg);
                    }
                }else{
                    break;
                }
            }
            executorService_receivedMsg.shutdown();
            consumer_ReceiveMsg.close();
            session_ReceivedMsg.close();
            connection_ReceivedMsg.close();
            // start proccess for OTP Queue
            Connection connection_OTP = connectionFactory.createConnection();
            connection_OTP.start();
            Session session_OTP = connection_OTP.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination_OTP = session_OTP.createQueue(QueueHandler.Q_OTP);
            MessageConsumer consumer_OTP = session_OTP.createConsumer(destination_OTP);
            ExecutorService executorService_OTP = Executors.newFixedThreadPool(50);
            while(true){
                Message message_OTP = consumer_OTP.receive(TIME_OUT);
                if(message_OTP != null){
                    if(message_OTP instanceof TextMessage){
                        String strJson = ((TextMessage) message_OTP).getText();
                        Gson gson = new GsonBuilder().create();
                        OTPObject otpo = gson.fromJson(strJson, OTPObject.class);
                        // insert data to Q-OTPBuffer for other module
                        // but, we save it on MySQL database
                        Thread th_OTP = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("[*] " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                                        format(Calendar.getInstance().getTime()) +
                                        " OTP ID : " + otpo.getOtpId() +
                                        " Recipient : " + otpo.getRecipient() +
                                        " Status ID : " + otpo.getStatusId());
                                //disable this code because Consumer OTP Rsp have been disabled.
                                //qHandler.InsertToOTPBufferQueue(otpo); 
                                db.open();
                                db.saveOTPLog(
                                        new OTPLogObject(
                                                otpo.getOtpId(),
                                                otpo.getStatusId(),
                                                otpo.getRecipient(),
                                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                                                        format(Calendar.getInstance().getTime())
                                        )
                                );
                                db.close();
                            }
                        });
                        executorService_OTP.execute(th_OTP);
                    }
                }else{
                    break;
                }
            }
            executorService_OTP.shutdown();
            consumer_OTP.close();
            session_OTP.close();
            connection_OTP.close();
            // start proccess for Notification queue
            Connection connection_notification = connectionFactory.createConnection();
            connection_notification.start();
            Session session_notification = connection_notification.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination_notification = session_notification.createQueue(QueueHandler.Q_Notification);
            MessageConsumer consumer_notification = session_notification.createConsumer(destination_notification);
            ExecutorService executorService_notification = Executors.newFixedThreadPool(50);
            while(true){
                Message message_notification = consumer_notification.receive(TIME_OUT);
                if(message_notification != null){
                    if(message_notification instanceof TextMessage){
                        String strJson = ((TextMessage) message_notification).getText();
                        Gson gson = new GsonBuilder().create();
                        NotificationObject notificationObject = gson.fromJson(strJson, NotificationObject.class);
                        Thread th_Notification = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.open();
                                int iServiceCode = db.getServiceCode(notificationObject);
                                db.close();
                                System.out.println("[*] " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                                        format(Calendar.getInstance().getTime()) +
                                        " ServiceCode : " + iServiceCode +
                                        " ReceMsg : " + notificationObject.getText() +
                                        " From : " + notificationObject.getFrom());
                                if(iServiceCode != 0){
                                    switch(notificationObject.getText()){
                                        case TXT_SUB:
                                            // put data on Q-SubUser
                                            qHandler.InsertToSubUserQueue(new SubUserObject(iServiceCode, notificationObject));
                                            break;
                                        case TXT_UNSUB:
                                            // put data on Q-UnSubUser
                                            qHandler.InsertUnSubUserQueue(new UnSubUserObject(iServiceCode, notificationObject));
                                            break;
                                    }
                                }
                                // save data on tbl_notification_log
                                db.open();
                                db.saveNotificationLog(notificationObject);
                                db.close();
                            }
                        });
                        executorService_notification.execute(th_Notification);
                    }
                }else{
                    break;
                }
            }
            executorService_notification.shutdown();
            consumer_notification.close();
            session_notification.close();
            connection_notification.close();
            // wait untile all threads job done! 
            while(!executorService_receivedMsg.isTerminated()){}
            while(!executorService_OTP.isTerminated()){}
            while(!executorService_notification.isTerminated()){}
        }catch(JMSException e){
            System.err.println("LogicApp - DoProccess : " + e);
        }
        // end proccess
        // accept re-run this function
        bProccessCurrent = false;
    }
    
    
    
    
}
