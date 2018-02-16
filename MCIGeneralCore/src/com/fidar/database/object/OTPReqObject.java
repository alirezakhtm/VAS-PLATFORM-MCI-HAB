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
public class OTPReqObject {
    private String msisdn;
    private String shortCode;
    private String status;
    private String serviceCode;
    private String requestDate;
    private String otpId;

    public OTPReqObject(String msisdn, String shortCode, String status, String serviceCode, String requestDate, String otpId) {
        this.msisdn = msisdn;
        this.shortCode = shortCode;
        this.status = status;
        this.serviceCode = serviceCode;
        this.requestDate = requestDate;
        this.otpId = otpId;
    }

    public OTPReqObject() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getOtpId() {
        return otpId;
    }

    public void setOtpId(String otpId) {
        this.otpId = otpId;
    }
}
