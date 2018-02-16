/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.mci.hub.webservice.object;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alirezakhtm
 */
@XmlRootElement(name = "recipient")
public class RecipientPushOtp {
    private String originator;
    private String mobile;
    private String cost;
    private String doerid;

    public String getOriginator() {
        return originator;
    }

    @XmlAttribute(name = "originator")
    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public String getMobile() {
        return mobile;
    }

    @XmlAttribute(name = "mobile")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCost() {
        return cost;
    }

    @XmlAttribute(name = "cost")
    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDoerid() {
        return doerid;
    }

    @XmlAttribute(name = "doerid")
    public void setDoerid(String doerid) {
        this.doerid = doerid;
    }   
}