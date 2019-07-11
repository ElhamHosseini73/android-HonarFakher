package ir.rayapars.honarfakher.classes;

        import com.google.gson.annotations.SerializedName;

public class BestSeller {

    @SerializedName("title")
    public String name;
    @SerializedName("image")
    public String url;
    public String price;
    public String id;

    public BestSeller(String name, String price, String url) {
        this.name = name;
        this.price = price;
        this.url = url;
    }
}
