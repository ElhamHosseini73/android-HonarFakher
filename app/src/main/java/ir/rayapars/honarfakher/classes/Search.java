package ir.rayapars.honarfakher.classes;

import com.google.gson.annotations.SerializedName;

public class Search {

    @SerializedName("product_name")
    public String name;

    @SerializedName("section_id")
    public String sId;

    @SerializedName("image")
    public String url;
    @SerializedName("product_price")
    public String price;
    public String discount;

    @SerializedName("product_id")
    public String id;
    public String product_name_en;
    public String exist;
    public String msg;
    public String status;
    public String final_price;
    @SerializedName("product_quantity")
    public String productQuantity;

    public Search(String name, String price, String url, String id) {
        this.name = name;
        this.price = price;
        this.url = url;
        this.id = id;
    }
}
