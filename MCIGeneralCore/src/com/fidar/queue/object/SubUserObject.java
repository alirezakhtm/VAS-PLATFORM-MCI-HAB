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
public class SubUserObject {
    private final int serviceCode;
    private final ReceiveMsgObject receiveMsgObject;

    public SubUserObject(int serviceCode, NotificationObject notificationObject) {
        this.serviceCode = serviceCode;
        this.receiveMsgObject = new ReceiveMsgObject(
                notificationObject.getText(), 
                notificationObject.getFrom(),
                notificationObject.getTo(), 
                notificationObject.getNotificationId(),
                notificationObject.getUserId());
    }
    
    public SubUserObject(int serviceCode, ReceiveMsgObject receiveMsgObject) {
        this.serviceCode = serviceCode;
        this.receiveMsgObject = receiveMsgObject;
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public ReceiveMsgObject getReceiveMsgObject() {
        return receiveMsgObject;
    }
    
    
}
