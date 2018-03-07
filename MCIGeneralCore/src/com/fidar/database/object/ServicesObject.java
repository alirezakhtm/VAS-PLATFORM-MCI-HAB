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
public class ServicesObject {
    private final String ServiceCode;
    private final String servicename;
    private final String ShortCode;
    private final String serviceId;
    private final String welcomeMT;

    public ServicesObject(String ServiceCode, String servicename, String ShortCode, String serviceId, String welcomeMT) {
        this.ServiceCode = ServiceCode;
        this.servicename = servicename;
        this.ShortCode = ShortCode;
        this.serviceId = serviceId;
        this.welcomeMT = welcomeMT;
    }

    public String getServiceCode() {
        return ServiceCode;
    }

    public String getServicename() {
        return servicename;
    }

    public String getShortCode() {
        return ShortCode;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getWelcomeMT() {
        return welcomeMT;
    }
}
