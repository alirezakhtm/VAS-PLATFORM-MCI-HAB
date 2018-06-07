/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khtm.object;

import java.util.List;

/**
 *
 * @author alireza
 */
public class BulkObject {
    private List<String> lstPhone;
    private String message;
    private String serviceCode;

    public BulkObject(List<String> lstPhone, String message, String serviceCode) {
        this.lstPhone = lstPhone;
        this.message = message;
        this.serviceCode = serviceCode;
    }
    
    public List<String> getLstPhone() {
        return lstPhone;
    }

    public void setLstPhone(List<String> lstPhone) {
        this.lstPhone = lstPhone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
}
