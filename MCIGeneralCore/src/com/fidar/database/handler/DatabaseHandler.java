/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.database.handler;

import com.fidar.database.object.ChargeOTPObject;
import com.fidar.database.object.MOMTLogObject;
import com.fidar.database.object.OTPLogObject;
import com.fidar.database.object.OTPReqObject;
import com.fidar.database.object.ReceiveMsgObject;
import com.fidar.database.object.ServicesObject;
import com.fidar.database.object.ServicesUser;
import com.fidar.json.handler.JsonHandler;
import com.fidar.queue.object.NotificationObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author alirezakhtm
 */
public class DatabaseHandler {
    
    private final String dbName;
    private final String dbUsername;
    private final String dbPassword;
    private final String dbUrl;
    private static JsonHandler jHandler = null;

    private Connection conn;
    private Statement stm;
    private ResultSet rst;
    
    public DatabaseHandler() {
        if(jHandler == null){
            jHandler = new JsonHandler();
        }
        dbName = jHandler.getDBName();
        dbPassword = jHandler.getDBPassword();
        dbUsername = jHandler.getDBUsername();
        dbUrl = "jdbc:mysql://localhost:3306/" +
                dbName +
                "?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    }
    
    public void open(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        }catch(SQLException |ClassNotFoundException e){
            System.err.println("DatabaseHandler - open : " + e);
        }
    }
    
    public void close(){
        try{
            if(!conn.isClosed()) conn.close();
        }catch(SQLException e){
            System.err.println("DatabaseHandler - close : " + e);
        }
    }
    
    public void saveOTPLog(OTPLogObject otplo){
        try{
            String strQuery = "INSERT INTO `mci_hub_db`.`tbl_otp_log`\n" +
                            "(" +
                            "`otpId`,\n" +
                            "`statusId`,\n" +
                            "`recipient`,\n" +
                            "`registrationDate`)\n" +
                            "VALUES\n" +
                            "(" +
                            "'"+otplo.getOtpId()+"',\n" +
                            "'"+otplo.getStatusId()+"',\n" +
                            "'"+otplo.getRecipient()+"',\n" +
                            "'"+otplo.getRegistrationDate()+"')";
            stm = conn.createStatement();
            stm.execute(strQuery);
        }catch(SQLException e){
            System.err.println("DatabaseHandler - saveOTPLog : " + e);
        }
    }
    
    public void saveReceiveSMS(ReceiveMsgObject msgObject){
        try{
            String strQuery = "INSERT INTO `mci_hub_db`.`tbl_receive_msg`\n" +
                                "(" +
                                "`text`,\n" +
                                "`from`,\n" +
                                "`to`,\n" +
                                "`smsID`,\n" +
                                "`userID`)\n" +
                                "VALUES\n" +
                                "(" +
                                "'"+msgObject.getText()+"',\n" +
                                "'"+msgObject.getFrom()+"',\n" +
                                "'"+msgObject.getTo()+"',\n" +
                                "'"+msgObject.getSmsId()+"',\n" +
                                "'"+msgObject.getUserId()+"')";
            stm = conn.createStatement();
            stm.execute(strQuery);
        }catch(SQLException e){
            System.err.println("DatabaseHandler - saveReceiveSMS : " + e);
        }
    }
    
    public void saveMOMTLog(MOMTLogObject momtlo){
        try{
            String strQuery = "INSERT INTO `mci_hub_db`.`tbl_mo_mt_log`\n" +
                                "(" +
                                "`serviceCode`,\n" +
                                "`msisdn`,\n" +
                                "`mo`,\n" +
                                "`mt`,\n" +
                                "`registrationDate`)\n" +
                                "VALUES\n" +
                                "(" +
                                "'"+momtlo.getServiceCode()+"',\n" +
                                "'"+momtlo.getMsisdn()+"',\n" +
                                "'"+momtlo.getMo()+"',\n" +
                                "'"+momtlo.getMt()+"',\n" +
                                "'"+momtlo.getRegistrationDate()+"')";
            stm = conn.createStatement();
            stm.execute(strQuery);
        }catch(SQLException e){
            System.err.println("DatabaseHandler - saveMOMTLog : " + e);
        }
    }
    
