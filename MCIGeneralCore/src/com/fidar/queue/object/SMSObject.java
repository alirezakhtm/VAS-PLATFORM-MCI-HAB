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
public class SMSObject {
    private String serivceCode;
    private String msisdn;
    private String txtMessage;

    public SMSObject(String serivceCode, String msisdn, String txtMessage) {
        this.serivceCode = serivceCode;
        this.msisdn = msisdn;
        this.txtMessage = txtMessage;
    }
    
    public String getSerivceCode() {
        return serivceCode;
    }

    public void setSerivceCode(String serivceCode) {
        this.serivceCode = serivceCode;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getTxtMessage() {
        return txtMessage;
    }

    public void setTxtMessage(String txtMessage) {
        this.txtMessage = txtMessage;
    }
    
}
