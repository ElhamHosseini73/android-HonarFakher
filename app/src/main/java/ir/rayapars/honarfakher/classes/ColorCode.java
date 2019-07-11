package ir.rayapars.honarfakher.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ali ahmadian on 5/30/2019.
 */

public class ColorCode {

    @SerializedName("red")
    @Expose
    private Integer red;
    @SerializedName("green")
    @Expose
    private Integer green;
    @SerializedName("blue")
    @Expose
    private Integer blue;

    public Integer getRed() {
        return red;
    }

    public void setRed(Integer red) {
        this.red = red;
    }

    public Integer getGreen() {
        return green;
    }

    public void setGreen(Integer green) {
        this.green = green;
    }

    public Integer getBlue() {
        return blue;
    }

    public void setBlue(Integer blue) {
        this.blue = blue;
    }

}
