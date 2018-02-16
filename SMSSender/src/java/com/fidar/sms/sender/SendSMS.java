/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.sms.sender;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author alirezakhtm
 */
@WebService(serviceName = "SendSMS")
public class SendSMS {
    // define constant token for local webservice
    private final String TOKEN = "Plft326Vgt33PortFidar1313wwee";
    
    @WebMethod(operationName = "sendSMS")
    public String sendSMS(
            @WebParam(name = "txtMsg") String msg,
            @WebParam(name = "msisdn") String msisdn,
            @WebParam(name = "token") String token,
            @WebParam(name = "shortCode") String shortCode){
        String ans = "";
        if(token.equals(TOKEN)){
            String msisdn_edited = "";
            if(msisdn.startsWith("0")) msisdn_edited = msisdn.substring(1);
            if(msisdn.startsWith("98")) msisdn_edited = msisdn.substring(2);
            
        }else{
            ans = "ERROR - TOKEN IS NOT CORRECT";
        }
        return ans;
    }
}
