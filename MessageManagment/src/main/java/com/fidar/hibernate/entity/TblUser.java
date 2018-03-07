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
@Table(name = "tbl_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblUser.findAll", query = "SELECT t FROM TblUser t")
    , @NamedQuery(name = "TblUser.findByIndx", query = "SELECT t FROM TblUser t WHERE t.indx = :indx")
    , @NamedQuery(name = "TblUser.findByUPeriority", query = "SELECT t FROM TblUser t WHERE t.uPeriority = :uPeriority")})
public class TblUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "indx")
    private Integer indx;
    @Lob
    @Size(max = 65535)
    @Column(name = "u_username")
    private String uUsername;
    @Lob
    @Size(max = 65535)
    @Column(name = "u_password")
    private String uPassword;
    @Column(name = "u_periority")
    private Integer uPeriority;

    public TblUser() {
    }

    public TblUser(Integer indx) {
        this.indx = indx;
    }

    public Integer getIndx() {
        return indx;
    }

    public void setIndx(Integer indx) {
        this.indx = indx;
    }

    public String getUUsername() {
        return uUsername;
    }

    public void setUUsername(String uUsername) {
        this.uUsername = uUsername;
    }

    public String getUPassword() {
        return uPassword;
    }

    public void setUPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public Integer getUPeriority() {
        return uPeriority;
    }

    public void setUPeriority(Integer uPeriority) {
        this.uPeriority = uPeriority;
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
        if (!(object instanceof TblUser)) {
            return false;
        }
        TblUser other = (TblUser) object;
        if ((this.indx == null && other.indx != null) || (this.indx != null && !this.indx.equals(other.indx))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fidar.hibernate.entity.TblUser[ indx=" + indx + " ]";
    }
    
}
