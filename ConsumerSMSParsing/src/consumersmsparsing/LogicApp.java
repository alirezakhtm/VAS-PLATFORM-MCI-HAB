/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumersmsparsing;

import com.fidar.database.handler.DatabaseHandler;
import com.fidar.database.object.MOMTLogObject;
import com.fidar.json.handler.JsonHandler;
import com.fidar.mci.hub.webservice.handler.WSHandler;
import com.fidar.mci.hub.webservice.handler.WSXMLGenerator;
import com.fidar.queue.handler.QueueHandler;
import com.fidar.queue.object.SMSObject;
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
            Destination destination = session.createQueue(QueueHandler.Q_SMS);
            MessageConsumer consumer = session.createConsumer(destination);
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            while(true){
                Message message = consumer.receive(TIME_OUT);
                if(message != null){
                    if(message instanceof TextMessage){
                        String strJson = ((TextMessage) message).getText();
                        Gson gson = new GsonBuilder().create();
                        SMSObject smso = gson.fromJson(strJson, SMSObject.class);
                        Thread th_smso = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // call web service for subscribe user
                                WSXMLGenerator wsxmlg = new WSXMLGenerator();
                                // send welcome MT message to user by SMS
                                // read shortcode from database by service code
                                db.open();
                                String shortCode = db.getShortCode(smso.getSerivceCode());
                                db.close();
                                db.open();
                                String strServiceId = db.getServiceId(Integer.parseInt(smso.getSerivceCode()));
                                db.close();
                                String msisdn_edited = "";
                                if(smso.getMsisdn().startsWith("0")) msisdn_edited = smso.getMsisdn().substring(1);
                                else if(smso.getMsisdn().startsWith("98")) msisdn_edited = smso.getMsisdn().substring(2);
                                else msisdn_edited = smso.getMsisdn();
                                // create XML for send message to user
                                String strSendSMS = "<xmsrequest>\n" +
                                                    "<userid>"+jHandler.getWSUserId()+"</userid>\n" +
                                                    "<password>"+jHandler.getWSPassword()+"</password>\n" +
                                                    "<action>smssend</action>\n" +
                                                    "<body>\n" +
                                                    "<type>oto</type>\n" +
                                                    "<serviceid>"+strServiceId+"</serviceid>\n" +
                                                    "<recipient mobile=\""+msisdn_edited+"\" originator=\""+shortCode+"\" cost=\"0\">"+smso.getTxtMessage()+"</recipient>\n" +
                                                    "</body>" +
                                                    "</xmsrequest>";
                                String rspWSMCI = WSHandler.sendToWebServiceHUB(strSendSMS);
                                System.out.println(
                                        "[*] " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                                                format(Calendar.getInstance().getTime()) +
                                                " XML that send > " + strSendSMS.replace("\n", "")
                                );
                                // get log webservice response
                                System.out.println(
                                        "[*] " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + " Local SMS Sender Webservice Rsp ["+rspWSMCI+"]"
                                );
                                // save log on tbl_mo_mt_log
                                db.open();
                                db.saveMOMTLog(new MOMTLogObject(
                                        Integer.parseInt(smso.getSerivceCode()),
                                        smso.getMsisdn(),
                                        "null",
                                        smso.getTxtMessage(),
                                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()))
                                );
                                db.close();
                            }
                        });
                        executorService.execute(th_smso);
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
