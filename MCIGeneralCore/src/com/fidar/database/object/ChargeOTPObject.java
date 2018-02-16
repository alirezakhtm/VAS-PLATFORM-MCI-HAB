/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.database.object;

/**
 *
 * @author alireza
 */
public class ChargeOTPObject {
    private String msisdn;
    private String shortCode;
    private String serviceCode;
    private String requestDate;
    private String pinCode;
    private String otpId;

    public ChargeOTPObject(String msisdn, String shortCode, String serviceCode, String requestDate, String pinCode, String otpId) {
        this.msisdn = msisdn;
        this.shortCode = shortCode;
        this.serviceCode = serviceCode;
        this.requestDate = requestDate;
        this.pinCode = pinCode;
        this.otpId = otpId;
    }

    public ChargeOTPObject() {
    }
    
    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getOtpId() {
        return otpId;
    }

    public void setOtpId(String otpId) {
        this.otpId = otpId;
    }
    
}
