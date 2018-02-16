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
public class MOMTLogObject {
    private final int serviceCode;
    private final String msisdn;
    private final String mo;
    private final String mt;
    private final String registrationDate;

    public MOMTLogObject(int serviceCode, String msisdn, String mo, String mt, String registrationDate) {
        this.serviceCode = serviceCode;
        this.msisdn = msisdn;
        this.mo = mo;
        this.mt = mt;
        this.registrationDate = registrationDate;
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getMo() {
        return mo;
    }

    public String getMt() {
        return mt;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }
    
}
