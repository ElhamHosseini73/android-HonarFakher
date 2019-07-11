package ir.rayapars.honarfakher.classes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetails {

    public String id;
    public String title;
    public String title2;
    public String section_title;
    public String manufacture;
    private String description;
    public String price;
    public String section_parent;
    public String final_price;
    public String discount;
    public String status;
    private String quantity;
    private String guarantee;
    private String sender;
    private String seller;
    private String englishTitle;
    private String persianTitle;
    public String max_order;
    public String min_order;
    public String featured;
    public String short_text;
    public String full_text;
    public List<MyImage> image;
    public int numberOfProduct = 0;
    public String manufacture_title;
    public String url;

    public String msg;
    public String rate;
    public String comments;


    public List<MySpicification> specificatin;
    public List<MyReviews> reviews;


    /*
"section_id": "61",
"section_title": "بخش امتحانی",
"price": "20000",
"discount": "20",
"final_price": 16000,
"min_order": "1",
"max_order": "0",
"quantity": "0",
"manufacture_id": "21",
"manufacture_title": "ال جی",
"image": [
  {
"src": "http://fasleshekar.demopars.ir/productimage/66-1-1547286917.jpg"
}*/

    public ProductDetails(String id, String title, String description, String price, String discount, String image, String quantity, String guarantee, String sender, String seller, String englishTitle, String persianTitle, String sid, long finalPrice) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.discount = discount;

        this.quantity = quantity;
        this.guarantee = guarantee;
        this.sender = sender;
        this.seller = seller;
        this.englishTitle = englishTitle;
        this.persianTitle = persianTitle;
        this.sid = sid;
        this.finalPrice = finalPrice;
    }

    @SerializedName("section_id")
    public String sid;
    @SerializedName("manufacture_id")
    public String cid;
    private long finalPrice;


    public ProductDetails() {
        this.id = id;
        this.title = title;
        this.price = price;
        this.discount = discount;

    }

    public ProductDetails(String id, String title, String price, String discount, String image, String description, String quantity) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.discount = discount;

        this.description = description;
        this.quantity = quantity;
    }

    public ProductDetails(String id, String title, String description, String price, String discount, String image, String quantity, String guarantee, String sender, String seller, String englishTitle, String persianTitle, long finalPrice) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.discount = discount;

        this.quantity = quantity;
        this.guarantee = guarantee;
        this.sender = sender;
        this.seller = seller;
        this.englishTitle = englishTitle;
        this.persianTitle = persianTitle;
        this.finalPrice = finalPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setFinalPrice(long finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getFinalvPrice() {
        return String.format("%,d", Long.parseLong(price) - (Long.parseLong(price) * (Long.parseLong(discount)) / 100));
    }

    public String getFinalvPrice2() {
        return final_price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
