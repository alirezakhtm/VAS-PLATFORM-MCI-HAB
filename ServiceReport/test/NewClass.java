
import com.khtm.database.DBHandler;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alireza
 */
public class NewClass {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, 4);
        cal.set(Calendar.DAY_OF_MONTH, 19);
        List<String> lst = new LinkedList<>();
        while(!currentDate.equals(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()))){
            cal.add(Calendar.DAY_OF_YEAR, 1);
            String date = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            lst.add(date);
        }
        for(String s : lst){
            System.out.println(">>> " + s);
        }
    }
}
