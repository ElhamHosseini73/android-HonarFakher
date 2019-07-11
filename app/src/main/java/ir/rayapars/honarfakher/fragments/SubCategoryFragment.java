package ir.rayapars.honarfakher.fragments;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import ir.rayapars.honarfakher.BR;
import ir.rayapars.honarfakher.DialogFragments.LoadingDialog;
import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.adapters.UniversalAdapter2;
import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.Category;
import ir.rayapars.honarfakher.classes.ExpenCategory;
import ir.rayapars.honarfakher.classes.ProductDetails;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.classes.Sections;
import ir.rayapars.honarfakher.classes.Setting;
import ir.rayapars.honarfakher.databinding.FragmentSubCategoryBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    View x;
    RecyclerView categoryRecyclerView;
    UniversalAdapter2 adapterBestSelling, adapterNew;
    FragmentSubCategoryBinding binding;
    public Sections parent;
    public String parentID;
    Button btnRegister;
    List<ProductDetails> productsBestSelling, productsNew;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentSubCategoryBinding.inflate(getLayoutInflater());
        x = binding.getRoot();
        x.setFocusable(true);
        x.setClickable(true);

        View headerview = binding.navView.getHeaderView(0);

        btnRegister = (Button) headerview.findViewById(R.id.btnRegister);
        Setting s = Select.from(Setting.class).first();
        if (s != null) {
            binding.includeLayout.btnProfile.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.GONE);
        } else {
            binding.includeLayout.btnProfile.setVisibility(View.GONE);
        }

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.includeLayout.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.includeLayout.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(parent.title);

        productsBestSelling = new ArrayList<>();
        productsNew = new ArrayList<>();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), binding.drawerLayout, binding.includeLayout.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        setContents();
        getProductBestSelling("1", "10", "", "1", parentID);
        getProductNew("1", "10", "", "", parentID);


        //LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //instantiate your adapter with the list of genres
        //ExpendableCategoryAdapter adapter = new ExpendableCategoryAdapter(genres,(AppCompatActivity)getActivity());
        //binding.includeLayout.categoryRecyclerView.setLayoutManager(layoutManager);
        //binding.includeLayout.categoryRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.includeLayout.categoryRecyclerView.setLayoutManager(layoutManager);
        getSections(parentID);

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
        return x;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.frame_layout, new MainFragment()).commit();

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


    RecyclerView wondersRecyclerView;

    public void setContents() {

        View headerview = binding.navView.getHeaderView(0);
        Button btnRegister = (Button) headerview.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                FragmentTransaction transactionLogin = getFragmentManager().beginTransaction();
                LoginFragment loginFragment = new LoginFragment();
                loginFragment.setOnLoginSuccessListener(new LoginFragment.OnLoginSuccessListener() {
                    @Override
                    public void onSuccess() {
                    }
                });

                transactionLogin.add(R.id.frame_layout, loginFragment).addToBackStack("").commit();
            }
        });

     /*   DefaultSliderView textSliderView = new DefaultSliderView (getContext());

        textSliderView
                //  .description(" "+locationItems.get(i).location_name+" ")
                .setScaleType(BaseSliderView.ScaleType.Fit)
                .image("https://file.digi-kala.com/digikala/Image/Webstore/Banner/1396/7/29/0a687ef1.jpg");

        binding.includeLayout.slider.addSlider(textSliderView);
        DefaultSliderView textSliderView2 = new DefaultSliderView (getContext());
        textSliderView2
                //  .description(" "+locationItems.get(i).location_name+" ")
                .setScaleType(BaseSliderView.ScaleType.Fit)
                .image("https://file.digi-kala.com/digikala/Image/Webstore/Banner/1396/7/26/f0038db9.jpg");

        binding.includeLayout.slider.addSlider(textSliderView2);
        DefaultSliderView textSliderView3 = new DefaultSliderView (getContext());
        textSliderView3
                //  .description(" "+locationItems.get(i).location_name+" ")
                .image("https://file.digi-kala.com/digikala/Image/Webstore/Banner/1396/5/24/5b85009b.jpg")
                .setScaleType(BaseSliderView.ScaleType.Fit);

        binding.includeLayout.slider.addSlider(textSliderView3);
        */

        binding.includeLayout.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transactionCart = getFragmentManager().beginTransaction();
                transactionCart.add(R.id.frame_layout, new CartFragment()).addToBackStack("").commit();
            }
        });

        List<ExpenCategory> genres = new ArrayList<>();
        List<Category> emt = new ArrayList<>();
        ExpenCategory expenCategory = new ExpenCategory(new Category("1", "گوشی موبایل", ""), emt);
        genres.add(expenCategory);

    }

    private void getSections(String parent) {

        Call<List<Sections>> call = App.apiInterface.getSections(PublicVariable.key, parent);

        call.enqueue(new Callback<List<Sections>>() {
            @Override
            public void onResponse(Call<List<Sections>> call, final Response<List<Sections>> response) {

                UniversalAdapter2 adapter = new UniversalAdapter2(R.layout.item_expandable_category, response.body(), BR.category);

                adapter.setOnItemClickListener(new UniversalAdapter2.OnItemClickListener() {
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
                        ft.add(R.id.frame_layout, productsFragment).addToBackStack("").commit();

                    }
                });

                binding.includeLayout.categoryRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Sections>> call, Throwable t) {
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

                        int intPage = Integer.parseInt(page);

                        if (position + 1 == intPage * 10) {

                            intPage++;

                            getProductBestSelling(intPage + "", "10", "", "1", "");

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

}
