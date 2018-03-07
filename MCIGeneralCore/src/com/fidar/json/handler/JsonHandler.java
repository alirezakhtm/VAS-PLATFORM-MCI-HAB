/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.json.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author alirezakhtm
 */
public class JsonHandler {
    private final double delay;
    private final String DBName;
    private final String DBUsername;
    private final String DBPassword;
    private final String ActiveMQUsername;
    private final String ActiveMQPassword;
    private final String WSUserId;
    private final String WSPassword;

    public JsonHandler() {
        String strJson = "";
        try{
            InputStream inputStream = 
                    JsonHandler.class.getResourceAsStream("/com/fidar/json/handler/setting.json");
            byte[] buffer = new byte[1024*3];
            inputStream.read(buffer, 0, buffer.length);
            strJson = new String(buffer).replace((char)((byte)0x00), '*').replace("*", "");
        }catch(IOException e){
            System.err.println("JsonHandler - Constructur - 01 : " + e);
        }
        Gson gson = new GsonBuilder().create();
        Setting setting = gson.fromJson(strJson, Setting.class);
        this.delay = setting.getDelay();
        this.DBName = setting.getDBName();
        this.DBUsername = setting.getDBUsername();
        this.DBPassword = setting.getDBPassword();
        this.ActiveMQUsername = setting.getActiveMQUsername();
        this.ActiveMQPassword = setting.getActiveMQPassword();
        this.WSUserId = setting.getWSUserId();
        this.WSPassword = setting.getWSPassword();
    }

    public double getDelay() {
        return delay;
    }

    public String getDBName() {
        return DBName;
    }

    public String getDBUsername() {
        return DBUsername;
    }

    public String getDBPassword() {
        return DBPassword;
    }

    public String getActiveMQUsername() {
        return ActiveMQUsername;
    }

    public String getActiveMQPassword() {
        return ActiveMQPassword;
    }

    public String getWSUserId() {
        return WSUserId;
    }

    public String getWSPassword() {
        return WSPassword;
    }
    
}
