/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.hibernate.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alireza
 */
@Entity
@Table(name = "tbl_charge_otp_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblChargeOtpLog.findAll", query = "SELECT t FROM TblChargeOtpLog t")
    , @NamedQuery(name = "TblChargeOtpLog.findByIndx", query = "SELECT t FROM TblChargeOtpLog t WHERE t.indx = :indx")
    , @NamedQuery(name = "TblChargeOtpLog.findByServiceCode", query = "SELECT t FROM TblChargeOtpLog t WHERE t.serviceCode = :serviceCode")})
public class TblChargeOtpLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "indx")
    private Integer indx;
    @Lob
    @Size(max = 65535)
    @Column(name = "msisdn")
    private String msisdn;
    @Lob
    @Size(max = 65535)
    @Column(name = "shortCode")
    private String shortCode;
    @Column(name = "serviceCode")
    private Integer serviceCode;
    @Lob
    @Size(max = 65535)
    @Column(name = "requestDate")
    private String requestDate;
    @Lob
    @Size(max = 65535)
    @Column(name = "pinCode")
    private String pinCode;
    @Lob
    @Size(max = 65535)
    @Column(name = "otpId")
    private String otpId;

    public TblChargeOtpLog() {
    }

    public TblChargeOtpLog(Integer indx) {
        this.indx = indx;
    }

    public Integer getIndx() {
        return indx;
    }

    public void setIndx(Integer indx) {
        this.indx = indx;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public Integer getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(Integer serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getOtpId() {
        return otpId;
    }

    public void setOtpId(String otpId) {
        this.otpId = otpId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (indx != null ? indx.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblChargeOtpLog)) {
            return false;
        }
        TblChargeOtpLog other = (TblChargeOtpLog) object;
        if ((this.indx == null && other.indx != null) || (this.indx != null && !this.indx.equals(other.indx))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fidar.hibernate.entity.TblChargeOtpLog[ indx=" + indx + " ]";
    }
    
}
