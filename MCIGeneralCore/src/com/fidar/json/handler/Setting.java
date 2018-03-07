/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.json.handler;

/**
 *
 * @author alirezakhtm
 */
public class Setting {
    private double delay;
    private String DBName;
    private String DBUsername;
    private String DBPassword;
    private String ActiveMQUsername;
    private String ActiveMQPassword;
    private String WSUserId;
    private String WSPassword;

    public double getDelay() {
        return delay;
    }

    public void setDelay(double delay) {
        this.delay = delay;
    }

    public String getDBName() {
        return DBName;
    }

    public void setDBName(String DBName) {
        this.DBName = DBName;
    }

    public String getDBUsername() {
        return DBUsername;
    }

    public void setDBUsername(String DBUsername) {
        this.DBUsername = DBUsername;
    }

    public String getDBPassword() {
        return DBPassword;
    }

    public void setDBPassword(String DBPassword) {
        this.DBPassword = DBPassword;
    }

    public String getActiveMQUsername() {
        return ActiveMQUsername;
    }

    public void setActiveMQUsername(String ActiveMQUsername) {
        this.ActiveMQUsername = ActiveMQUsername;
    }

    public String getActiveMQPassword() {
        return ActiveMQPassword;
    }

    public void setActiveMQPassword(String ActiveMQPassword) {
        this.ActiveMQPassword = ActiveMQPassword;
    }

    public String getWSUserId() {
        return WSUserId;
    }

    public void setWSUserId(String WSUserId) {
        this.WSUserId = WSUserId;
    }

    public String getWSPassword() {
        return WSPassword;
    }

    public void setWSPassword(String WSPassword) {
        this.WSPassword = WSPassword;
    }
    
}
