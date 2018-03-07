/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.hibernate.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alireza
 */
@Entity
@Table(name = "tbl_services")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblServices.findAll", query = "SELECT t FROM TblServices t")
    , @NamedQuery(name = "TblServices.findByServiceCode", query = "SELECT t FROM TblServices t WHERE t.serviceCode = :serviceCode")
    , @NamedQuery(name = "TblServices.findByServicename", query = "SELECT t FROM TblServices t WHERE t.servicename = :servicename")
    , @NamedQuery(name = "TblServices.findByShortCode", query = "SELECT t FROM TblServices t WHERE t.shortCode = :shortCode")
    , @NamedQuery(name = "TblServices.findByChannel", query = "SELECT t FROM TblServices t WHERE t.channel = :channel")
    , @NamedQuery(name = "TblServices.findByLaunchDate", query = "SELECT t FROM TblServices t WHERE t.launchDate = :launchDate")
    , @NamedQuery(name = "TblServices.findByRegKeyword", query = "SELECT t FROM TblServices t WHERE t.regKeyword = :regKeyword")
    , @NamedQuery(name = "TblServices.findByServiceStatus", query = "SELECT t FROM TblServices t WHERE t.serviceStatus = :serviceStatus")
    , @NamedQuery(name = "TblServices.findByUnregKeyword", query = "SELECT t FROM TblServices t WHERE t.unregKeyword = :unregKeyword")
    , @NamedQuery(name = "TblServices.findByPrice", query = "SELECT t FROM TblServices t WHERE t.price = :price")
    , @NamedQuery(name = "TblServices.findByHamrahSID", query = "SELECT t FROM TblServices t WHERE t.hamrahSID = :hamrahSID")
    , @NamedQuery(name = "TblServices.findByMTShortCode", query = "SELECT t FROM TblServices t WHERE t.mTShortCode = :mTShortCode")
    , @NamedQuery(name = "TblServices.findByChargingMethod", query = "SELECT t FROM TblServices t WHERE t.chargingMethod = :chargingMethod")
    , @NamedQuery(name = "TblServices.findByInteractiveKey", query = "SELECT t FROM TblServices t WHERE t.interactiveKey = :interactiveKey")
    , @NamedQuery(name = "TblServices.findByIsInteractive", query = "SELECT t FROM TblServices t WHERE t.isInteractive = :isInteractive")
    , @NamedQuery(name = "TblServices.findByIsCompetition", query = "SELECT t FROM TblServices t WHERE t.isCompetition = :isCompetition")
    , @NamedQuery(name = "TblServices.findByAnswkey", query = "SELECT t FROM TblServices t WHERE t.answkey = :answkey")
    , @NamedQuery(name = "TblServices.findByCompHelpKey", query = "SELECT t FROM TblServices t WHERE t.compHelpKey = :compHelpKey")
    , @NamedQuery(name = "TblServices.findByCompPointKey", query = "SELECT t FROM TblServices t WHERE t.compPointKey = :compPointKey")
    , @NamedQuery(name = "TblServices.findByCompChangeCatKey", query = "SELECT t FROM TblServices t WHERE t.compChangeCatKey = :compChangeCatKey")
    , @NamedQuery(name = "TblServices.findByUser", query = "SELECT t FROM TblServices t WHERE t.user = :user")
    , @NamedQuery(name = "TblServices.findByPass", query = "SELECT t FROM TblServices t WHERE t.pass = :pass")
    , @NamedQuery(name = "TblServices.findByImiServiceKey", query = "SELECT t FROM TblServices t WHERE t.imiServiceKey = :imiServiceKey")
    , @NamedQuery(name = "TblServices.findByImiShortCodeDefaultServiceKey", query = "SELECT t FROM TblServices t WHERE t.imiShortCodeDefaultServiceKey = :imiShortCodeDefaultServiceKey")
    , @NamedQuery(name = "TblServices.findBySdp", query = "SELECT t FROM TblServices t WHERE t.sdp = :sdp")
    , @NamedQuery(name = "TblServices.findBySubChargeCode", query = "SELECT t FROM TblServices t WHERE t.subChargeCode = :subChargeCode")
    , @NamedQuery(name = "TblServices.findByUnSubChargeCode", query = "SELECT t FROM TblServices t WHERE t.unSubChargeCode = :unSubChargeCode")
    , @NamedQuery(name = "TblServices.findByHasSilentCharge", query = "SELECT t FROM TblServices t WHERE t.hasSilentCharge = :hasSilentCharge")
    , @NamedQuery(name = "TblServices.findByGhestiPriceLimitPerDay", query = "SELECT t FROM TblServices t WHERE t.ghestiPriceLimitPerDay = :ghestiPriceLimitPerDay")
    , @NamedQuery(name = "TblServices.findByIsWappush", query = "SELECT t FROM TblServices t WHERE t.isWappush = :isWappush")
    , @NamedQuery(name = "TblServices.findByServicemodel", query = "SELECT t FROM TblServices t WHERE t.servicemodel = :servicemodel")
    , @NamedQuery(name = "TblServices.findByCategory", query = "SELECT t FROM TblServices t WHERE t.category = :category")
    , @NamedQuery(name = "TblServices.findByIsCommercial", query = "SELECT t FROM TblServices t WHERE t.isCommercial = :isCommercial")
    , @NamedQuery(name = "TblServices.findByPartnerOTPURL", query = "SELECT t FROM TblServices t WHERE t.partnerOTPURL = :partnerOTPURL")
    , @NamedQuery(name = "TblServices.findByPartnerId", query = "SELECT t FROM TblServices t WHERE t.partnerId = :partnerId")})
