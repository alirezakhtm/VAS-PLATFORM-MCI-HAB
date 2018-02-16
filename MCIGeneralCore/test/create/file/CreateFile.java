/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package create.file;

import com.fidar.json.handler.Setting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author alirezakhtm
 */
public class CreateFile {
    public static void main(String[] args) throws IOException{
        Gson gson = new GsonBuilder().create();
        Setting s = new Setting();
        s.setActiveMQPassword("admin");
        s.setActiveMQUsername("admin");
        s.setDBName("mci_hub_db");
        s.setDBPassword("");
        s.setDBUsername("root");
        s.setDelay(1000.0);
        String strJson = gson.toJson(s, Setting.class);
        Files.write(Paths.get("setting.json"), strJson.getBytes(), StandardOpenOption.CREATE);
    }
}
