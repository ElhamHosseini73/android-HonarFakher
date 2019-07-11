package ir.rayapars.honarfakher.classes;
import com.orm.SugarRecord;

public class MobileNumberHolder extends SugarRecord {
    public String mobile;


    public MobileNumberHolder(String mobile) {
        this.mobile = mobile;
    }

    public MobileNumberHolder() {
    }
}
