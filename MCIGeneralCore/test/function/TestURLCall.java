/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author alireza
 */
public class TestURLCall {
    public static void main(String[] args) throws MalformedURLException {
        String strUrl = "http://79.175.133.166:8080/MobileApp/pushotp?"
                + "token=FIDAR123MLmkOsKKMc786231QweXjzPPLLARKHTM&msisdn=9128554075"
                + "&shortcode=405571"
                + "&status=subscription"
                + "&servicecode=1";
        URL url = new URL(strUrl);
        try{
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = connection.getResponseCode();
            System.out.println("response code : " + responseCode);
//            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String line = "";
//            while((line = br.readLine()) != null){
//                System.out.println(line);
//            }
//            br.close();
        }catch(IOException e){
            System.err.println("ConsumerSaveLog - LogicApp - code 01 : " + e);
        }
    }
}
