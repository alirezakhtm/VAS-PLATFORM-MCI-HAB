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
@Table(name = "tbl_serviceusers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblServiceusers.findAll", query = "SELECT t FROM TblServiceusers t")
    , @NamedQuery(name = "TblServiceusers.findByServiceCode", query = "SELECT t FROM TblServiceusers t WHERE t.serviceCode = :serviceCode")
    , @NamedQuery(name = "TblServiceusers.findByMsisdn", query = "SELECT t FROM TblServiceusers t WHERE t.msisdn = :msisdn")
    , @NamedQuery(name = "TblServiceusers.findBySubStatus", query = "SELECT t FROM TblServiceusers t WHERE t.subStatus = :subStatus")
    , @NamedQuery(name = "TblServiceusers.findByFldIndex", query = "SELECT t FROM TblServiceusers t WHERE t.fldIndex = :fldIndex")
    , @NamedQuery(name = "TblServiceusers.findBySelectedCategoryIndx", query = "SELECT t FROM TblServiceusers t WHERE t.selectedCategoryIndx = :selectedCategoryIndx")
    , @NamedQuery(name = "TblServiceusers.findByUnsubscribedBy", query = "SELECT t FROM TblServiceusers t WHERE t.unsubscribedBy = :unsubscribedBy")
    , @NamedQuery(name = "TblServiceusers.findBySubscribedBy", query = "SELECT t FROM TblServiceusers t WHERE t.subscribedBy = :subscribedBy")
    , @NamedQuery(name = "TblServiceusers.findByNumberType", query = "SELECT t FROM TblServiceusers t WHERE t.numberType = :numberType")})
public class TblServiceusers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "ServiceCode")
    private Integer serviceCode;
    @Size(max = 15)
    @Column(name = "MSISDN")
    private String msisdn;
    @Column(name = "SubStatus")
    private Integer subStatus;
    @Lob
    @Size(max = 65535)
    @Column(name = "MembershipDate")
    private String membershipDate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fldIndex")
    private Integer fldIndex;
    @Lob
    @Size(max = 65535)
    @Column(name = "UnSubscribeDate")
    private String unSubscribeDate;
    @Column(name = "selectedCategoryIndx")
    private Integer selectedCategoryIndx;
    @Column(name = "unsubscribedBy")
    private Integer unsubscribedBy;
    @Column(name = "subscribedBy")
    private Integer subscribedBy;
    @Size(max = 45)
    @Column(name = "numberType")
    private String numberType;

    public TblServiceusers() {
    }

    public TblServiceusers(Integer fldIndex) {
        this.fldIndex = fldIndex;
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

    public Integer getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }

    public String getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(String membershipDate) {
        this.membershipDate = membershipDate;
    }

    public Integer getFldIndex() {
        return fldIndex;
    }

    public void setFldIndex(Integer fldIndex) {
        this.fldIndex = fldIndex;
    }

    public String getUnSubscribeDate() {
        return unSubscribeDate;
    }

    public void setUnSubscribeDate(String unSubscribeDate) {
        this.unSubscribeDate = unSubscribeDate;
    }

    public Integer getSelectedCategoryIndx() {
        return selectedCategoryIndx;
    }

    public void setSelectedCategoryIndx(Integer selectedCategoryIndx) {
        this.selectedCategoryIndx = selectedCategoryIndx;
    }

    public Integer getUnsubscribedBy() {
        return unsubscribedBy;
    }

    public void setUnsubscribedBy(Integer unsubscribedBy) {
        this.unsubscribedBy = unsubscribedBy;
    }

    public Integer getSubscribedBy() {
        return subscribedBy;
    }

    public void setSubscribedBy(Integer subscribedBy) {
        this.subscribedBy = subscribedBy;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fldIndex != null ? fldIndex.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblServiceusers)) {
            return false;
        }
        TblServiceusers other = (TblServiceusers) object;
        if ((this.fldIndex == null && other.fldIndex != null) || (this.fldIndex != null && !this.fldIndex.equals(other.fldIndex))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fidar.hibernate.entity.TblServiceusers[ fldIndex=" + fldIndex + " ]";
    }
    
}
