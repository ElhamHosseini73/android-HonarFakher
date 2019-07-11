package ir.rayapars.honarfakher.classes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("products")
    Call<List<ProductDetails>> GetProducts(@Query("KEY") String key,
                                           @Query("page") String page,
                                           @Query("perpage") String perPage,
                                           @Query("featured") String featured,
                                           @Query("popular") String popular,
                                           @Query("sid") String sid,
                                           @Query("order") String order,
                                           @Query("quantity") String quantity,
                                           @Query("discount") String discount);

    @GET("news")
    Call<NewsLIst> getNews(@Query("KEY") String key, @Query("page") String page, @Query("perpage") String perPage, @Query("id") String id);

    @GET("product")
    Call<ProductDetails> productDetails(@Query("KEY") String key, @Query("id") String id);

    @FormUrlEncoded
    @POST("login")
    Call<List<LoginMsg>> login(@Field("KEY") String key, @Field("username") String username, @Field("password") String password);

    @GET("sections")
        Call<List<Sections>> getSections(@Query("KEY") String key, @Query("parent") String parent);

    @FormUrlEncoded
    @POST("addcomment")
    Call<List<LoginMsg>> addComment(@Field("KEY") String key, @Field("full_name") String name, @Field("comment") String comment, @Field("pid") String idProduct, @Field("rating") String rating);

    @GET("search")
    Call<List<Search>> search(@Query("KEY") String key, @Query("page") String page, @Query("perpage") String perPage, @Query("q") String q);

    @FormUrlEncoded
    @POST("preRegister")
    Call<List<LoginMsg>> preRegister(@Field("KEY") String key, @Field("mobile") String username);

    @FormUrlEncoded
    @POST("register")
    Call<List<LoginMsg>> register(@Field("KEY") String key, @Field("mobile") String mobile, @Field("verify_code") String verify_code);


    @FormUrlEncoded
    @POST("change_password")
    Call<List<LoginMsg>> change_password(@Field("KEY") String key, @Field("id") String id, @Field("MDU") String MDU, @Field("password") String password, @Field("old_password") String oldPassword);

    @GET("customers_info")
    Call<List<Customer>> retrieve(@Query("KEY") String key, @Query("uid") String uid, @Query("MDU") String MDU);

    @FormUrlEncoded
    @POST("update_customer")
    Call<ServerMsg> update_customer(@Field("KEY") String key, @Field("uid") String uid, @Field("MDU") String MUD,
                                    @Field("full_name") String full_name, @Field("email") String email, @Field("gender") String gender,
                                    @Field("tel") String tel, @Field("mobile") String mobile, @Field("state") String state, @Field("city") String city,
                                    @Field("address") String address, @Field("postal_code") String postal_code);


    @FormUrlEncoded
    @POST("checkout")
    Call<Checkout> checkout(@Field("KEY") String key,
                            @Field("order") String order,
                            @Field("uid") String uid,
                            @Field("MDU") String MDU,
                            @Field("payment") String payment,
                            @Field("name") String name,
                            @Field("address") String address,
                            @Field("state") String state,
                            @Field("city") String city,
                            @Field("postcode") String postcode,
                            @Field("telephone") String telephone,
                            @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("verify")
    Call<List<Checkout>> verify(@Field("KEY") String key, @Field("uid") String uid, @Field("MDU") String MDU, @Field("payment") String payment, @Field("iN") String iN, @Field("old") String old, @Field("refID") String refID);

    @GET("slider")
        Call<List<Slider>> getSliderPictures(@Query("KEY") String key);

    @GET("orders")
    Call<List<OrdersList>> getOrders(@Query("KEY") String key, @Query("uid") String uid, @Query("MDU") String MDU);

    @GET("order")
    Call<OrderInfo> getOrderDetails(@Query("KEY") String key, @Query("uid") String uid, @Query("MDU") String MDU, @Query("id") String id);

    @GET("shop")
    Call<List<About>> about(@Query("KEY") String key);

    @GET("states")
    Call<List<SpinnerItems_counties>> getStates(@Query("KEY") String key);

    @GET("cities")
    Call<List<SpinnerItems_cities>> getCities(@Query("KEY") String key, @Query("state") String state);

    @GET("cities")
    Call<List<ModelCity>>city(@Query("KEY") String key, @Query("state") String state);

    @FormUrlEncoded
    @POST("pre_register")
    Call<List<ModelPerRegister>>perRegister(@Field("KEY") String key, @Field("mobile") String mobile);


    @FormUrlEncoded
    @POST("pre_register")
    Call<List<ModelRegister>>perRegister(@Field("KEY") String key, @Field("mobile") String mobile,
                                         @Field("code") String code, @Field("password") String password);


    @FormUrlEncoded
    @POST("Remember")
    Call<List<ModelPerRegister>>remember(@Field("KEY") String key, @Field("mobile") String mobile);



    @FormUrlEncoded
    @POST("Remember")
    Call<List<ModelResetPass>>resetPassword(@Field("KEY") String key,
                                            @Field("mobile") String mobile,
                                            @Field("code") String code,
                                            @Field("password") String password);



}
