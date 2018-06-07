/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khtm.object;

/**
 *
 * @author alireza
 */
public class ServiceUserObj {
    private int serviceCode;
    private String msisdn;
    private int subStatus;
    private String membershipDate;
    private String unsubDate;
    private int selctedCategoryIndex;
    private String unsubBy;
    private String subBy;
    private String numberType;

    public ServiceUserObj() {
    }

    public ServiceUserObj(int serviceCode, String msisdn, int subStatus, String membershipDate, String unsubDate, int selctedCategoryIndex, String unsubBy, String subBy, String numberType) {
        this.serviceCode = serviceCode;
        this.msisdn = msisdn;
        this.subStatus = subStatus;
        this.membershipDate = membershipDate;
        this.unsubDate = unsubDate;
        this.selctedCategoryIndex = selctedCategoryIndex;
        this.unsubBy = unsubBy;
        this.subBy = subBy;
        this.numberType = numberType;
    }
    
    public int getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public int getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(int subStatus) {
        this.subStatus = subStatus;
    }

    public String getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(String membershipDate) {
        this.membershipDate = membershipDate;
    }

    public String getUnsubDate() {
        return unsubDate;
    }

    public void setUnsubDate(String unsubDate) {
        this.unsubDate = unsubDate;
    }

    public int getSelctedCategoryIndex() {
        return selctedCategoryIndex;
    }

    public void setSelctedCategoryIndex(int selctedCategoryIndex) {
        this.selctedCategoryIndex = selctedCategoryIndex;
    }

    public String getUnsubBy() {
        return unsubBy;
    }

    public void setUnsubBy(String unsubBy) {
        this.unsubBy = unsubBy;
    }

    public String getSubBy() {
        return subBy;
    }

    public void setSubBy(String subBy) {
        this.subBy = subBy;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }
}