public class TblServices implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ServiceCode")
    private Integer serviceCode;
    @Size(max = 255)
    @Column(name = "servicename")
    private String servicename;
    @Size(max = 10)
    @Column(name = "ShortCode")
    private String shortCode;
    @Size(max = 10)
    @Column(name = "Channel")
    private String channel;
    @Column(name = "LaunchDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date launchDate;
    @Size(max = 200)
    @Column(name = "RegKeyword")
    private String regKeyword;
    @Column(name = "ServiceStatus")
    private Boolean serviceStatus;
    @Size(max = 200)
    @Column(name = "unregKeyword")
    private String unregKeyword;
    @Size(max = 6)
    @Column(name = "price")
    private String price;
    @Size(max = 200)
    @Column(name = "HamrahSID")
    private String hamrahSID;
    @Size(max = 10)
    @Column(name = "MT_ShortCode")
    private String mTShortCode;
    @Size(max = 5)
    @Column(name = "ChargingMethod")
    private String chargingMethod;
    @Size(max = 255)
    @Column(name = "InteractiveKey")
    private String interactiveKey;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "InteractiveMessage")
    private String interactiveMessage;
    @Column(name = "isInteractive")
    private Boolean isInteractive;
    @Column(name = "isCompetition")
    private Boolean isCompetition;
    @Size(max = 255)
    @Column(name = "answkey")
    private String answkey;
    @Size(max = 255)
    @Column(name = "compHelpKey")
    private String compHelpKey;
    @Size(max = 255)
    @Column(name = "compPointKey")
    private String compPointKey;
    @Size(max = 255)
    @Column(name = "compChangeCatKey")
    private String compChangeCatKey;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "compHelpMt")
    private String compHelpMt;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "compPointMt")
    private String compPointMt;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "compChangeCatMT")
    private String compChangeCatMT;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "regMt")
    private String regMt;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "unRegMt")
    private String unRegMt;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "existRegMt")
    private String existRegMt;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "existUnRegMt")
    private String existUnRegMt;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "HelpMt")
    private String helpMt;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "NotValidMt")
    private String notValidMt;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "NotRegYetMt")
    private String notRegYetMt;
    @Size(max = 255)
    @Column(name = "user")
    private String user;
    @Size(max = 255)
    @Column(name = "pass")
    private String pass;
    @Size(max = 255)
    @Column(name = "imiServiceKey")
    private String imiServiceKey;
    @Size(max = 255)
    @Column(name = "imiShortCodeDefaultServiceKey")
    private String imiShortCodeDefaultServiceKey;
    @Size(max = 45)
    @Column(name = "sdp")
    private String sdp;
    @Size(max = 255)
    @Column(name = "subChargeCode")
    private String subChargeCode;
    @Size(max = 255)
    @Column(name = "unSubChargeCode")
    private String unSubChargeCode;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "reminderMT")
    private String reminderMT;
    @Column(name = "hasSilentCharge")
    private Boolean hasSilentCharge;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "SilentChargeChargeCodes")
    private String silentChargeChargeCodes;
    @Column(name = "GhestiPriceLimitPerDay")
    private Short ghestiPriceLimitPerDay;
    @Column(name = "isWappush")
    private Boolean isWappush;
    @Size(max = 255)
    @Column(name = "servicemodel")
    private String servicemodel;
    @Size(max = 255)
    @Column(name = "category")
    private String category;
    @Column(name = "isCommercial")
    private Boolean isCommercial;
    @Size(max = 50)
    @Column(name = "PartnerOTPURL")
    private String partnerOTPURL;
    @Size(max = 50)
    @Column(name = "PartnerId")
    private String partnerId;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "blankInputtxt")
    private String blankInputtxt;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "invalidInputtxt")
    private String invalidInputtxt;
    @Lob
    @Size(max = 65535)
    @Column(name = "serviceId")
    private String serviceId;
    @Lob
    @Size(max = 65535)
    @Column(name = "welcomeMT")
    private String welcomeMT;

    public TblServices() {
    }

    public TblServices(Integer serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Integer getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(Integer serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public String getRegKeyword() {
        return regKeyword;
    }

    public void setRegKeyword(String regKeyword) {
        this.regKeyword = regKeyword;
    }

    public Boolean getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(Boolean serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getUnregKeyword() {
        return unregKeyword;
    }

    public void setUnregKeyword(String unregKeyword) {
        this.unregKeyword = unregKeyword;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHamrahSID() {
        return hamrahSID;
    }

    public void setHamrahSID(String hamrahSID) {
        this.hamrahSID = hamrahSID;
    }

    public String getMTShortCode() {
        return mTShortCode;
    }

    public void setMTShortCode(String mTShortCode) {
        this.mTShortCode = mTShortCode;
    }

    public String getChargingMethod() {
        return chargingMethod;
    }

    public void setChargingMethod(String chargingMethod) {
        this.chargingMethod = chargingMethod;
    }

    public String getInteractiveKey() {
        return interactiveKey;
    }

    public void setInteractiveKey(String interactiveKey) {
        this.interactiveKey = interactiveKey;
    }

    public String getInteractiveMessage() {
        return interactiveMessage;
    }

    public void setInteractiveMessage(String interactiveMessage) {
        this.interactiveMessage = interactiveMessage;
    }

    public Boolean getIsInteractive() {
        return isInteractive;
    }

    public void setIsInteractive(Boolean isInteractive) {
        this.isInteractive = isInteractive;
    }

    public Boolean getIsCompetition() {
        return isCompetition;
    }

    public void setIsCompetition(Boolean isCompetition) {
        this.isCompetition = isCompetition;
    }

    public String getAnswkey() {
        return answkey;
    }

    public void setAnswkey(String answkey) {
        this.answkey = answkey;
    }

    public String getCompHelpKey() {
        return compHelpKey;
    }

    public void setCompHelpKey(String compHelpKey) {
        this.compHelpKey = compHelpKey;
    }

    public String getCompPointKey() {
        return compPointKey;
    }

    public void setCompPointKey(String compPointKey) {
        this.compPointKey = compPointKey;
    }

    public String getCompChangeCatKey() {
        return compChangeCatKey;
    }

    public void setCompChangeCatKey(String compChangeCatKey) {
        this.compChangeCatKey = compChangeCatKey;
    }

    public String getCompHelpMt() {
        return compHelpMt;
    }

    public void setCompHelpMt(String compHelpMt) {
        this.compHelpMt = compHelpMt;
    }

    public String getCompPointMt() {
        return compPointMt;
    }

    public void setCompPointMt(String compPointMt) {
        this.compPointMt = compPointMt;
    }

    public String getCompChangeCatMT() {
        return compChangeCatMT;
    }

    public void setCompChangeCatMT(String compChangeCatMT) {
        this.compChangeCatMT = compChangeCatMT;
    }

    public String getRegMt() {
        return regMt;
    }

    public void setRegMt(String regMt) {
        this.regMt = regMt;
    }

    public String getUnRegMt() {
        return unRegMt;
    }

    public void setUnRegMt(String unRegMt) {
        this.unRegMt = unRegMt;
    }

    public String getExistRegMt() {
        return existRegMt;
    }

    public void setExistRegMt(String existRegMt) {
        this.existRegMt = existRegMt;
    }

    public String getExistUnRegMt() {
        return existUnRegMt;
    }

    public void setExistUnRegMt(String existUnRegMt) {
        this.existUnRegMt = existUnRegMt;
    }

    public String getHelpMt() {
        return helpMt;
    }

    public void setHelpMt(String helpMt) {
        this.helpMt = helpMt;
    }

    public String getNotValidMt() {
        return notValidMt;
    }

    public void setNotValidMt(String notValidMt) {
        this.notValidMt = notValidMt;
    }

    public String getNotRegYetMt() {
        return notRegYetMt;
    }

    public void setNotRegYetMt(String notRegYetMt) {
        this.notRegYetMt = notRegYetMt;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getImiServiceKey() {
        return imiServiceKey;
    }

    public void setImiServiceKey(String imiServiceKey) {
        this.imiServiceKey = imiServiceKey;
    }

    public String getImiShortCodeDefaultServiceKey() {
        return imiShortCodeDefaultServiceKey;
    }

    public void setImiShortCodeDefaultServiceKey(String imiShortCodeDefaultServiceKey) {
        this.imiShortCodeDefaultServiceKey = imiShortCodeDefaultServiceKey;
    }

    public String getSdp() {
        return sdp;
    }

    public void setSdp(String sdp) {
        this.sdp = sdp;
    }

    public String getSubChargeCode() {
        return subChargeCode;
    }

    public void setSubChargeCode(String subChargeCode) {
        this.subChargeCode = subChargeCode;
    }

    public String getUnSubChargeCode() {
        return unSubChargeCode;
    }

    public void setUnSubChargeCode(String unSubChargeCode) {
        this.unSubChargeCode = unSubChargeCode;
    }

    public String getReminderMT() {
        return reminderMT;
    }

    public void setReminderMT(String reminderMT) {
        this.reminderMT = reminderMT;
    }

    public Boolean getHasSilentCharge() {
        return hasSilentCharge;
    }

    public void setHasSilentCharge(Boolean hasSilentCharge) {
        this.hasSilentCharge = hasSilentCharge;
    }

    public String getSilentChargeChargeCodes() {
        return silentChargeChargeCodes;
    }

    public void setSilentChargeChargeCodes(String silentChargeChargeCodes) {
        this.silentChargeChargeCodes = silentChargeChargeCodes;
    }

    public Short getGhestiPriceLimitPerDay() {
        return ghestiPriceLimitPerDay;
    }

    public void setGhestiPriceLimitPerDay(Short ghestiPriceLimitPerDay) {
        this.ghestiPriceLimitPerDay = ghestiPriceLimitPerDay;
    }

    public Boolean getIsWappush() {
        return isWappush;
    }

    public void setIsWappush(Boolean isWappush) {
        this.isWappush = isWappush;
    }

    public String getServicemodel() {
        return servicemodel;
    }

    public void setServicemodel(String servicemodel) {
        this.servicemodel = servicemodel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getIsCommercial() {
        return isCommercial;
    }

    public void setIsCommercial(Boolean isCommercial) {
        this.isCommercial = isCommercial;
    }

    public String getPartnerOTPURL() {
        return partnerOTPURL;
    }

    public void setPartnerOTPURL(String partnerOTPURL) {
        this.partnerOTPURL = partnerOTPURL;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getBlankInputtxt() {
        return blankInputtxt;
    }

    public void setBlankInputtxt(String blankInputtxt) {
        this.blankInputtxt = blankInputtxt;
    }

    public String getInvalidInputtxt() {
        return invalidInputtxt;
    }

    public void setInvalidInputtxt(String invalidInputtxt) {
        this.invalidInputtxt = invalidInputtxt;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getWelcomeMT() {
        return welcomeMT;
    }

    public void setWelcomeMT(String welcomeMT) {
        this.welcomeMT = welcomeMT;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serviceCode != null ? serviceCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblServices)) {
            return false;
        }
        TblServices other = (TblServices) object;
        if ((this.serviceCode == null && other.serviceCode != null) || (this.serviceCode != null && !this.serviceCode.equals(other.serviceCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fidar.hibernate.entity.TblServices[ serviceCode=" + serviceCode + " ]";
    }
    
}
