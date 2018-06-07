/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

import com.fidar.database.handler.DatabaseHandler;

/**
 *
 * @author alireza
 */
public class TestUniqueCodeTable {
    public static void main(String[] args) {
        DatabaseHandler db = new DatabaseHandler();
        db.open();
        boolean answer = db.saveInvitedCodeForUser("9198557575", "405571", "1050");
        db.close();
        System.out.println("answer = " + answer);
        
        db.open();
        System.out.println("Unique code is valid ? " + db.isUniqueCodeValid("5263"));
        db.close();
    }
}
