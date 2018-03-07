/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.mci.hub.webservice.handler;

import com.fidar.mci.hub.webservice.ObjectFactory;
import com.fidar.mci.hub.webservice.Sms;
import com.fidar.mci.hub.webservice.SmsSoap;
import com.fidar.mci.hub.webservice.XmsRequest;

/**
 *
 * @author alirezakhtm
 */
public class WSHandler {
    
    public static String sendToWebServiceHUB(String Input){
        ObjectFactory objectFactory = new ObjectFactory();
        XmsRequest request = objectFactory.createXmsRequest();
        request.setRequestData(Input);
        SmsSoap smsSoap = (new Sms()).getSmsSoap();
        String strAns = smsSoap.xmsRequest(Input) + "";
        return strAns;
    }
    
}
