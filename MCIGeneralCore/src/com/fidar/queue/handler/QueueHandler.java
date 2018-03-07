/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.queue.handler;

import com.fidar.json.handler.JsonHandler;
import com.fidar.queue.object.NotificationObject;
import com.fidar.queue.object.OTPObject;
import com.fidar.queue.object.ReceiveMsgObject;
import com.fidar.queue.object.SMSObject;
import com.fidar.queue.object.SubUserObject;
import com.fidar.queue.object.UnSubUserObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author alirezakhtm
 */
public class QueueHandler {
    
    private JsonHandler jHandler = null;
    
    private static String userName = "";
    private static String password = "";
    private static final String urlConnection = "tcp://localhost:61616";
    
    public static final String Q_OTP = "Q-OTP";
    public static final String Q_ReceiveMsg = "Q-ReceiveMsg";
    public static final String Q_SubUser = "Q-SubUser";
    public static final String Q_UnSubUser = "Q-UnSubUser";
    public static final String Q_SMS = "Q-SMS";
    public static final String Q_ErrorPushOTP = "Q-ErrorPushOTP";
    public static final String Q_PushOTPResponse = "Q-PushOTPResponse";
    public static final String Q_OTHER_MSG = "Q-OtherMsg";
    public static final String Q_OTP_BUFFER = "Q-OTPBuffer";
    public static final String Q_Notification = "Q-Notification";

    public QueueHandler() {
        if(jHandler == null) jHandler = new JsonHandler();
        QueueHandler.userName = jHandler.getActiveMQUsername();
        QueueHandler.password = jHandler.getActiveMQPassword();
    }
    
    public void InsertToNotificationQueue(NotificationObject no){
        String strJson = getJsonObject(no);
        saveTextToQueue(strJson, Q_Notification, "QueueHandler - InsertToNotificationQueue : ");
    }
    
    public void InsertToOTPQueue(OTPObject otpo){
        String strJson = getJsonObject(otpo);
        saveTextToQueue(strJson, Q_OTP, "QueueHandler - InsertToOTPQueue : ");
    }
    
    public void InsertToReceiveMsgQueue(ReceiveMsgObject rmo){
        String strJson = getJsonObject(rmo);
        saveTextToQueue(strJson, Q_ReceiveMsg, "QueueHandler - InsertToReceiveMsgQueue : ");
    }
    
    public void InsertToSubUserQueue(SubUserObject suo){
        String strJson = getJsonObject(suo);
        saveTextToQueue(strJson, Q_SubUser, "QueueHandler - InsertToSubUserQueue : ");
    }
    
    public void InsertUnSubUserQueue(UnSubUserObject usuo){
        String strJson = getJsonObject(usuo);
        saveTextToQueue(strJson, Q_UnSubUser, "QueueHandler - InsertUnSubUserQueue : ");
    }
    
    public void InsertPushOTPResponse(String StrXML){
        saveTextToQueue(StrXML, Q_PushOTPResponse, "QueueHandler - InsertPushOTPResponse : ");
    }
    
    public void InsertToSMSQueue(SMSObject smso){
        String strJson = getJsonObject(smso);
        saveTextToQueue(strJson, Q_SMS, "QueueHandler - InsertToSMSQueue : ");
    }
    
    public void InsertToErrorPushOTP(String strXML){
        saveTextToQueue(strXML, Q_ErrorPushOTP, "QueueHandler - InsertToErrorPushOTP : ");
    }
    
    public void InsertToOtherMsgQueue(ReceiveMsgObject rmo){
        String strJson = getJsonObject(rmo);
        saveTextToQueue(strJson, Q_OTHER_MSG, "QueueHandler - InsertToOtherMsgQueue : ");
    }
    
    public void InsertToOTPBufferQueue(OTPObject otp){
        String strJson = getJsonObject(otp);
        saveTextToQueue(strJson, Q_OTP_BUFFER, "QueueHandler - InsertToOTPBufferQueue : ");
    }
    
    public String getJsonObject(Object object){
        Gson gson = new GsonBuilder().create();
        String strJson = gson.toJson(object, object.getClass());
        return strJson;
    }
    
    private void saveTextToQueue(String strMessage, String QName, String errorMessage){
        ActiveMQConnectionFactory connectionFactory = 
                new ActiveMQConnectionFactory(userName, password, urlConnection);
        Connection connection = null;
        try{
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(QName);
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage(strMessage);
            producer.send(message);
            producer.close();
            session.close();
            connection.close();
        }catch(JMSException e){
            System.err.println(errorMessage + e);
        }
    }
}
