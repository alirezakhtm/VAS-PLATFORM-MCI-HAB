/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XMLGenerator;

import com.fidar.mci.hub.webservice.object.PushOTPResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.camel.converter.stream.ReaderCache;

/**
 *
 * @author alirezakhtm
 */
public class PushOTPResTest {
    public static void main(String[] args) {
        String str = //"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
                    "<xmsresponse>\n" +
                    "<userid>*****</userid>\n" +
                    "<action>PushOtp</action>\n" +
                    "<code id=\"0\">ok</code>\n" +
                    "<body>\n" +
                    "<recipient mobile=\"09194018087\" doerid=\"8702413\" status=\"paied\"> otpid </recipient>\n" +
                    "</body>\n" +
                    "</xmsresponse>";
        try{
            JAXBContext context = JAXBContext.newInstance(PushOTPResponse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(str);
            PushOTPResponse potpr = (PushOTPResponse) unmarshaller.unmarshal(sr);
            System.out.println("action : " + potpr.getAction());
            System.out.println("userid : " + potpr.getUserId());
            System.out.println("id : " + potpr.getCode().getId());
            System.out.println("doerid : " + potpr.getBody().getPushOtpResponse().getDoerid());
            System.out.println("mobile : " + potpr.getBody().getPushOtpResponse().getMobile());
            System.out.println("status : " + potpr.getBody().getPushOtpResponse().getStatus());
            System.out.println("recipient : " + potpr.getBody().getPushOtpResponse().getValue());
        }catch(JAXBException e){
            System.err.println(e);
        }
    }
}