    /***************************************************************************
     *                        Services DB Management                           *
     ***************************************************************************/
    /**
     * @return 
     */
    public List<ServicesObject> getAllServices(){
        List<ServicesObject> lstServices = new ArrayList<>();
        try{
            String query = "SELECT * FROM `mci_hub_db`.`tbl_services`";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                String ServiceCode = rst.getString("ServiceCode");
                String servicename = rst.getString("servicename");
                String ShortCode = rst.getString("ShortCode");
                String serviceId = rst.getString("serviceId");
                String welcomeMT = rst.getString("welcomeMT");
                ServicesObject so = new ServicesObject(ServiceCode,
                        servicename, ShortCode, serviceId, welcomeMT);
                lstServices.add(so);
            }
        }catch(SQLException e){
            System.err.println("DatabaseHandler - getAllServices : " + e);
        }
        return lstServices;
    }
    
    public int getServiceCode(com.fidar.queue.object.ReceiveMsgObject receiveMsgObject){
        int serviceCode = 0;
        try{
            String shortCode = receiveMsgObject.getTo().startsWith("98") ?
                    receiveMsgObject.getTo().substring(2) : 
                    receiveMsgObject.getTo();
            String query = "SELECT `ServiceCode` FROM `mci_hub_db`.`tbl_services` "
                    + "WHERE `ShortCode` = '"+shortCode+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            serviceCode = Integer.parseInt(rst.getString("ServiceCode"));
        }catch(SQLException e){
            System.err.println("DatabaseHandler - getServiceCode : " + e);
        }
        return serviceCode;
    }
    
    public int getServiceCode(String shortCode){
        int serviceCode = 0;
        try{
            shortCode = shortCode.startsWith("98") ?
                    shortCode.substring(2) : 
                    shortCode;
            String query = "SELECT `ServiceCode` FROM `mci_hub_db`.`tbl_services` "
                    + "WHERE `ShortCode` = '"+shortCode+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            serviceCode = Integer.parseInt(rst.getString("ServiceCode"));
        }catch(SQLException e){
            System.err.println("DatabaseHandler - getServiceCode : " + e);
        }
        return serviceCode;
    }
    
    public int getServiceCode(NotificationObject notificationObject){
        int serviceCode = 0;
        try{
            String shortCode = notificationObject.getTo().startsWith("98") ?
                    notificationObject.getTo().substring(2) : 
                    notificationObject.getTo();
            String query = "SELECT `ServiceCode` FROM `mci_hub_db`.`tbl_services` "
                    + "WHERE `ShortCode` = '"+shortCode+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            serviceCode = Integer.parseInt(rst.getString("ServiceCode"));
        }catch(SQLException e){
            System.err.println("DatabaseHandler - getServiceCode : " + e);
        }
        return serviceCode;
    }
    
    public String getServiceId(int ServiceCode){
        String ans = "0";
        try{
            String query = "SELECT * FROM `mci_hub_db`.`tbl_services` WHERE `ServiceCode` = '"+ServiceCode+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                ans = rst.getString("serviceId");
            }
        }catch(SQLException e){
            System.err.println("DatabaseHandler - getServiceId : " + e);
        }
        return ans;
    }
    
    public String getWelcomeMT(int serviceCode){
        String ans = "";
        try{
            String query = "SELECT * FROM `mci_hub_db`.`tbl_services` WHERE `ServiceCode` = '"+serviceCode+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                ans = rst.getString("welcomeMT");
            }
        }catch(SQLException e){
            System.err.println("DatabaseHandler - getWelcomeMT : " + e);
        }
        return ans;
    }
    
    public String getUnRegMT(int serviceCode){
        String ans = "";
        try{
            String query = "SELECT * FROM `mci_hub_db`.`tbl_services` WHERE `ServiceCode` = '"+serviceCode+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                ans = rst.getString("unRegMt");
            }
        }catch(SQLException e){
            System.err.println("DatabaseHandler - getUnRegMT : " + e);
        }
        return ans;
    }
    
    public String getShortCode(String serivceCode) {
        String ans = "";
        try{
            String query = "SELECT * FROM `mci_hub_db`.`tbl_services` WHERE `ServiceCode` = '"+serivceCode+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                ans = rst.getString("ShortCode");
            }
        }catch(SQLException e){
            System.err.println("DatabaseHandler - getShortCode : " + e);
        }
        return ans;
    }
    
    public String getNotValidMT(int ServiceCode){
        String ans = "";
        try{
            String query = "SELECT `NotValidMt` FROM `mci_hub_db`.`tbl_services` WHERE `ServiceCode` = '"+ServiceCode+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            ans = rst.getString("NotValidMt");
        }catch(SQLException e){
            System.err.println("DatabaseHandler - getNotValidMT : " + e);
        }
        return ans;
    }
    
    public String getHelpMT(int ServiceCode){
        String ans = "";
        try{
            String query = "SELECT `HelpMt` FROM `mci_hub_db`.`tbl_services` WHERE `ServiceCode` = '"+ServiceCode+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            ans = rst.getString("HelpMt");
        }catch(SQLException e){
            System.err.println("DatabaseHandler - getHelpMT : " + e);
        }
        return ans;
    }
    
    /***************************************************************************
     *                            Service User DB                              *
     ***************************************************************************/
    
    public void saveUserOnServiceUser(ServicesUser servicesUser){
        try{
            String query_insert_new_sub = "INSERT INTO `mci_hub_db`.`tbl_serviceusers`\n" +
                            "(`ServiceCode`,\n" +
                            "`MSISDN`,\n" +
                            "`SubStatus`,\n" +
                            "`MembershipDate`\n" +
                            ")\n" +
                            "VALUES\n" +
                            "('"+servicesUser.getServiceCode()+"',\n" +
                            "'"+servicesUser.getMsisdn()+"',\n" +
                            "'"+servicesUser.getSubstatus()+"',\n" +
                            "'"+servicesUser.getMembershipDate()+"'\n" +
                            ")";
            String query_insert_new_unsub = "INSERT INTO `mci_hub_db`.`tbl_serviceusers`\n" +
                            "(`ServiceCode`,\n" +
                            "`MSISDN`,\n" +
                            "`SubStatus`,\n" +
                            "`UnSubscribeDate`\n" +
                            ")\n" +
                            "VALUES\n" +
                            "('"+servicesUser.getServiceCode()+"',\n" +
                            "'"+servicesUser.getMsisdn()+"',\n" +
                            "'"+servicesUser.getSubstatus()+"',\n" +
                            "'"+servicesUser.getUnsubscribeDate()+"'\n" +
                            ")";
            String query_update_sub = "UPDATE `mci_hub_db`.`tbl_serviceusers`\n" +
                            "SET\n" +
                            "`SubStatus` = '"+servicesUser.getSubstatus()+"',\n" +
                            "`MembershipDate` = '"+servicesUser.getMembershipDate()+"'\n" +
                            " WHERE `ServiceCode` = '"+servicesUser.getServiceCode()+"' and `MSISDN` = '"+servicesUser.getMsisdn()+"'";
            String query_update_unsub = "UPDATE `mci_hub_db`.`tbl_serviceusers`\n" +
                            "SET\n" +
                            "`SubStatus` = '"+servicesUser.getSubstatus()+"',\n" +
                            "`UnSubscribeDate` = '"+servicesUser.getUnsubscribeDate()+"'\n" +
                            " WHERE `ServiceCode` = '"+servicesUser.getServiceCode()+"' and `MSISDN` = '"+servicesUser.getMsisdn()+"'";
            boolean bExistUser = this.isExistUserOnService(servicesUser.getServiceCode(), servicesUser.getMsisdn());
            this.close();
            this.open();
            String query = "";
            if(servicesUser.getSubstatus() == 1){
                if(bExistUser){
                    // update user info (SUB)
                    query = query_update_sub;
                }else{
                    // insert new record (SUB)
                    query = query_insert_new_sub;
                }
            }else{
                if(bExistUser){
                    // update user info (UNSUB) 
                    query = query_update_unsub;
                }else{
                    // insert new record (UNSUB)
                    query = query_insert_new_unsub;
                }
            }
            stm = conn.createStatement();
            stm.execute(query);
        }catch(SQLException e){
            System.err.println("DatabaseHandler - saveUserOnServiceUser : " + e);
        }
    }
    
    public boolean isExistUserOnService(int ServiceCode, String msisdn){
        boolean ans = false;
        try{
            String query = "SELECT count(*) FROM `mci_hub_db`.`tbl_serviceusers`"
                    + " WHERE `ServiceCode` = '"+ServiceCode+"' and `MSISDN` = '"+msisdn+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            int number = rst.getInt(1);
            if(number > 0){
                ans = true;
            }else{
                ans = false;
            }
        }catch(SQLException e){
            System.err.println("DatabaseHandler - isExistUserOnService : " + e);
        }
        return ans;
    }

    /***************************************************************************
     *                              OTP LOG DB                                 *
     ***************************************************************************/
    /**
     * @param otpId 
     * @return 
     */
    public OTPLogObject getOTPLog(String otpId){
        OTPLogObject otplo = new OTPLogObject("", "", "", "");
        try{
            String query = "SELECT * FROM `mci_hub_db`.`tbl_otp_log` WHERE `otpId` = '"+otpId+"' order by `indx` DESC";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            otplo = new OTPLogObject(
                    rst.getString("otpId"),
                    rst.getString("statusId"),
                    rst.getString("recipient"),
                    rst.getString("registrationDate")
            );
        }catch(SQLException e){
            System.err.println("DatabaseHandler - getOTPLog : " + e);
        }
        return otplo;
    }
    
    /***************************************************************************
     *                          OTP Request DB                                 *
     ***************************************************************************/
    public void saveOTPRequest(OTPReqObject otpro){
        try{
            String query = "INSERT INTO `mci_hub_db`.`tbl_otp_request`\n" +
                            "(`msisdn`,\n" +
                            "`shortCode`,\n" +
                            "`status`,\n" +
                            "`serviceCode`,\n" +
                            "`requestDate`,\n" +
                            "`otpId`)\n" +
                            "VALUES\n" +
                            "('"+otpro.getMsisdn()+"',\n" +
                            "'"+otpro.getShortCode()+"',\n" +
                            "'"+otpro.getStatus()+"',\n" +
                            "'"+otpro.getServiceCode()+"',\n" +
                            "'"+otpro.getRequestDate()+"',\n" +
                            "'"+otpro.getOtpId()+"')";
            stm = conn.createStatement();
            stm.execute(query);
        }catch(SQLException e){
            System.err.println("DatabaseHandler - saveOTPRequest : " + e);
        }
    }
    
    public OTPReqObject getOTPRequest(String otpId){
        OTPReqObject otpro = new OTPReqObject();
        try{
            String query = "SELECT * FROM `mci_hub_db`.`tbl_otp_request` WHERE `otpId` = '"+otpId+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            otpro = new OTPReqObject(
                    rst.getString("msisdn"),        // msisdn
                    rst.getString("shortCode"),     // shortCode
                    rst.getString("status"),        // status
                    rst.getString("serviceCode"),   // serviceCode
                    rst.getString("requestDate"),   // requestDate
                    rst.getString("otpId")          // otpId
            );
        }catch(SQLException e){
            System.err.println("DatabaseHandler - saveOTPRequest : " + e);
        }
        return otpro;
    }
    
    public List<OTPReqObject> getAllOTPReuest(String msisdn, int serviceCode, String date){
        List<OTPReqObject> lst = new ArrayList<>();
        try{
            String query = "select * from `mci_hub_db`.`tbl_otp_request` where `requestDate` like '%"+date+"%'"
                    + " and `msisdn` like '%"+msisdn+"%' and `serviceCode` = '"+serviceCode+"' order by `indx` DESC";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                OTPReqObject otpro = new OTPReqObject(
                        rst.getString("msisdn"),
                        rst.getString("shortCode"),
                        rst.getString("status"),
                        rst.getString("serviceCode"),
                        rst.getString("requestDate"),
                        rst.getString("otpId"));
                lst.add(otpro);
            }
        }catch(SQLException e){
            System.err.println("DatabaseHandler - getAllOTPReuest : " + e);
        }
        return lst;
    }
    
    public int getNumOTPRequest(String msisdn, int serviceCode, String date){
        int ans = 0;
        try{
            String query = "select count(*) from `mci_hub_db`.`tbl_otp_request` where `requestDate` like '%"+date+"%'"
                    + " and `msisdn` like '%"+msisdn+"%' and `serviceCode` = '"+serviceCode+"' order by `indx` DESC";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            ans = rst.getInt(1);
        }catch(SQLException e){
            System.err.println("DatabaseHandler - getNumOTPRequest : " + e);
        }
        return ans;
    }
    
    /***************************************************************************
     *                          Charge OTP Log                                 *
     ***************************************************************************/
    public void saveChargeOTP(ChargeOTPObject cotpo){
        try{
            String query = "INSERT INTO `mci_hub_db`.`tbl_charge_otp_log`\n" +
                            "(" +
                            "`msisdn`,\n" +
                            "`shortCode`,\n" +
                            "`serviceCode`,\n" +
                            "`requestDate`,\n" +
                            "`pinCode`,\n" +
                            "`otpId`)\n" +
                            "VALUES\n" +
                            "(" +
                            "'"+cotpo.getMsisdn()+"',\n" +
                            "'"+cotpo.getShortCode()+"',\n" +
                            "'"+cotpo.getServiceCode()+"',\n" +
                            "'"+cotpo.getRequestDate()+"',\n" +
                            "'"+cotpo.getPinCode()+"',\n" + 
                            "'"+cotpo.getOtpId()+"')";
            stm = conn.createStatement();
            stm.execute(query);
        }catch(SQLException e){
            System.err.println("DatabaseHandler - saveChargeOTP : " + e);
        }
    }
    
    /***************************************************************************
     *                      Table of tbl_notification_db                       *
     ***************************************************************************/
    public void saveNotificationLog(NotificationObject notificationObject){
        try{
            String query =  "INSERT INTO `mci_hub_db`.`tbl_notification_log`\n" +
                            "(`text`,\n" +
                            "`keyword`,\n" +
                            "`channel`,\n" +
                            "`from`,\n" +
                            "`to`,\n" +
                            "`notificationId`,\n" +
                            "`userId`,\n" +
                            "`receiveDate`)\n" +
                            "VALUES\n" +
                            "('"+notificationObject.getText()+"',\n" +
                            "'"+notificationObject.getKeyword()+"',\n" +
                            "'"+notificationObject.getChannel()+"',\n" +
                            "'"+notificationObject.getFrom()+"',\n" +
                            "'"+notificationObject.getTo()+"',\n" +
                            "'"+notificationObject.getNotificationId()+"',\n" +
                            "'"+notificationObject.getUserId()+"',\n" +
                            "'"+notificationObject.getDateTime()+"')";
            stm = conn.createStatement();
            stm.execute(query);
        }catch(SQLException e){
            System.err.println("DatabaseHandler - saveNotificationLog : " + e);
        }
    }
    
    /***************************************************************************
     *                      Reward & Unique Code Handle                        *
     ***************************************************************************/
    /**
     * @param msisdn 
     * @param shortCode 
     * @param code 
     * @return 
     */
    public boolean saveInvitedCodeForUser(String msisdn, String shortCode, String code){
        // there is not recode for this user in table of service users.
        int serviceCode = this.getServiceCode(shortCode);
        this.close();
        this.open();
        boolean userExist = this.isExistUserOnService(serviceCode, msisdn);
        this.close();
        this.open();
        if(userExist){
            return false;
        }else{
            // if there are any records for current user, save this users on table of unique code
            // search in table of unique code that is exist on table
            String query = "select count(*) from `mci_hub_db`.`tbl_unique_code` "
                    + "where `msisdn` = '"+msisdn+"' and serviceCode = '"+serviceCode+"'";
            try{
                stm = conn.createStatement();
                rst = stm.executeQuery(query);
                rst.next();
                if(rst.getInt(1) > 0){
                    // if user exist on table of unique code, dont save or update information
                    // but send user OTP
                    return true;//false;
                }else{
                    this.close();
                    this.open();
                    query = "INSERT INTO `mci_hub_db`.`tbl_unique_code`\n" +
                            "(`msisdn`,`receivedCode`,`receivedDate`,`statusSubscription`,`serviceCode`)\n" +
                            "VALUES ('"+msisdn+"','"+code+"',"
                            + "'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                    .format(Calendar.getInstance().getTime())+"','0','"+serviceCode+"')";
                    stm = conn.createStatement();
                    stm.execute(query);
                    return true;
                }
            }catch(SQLException e){
                System.err.println("DatabaseHandler - saveInvitedCodeForUser - 01 : " + e);
                return false;
            }
        }
    }
    
    public boolean isUniqueCodeValid(String code){
        try{
            int userId = Integer.parseInt(code) - 1010;
            String query = "select count(*) from `mci_hub_db`.`tbl_serviceusers` "
                    + "where `fldIndex` = '"+userId+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            if(rst.getInt(1) > 0) return true; else return false;
        }catch(SQLException e){
            System.err.println("DatabaseHandler - isUniqueCodeValid - 02 : " + e);
            return false;
        }
    }
    
    public void updateInvitedTableForThisUser(String msisdn, String serviceCode){
        try{
            String query = "update `mci_hub_db`.`tbl_unique_code` set `statusSubscription`='1'"
                    + " where `msisdn`='"+msisdn+"' and `serviceCode`='"+serviceCode+"'";
            stm = conn.createStatement();
            stm.execute(query);
        }catch(SQLException e){
            System.err.println("DatabaseHandler - updateInvitedTableForThisUser : " + e);
        }
    }
    
    public int getUniqueCodeForUser(String msisdn, int serviceCode){
        int ans = -1;
        try{
            String query = "select `fldIndex` from `mci_hub_db`.`tbl_serviceusers` "
                    + "where `MSISDN`='"+msisdn+"' and `ServiceCode`='"+serviceCode+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                ans = rst.getInt(1) + 1010;
            }
        }catch(SQLException e){
            System.err.println("DatabaseHandler - getUniqueCodeForUser : " + e);
        }
        return ans;
    }
    
    public int getInvitedNumberByUser(String msisdn, int serviceCode){
        int ans = 0;
        try{
            int uniqueCode = this.getUniqueCodeForUser(msisdn, serviceCode);
            this.close();
            this.open();
            String query = "select count(*) from `mci_hub_db`.`tbl_unique_code`"
                    + " where `receivedCode`='"+uniqueCode+"'"
                    + " and `serviceCode` = '"+serviceCode+"'"
                    + " and `statusSubscription`='1'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            ans = rst.getInt(1);
        }catch(SQLException e){
            System.err.println("DatabaseHandler - getInvitedNumberByUser : " + e);
        }
        return ans;
    }
    
    public boolean isUserGetRewardInPast(String msisdn, int serviceCode){
        try{
            String query = "select count(*) from `mci_hub_db`.`tbl_reward`"
                    + " where `msisdn` = '"+msisdn+"' and `serviceCode` = '"+serviceCode+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            if(rst.getInt(1) == 0) return false; else return true;
        }catch(SQLException e){
            System.err.println("DatabaseHandler - isUserGetRewardInPast : " + e);
            return false;
        }
    }
    
    /**
     * @param description 
     * 1000 toman charge = chrg1000,
     * Internet 3G = int3g,
     * Internet 5G = int5g,
     * Internet 7G = int7g
     * @param msisdn 
     * @param serviceCode 
     * @return it is maybe equal to "NOTHING" or ChargeCode ir "48HOUR" or "ERROR"
     */
    public String getRewardForThisUser(String msisdn, String description, int serviceCode){
        try{
            
            String query_userInfo_is_active = "select * from `mci_hub_db`.`tbl_serviceusers`"
                    + " where `MSISDN`='"+msisdn+"' and `ServiceCode`='"+serviceCode+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query_userInfo_is_active);
            String regDate = "";
            int SubStatus = -1;
            while(rst.next()){
                regDate = rst.getString("MembershipDate");
                SubStatus = rst.getInt("SubStatus");
            }
            if(SubStatus < 1){
                return "USERDEACTIVE";
            }else{
                try{
                    String[] regDateSeries = regDate.split(" ")[0].split("-");
                    int year = Integer.parseInt(regDateSeries[0]);
                    int month = Integer.parseInt(regDateSeries[1]);
                    int day = Integer.parseInt(regDateSeries[2]);
                    int cYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime()));
                    int cMonth = Integer.parseInt(new SimpleDateFormat("MM").format(Calendar.getInstance().getTime()));
                    int cDay = Integer.parseInt(new SimpleDateFormat("dd").format(Calendar.getInstance().getTime()));
                    if(year < cYear || month < cMonth || day < cDay - 2){
                        //////
                        this.close();
                        this.open();

                        String query_getRewardId = "select * from `mci_hub_db`.`tbl_reward`"
                                + " where `statusUsable`='1' and `description`='"+description+"'";
                        stm = conn.createStatement();
                        rst = stm.executeQuery(query_getRewardId);
                        int id = 0;
                        String chargeCode = "";
                        while(rst.next()){
                            id = rst.getInt("indx");
                            chargeCode = rst.getString("chargeCode");
                            break;
                        }
                        if(id > 0) {
                            this.close();
                            this.open();
                            String query = "update `mci_hub_db`.`tbl_reward` set"
                                    + " `statusUsable`='0',"
                                    + " `msisdn`='"+msisdn+"',"
                                    + " `receivedDate`='"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())+"',"
                                    + " `serviceCode`='"+serviceCode+"' where `indx` = '"+id+"'";
                            stm = conn.createStatement();
                            stm.execute(query);
                            return chargeCode;
                        }else{
                            return "NOTHING";
                        }
                        //////
                    }else{
                        return "48HOUR";
                    }
                }catch(Exception e){
                    System.err.println("DatabaseHandler - getRewardForThisUser - 02 : " + e);
                    return "ERROR";
                }
            }
            
            
        }catch(SQLException e){
            System.err.println("DatabaseHanlder - getRewardForThisUser : " + e);
            return "NOTHING";
        }
    }
}
