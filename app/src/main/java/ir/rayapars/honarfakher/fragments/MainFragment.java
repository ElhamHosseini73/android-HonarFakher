package ir.rayapars.honarfakher.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ir.rayapars.honarfakher.BR;
import ir.rayapars.honarfakher.DialogFragments.LoadingDialog;
import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.adapters.UniversalAdapter2;
import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.News;
import ir.rayapars.honarfakher.classes.NewsLIst;
import ir.rayapars.honarfakher.classes.ProductDetails;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.classes.Sections;
import ir.rayapars.honarfakher.classes.Setting;
import ir.rayapars.honarfakher.classes.Slider;
import ir.rayapars.honarfakher.databinding.FragmentMainBinding;
import ir.rayapars.honarfakher.databinding.ItemWondersBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    List<ProductDetails> productsWonders, productsBestSelling, productsNew;
    List<Sections> categories;
    UniversalAdapter2 adapterWonders, adapterBestSelling, adapterNew, adapterNews;
    List<Slider> sliders;
    List<News> news;
    View x;

    //Timer

    private static final String FORMAT = "%02d:%02d:%02d";
    private static final String FORMAT1 = "%02d";
    private static final String FORMAT2 = "%02d";
    private static final String FORMAT3 = "%02d";
    public String count;
    int seconds, minutes;

    FragmentMainBinding binding;
    Button btnRegister;

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(getLayoutInflater());

        x = binding.getRoot();
        x.setFocusable(true);
        x.setClickable(true);

        productsWonders = new ArrayList<>();
        productsBestSelling = new ArrayList<>();
        productsNew = new ArrayList<>();

        news = new ArrayList<>();

        categories = new ArrayList<>();

        View headerview = binding.navView.getHeaderView(0);

        btnRegister =  headerview.findViewById(R.id.btnRegister);

        Setting s = Select.from(Setting.class).first();

        if (s != null) {

            binding.includeLayout.btnProfile.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.GONE);

        } else {

            binding.includeLayout.btnProfile.setVisibility(View.GONE);
        }

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.includeLayout.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.app_name);


        binding.includeLayout.fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        binding.includeLayout.fab.setVisibility(View.GONE);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), binding.drawerLayout, binding.includeLayout.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);
        setContents();

        binding.includeLayout.btnProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                ProfileFragment pf = new ProfileFragment();
                pf.setExitListener(new ProfileFragment.ExitListener() {
                    @Override
                    public void onSuccess() {
                        Setting s = Select.from(Setting.class).first();
                        if (s != null) {

                            binding.includeLayout.btnProfile.setVisibility(View.VISIBLE);
                            btnRegister.setVisibility(View.GONE);

                        } else {
                            binding.includeLayout.btnProfile.setVisibility(View.GONE);
                            btnRegister.setVisibility(View.VISIBLE);
                        }
                    }
                });
                transaction.add(R.id.frame_layout, pf).addToBackStack("").commit();
            }
        });


        binding.includeLayout.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.frame_layout, new SearchFragment()).addToBackStack("").commit();
            }
        });

        binding.includeLayout.fullList2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductsFragment productsFragment = new ProductsFragment();
                productsFragment.featuredProduct = "";
                productsFragment.orderProduct = "";
                productsFragment.popularProduct = "1";
                productsFragment.discount = "";
                productsFragment.quantity = "";
                productsFragment.idParent = "";
                productsFragment.titleProduct = "محصولات پربیننده";

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_out_right, android.R.anim.slide_out_right);
                ft.add(R.id.frame_layout, productsFragment).addToBackStack("").commit();

            }
        });

        binding.includeLayout.fullListNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProductsFragment productsFragment = new ProductsFragment();
                productsFragment.featuredProduct = "";
                productsFragment.orderProduct = "";
                productsFragment.popularProduct = "";
                productsFragment.discount = "";
                productsFragment.quantity = "";
                productsFragment.idParent = "";
                productsFragment.titleProduct = "جدیدترین محصولات";

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_out_right, android.R.anim.slide_out_right);
                ft.add(R.id.frame_layout, productsFragment).addToBackStack("").commit();

            }
        });

        binding.includeLayout.count.setText(App.pd.size() + "");

        getSliderPictures();

        getSections("0");

        getProductWonders("1", "10", "1", "", "");
        getProductBestSelling("1", "10", "", "1", "");
        getProductNew("1", "10", "", "", "");
        getNews("1", "10", "");
        return x;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            // Handle the camera action
        } else if (id == R.id.nav_list) {

            binding.drawerLayout.closeDrawer(GravityCompat.START);
            FragmentTransaction transactionLogin = getFragmentManager().beginTransaction();
            transactionLogin.add(R.id.frame_layout, new CategoryFragment()).addToBackStack("").commit();


        } else if (id == R.id.nav_cart) {

            if (App.pd.size() > 0) {

                FragmentTransaction t = getFragmentManager().beginTransaction();
                t.add(R.id.frame_layout, new ShoppingCartFragment()).addToBackStack("").commit();

            } else {

                FragmentTransaction transactionCart = getFragmentManager().beginTransaction();
                transactionCart.add(R.id.frame_layout, new CartFragment()).addToBackStack("").commit();
            }

        } else if (id == R.id.nav_best_seller) {

            ProductsFragment productsFragment = new ProductsFragment();
            productsFragment.featuredProduct = "";
            productsFragment.orderProduct = "";
            productsFragment.popularProduct = "1";
            productsFragment.discount = "";
            productsFragment.quantity = "";
            productsFragment.idParent = "";
            productsFragment.titleProduct = "محصولات پرفروش";

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_out_right, android.R.anim.slide_out_right);
            ft.add(R.id.frame_layout, productsFragment).addToBackStack("").commit();

        } else if (id == R.id.nav_about_us) {

            binding.drawerLayout.closeDrawer(GravityCompat.START);
            FragmentTransaction transactionLogin = getFragmentManager().beginTransaction();
            transactionLogin.add(R.id.frame_layout, new AboutUsFragment()).addToBackStack("").commit();

        }

        binding.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void getSliderPictures() {

        Call<List<Slider>> call = App.apiInterface.getSliderPictures(PublicVariable.key);

        call.enqueue(new Callback<List<Slider>>() {

            @Override
            public void onResponse(Call<List<Slider>> call, Response<List<Slider>> response) {

                sliders = response.body();

                for (int i = 0; i < sliders.size(); i++) {

                    DefaultSliderView textSliderView = new DefaultSliderView(getContext());
                    textSliderView
                            //  .description(" "+locationItems.get(i).location_name+" ")
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .image(sliders.get(i).src);
                    binding.includeLayout.slider.addSlider(textSliderView);

                }
            }

            @Override
            public void onFailure(Call<List<Slider>> call, Throwable t) {

            }
        });
    }

    private void getSections(String parent) {

        Call<List<Sections>> call = App.apiInterface.getSections(PublicVariable.key, parent);

        call.enqueue(new Callback<List<Sections>>() {

            @Override
            public void onResponse(Call<List<Sections>> call, final Response<List<Sections>> response) {

                categories = response.body();

                UniversalAdapter2 categoryAdapter = new UniversalAdapter2(R.layout.item_horizontal_category, categories, BR.category);

                categoryAdapter.setOnItemClickListener(new UniversalAdapter2.OnItemClickListener() {

                    @Override
                    public void onClick(ViewDataBinding binding, int position) {

                        ProductsFragment productsFragment = new ProductsFragment();
                        productsFragment.featuredProduct = "";
                        productsFragment.orderProduct = "";
                        productsFragment.popularProduct = "";
                        productsFragment.discount = "";
                        productsFragment.quantity = "";
                        productsFragment.idParent = response.body().get(position).id;

                        if (response.body().get(position).title == null) {
                            productsFragment.titleProduct = "";
                        } else {
                            productsFragment.titleProduct = response.body().get(position).title;
                        }

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_out_right, android.R.anim.slide_out_right);
                        ft.add(R.id.frame_layout, productsFragment).addToBackStack("").commit();

                    }
                });

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                binding.includeLayout.categoryRecyclerView.setLayoutManager(layoutManager);

                binding.includeLayout.categoryRecyclerView.setAdapter(categoryAdapter);

            }

            @Override
            public void onFailure(Call<List<Sections>> call, Throwable t) {

            }
        });
    }

    private void getProductWonders(final String page, final String perPage, final String featured, final String popular, String sid) {

        final LoadingDialog progressDialog = new LoadingDialog();
        progressDialog.show(getFragmentManager(), "");
        progressDialog.setCancelable(false);

        Call<List<ProductDetails>> call = App.apiInterface.GetProducts(PublicVariable.key, page, perPage, featured, popular, sid, "", "", "");

        call.enqueue(new Callback<List<ProductDetails>>() {

            @Override
            public void onResponse(Call<List<ProductDetails>> call, Response<List<ProductDetails>> response) {

                List<ProductDetails> temp = response.body();

                try {

                    if (temp.get(0).getId()!=null){
                        productsWonders.addAll(temp);
                    }


                } catch (Exception e) {

                }

                if (adapterWonders == null) {

//                    if (temp.get(0).getId()!=null) {
                        adapterWonders = new UniversalAdapter2(R.layout.item_wonders, productsWonders, BR.product);
                        binding.includeLayout.wondersRecyclerView.setAdapter(adapterWonders);
                        LinearLayoutManager layoutManagerWonders = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        binding.includeLayout.wondersRecyclerView.setLayoutManager(layoutManagerWonders);

//                    }
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

                            getProductWonders(intPage + "", "10", "1", "", "");

                        }

                        ItemWondersBinding wondersBinding = (ItemWondersBinding) binding;

                        if (productsWonders.get(position).discount.equals("0")) {

                            wondersBinding.productPrice.setVisibility(View.INVISIBLE);

                        }

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ProductDetails>> call, Throwable t) {

                progressDialog.dismiss();

                new android.app.AlertDialog.Builder(getContext()).setMessage("عدم ارتباط با اینترنت")
                        .setPositiveButton("تلاش مجدد", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                getProductWonders("1", "10", "1", "", "");

                                getSections("0");
                                getSliderPictures();

                            }
                        }).setNegativeButton("خروج", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                }).setCancelable(false).show();

            }

        });
    }

    private void getProductBestSelling(final String page, final String perPage, final String featured, final String popular, String sid) {

        final LoadingDialog progressDialog = new LoadingDialog();
        progressDialog.show(getFragmentManager(), "");
        progressDialog.setCancelable(false);

        Call<List<ProductDetails>> call = App.apiInterface.GetProducts(PublicVariable.key, page, perPage, featured, popular, sid, "", "", "");

        call.enqueue(new Callback<List<ProductDetails>>() {

            @Override
            public void onResponse(Call<List<ProductDetails>> call, Response<List<ProductDetails>> response) {

                List<ProductDetails> temp = response.body();

                productsBestSelling.addAll(temp);

                if (adapterBestSelling == null) {

                    adapterBestSelling = new UniversalAdapter2(R.layout.item_wonders, productsBestSelling, BR.product);
                    binding.includeLayout.BestSellingProducts.setAdapter(adapterBestSelling);
                    LinearLayoutManager layoutManagerWonders = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    binding.includeLayout.BestSellingProducts.setLayoutManager(layoutManagerWonders);

                } else {

                    int intPage = Integer.parseInt(page);

                    adapterBestSelling.setList(productsBestSelling);
                    adapterBestSelling.notifyItemInserted(intPage * 10);

                }

                adapterBestSelling.setOnItemClickListener(new UniversalAdapter2.OnItemClickListener() {

                    @Override
                    public void onClick(ViewDataBinding binding, int position) {

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_out_right, android.R.anim.slide_out_right);
                        ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                        productDetailsFragment.idProduct = productsBestSelling.get(position).id;
                        productDetailsFragment.sectionParent = productsBestSelling.get(position).sid;
                        ft.add(R.id.frame_layout, productDetailsFragment).addToBackStack("").commit();

                    }
                });


                progressDialog.dismiss();

                adapterBestSelling.setOnItemBindListener(new UniversalAdapter2.OnItemBindListener() {

                    @Override
                    public void onBind(ViewDataBinding binding, int position) {

                        ItemWondersBinding wondersBinding = (ItemWondersBinding) binding;

                        if (productsBestSelling.get(position).discount.equals("0")) {

                            wondersBinding.productPrice.setVisibility(View.INVISIBLE);

                        } else {
                            wondersBinding.productPrice.setVisibility(View.VISIBLE);
                        }


//                        int intPage = Integer.parseInt(page);
//
//                        if (position + 1 == intPage * 10) {
//
//                            intPage++;
//
//                            getProductBestSelling(intPage + "", "10", "", "1", "");
//
//                        }

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ProductDetails>> call, Throwable t) {

                progressDialog.dismiss();

            }

        });
    }

    private void getProductNew(final String page, final String perPage, final String featured, final String popular, String sid) {

        final LoadingDialog progressDialog = new LoadingDialog();
        progressDialog.show(getFragmentManager(), "");
        progressDialog.setCancelable(false);

        Call<List<ProductDetails>> call = App.apiInterface.GetProducts(PublicVariable.key, page, perPage, featured, popular, sid, "", "", "");

        call.enqueue(new Callback<List<ProductDetails>>() {

            @Override
            public void onResponse(Call<List<ProductDetails>> call, Response<List<ProductDetails>> response) {

                List<ProductDetails> temp = response.body();

                productsNew.addAll(temp);

                if (adapterNew == null) {

                    adapterNew = new UniversalAdapter2(R.layout.item_wonders, productsNew, BR.product);
                    binding.includeLayout.NewProducts.setAdapter(adapterNew);
                    LinearLayoutManager layoutManagerWonders = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    binding.includeLayout.NewProducts.setLayoutManager(layoutManagerWonders);

                } else {

                    int intPage = Integer.parseInt(page);

                    adapterNew.setList(productsNew);
                    adapterNew.notifyItemInserted(intPage * 10);

                }

                adapterNew.setOnItemClickListener(new UniversalAdapter2.OnItemClickListener() {

                    @Override
                    public void onClick(ViewDataBinding binding, int position) {

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_out_right, android.R.anim.slide_out_right);
                        ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                        productDetailsFragment.idProduct = productsNew.get(position).id;
                        productDetailsFragment.sectionParent = productsNew.get(position).sid;
                        ft.add(R.id.frame_layout, productDetailsFragment).addToBackStack("").commit();

                    }
                });

                progressDialog.dismiss();

                adapterNew.setOnItemBindListener(new UniversalAdapter2.OnItemBindListener() {

                    @Override
                    public void onBind(ViewDataBinding binding, int position) {

                        ItemWondersBinding wondersBinding = (ItemWondersBinding) binding;

                        if (productsNew.get(position).discount.equals("0")) {

                            wondersBinding.productPrice.setVisibility(View.INVISIBLE);

                        } else {
                            wondersBinding.productPrice.setVisibility(View.VISIBLE);
                        }

                        int intPage = Integer.parseInt(page);

                        if (position + 1 == intPage * 10) {

                            intPage++;

                            getProductNew(intPage + "", "10", "", "", "");

                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<List<ProductDetails>> call, Throwable t) {

                progressDialog.dismiss();

            }

        });
    }

    private void getNews(final String page, final String perPage, final String id) {

        final LoadingDialog progressDialog = new LoadingDialog();
        progressDialog.show(getFragmentManager(), "");
        progressDialog.setCancelable(false);

        Call<NewsLIst> call = App.apiInterface.getNews(PublicVariable.key, page, perPage, id);

        call.enqueue(new Callback<NewsLIst>() {

            @Override
            public void onResponse(Call<NewsLIst> call, Response<NewsLIst> response) {

                progressDialog.dismiss();

                if (response.body().status.equals("1")) {

                    List<News> temp = response.body().news;
                    news.addAll(temp);

                    if (adapterNews == null) {

                        adapterNews = new UniversalAdapter2(R.layout.item_news, news, BR.news);
                        binding.includeLayout.news.setAdapter(adapterNews);
                        LinearLayoutManager layoutManagerWonders = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        binding.includeLayout.news.setLayoutManager(layoutManagerWonders);

                    } else {

                        int intPage = Integer.parseInt(page);

                        adapterNews.setList(news);
                        adapterNews.notifyItemInserted(intPage * 10);

                    }

                    adapterNews.setOnItemClickListener(new UniversalAdapter2.OnItemClickListener() {

                        @Override
                        public void onClick(ViewDataBinding binding, int position) {

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_out_right, android.R.anim.slide_out_right);
                            NewsFragment newsFragment = new NewsFragment();
                            newsFragment.id = news.get(position).id;
                            ft.add(R.id.frame_layout, newsFragment).addToBackStack("").commit();

                        }
                    });


                  /*



                    progressDialog.dismiss();

                    adapterNews.setOnItemBindListener(new UniversalAdapter2.OnItemBindListener() {

                        @Override
                        public void onBind(ViewDataBinding binding, int position) {

                           *//* ItemWondersBinding wondersBinding = (ItemWondersBinding) binding;

                            if (productsNew.get(position).discount.equals("0")) {

                                wondersBinding.productPrice.setVisibility(View.INVISIBLE);

                            } else {
                                wondersBinding.productPrice.setVisibility(View.VISIBLE);
                            }*//*

                            int intPage = Integer.parseInt(page);

                            if (position + 1 == intPage * 10) {

                                intPage++;

                                getNews(intPage + "", "10", "");

                            }
                        }
                    });

*/
                } else {

                    Toast.makeText(x.getContext(), "" + response.body().msg, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NewsLIst> call, Throwable t) {

                progressDialog.dismiss();
            }

        });
    }

    //second row of products

    private void setContents() {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.drawerLayout.closeDrawer(GravityCompat.START);

                FragmentTransaction transactionLogin = getFragmentManager().beginTransaction();

                LoginFragment loginFragment = new LoginFragment();

                loginFragment.setOnLoginSuccessListener(new LoginFragment.OnLoginSuccessListener() {
                    @Override
                    public void onSuccess() {
                        binding.includeLayout.btnProfile.setVisibility(View.VISIBLE);
                        btnRegister.setBackgroundColor(Color.CYAN);
                    }
                });

                transactionLogin.add(R.id.frame_layout, loginFragment).addToBackStack("").commit();
            }
        });


        Date currentTime = Calendar.getInstance().getTime();
        Date currentTime2 = Calendar.getInstance().getTime();
        currentTime2.setHours(24);
        currentTime2.setSeconds(0);
        currentTime2.setMinutes(0);
        long difference = currentTime2.getTime() - currentTime.getTime();


        new CountDownTimer(difference, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                binding.includeLayout.timerH.setText("" + String.format(FORMAT1,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                binding.includeLayout.timerM.setText("" + String.format(FORMAT2,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished))));
                binding.includeLayout.timerS.setText("" + String.format(FORMAT3,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                binding.includeLayout.timerH.setText("done!");
            }

        }.start();


        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR);
        int mMinute = c.get(Calendar.MINUTE);
        //binding.includeLayout.timerH.setText("ggggg");

        //cart
        binding.includeLayout.btnCart.setOnClickListener(new View.OnClickListener() {

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
    }

}
