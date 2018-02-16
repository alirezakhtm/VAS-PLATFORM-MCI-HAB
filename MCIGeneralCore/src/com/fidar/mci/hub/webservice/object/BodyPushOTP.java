/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.mci.hub.webservice.object;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author alirezakhtm
 */
public class BodyPushOTP {
    private String serviceId;
    private RecipientPushOtp recipientPushOtp;

    public String getServiceId() {
        return serviceId;
    }

    @XmlElement(name = "serviceid")
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public RecipientPushOtp getRecipientPushOtp() {
        return recipientPushOtp;
    }

    @XmlElement(name = "recipient")
    public void setRecipientPushOtp(RecipientPushOtp recipientPushOtp) {
        this.recipientPushOtp = recipientPushOtp;
    }
    
}