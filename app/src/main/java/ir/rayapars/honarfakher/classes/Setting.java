package ir.rayapars.honarfakher.classes;

import com.orm.SugarRecord;

//this class records uid and MDU so the user wouldn't be logged out even if he/she closes the App and Open ot again
//We extends App class like:  extends AppSugar
public class Setting extends SugarRecord {
    public String uid;
    public String MDU;


    public Setting(String uid, String MDU, String mobile) {
        this.uid = uid;
        this.MDU = MDU;

    }

    public Setting() {
    }
}
