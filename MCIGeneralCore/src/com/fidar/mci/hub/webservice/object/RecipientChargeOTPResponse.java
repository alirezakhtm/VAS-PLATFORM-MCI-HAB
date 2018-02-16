/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.mci.hub.webservice.object;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author alireza
 */
@XmlRootElement(name = "recipient")
public class RecipientChargeOTPResponse {
    private String mobile;
    private String doerId;
    private String status;
    private String value;

    public String getMobile() {
        return mobile;
    }

    @XmlAttribute(name = "mobile")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDoerId() {
        return doerId;
    }

    @XmlAttribute(name = "doerid")
    public void setDoerId(String doerId) {
        this.doerId = doerId;
    }

    public String getStatus() {
        return status;
    }

    @XmlAttribute(name = "status")
    public void setStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    @XmlValue
    public void setValue(String value) {
        this.value = value;
    }
}
