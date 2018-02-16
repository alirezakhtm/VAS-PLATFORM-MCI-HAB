/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package create.file;

import com.fidar.json.handler.JsonHandler;

/**
 *
 * @author alirezakhtm
 */
public class ReadSetting {
    public static void main(String[] args) {
        JsonHandler jh = new JsonHandler();
        System.out.println(
                "DB Name : " + jh.getDBName() + "\n" +
                "DB Username : " + jh.getDBUsername()
        );
    }
}
