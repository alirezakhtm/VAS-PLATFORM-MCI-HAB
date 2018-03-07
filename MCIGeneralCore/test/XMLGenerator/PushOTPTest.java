/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XMLGenerator;

import com.fidar.mci.hub.webservice.handler.WSXMLGenerator;
import com.fidar.queue.object.ReceiveMsgObject;
import com.fidar.queue.object.SubUserObject;

/**
 *
 * @author alirezakhtm
 */
public class PushOTPTest {
    public static void main(String[] args) {
        WSXMLGenerator generator = new WSXMLGenerator();
        String strOutput = 
                generator.getPushOTP(
                        new SubUserObject(
                                12, 
                                new ReceiveMsgObject("HI", "09194018087", "09128554075", "110", "1")),
                        5
                );
        System.out.println(strOutput);
    }
}
