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
@Table(name = "tbl_otp_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblOtpLog.findAll", query = "SELECT t FROM TblOtpLog t")
    , @NamedQuery(name = "TblOtpLog.findByIndx", query = "SELECT t FROM TblOtpLog t WHERE t.indx = :indx")})
public class TblOtpLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "indx")
    private Integer indx;
    @Lob
    @Size(max = 65535)
    @Column(name = "otpId")
    private String otpId;
    @Lob
    @Size(max = 65535)
    @Column(name = "statusId")
    private String statusId;
    @Lob
    @Size(max = 65535)
    @Column(name = "recipient")
    private String recipient;
    @Lob
    @Size(max = 65535)
    @Column(name = "registrationDate")
    private String registrationDate;

    public TblOtpLog() {
    }

    public TblOtpLog(Integer indx) {
        this.indx = indx;
    }

    public Integer getIndx() {
        return indx;
    }

    public void setIndx(Integer indx) {
        this.indx = indx;
    }

    public String getOtpId() {
        return otpId;
    }

    public void setOtpId(String otpId) {
        this.otpId = otpId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
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
        if (!(object instanceof TblOtpLog)) {
            return false;
        }
        TblOtpLog other = (TblOtpLog) object;
        if ((this.indx == null && other.indx != null) || (this.indx != null && !this.indx.equals(other.indx))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fidar.hibernate.entity.TblOtpLog[ indx=" + indx + " ]";
    }
    
}
