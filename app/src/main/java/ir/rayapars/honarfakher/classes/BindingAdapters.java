package ir.rayapars.honarfakher.classes;


import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

public class BindingAdapters {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(SimpleDraweeView draweeImage, String url) {
        if (url != null || draweeImage != null) {
            draweeImage.setImageURI(url);
        }

    }


    @BindingAdapter("setPrice")
    public static void setPrice(TextView textView ,String price){

        if (textView!=null){
            textView.setText(" قیمت :"+String.valueOf(String.format("%,d",Integer.parseInt(price)))+" تومان ");
        }

    }

    @BindingAdapter("price")
    public static void price(TextView textView,String price){

        if (textView!=null){
//            int totalPrice = count* Integer.parseInt(price);
            textView.setText(String.valueOf(String.format("%,d",Integer.parseInt(price)))+" تومان ");
        }

    }




    @BindingAdapter("image")
    public static void image(ImageView imageView, String url) {

        if (imageView!=null){

            Picasso.with(imageView.getContext()).load(url).fit().into(imageView);
        }

    }


    @BindingAdapter("setDate")
    public static void setDate(TextView textView, String dateTime) {
        if (dateTime != null) {
            String[] strings = dateTime.split(" ");
            textView.setText(strings[0]);

        }
    }

    @BindingAdapter("setTime")
    public static void setTime(TextView textView, String dateTime) {
        if (dateTime != null) {
            String[] strings = dateTime.split(" ");
            textView.setText(strings[1]);
        }
    }

    @BindingAdapter("setProductQuantity")
    public static void setProductQuantity(TextView textView, String text) {
        textView.setText("موجودی: " + text);
    }

    @BindingAdapter("setTextWithCurrency")
    public static void setTextWithCurrency(TextView textView, String text) {

        try {

            text.replace(",", "");

            Long price = Long.parseLong(text);

            textView.setText(String.format("%,d", price) + " تومان ");

        } catch (Exception e) {

            textView.setText(text);

        }

    }

}
