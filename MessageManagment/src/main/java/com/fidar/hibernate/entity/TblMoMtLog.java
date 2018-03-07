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
@Table(name = "tbl_mo_mt_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblMoMtLog.findAll", query = "SELECT t FROM TblMoMtLog t")
    , @NamedQuery(name = "TblMoMtLog.findByIndx", query = "SELECT t FROM TblMoMtLog t WHERE t.indx = :indx")
    , @NamedQuery(name = "TblMoMtLog.findByServiceCode", query = "SELECT t FROM TblMoMtLog t WHERE t.serviceCode = :serviceCode")})
public class TblMoMtLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "indx")
    private Integer indx;
    @Column(name = "serviceCode")
    private Integer serviceCode;
    @Lob
    @Size(max = 65535)
    @Column(name = "msisdn")
    private String msisdn;
    @Lob
    @Size(max = 65535)
    @Column(name = "mo")
    private String mo;
    @Lob
    @Size(max = 65535)
    @Column(name = "mt")
    private String mt;
    @Lob
    @Size(max = 65535)
    @Column(name = "registrationDate")
    private String registrationDate;

    public TblMoMtLog() {
    }

    public TblMoMtLog(Integer indx) {
        this.indx = indx;
    }

    public Integer getIndx() {
        return indx;
    }

    public void setIndx(Integer indx) {
        this.indx = indx;
    }

    public Integer getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(Integer serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getMo() {
        return mo;
    }

    public void setMo(String mo) {
        this.mo = mo;
    }

    public String getMt() {
        return mt;
    }

    public void setMt(String mt) {
        this.mt = mt;
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
        if (!(object instanceof TblMoMtLog)) {
            return false;
        }
        TblMoMtLog other = (TblMoMtLog) object;
        if ((this.indx == null && other.indx != null) || (this.indx != null && !this.indx.equals(other.indx))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fidar.hibernate.entity.TblMoMtLog[ indx=" + indx + " ]";
    }
    
}
