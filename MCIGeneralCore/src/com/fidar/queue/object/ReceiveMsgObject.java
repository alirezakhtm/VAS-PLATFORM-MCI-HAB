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
public class ReceiveMsgObject {
    private String text;
    private String from;
    private String to;
    private String smsId;
    private String userid;

    public ReceiveMsgObject(String text, String from, String to, String smsId, String userid) {
        this.text = text;
        this.from = from;
        this.to = to;
        this.smsId = smsId;
        this.userid = userid;
    }

    public ReceiveMsgObject() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    
}
