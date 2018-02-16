/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XMLGenerator;

import com.fidar.mci.hub.webservice.object.BodyChargeOTPResponse;
import com.fidar.mci.hub.webservice.object.ChargeOTPResponse;
import com.fidar.mci.hub.webservice.object.CodeChargeOTPResponse;
import com.fidar.mci.hub.webservice.object.RecipientChargeOTPResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author alireza
 */
public class ChargeOTPTest {
    public static void main(String[] args) {
        ChargeOTPResponse chargeOTPResponse = new ChargeOTPResponse();
        chargeOTPResponse.setAction("chargeOTP");
        chargeOTPResponse.setUserId("Alireza");
        RecipientChargeOTPResponse recipientChargeOTPResponse = new RecipientChargeOTPResponse();
        recipientChargeOTPResponse.setDoerId("123");
        recipientChargeOTPResponse.setMobile("9194018087");
        recipientChargeOTPResponse.setStatus("4545");
        recipientChargeOTPResponse.setValue("charge");
        BodyChargeOTPResponse body = new BodyChargeOTPResponse();
        body.setValue(recipientChargeOTPResponse);
        chargeOTPResponse.setBody(body);
        CodeChargeOTPResponse code = new CodeChargeOTPResponse();
        code.setId(0);
        code.setValue("ok");
        chargeOTPResponse.setCode(code);
        try{
            JAXBContext context = JAXBContext.newInstance(ChargeOTPResponse.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(chargeOTPResponse, System.out);
        }catch(JAXBException e){
            System.err.println("Error > " + e);
        }
    }
}
