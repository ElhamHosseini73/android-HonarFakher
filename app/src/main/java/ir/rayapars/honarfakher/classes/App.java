package ir.rayapars.honarfakher.classes;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orm.SugarApp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends SugarApp {

    public static List<ProductDetails> pd = new ArrayList<>();
    public static APIInterface apiInterface;
    private static Retrofit retrofit;

    String baseUrl = "http://turksen.demopars.ir/webservice/";

    @Override
    public void onCreate() {

        Fresco.initialize(this);
        super.onCreate();

        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        apiInterface = retrofit.create(APIInterface.class);

    }


}
