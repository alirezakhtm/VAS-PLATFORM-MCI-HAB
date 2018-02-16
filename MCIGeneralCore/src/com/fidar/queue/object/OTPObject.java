/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.queue.object;

/**
 *
 * @author alirezakhtm
 */
public class OTPObject {
    private String otpId;
    private String statusId;
    private String recipient;

    public OTPObject(String otpId, String statusId, String recipient) {
        this.otpId = otpId;
        this.statusId = statusId;
        this.recipient = recipient;
    }

    public OTPObject() {
    }
    
    public String getOtpId() {
        return otpId;
    }

    public void setOtpId(String otpId) {
        this.otpId = otpId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    
}
