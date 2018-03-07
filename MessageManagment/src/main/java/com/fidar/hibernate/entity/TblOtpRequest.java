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
@Table(name = "tbl_otp_request")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblOtpRequest.findAll", query = "SELECT t FROM TblOtpRequest t")
    , @NamedQuery(name = "TblOtpRequest.findByIndx", query = "SELECT t FROM TblOtpRequest t WHERE t.indx = :indx")
    , @NamedQuery(name = "TblOtpRequest.findByServiceCode", query = "SELECT t FROM TblOtpRequest t WHERE t.serviceCode = :serviceCode")})
public class TblOtpRequest implements Serializable {

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
    @Lob
    @Size(max = 65535)
    @Column(name = "status")
    private String status;
    @Column(name = "serviceCode")
    private Integer serviceCode;
    @Lob
    @Size(max = 65535)
    @Column(name = "requestDate")
    private String requestDate;
    @Lob
    @Size(max = 65535)
    @Column(name = "otpId")
    private String otpId;

    public TblOtpRequest() {
    }

    public TblOtpRequest(Integer indx) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(object instanceof TblOtpRequest)) {
            return false;
        }
        TblOtpRequest other = (TblOtpRequest) object;
        if ((this.indx == null && other.indx != null) || (this.indx != null && !this.indx.equals(other.indx))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fidar.hibernate.entity.TblOtpRequest[ indx=" + indx + " ]";
    }
    
}
