/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.queue.object;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author alirzea
 */
public class NotificationObject {
    private String text;
    private String keyword;
    private String channel;
    private String from;
    private String to;
    private String notificationId;
    private String userId;
    private String dateTime;

    public NotificationObject(String text, String keyword, String channel, String from, String to, String notificationId, String userId) {
        this.text = text;
        this.keyword = keyword;
        this.channel = channel;
        this.from = from;
        this.to = to;
        this.notificationId = notificationId;
        this.userId = userId;
        this.dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
}
