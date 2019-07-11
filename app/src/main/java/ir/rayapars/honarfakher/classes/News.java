package ir.rayapars.honarfakher.classes;

import com.google.gson.annotations.SerializedName;

public class News {

    public String id;
    public String title;

    @SerializedName("short_text")
    public String shortText;

    @SerializedName("full_text")
    public String fullText;

    @SerializedName("reg_dabe")
    public String regDabe;

    public String url;
    public String image;
}
