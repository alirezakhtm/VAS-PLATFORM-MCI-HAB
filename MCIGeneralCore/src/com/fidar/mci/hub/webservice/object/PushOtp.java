/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.mci.hub.webservice.object;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alirezakhtm
 */
@XmlRootElement(name = "xmsrequest")
public class PushOtp {
    private String username;
    private String password;
    private String action;
    private BodyPushOTP body;

    public String getUsername() {
        return username;
    }

    @XmlElement(name = "userid")
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement(name = "password")
    public void setPassword(String password) {
        this.password = password;
    }

    public String getAction() {
        return action;
    }

    @XmlElement(name = "action")
    public void setAction(String action) {
        this.action = action;
    }

    public BodyPushOTP getBody() {
        return body;
    }

    @XmlElement(name = "body")
    public void setBody(BodyPushOTP body) {
        this.body = body;
    }
    
}




