/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khtm.report;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alireza
 */
public class TimeManager {
    public static List<String> getDateListProcess(){
        Calendar cal = Calendar.getInstance();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, 5);
        cal.set(Calendar.DAY_OF_MONTH, 9);
        List<String> lst = new LinkedList<>();
        while(!currentDate.equals(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()))){
            cal.add(Calendar.DAY_OF_YEAR, 1);
            String date = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            lst.add(date);
        }
        return lst;
    }
}
