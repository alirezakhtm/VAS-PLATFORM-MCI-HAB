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
@Table(name = "tbl_receive_msg")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblReceiveMsg.findAll", query = "SELECT t FROM TblReceiveMsg t")
    , @NamedQuery(name = "TblReceiveMsg.findByIndx", query = "SELECT t FROM TblReceiveMsg t WHERE t.indx = :indx")})
public class TblReceiveMsg implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "indx")
    private Integer indx;
    @Lob
    @Size(max = 65535)
    @Column(name = "text")
    private String text;
    @Lob
    @Size(max = 65535)
    @Column(name = "from")
    private String from;
    @Lob
    @Size(max = 65535)
    @Column(name = "to")
    private String to;
    @Lob
    @Size(max = 65535)
    @Column(name = "smsID")
    private String smsID;
    @Lob
    @Size(max = 65535)
    @Column(name = "userID")
    private String userID;

    public TblReceiveMsg() {
    }

    public TblReceiveMsg(Integer indx) {
        this.indx = indx;
    }

    public Integer getIndx() {
        return indx;
    }

    public void setIndx(Integer indx) {
        this.indx = indx;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSmsID() {
        return smsID;
    }

    public void setSmsID(String smsID) {
        this.smsID = smsID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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
        if (!(object instanceof TblReceiveMsg)) {
            return false;
        }
        TblReceiveMsg other = (TblReceiveMsg) object;
        if ((this.indx == null && other.indx != null) || (this.indx != null && !this.indx.equals(other.indx))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fidar.hibernate.entity.TblReceiveMsg[ indx=" + indx + " ]";
    }
    
}
