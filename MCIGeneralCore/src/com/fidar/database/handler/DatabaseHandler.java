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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
                            "`MembershipDate` = '"+servicesUser.getMembershipDate()+"',\n" +
                            "WHERE `ServiceCode` = '"+servicesUser.getServiceCode()+"' and `MSISDN` = '"+servicesUser.getMsisdn()+"'";
            String query_update_unsub = "UPDATE `mci_hub_db`.`tbl_serviceusers`\n" +
                            "SET\n" +
                            "`SubStatus` = '"+servicesUser.getSubstatus()+"',\n" +
                            "`UnSubscribeDate` = '"+servicesUser.getUnsubscribeDate()+"',\n" +
                            "WHERE `ServiceCode` = '"+servicesUser.getServiceCode()+"' and `MSISDN` = '"+servicesUser+"'";
            boolean bExistUser = this.isExistUserOnService(servicesUser.getServiceCode(), servicesUser.getMsisdn());
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
}
