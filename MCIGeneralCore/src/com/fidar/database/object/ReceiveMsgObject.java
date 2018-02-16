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
public class ReceiveMsgObject {
    private final String text;
    private final String from;
    private final String to;
    private final String smsId;
    private final String userId;

    public ReceiveMsgObject(String text, String from, String to, String smsId, String userId) {
        this.text = text;
        this.from = from;
        this.to = to;
        this.smsId = smsId;
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSmsId() {
        return smsId;
    }

    public String getUserId() {
        return userId;
    }
    
}
