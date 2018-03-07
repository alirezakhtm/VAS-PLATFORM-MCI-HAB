/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.database.object;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author alirezakhtm
 */
public class ServicesUser {
    private int serviceCode;
    private String msisdn;
    private int substatus;
    private final String membershipDate;
    private final String unsubscribeDate;

    public ServicesUser(int serviceCode, String msisdn, int substatus) {
        this.serviceCode = serviceCode;
        this.msisdn = msisdn;
        this.substatus = substatus;
        this.membershipDate = (substatus == 1) ?
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) :
                "null";
        this.unsubscribeDate = (substatus == 0) ? 
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) :
                "null";
    }

    public ServicesUser(int serviceCode, String msisdn, int substatus, String membershipDate,
            String unsubscribeDate) {
        this.serviceCode = serviceCode;
        this.msisdn = msisdn;
        this.substatus = substatus;
        this.membershipDate = membershipDate;
        this.unsubscribeDate = unsubscribeDate;
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public int getSubstatus() {
        return substatus;
    }

    public void setSubstatus(int substatus) {
        this.substatus = substatus;
    }

    public String getMembershipDate() {
        return membershipDate;
    }

    public String getUnsubscribeDate() {
        return unsubscribeDate;
    }
    
}
