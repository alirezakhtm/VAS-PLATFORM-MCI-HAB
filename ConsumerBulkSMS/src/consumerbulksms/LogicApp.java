/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumerbulksms;

import com.fidar.mci.hub.webservice.SmsSoap;
import com.fidar.mci.hub.webservice.XmsRequest;
import com.fidar.sms.sender.ObjectFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.khtm.object.BulkObject;
import com.fidar.mci.hub.webservice.Sms;
import com.fidar.mci.hub.webservice.SmsSoap;
import com.fidar.mci.hub.webservice.XmsRequest;
import com.fidar.mci.hub.webservice.handler.WSHandler;
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
    private final String amq_username = "admin";
    private final String amq_password = "alireza@2413";
    // create guard timer for protection of consumer conection
    private final Timer GuardTimer = new Timer();
    // felag used by guard timer
    private static boolean bProcessCurrent = false;
    // define constence
    private final int TIME_OUT = 2000;
    // define constant token for local webservice
    private final String TOKEN = "Plft326Vgt33PortFidar1313wwee";
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
        }, 0, 1000);
    }
    
    public void DoProcess(){
        ActiveMQConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(amq_username,
                        amq_password,
                        "tcp://localhost:61616");
        Connection connection = null;
        try{
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("Q-BULKSMS");
            MessageConsumer consumer = session.createConsumer(destination);
            ExecutorService executorService = Executors.newFixedThreadPool(50);
            while(true){
                Message message = consumer.receive(TIME_OUT);
                if(message != null){
                    if(message instanceof TextMessage){
                        String strJson = ((TextMessage) message).getText();
                        Gson gson = new GsonBuilder().create();
                        BulkObject bulkSMS = gson.fromJson(strJson, BulkObject.class);
                        for(int n = 0; n < Math.ceil(bulkSMS.getLstPhone().size()/100000) + 1; n++){
                            StringBuilder sb = new StringBuilder();
                            for(int m = n*100000; m < n*100000 + 100000; m++){
                                if(m < bulkSMS.getLstPhone().size()){
                                    String msisdn = bulkSMS.getLstPhone().get(m);
                                    msisdn = (msisdn.startsWith("0")) ? msisdn.substring(1) : msisdn;
                                    msisdn = (msisdn.startsWith("98")) ? msisdn.substring(2) : msisdn;
                                    sb.append("<recipient>" + msisdn + "</recipient>\n");
                                }
                            }
                            String xmlString = "<xmsrequest>\n" +
                                "<userid>57303</userid>\n" +
                                "<password>R@hkar!2018</password>\n" +
                                "<action>smssend</action>\n" +
                                "<body>\n" +
                                "<type>otm</type>\n" +
                                "<serviceid>613</serviceid>\n" +
                                "<message originator=\"405571\">" + bulkSMS.getMessage() + "</message>\n" +
                                sb.toString() +
                                "</body>\n" +
                                "</xmsrequest>";
                            System.out.println("[<<<<] " + xmlString);
                            System.out.println("==================================");
                            String rspWSMCI = WSHandler.sendToWebServiceHUB(xmlString);
                            System.out.println("[>>>>] " + rspWSMCI);
                        }
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
            System.err.println("ConsumerSMSParsing - LogicApp - DoProccess : " + e);
        }
        bProcessCurrent = false;
    }
    
}
