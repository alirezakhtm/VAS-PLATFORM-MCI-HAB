/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khtm.database;

import com.khtm.object.NotificationObj;
import com.khtm.object.ServiceUserObj;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alireza
 */
public class DBHandler {
    private String username = "root";
    private String password = "";
    private String url = "jdbc:mysql://localhost:3306/mci_hub_db?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private Connection conn;
    private Statement stm;
    private ResultSet rst;

    public void open(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection)DriverManager.getConnection(url, username, password);
        }catch(SQLException | ClassNotFoundException e){
            System.err.println("[*] ERROR - DBHandler/open : " + e);
        }
    }
    
    public void close(){
        try{
            if(!conn.isClosed()) conn.close();
        }catch(SQLException e){
            System.err.println("[*] ERROR DBHandler/close : " + e);
            
        }
    }
    
    public void exeSQLFile(File file) throws FileNotFoundException, IOException{
        BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = "";
        StringBuilder sb = new StringBuilder();
        while((line = bf.readLine()) != null){
            sb.append(line + "\n");
        }
        String query = sb.toString();
        try{
            stm = conn.createStatement();
            stm.execute(query);
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/exeSQLFile : " + e);
        }
    }
    
    public int getTotalUser(){
        int number = 0;
        try{
            String query = "select count(*) from `mci_hub_db`.`tbl_serviceusers` where `serviceCode`='1'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            number = rst.getInt(1);
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getTotalUser : " + e);
        }
        return number;
    }
    
    public int getActiveUser(){
        int number = 0;
        try{
            String query = "select count(*) from `mci_hub_db`.`tbl_serviceusers` where `serviceCode`='1' and `SubStatus`='1'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            number = rst.getInt(1);
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getActiveUser : " + e);
        }
        return number;
    }
    
    public int getSubNumber(String date){
        int number = 0;
        try{
            String query = "select count(*) from `mci_hub_db`.`tbl_serviceusers` where `MembershipDate` like '%"+date+"%' and `serviceCode`='1'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            number = rst.getInt(1);
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getSubNumber : " + e);
        }
        return number;
    }
    
    public int getUnSubNumber(String date){
        int number = 0;
        try{
            String query = "select count(*) from `mci_hub_db`.`tbl_serviceusers` where `UnsubscribeDate` like '%"+date+"%' and `serviceCode`='1'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            number = rst.getInt(1);
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getUnSubNumber : " + e);
        }
        return number;
    }
    
    public int getBadUserNumber(String date){
        int number = 0;
        try{
            String query = "select count(*) from `mci_hub_db`.`tbl_serviceusers` where `MembershipDate` like '%"+date+"%'  and `UnsubscribeDate` like '%"+date+"%' and `serviceCode`='1'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            number = rst.getInt(1);
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getBadUserNumber : " + e);
        }
        return number;
    }
    
    public List<ServiceUserObj> getListBadUser(String date){
        List<ServiceUserObj> lst = new ArrayList<>();
        try{
            String query = "select * from `mci_hub_db`.`tbl_serviceusers` where `MembershipDate` like '%"+date+"%'  and `UnsubscribeDate` like '%"+date+"%' and `serviceCode`='1'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                ServiceUserObj suo = new ServiceUserObj();
                suo.setMembershipDate(rst.getString("MembershipDate"));
                suo.setMsisdn(rst.getString("msisdn"));
                suo.setNumberType(rst.getString("numberType"));
                suo.setSelctedCategoryIndex(rst.getInt("selectedCategoryIndx"));
                suo.setServiceCode(rst.getInt("ServiceCode"));
                suo.setSubBy(rst.getString("subscribedBy"));
                suo.setSubStatus(rst.getInt("SubStatus"));
                suo.setUnsubBy(rst.getString("unsubscribedBy"));
                suo.setUnsubDate(rst.getString("UnSubscribeDate"));
                lst.add(suo);
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getListBadUser : " + e);
        }
        return lst;
    }
    
    public List<ServiceUserObj> getSubUser(String date){
        List<ServiceUserObj> lst = new ArrayList<>();
        try{
            String query = "select * from `mci_hub_db`.`tbl_serviceusers` where `MembershipDate` like '%"+date+"%'  and `serviceCode`='1'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                ServiceUserObj suo = new ServiceUserObj();
                suo.setMembershipDate(rst.getString("MembershipDate"));
                suo.setMsisdn(rst.getString("msisdn"));
                suo.setNumberType(rst.getString("numberType"));
                suo.setSelctedCategoryIndex(rst.getInt("selectedCategoryIndx"));
                suo.setServiceCode(rst.getInt("ServiceCode"));
                suo.setSubBy(rst.getString("subscribedBy"));
                suo.setSubStatus(rst.getInt("SubStatus"));
                suo.setUnsubBy(rst.getString("unsubscribedBy"));
                suo.setUnsubDate(rst.getString("UnSubscribeDate"));
                lst.add(suo);
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getSubUser : " + e);
        }
        return lst;
    }
    
    public List<ServiceUserObj> getUnSubUser(String date){
        List<ServiceUserObj> lst = new ArrayList<>();
        try{
            String query = "select * from `mci_hub_db`.`tbl_serviceusers` where `UnsubscribeDate` like '%"+date+"%' and `serviceCode`='1'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                ServiceUserObj suo = new ServiceUserObj();
                suo.setMembershipDate(rst.getString("MembershipDate"));
                suo.setMsisdn(rst.getString("msisdn"));
                suo.setNumberType(rst.getString("numberType"));
                suo.setSelctedCategoryIndex(rst.getInt("selectedCategoryIndx"));
                suo.setServiceCode(rst.getInt("ServiceCode"));
                suo.setSubBy(rst.getString("subscribedBy"));
                suo.setSubStatus(rst.getInt("SubStatus"));
                suo.setUnsubBy(rst.getString("unsubscribedBy"));
                suo.setUnsubDate(rst.getString("UnSubscribeDate"));
                lst.add(suo);
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getUnSubUser : " + e);
        }
        return lst;
    }

    public ServiceUserObj getRecordSub(String phone) {
        ServiceUserObj suo = new ServiceUserObj();
        try{
            String query = "select * from `mci_hub_db`.`tbl_serviceusers` where `msisdn`='"+phone+"' and `serviceCode`='1'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                suo.setMembershipDate(rst.getString("MembershipDate"));
                suo.setMsisdn(rst.getString("msisdn"));
                suo.setNumberType(rst.getString("numberType"));
                suo.setSelctedCategoryIndex(rst.getInt("selectedCategoryIndx"));
                suo.setServiceCode(rst.getInt("ServiceCode"));
                suo.setSubBy(rst.getString("subscribedBy"));
                suo.setSubStatus(rst.getInt("SubStatus"));
                suo.setUnsubBy(rst.getString("unsubscribedBy"));
                suo.setUnsubDate(rst.getString("UnSubscribeDate"));
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getRecordSub : " + e);
        }
        return suo;
    }

    public int getRenewalNumber(String date) {
        int number = 0;
        try{
            String query = "select count(*) from `mci_hub_db`.`tbl_notification_log` where `receiveDate` like '%"+date+"%'  and `text`='Renewal' and `to`='405571'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            number = rst.getInt(1);
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getRenewalNumber : " + e);
        }
        return number;
    }
    
    public List<NotificationObj> getRenewal(String date) {
        List<NotificationObj> lst = new ArrayList<>();
        try{
            String query = "select * from `mci_hub_db`.`tbl_notification_log` where `receiveDate` like '%"+date+"%' and `to`='405571' and `text`='Renewal'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                NotificationObj notificationObj = new NotificationObj();
                notificationObj.setChannel(rst.getString("channel"));
                notificationObj.setFrom(rst.getString("from"));
                notificationObj.setKeyword(rst.getString("keyword"));
                notificationObj.setNotificationId(rst.getString("notificationId"));
                notificationObj.setReceiveDate(rst.getString("receiveDate"));
                notificationObj.setText(rst.getString("text"));
                notificationObj.setTo(rst.getString("to"));
                notificationObj.setUserId(rst.getString("userId"));
                lst.add(notificationObj);
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getRenewal : " + e);
        }
        return lst;
    }
    
    public List<NotificationObj> getUserChargedInDate(String dateSub, String dateCharge){
        List<NotificationObj> lst = new ArrayList<>();
        try{
            String strQuery = "select * from `mci_hub_db`.`tbl_notification_log`"
                    + " where `from` in "
                    + "(select `msisdn` from `mci_hub_db`.`tbl_serviceusers`"
                    + " where `MembershipDate` like '%"+dateSub+"%' and `ServiceCode`='1')"
                    + " and `to`='405571' and `text`='Renewal' and `receiveDate` like '%"+dateCharge+"%'";
            stm = conn.createStatement();
            rst = stm.executeQuery(strQuery);
            while(rst.next()){
                NotificationObj notificationObj = new NotificationObj();
                notificationObj.setChannel(rst.getString("channel"));
                notificationObj.setFrom(rst.getString("from"));
                notificationObj.setKeyword(rst.getString("keyword"));
                notificationObj.setNotificationId(rst.getString("notificationId"));
                notificationObj.setReceiveDate(rst.getString("receiveDate"));
                notificationObj.setText(rst.getString("text"));
                notificationObj.setTo(rst.getString("to"));
                notificationObj.setUserId(rst.getString("userId"));
                lst.add(notificationObj);
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getUserChargeInDate : " + e);
        }
        return lst;
    }
    
    public int getUserChargedInDateNumber(String dateSub, String dateCharge){
        int ans = 0;
        try{
            String strQuery = "select count(*) from `mci_hub_db`.`tbl_notification_log`"
                    + " where `from` in "
                    + "(select `msisdn` from `mci_hub_db`.`tbl_serviceusers`"
                    + " where `MembershipDate` like '%"+dateSub+"%' and `ServiceCode`='1')"
                    + " and `to`='405571' and `text`='Renewal' and `receiveDate` like '%"+dateCharge+"%'";
            stm = conn.createStatement();
            rst = stm.executeQuery(strQuery);
            while(rst.next()){
                ans = rst.getInt(1);
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getUserChargedInDateNumber : " + e);
        }
        return ans;
    }
}
