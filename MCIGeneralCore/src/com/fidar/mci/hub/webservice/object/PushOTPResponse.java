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
@XmlRootElement(name = "xmsresponse")
public class PushOTPResponse {
    private String userId;
    private String action;
    private CodePushOTPResponse code;
    private BodyPushOTPResponse body;

    public String getUserId() {
        return userId;
    }

    @XmlElement(name = "userid")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    @XmlElement(name = "action")
    public void setAction(String action) {
        this.action = action;
    }

    public CodePushOTPResponse getCode() {
        return code;
    }

    @XmlElement(name = "code")
    public void setCode(CodePushOTPResponse code) {
        this.code = code;
    }

    public BodyPushOTPResponse getBody() {
        return body;
    }

    @XmlElement(name = "body")
    public void setBody(BodyPushOTPResponse body) {
        this.body = body;
    }
    
}
