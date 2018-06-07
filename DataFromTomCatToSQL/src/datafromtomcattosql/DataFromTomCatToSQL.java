/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datafromtomcattosql;

import com.khtm.database.DBHandler;
import com.khtm.file.FileHandle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author alireza
 */
public class DataFromTomCatToSQL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        echo("Start application");
        echo("Select function");
        System.out.println("[1] Generate SQL files.\n[2] Insert SQL files to database.");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int ans = Integer.parseInt(in.readLine());
        switch(ans){
            case 1:
                FileHandle fileHandle = new FileHandle("tomcatlog");
                {
                    try {
                        fileHandle.readTomcatLog();
                    } catch (IOException ex) {
                        System.err.println("[*] ERROR - main : " + ex);
                    }
                }
                break;
            case 2:
                DBHandler db = new DBHandler();
                File[] sqlFilesServiceUser = new File("sqlfile/serviceuser").listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.getName().endsWith(".sql");
                    }
                });
                File[] sqlFilesNotification = new File("sqlfile/notification").listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.getName().endsWith(".sql");
                    }
                });
                for(File file : sqlFilesServiceUser){
                    db.open();
                    try {
                        db.exeSQLFile(file);
                    } catch (IOException ex) {
                        System.err.println("[*] ERROR - main 02 : " + ex);
                    }
                    db.close();
                    echo("File -> " + file.getName() + " inserted.");
                }
                for(File file : sqlFilesNotification){
                    db.open();
                    try {
                        db.exeSQLFile(file);
                    } catch (IOException ex) {
                        System.err.println("[*] ERROR - main 03 : " + ex);
                    }
                    db.close();
                    echo("File -> " + file.getName() + " inserted.");
                }
                break;
            default:
                echo("Function selected is not valid.");
                echo("Finish.");
                break;
        }
        
    }
    
    public static void echo(String msg){
        System.out.println("[*] INFO : " + msg);
    }
    
}