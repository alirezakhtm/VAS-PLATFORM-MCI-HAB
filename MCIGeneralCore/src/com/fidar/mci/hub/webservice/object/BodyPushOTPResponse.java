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
public class BodyPushOTPResponse {
    private RecipientPushOtpResponse pushOtpResponse;

    public RecipientPushOtpResponse getPushOtpResponse() {
        return pushOtpResponse;
    }

    @XmlElement(name = "recipient")
    public void setPushOtpResponse(RecipientPushOtpResponse pushOtpResponse) {
        this.pushOtpResponse = pushOtpResponse;
    }
}