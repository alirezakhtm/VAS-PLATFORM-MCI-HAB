/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khtm.file;

import com.khtm.object.Notification;
import com.khtm.object.ServiceUsers;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 *
 * @author alireza
 */
public class FileHandle {
    private final String dirAddress;
    private static Map<String, ServiceUsers> mapUsers = new TreeMap<>();
    private static List<Notification> lstNotification = new ArrayList<>();

    public FileHandle(String dirAddress) {
        this.dirAddress = dirAddress;
    }
    
    public void readTomcatLog() throws IOException{
        File[] files = new File(dirAddress).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().startsWith("localhost_access_log") && pathname.getName().endsWith("txt");
            }
        });
        echo("Start file parsing.");
        for(File file : files){
            BufferedReader fileBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = "";
            echo("Analysing file -> " + file.getName() + " size is -> " + file.length());
            while((line = fileBufferedReader.readLine()) != null){
                analysisString(line, file.getName());
            }
            fileBufferedReader.close();
        }
        echo("Finish file parsing.");
        echo("Start create SQL file from data");
        List<ServiceUsers> lstServiceUsers = new ArrayList<ServiceUsers>(mapUsers.values());
        echo("All records for tbl_serviceusers equeal to = " + lstServiceUsers.size());
        for(int n = 0; n < Math.ceil(lstServiceUsers.size()/200) + 1; n++){
            String query = "INSERT INTO `mci_hub_db`.`tbl_serviceusers`("
                    + "`ServiceCode`,`MSISDN`,`SubStatus`,`MembershipDate`,"
                    + "`UnSubscribeDate`,`selectedCategoryIndx`,`unsubscribedBy`,"
                    + "`subscribedBy`,`numberType`) VALUES\n";
            for(int m = n*200; m < n*200 + 200; m++){
                if(m < lstServiceUsers.size()){
                    ServiceUsers su = lstServiceUsers.get(m);
                    query += "('"+su.getServiceCode()+"','"+su.getMsisdn()+"','"+su.getSubStatus()+"',"
                            + "'"+su.getMembershipDate()+"','"+su.getUnsubDate()+"','"+su.getSelctedCategoryIndex()+"',"
                            + "'"+su.getUnsubBy()+"','"+su.getSubBy()+"','"+su.getNumberType()+"'),\n";
                }
            }
            query = query.substring(0, query.lastIndexOf(","));
            File sqlFile = new File("sqlfile/serviceuser/" + UUID.randomUUID() + ".sql");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sqlFile)));
            bw.write(query);
            bw.flush();
            bw.close();
            echo("SQL file for tbl_serviceusers have been created -> " + sqlFile.getName());
        }
        echo("All records for tbl_notifiacation_log equeal to = " + lstNotification.size());
        for(int n = 0; n < Math.ceil(lstNotification.size()/200) + 1; n++){
            String query = "insert into `mci_hub_db`.`tbl_notification_log`(`text`,`keyword`,"
                    + "`channel`,`from`,`to`,`notificationId`,`userId`,`receiveDate`) values\n";
            for(int m = n*200; m < n*200 + 200; m++){
                if(m < lstNotification.size()){
                    Notification note = lstNotification.get(m);
                    query += "('"+note.getText()+"','"+note.getKeyword()+"','"+note.getChannel()+"',"
                            + "'"+note.getFrom()+"','"+note.getTo()+"','"+note.getNotificationId()+"',"
                            + "'"+note.getUserId()+"','"+note.getReceiveDate()+"'),\n";
                }
            }
            query = query.substring(0, query.lastIndexOf(","));
            File sqlFile = new File("sqlfile/notification/" + UUID.randomUUID() + ".sql");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sqlFile)));
            bw.write(query);
            bw.flush();
            bw.close();
            echo("SQL file for tbl_notification_log have been created -> " + sqlFile.getName());
        }
    }

    private void analysisString(String line, String fileName) {
        String dateOfCall = line.substring(line.indexOf("["), line.lastIndexOf("]"));
        String[] strDate = dateOfCall.split(":");
        String hour = strDate[1];
        String minute = strDate[2];
        String second = strDate[3].substring(0, strDate[3].lastIndexOf(" "));
        dateOfCall = fileName.replace("localhost_access_log.", "")
                .replace(".txt", "") + " " + hour + ":" + minute + ":" + second;
        if(line.contains("Notification")){
            // this link for notification
            String linkInfo = line.substring(line.indexOf("?"), line.indexOf(" H")).replace("?", "").replace("&", "=");
            String[] allData = linkInfo.split("=");
            Map<String, String> mapInfo = new HashMap<>();
            for(int n = 0; n < allData.length; n+=2){
                mapInfo.put(allData[n], allData[n+1]);
            }
            int ServiceCode = (mapInfo.get("to").equals("405571")) ? 1 : 2;
            if(mapInfo.get("text").equals("Subscription") || mapInfo.get("text").equals("Unsubscription")){
                if(mapUsers.keySet().contains(ServiceCode + mapInfo.get("from"))){
                    // update
                    ServiceUsers serviceUsers = mapUsers.get(ServiceCode + mapInfo.get("from"));
                    if(mapInfo.get("text").equals("Subscription")){
                        serviceUsers.setMembershipDate(dateOfCall);
                        serviceUsers.setSubStatus(1);
                    }else{
                        serviceUsers.setUnsubDate(dateOfCall);
                        serviceUsers.setSubStatus(0);
                    }
                    mapUsers.put(ServiceCode + mapInfo.get("from"), serviceUsers);
                }else{
                    // insert data
                    ServiceUsers serviceUsers =
                            new ServiceUsers( 
                                    (mapInfo.get("to").equals("405571")) ? 1 : 2,
                                    mapInfo.get("from"),
                                    mapInfo.get("text").equals("Subscription") ? 1 : 0,
                                    mapInfo.get("text").equals("Subscription") ? dateOfCall : "",
                                    mapInfo.get("text").equals("Unsubscription") ? dateOfCall : "",
                                    0,
                                    "0",
                                    "0",
                                    ""
                            );
                    mapUsers.put(ServiceCode + mapInfo.get("from"), serviceUsers);
                }
            }
            
            Notification notification = new Notification(
                    mapInfo.get("text"),
                    mapInfo.get("keyword"),
                    mapInfo.get("channel"),
                    mapInfo.get("from"),
                    mapInfo.get("to"),
                    mapInfo.get("NotificationId"),
                    mapInfo.get("userid"),
                    dateOfCall
            );
            
            lstNotification.add(notification);
            
        }
    }

    private void echo(String msg) {
        System.out.println("[*] INFO : " + msg);
    }
    
}
