/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khtm.database;

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
    
}
