package ir.rayapars.honarfakher.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ali ahmadian on 5/26/2019.
 */

public class ModelColor {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("code")
    @Expose
    private ColorCode code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColorCode getCode() {
        return code;
    }

    public void setCode(ColorCode code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int select=0;

}
