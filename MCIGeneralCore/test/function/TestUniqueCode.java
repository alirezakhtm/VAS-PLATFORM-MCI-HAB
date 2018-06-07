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
public class TestUniqueCode {
    public static void main(String[] args) {
        DatabaseHandler db = new DatabaseHandler();
        db.open();
        int uniqueCode = db.getUniqueCodeForUser("9194018087", 1);
        db.close();
        System.out.println("unique Code : " + uniqueCode);
        
        db.open();
        int invitedNumber = db.getInvitedNumberByUser("9194018087", 1);
        db.close();
        System.out.println("Invited Number : " + invitedNumber);
        
        System.out.println("افراد دعوت شده از طرف شما " + 5 + " نفر می باشند.");
        
        db.open();
        System.out.println("is user get reward in past >> " + db.isUserGetRewardInPast("9194018087", 1));
        db.close();
        
        db.open();
        System.out.println("Reward for you >>> " + db.getRewardForThisUser("9194018087", "chrg1000", 1));
        db.close();
        
        db.open();
        System.out.println("ServiceCode >>> " + db.getServiceCode("405571"));
        db.close();
        
        String msgReward = "جایزه شما ::tittle::\n" +
                            "::code::";
        msgReward = msgReward
                    .replace("::tittle::", "۱۰۰۰ تومان شارژ همراه اول")
                    .replace("::code::", "21313213211");
        System.out.println("msgReward >> " + msgReward);
    }
}
