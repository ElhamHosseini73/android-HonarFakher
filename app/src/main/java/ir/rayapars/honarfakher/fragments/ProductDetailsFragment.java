package ir.rayapars.honarfakher.fragments;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import ir.rayapars.honarfakher.BR;
import ir.rayapars.honarfakher.DialogFragments.LoadingDialog;
import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.adapters.UniversalAdapter2;
import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.MyReviews;
import ir.rayapars.honarfakher.classes.MySpicification;
import ir.rayapars.honarfakher.classes.ProductDetails;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.classes.Sections;
import ir.rayapars.honarfakher.classes.Setting;
import ir.rayapars.honarfakher.classes.WishList;
import ir.rayapars.honarfakher.databinding.FragmentProductDetailsBinding;
import ir.rayapars.honarfakher.databinding.ItemWondersBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsFragment extends Fragment {

    View x;
    List<MySpicification> mp = new ArrayList<>();
    List<MySpicification> itemSpecifications = new ArrayList<>();
    List<MySpicification> itemComments = new ArrayList<>();
    List<MyReviews> reviewsFromTheServer = new ArrayList<>();
    FragmentProductDetailsBinding binding;

    ProductDetails productDetails;

    public String idProduct, sectionParent;
    String titleProduct;

    UniversalAdapter2 adapterWonders;
    List<ProductDetails> productsWonders;
    String fullText;
    boolean showFullText;
    List<Sections> categories;
    Boolean checkId = false;
    String url;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentProductDetailsBinding.inflate(getLayoutInflater());
        x = binding.getRoot();
        x.setFocusable(true);
        x.setClickable(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });



        binding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, url);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.app_name)));

            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");

        binding.nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ScrollPositionObserver());
        setContents();



        //دکمه افزودن به سبد خرید
        binding.addToCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentTransaction t = getFragmentManager().beginTransaction();
                ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();

                //check Quantity
                if (Integer.parseInt(productDetails.getQuantity()) > 0) {

                    if (App.pd.size() > 0) {

                        for (int f = 0; f < App.pd.size(); f++) {

                            if (App.pd.get(f).id.equals(idProduct)) {

                                if (Integer.parseInt(productDetails.getQuantity()) > App.pd.get(f).numberOfProduct) {

                                    checkId = true;

                                    int k = App.pd.get(f).numberOfProduct + 1;
                                    productDetails.numberOfProduct = k;
                                    App.pd.set(f, productDetails);

                                    t.add(R.id.frame_layout, shoppingCartFragment).addToBackStack("").commit();

                                } else {

                                    checkId = true;
                                    Toast.makeText(getContext(), "تعداد بیشتر از موجودی است", Toast.LENGTH_LONG).show();
                                }

                                break;

                            }

                        }

                        if (checkId == false) {

                            App.pd.add(productDetails);
                            App.pd.get(App.pd.indexOf(productDetails)).numberOfProduct++;
                            t.add(R.id.frame_layout, shoppingCartFragment).addToBackStack(" ").commit();

                        }

                    } else {


                        App.pd.add(productDetails);
                        App.pd.get(App.pd.indexOf(productDetails)).numberOfProduct = 1;
                        t.add(R.id.frame_layout, shoppingCartFragment).addToBackStack(" ").commit();

                    }
                }

            }
        });

        binding.specification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mp == null) {
                    Toast.makeText(getContext(), "مشخصات بیشتری برای این کالا وجود ندارد", Toast.LENGTH_LONG).show();
                } else {
                    FragmentTransaction t = getFragmentManager().beginTransaction();
                    ItemSpecificationFragment fragment = new ItemSpecificationFragment();
                    fragment.ms = mp;
                    fragment.title = titleProduct;
                    t.add(R.id.frame_layout, fragment).addToBackStack(" ").commit();
                }
            }
        });

        binding.userComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mp == null) {
                    Toast.makeText(getContext(), "نظری برای این کالا وجود ندارد", Toast.LENGTH_LONG).show();

                    List<Setting> list = Setting.listAll(Setting.class);

                    if (list.size() == 0) {

                        FragmentTransaction transactionLogin = getFragmentManager().beginTransaction();

                        LoginFragment loginFragment = new LoginFragment();

                        loginFragment.setOnLoginSuccessListener(new LoginFragment.OnLoginSuccessListener() {
                            @Override
                            public void onSuccess() {

                            }
                        });

                        transactionLogin.add(R.id.frame_layout, loginFragment).addToBackStack("").commit();

                    } else {

                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        WriteCommentFragment writeCommentFragment = new WriteCommentFragment();
                        writeCommentFragment.idProduct = productDetails.getId();
                        fragmentTransaction.add(R.id.frame_layout, writeCommentFragment).addToBackStack("").commit();

                    }

                } else {

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    CommentsFragment commentsFragment = new CommentsFragment();

                    commentsFragment.productDetails = productDetails;
                    commentsFragment.title = titleProduct;
                    ft.add(R.id.frame_layout, commentsFragment).addToBackStack("").commit();
                }

            }
        });

        binding.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (App.pd.size() > 0) {
                    FragmentTransaction t = getFragmentManager().beginTransaction();
                    t.add(R.id.frame_layout, new ShoppingCartFragment()).addToBackStack("").commit();
                } else {
                    FragmentTransaction transactionCart = getFragmentManager().beginTransaction();
                    transactionCart.add(R.id.frame_layout, new CartFragment()).addToBackStack("").commit();
                }
            }
        });

        showFullText = true;
        productsWonders = new ArrayList<>();
        categories = new ArrayList<>();

        binding.continu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (showFullText == true) {

                    binding.conten.setText(fullText);
                    showFullText = false;
                    binding.continu.setText("بستن");
                } else {

                    String substr = fullText.substring(0, 70);
                    binding.conten.setText(substr + " ...");
                    showFullText = true;
                    binding.continu.setText("ادامه مطالب");
                }
            }
        });


        binding.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Setting setting = Select.from(Setting.class).first();
                if (setting!=null) {

                    List<WishList> listFavorite = WishList.listAll(WishList.class);

                    if (listFavorite.size() == 0) {

                        WishList wishList = new WishList();
                        wishList.title = productDetails.title;
                        wishList.title2 = productDetails.title2;
                        wishList.pid = productDetails.id;
                        wishList.final_price = productDetails.final_price;
                        wishList.price = productDetails.price;
                        wishList.image = productDetails.image.get(0).src;

                        wishList.save();

                        binding.favorite.setImageResource(R.drawable.ic_favorite_black_24dp);
                        Toast.makeText(x.getContext(), "به لیست علاقه مندی اضافه گردید.", Toast.LENGTH_SHORT).show();

                    } else {

                        boolean checkExist = false;

                        for (int i = 0; i < listFavorite.size(); i++) {

                            if (productDetails.id.equals(listFavorite.get(i).pid)) {

                                checkExist = true;

                                WishList item = listFavorite.get(i);
                                item.delete();

                                break;
                            }
                        }

                        if (checkExist) {

                            Toast.makeText(x.getContext(), "از لیست علاقه مندی حذف گردید.", Toast.LENGTH_SHORT).show();
                            binding.favorite.setImageResource(R.drawable.ic_favorite_gray_24dp);

                        } else {

                            Toast.makeText(x.getContext(), "به لیست علاقه مندی اضافه گردید.", Toast.LENGTH_SHORT).show();

                            binding.favorite.setImageResource(R.drawable.ic_favorite_black_24dp);

                            WishList wishList = new WishList();
                            wishList.title = productDetails.title;
                            wishList.title2 = productDetails.title2;
                            wishList.pid = productDetails.id;
                            wishList.final_price = productDetails.final_price;
                            wishList.image = productDetails.image.get(0).src;

                            wishList.save();

                        }
                    }
                }
                else {
                    Toast.makeText(getContext(), "برای اضافه کردن به علاقه مندی باید لاگین باشید", Toast.LENGTH_SHORT).show();
                }

            }
        });


        getRelatedProducts("1", "10", "", "", sectionParent);
        productDetails(idProduct);
        getSections(sectionParent);
        return x;
    }

    private class ScrollPositionObserver implements ViewTreeObserver.OnScrollChangedListener {
        private int mImageViewHeight;

        public ScrollPositionObserver() {
            mImageViewHeight = 400;
        }

        @Override
        public void onScrollChanged() {
            int scrollY = Math.min(Math.max(binding.nestedScrollView.getScrollY(), 0), mImageViewHeight);
            //   int alpha = scrollY /  mImageViewHeight;
            //   binding.toolbar.getBackground().setAlpha(alpha);
        }
    }

    public void setContents() {

        //  binding.price.setText(product.getPrice());
        binding.price.setPaintFlags(binding.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        //binding.finalPrice.setText(product.getFinalvPrice());

        binding.userComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.frame_layout, new CommentsFragment()).addToBackStack("").commit();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void getRelatedProducts(final String page, final String perPage, final String featured, final String popular, String sid) {

        final LoadingDialog progressDialog = new LoadingDialog();
        progressDialog.show(getFragmentManager(), "");
        progressDialog.setCancelable(false);

        Call<List<ProductDetails>> call = App.apiInterface.GetProducts(PublicVariable.key, page, perPage, featured, popular, sid, "", "", "");

        call.enqueue(new Callback<List<ProductDetails>>() {

            @Override
            public void onResponse(Call<List<ProductDetails>> call, Response<List<ProductDetails>> response) {

                List<ProductDetails> temp = response.body();
                productsWonders.addAll(temp);

                if (adapterWonders == null) {

                    adapterWonders = new UniversalAdapter2(R.layout.item_wonders, productsWonders, BR.product);
                    binding.sameRecyclerView.setAdapter(adapterWonders);
                    LinearLayoutManager layoutManagerWonders = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    binding.sameRecyclerView.setLayoutManager(layoutManagerWonders);

                } else {

                    int intPage = Integer.parseInt(page);

                    adapterWonders.setList(productsWonders);
                    adapterWonders.notifyItemInserted(intPage * 10);

                }
                adapterWonders.setOnItemClickListener(new UniversalAdapter2.OnItemClickListener() {

                    @Override
                    public void onClick(ViewDataBinding binding, int position) {

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_out_right, android.R.anim.slide_out_right);
                        ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                        productDetailsFragment.idProduct = productsWonders.get(position).id;
                        productDetailsFragment.sectionParent = productsWonders.get(position).sid;
                        ft.add(R.id.frame_layout, productDetailsFragment).addToBackStack("").commit();

                    }
                });


                progressDialog.dismiss();

                adapterWonders.setOnItemBindListener(new UniversalAdapter2.OnItemBindListener() {

                    @Override
                    public void onBind(ViewDataBinding binding, int position) {

                        int intPage = Integer.parseInt(page);

                        if (position + 1 == intPage * 10) {

                            intPage++;

                            getRelatedProducts(intPage + "", "10", "", "", sectionParent);

                        }


                        ItemWondersBinding wondersBinding = (ItemWondersBinding) binding;

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ProductDetails>> call, Throwable t) {
            }
        });


    }

    private void productDetails(String id) {

        final LoadingDialog progressDialog = new LoadingDialog();
        progressDialog.show(getFragmentManager(), "");
        progressDialog.setCancelable(false);

        Call<ProductDetails> call = App.apiInterface.productDetails(PublicVariable.key, id);

        call.enqueue(new Callback<ProductDetails>() {

            @Override
            public void onResponse(Call<ProductDetails> call, Response<ProductDetails> response) {

                if (response.body().status.equals("1")) {


                    List<WishList> listFavorite = WishList.listAll(WishList.class);
                    boolean checkExist = false;
                    for (int i = 0; i < listFavorite.size(); i++) {

                        if (response.body().id.equals(listFavorite.get(i).pid)) {

                            binding.favorite.setImageResource(R.drawable.ic_favorite_black_24dp);

                            break;
                        }
                    }

                    url = response.body().url;


                    for (int i = 0; i < response.body().image.size(); i++) {

                        DefaultSliderView textSliderView = new DefaultSliderView(getContext());

                        textSliderView

                                .setScaleType(BaseSliderView.ScaleType.Fit)
                                .image(response.body().image.get(i).src);

                        binding.slider.addSlider(textSliderView);
                    }

                    if (response.body().featured.equals("0")) {

                        binding.wonder.setVisibility(View.GONE);

                    }

                    fullText = response.body().full_text;

                    binding.content.setText(response.body().short_text);


                    if (response.body().full_text.length() > 80) {

                        binding.continu.setVisibility(View.VISIBLE);

                        String substr = response.body().full_text.substring(0, 70);

                        binding.conten.setText(substr + " ...");

                    } else {

                        binding.conten.setText(response.body().full_text);
                    }


                    binding.titlePersian.setText(response.body().title);
                    binding.titleEnglish.setText(response.body().title2);

                    titleProduct = response.body().title;
                    Long price = Long.parseLong(response.body().price);
                    Long finalPrice = Long.parseLong(response.body().final_price);

                    if (response.body().discount.equals("0")) {

                        binding.price.setVisibility(View.GONE);

                    }

                    binding.price.setText(String.format("%,d", price) + " تومان ");
                    binding.finalPrice.setText(String.format("%,d", finalPrice) + " تومان ");

                    mp = response.body().specificatin;
                    reviewsFromTheServer = response.body().reviews;

                    productDetails = response.body();



                    if (Integer.parseInt(productDetails.getQuantity())< 0){

                        binding.addToCart.setBackgroundColor(getResources().getColor(R.color.red));
                        binding.addToCart.setText("ناموجود");


                    }
                    progressDialog.dismiss();


                } else {

                    Toast.makeText(x.getContext(), "" + response.body().msg, Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<ProductDetails> call, Throwable t) {

                progressDialog.dismiss();

            }

        });

    }

    private void getSections(String parent) {

        Call<List<Sections>> call = App.apiInterface.getSections(PublicVariable.key, parent);

        call.enqueue(new Callback<List<Sections>>() {

            @Override
            public void onResponse(Call<List<Sections>> call, final Response<List<Sections>> response) {

                categories = response.body();

                UniversalAdapter2 categoryAdapter = new UniversalAdapter2(R.layout.item_horizontal_category2, categories, BR.category);

                categoryAdapter.setOnItemClickListener(new UniversalAdapter2.OnItemClickListener() {

                    @Override
                    public void onClick(ViewDataBinding binding, int position) {

                        ProductsFragment productsFragment = new ProductsFragment();
                        productsFragment.featuredProduct = "";
                        productsFragment.orderProduct = "";
                        productsFragment.popularProduct = "";
                        productsFragment.discount = "";
                        productsFragment.quantity = "";
                        productsFragment.idParent = response.body().get(0).id;
                        productsFragment.titleProduct = response.body().get(0).title;

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_out_right, android.R.anim.slide_out_right);
                        ft.add(R.id.frame_layout, productsFragment).addToBackStack("").commit();

                    }
                });

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                binding.categoryRecyclerView.setLayoutManager(layoutManager);
                binding.categoryRecyclerView.setAdapter(categoryAdapter);

            }

            @Override
            public void onFailure(Call<List<Sections>> call, Throwable t) {

            }
        });
    }

}
