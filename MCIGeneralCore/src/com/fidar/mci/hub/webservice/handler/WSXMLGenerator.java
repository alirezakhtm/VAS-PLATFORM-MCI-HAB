/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.mci.hub.webservice.handler;

import com.fidar.database.handler.DatabaseHandler;
import com.fidar.database.object.ChargeOTPObject;
import com.fidar.json.handler.JsonHandler;
import com.fidar.mci.hub.webservice.object.BodyPushOTP;
import com.fidar.mci.hub.webservice.object.ChargeOTPResponse;
import com.fidar.mci.hub.webservice.object.PushOTPResponse;
import com.fidar.mci.hub.webservice.object.PushOtp;
import com.fidar.mci.hub.webservice.object.RecipientPushOtp;
import com.fidar.queue.object.SubUserObject;
import com.fidar.queue.object.UnSubUserObject;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Random;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.camel.converter.stream.ReaderCache;

/**
 *
 * @author alirezakhtm
 */
public class WSXMLGenerator {
    
    private static JsonHandler jHandler = null;
    private DatabaseHandler db = new DatabaseHandler();

    public WSXMLGenerator() {
        if(jHandler == null) jHandler = new JsonHandler();
    }
    
    public String getPushOTP(SubUserObject suo, int cost){
        String ans = "";
        db.open();
        int serviceId = Integer.parseInt(db.getServiceId(suo.getServiceCode()));
        db.close();
        Random rnd = new Random();
        try{
            JAXBContext context = JAXBContext.newInstance(PushOtp.class);
            Marshaller marshaller = context.createMarshaller();
            // create object
            PushOtp po = new PushOtp();
            po.setAction("PushOtp");
            po.setUsername(jHandler.getWSUserId());
            po.setPassword(jHandler.getWSPassword());
            BodyPushOTP body = new BodyPushOTP();
            body.setServiceId(serviceId+"");
            RecipientPushOtp recipient = new RecipientPushOtp();
            recipient.setCost(cost+"");
            recipient.setDoerid(Math.abs(rnd.nextLong())+"");
            recipient.setMobile(suo.getReceiveMsgObject().getFrom());
            recipient.setOriginator(suo.getReceiveMsgObject().getTo());
            body.setRecipientPushOtp(recipient);
            po.setBody(body);
            // create xml file
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            marshaller.marshal(po, sw);
            ans = sw.toString().replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
        }catch(JAXBException e){
            System.err.println("WSXMLGenerator - getPushOTP : " + e);
        }
        return ans;
    }
    
    public String getPushOTP(UnSubUserObject usuo, int cost){
        String ans = "";
        db.open();
        int serviceId = Integer.parseInt(db.getServiceId(usuo.getServiceCode()));
        db.close();
        Random rnd = new Random();
        try{
            JAXBContext context = JAXBContext.newInstance(PushOtp.class);
            Marshaller marshaller = context.createMarshaller();
            // create object
            PushOtp po = new PushOtp();
            po.setAction("PushOtp");
            po.setUsername(jHandler.getWSUserId());
            po.setPassword(jHandler.getWSPassword());
            BodyPushOTP body = new BodyPushOTP();
            body.setServiceId(serviceId+"");
            RecipientPushOtp recipient = new RecipientPushOtp();
            recipient.setCost(cost+"");
            recipient.setDoerid(Math.abs(rnd.nextLong())+"");
            recipient.setMobile(usuo.getReceiveMsgObject().getFrom());
            recipient.setOriginator(usuo.getReceiveMsgObject().getTo());
            body.setRecipientPushOtp(recipient);
            po.setBody(body);
            // create xml file
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            marshaller.marshal(po, sw);
            ans = sw.toString().replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
        }catch(JAXBException e){
            System.err.println("WSXMLGenerator - getPushOTP : " + e);
        }
        return ans;
    }
    
    public PushOTPResponse getPushOTPResponse(String rsp){
        PushOTPResponse oTPResponse = null;
        try{
            JAXBContext context = JAXBContext.newInstance(PushOTPResponse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(rsp);
            oTPResponse = (PushOTPResponse) unmarshaller.unmarshal(sr);
        }catch(JAXBException e){
            System.err.println("WSXMLGenerator - getPushOTPResponse : " + e);
        }
        return oTPResponse;
    }
    
    public String getChargeOTP(ChargeOTPObject cotpo){
        db.open();
        String strServiceId = db.getServiceId(Integer.parseInt(cotpo.getServiceCode()));
        db.close();
        Random rnd = new Random();
        String strAns = 
                "<xmsrequest>\n" +
                "<userid>"+jHandler.getWSUserId()+"</userid>\n" +
                "<password>"+jHandler.getWSPassword()+"</password>\n" +
                "<action>chargeotp</action>\n" +
                "<body>\n" +
                "<serviceid>"+strServiceId+"</serviceid>\n" +
                "<recipient mobile=\""+
                cotpo.getMsisdn()+
                "\" originator=\""+
                cotpo.getShortCode()+
                "\" pin=\""+cotpo.getPinCode()+
                "\" doerid=\""+
                Math.abs(rnd.nextLong())+
                "\" >"+cotpo.getOtpId()+"</recipient>\n" +
                "</body>\n" +
                "</xmsrequest>";
        return strAns;
    }
    
    public ChargeOTPResponse getChargeOTPResponse(String str){
        ChargeOTPResponse cotpr = new ChargeOTPResponse();
        try{
            JAXBContext context = JAXBContext.newInstance(ChargeOTPResponse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(str);
            cotpr = (ChargeOTPResponse) unmarshaller.unmarshal(sr);
        }catch(JAXBException e){
            System.err.println("WSXMLGenerator - getChargeOTPResponse : " + e);
        }
        return cotpr;
    }
}
