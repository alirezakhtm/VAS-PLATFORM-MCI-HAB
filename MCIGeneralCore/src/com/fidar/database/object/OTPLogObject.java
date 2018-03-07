/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.database.object;

/**
 *
 * @author alirezakhtm
 */
public class OTPLogObject {
    private final String otpId;
    private final String statusId;
    private final String recipient;
    private final String registrationDate;

    public OTPLogObject(String otpId, String statusId, String recipient, String registrationDate) {
        this.otpId = otpId;
        this.statusId = statusId;
        this.recipient = recipient;
        this.registrationDate = registrationDate;
    }

    public String getOtpId() {
        return otpId;
    }

    public String getStatusId() {
        return statusId;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }
    
}
