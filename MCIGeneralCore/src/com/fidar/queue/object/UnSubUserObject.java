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
public class UnSubUserObject {
    private final int serviceCode;
    private final ReceiveMsgObject receiveMsgObject;

    public UnSubUserObject(int serviceCode, ReceiveMsgObject receiveMsgObject) {
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
