package ir.rayapars.honarfakher.classes;

import com.google.gson.annotations.SerializedName;

public class LoginMsg {

    public String status;
    public String msg;
    public String MDU;

    @SerializedName("uid")
    public String userId;
    public String username;

}
