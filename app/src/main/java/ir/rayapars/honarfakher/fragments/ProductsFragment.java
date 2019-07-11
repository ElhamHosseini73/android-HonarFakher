package ir.rayapars.honarfakher.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import ir.rayapars.honarfakher.BR;
import ir.rayapars.honarfakher.DialogFragments.LoadingDialog;
import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.adapters.UniversalAdapter2;
import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.ProductDetails;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.databinding.FragmentProductsBinding;
import ir.rayapars.honarfakher.databinding.ItemHorizentalProductBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsFragment extends Fragment {

    UniversalAdapter2 adapter;
    FragmentProductsBinding binding;
    GridLayoutManager gridLayoutManager;

    int page = 1, perpage = 10;
    List<ProductDetails> products;
    public String titleProduct, idParent, featuredProduct, orderProduct, popularProduct, quantity, discount;
    View x;
    String checkType = "2";
    public String checkRadioButtom = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentProductsBinding.inflate(getLayoutInflater());
        x = this.binding.getRoot();
        x.setClickable(true);
        x.setFocusable(true);

        products = new ArrayList();
        getProducts("1", "10", featuredProduct, popularProduct, idParent, orderProduct, quantity, discount);

        binding.btnCart.setOnClickListener(new OnClickListener() {

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

        binding.title.setText(titleProduct);

        //search
        binding.btnSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction f = getFragmentManager().beginTransaction();
                f.add(R.id.frame_layout, new SearchFragment()).addToBackStack("").commit();
            }
        });

        //اضافه کردن دکمه بازگشت در toolbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().popBackStack();
            }
        });

        binding.toolbar.setTitle("");

        binding.viewType.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                if (checkType.equals("2")) {

                    gridLayoutManager.setSpanCount(2);
                    binding.viewType.setCompoundDrawablesWithIntrinsicBounds(null, null, ProductsFragment.this.getContext().getResources().getDrawable(R.drawable.ic_toc_black_24dp), null);

                } else if (checkType.equals("1")) {

                    gridLayoutManager.setSpanCount(1);
                    binding.viewType.setCompoundDrawablesWithIntrinsicBounds(null, null, ProductsFragment.this.getContext().getResources().getDrawable(R.drawable.ic_apps_black_24dp), null);

                }
                if (checkType.equals("1")) {
                    checkType = "2";
                } else if (checkType.equals("2")) {
                    checkType = "1";
                }

            }
        });

        binding.ordering.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                SortFragment sortFragment = new SortFragment();
                sortFragment.id = idParent;
                sortFragment.featuredProduct = featuredProduct;
                sortFragment.orderProduct = orderProduct;
                sortFragment.popularProduct = popularProduct;
                sortFragment.quantity = quantity;
                sortFragment.discount = discount;
                sortFragment.checkRadioButtom = checkRadioButtom;
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                sortFragment.setTargetFragment(ProductsFragment.this, 300);
                ft.add(R.id.frame_layout, sortFragment).addToBackStack("").commit();

            }
        });

        binding.filter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                FilterOptionsFragment filterOptionsFragment = new FilterOptionsFragment();
                filterOptionsFragment.id = idParent;
                filterOptionsFragment.featuredProduct = featuredProduct;
                filterOptionsFragment.orderProduct = orderProduct;
                filterOptionsFragment.popularProduct = popularProduct;
                filterOptionsFragment.quantity = quantity;
                filterOptionsFragment.discount = discount;
                filterOptionsFragment.checkRadioButtom = checkRadioButtom;
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                filterOptionsFragment.setTargetFragment(ProductsFragment.this, 300);
                ft.add(R.id.frame_layout, filterOptionsFragment).addToBackStack("").commit();

            }
        });

        return x;

    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(SimpleDraweeView draweeImage, String url) {

        if (url != null) {
            draweeImage.setImageURI(url);
        }
    }

    private void getProducts(final String page, final String perPage, final String featured, final String popular, String sid, String order, String quantityy, String discountt) {


        final LoadingDialog progressDialog = new LoadingDialog();
        progressDialog.show(getFragmentManager(), "");
        progressDialog.setCancelable(false);

        Call<List<ProductDetails>> call = App.apiInterface.GetProducts(PublicVariable.key, page, perPage, featured, popular, sid, order, quantityy, discountt);

        call.enqueue(new Callback<List<ProductDetails>>() {

            @Override
            public void onResponse(Call<List<ProductDetails>> call, Response<List<ProductDetails>> response) {

                try {

                    List<ProductDetails> temp = response.body();

                    try {

                        products.addAll(temp);

                    } catch (Exception e) {

                    }

                    try {

                        if (products.get(0).status.equals("0")) {

                            Toast.makeText(x.getContext(), "" + products.get(0).msg, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }

                    } catch (Exception e) {

                        if (adapter == null) {

                            if (products.size() == 1) {

                                try {

                                    if (products.get(0).status.equals("0")) {

                                        Toast.makeText(x.getContext(), "" + products.get(0).msg, Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception he) {

                                    he.printStackTrace();

                                    adapter = new UniversalAdapter2(R.layout.item_horizental_product, products, BR.product);
                                    binding.productRecyclerView.setAdapter(adapter);
                                    gridLayoutManager = new GridLayoutManager(getContext(), 1);
                                    binding.productRecyclerView.setLayoutManager(gridLayoutManager);
                                }

                            } else {

                                adapter = new UniversalAdapter2(R.layout.item_horizental_product, products, BR.product);
                                binding.productRecyclerView.setAdapter(adapter);
                                gridLayoutManager = new GridLayoutManager(getContext(), 1);
                                binding.productRecyclerView.setLayoutManager(gridLayoutManager);

                            }

                        } else {

                            if (products.size() == 1) {

                                try {

                                    if (products.get(0).status.equals("0")) {

                                        Toast.makeText(x.getContext(), "" + products.get(0).msg, Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception ge) {

                                    ge.printStackTrace();

                                    int intPage = Integer.parseInt(page);

                                    adapter.setList(products);
                                    adapter.notifyItemInserted(intPage * 10);
                                }

                            } else {

                                int intPage = Integer.parseInt(page);

                                adapter.setList(products);
                                adapter.notifyItemInserted(intPage * 10);
                            }


                        }

                        try {

                            adapter.setOnItemClickListener(new UniversalAdapter2.OnItemClickListener() {

                                @Override
                                public void onClick(ViewDataBinding binding, int position) {

                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_out_right, android.R.anim.slide_out_right);
                                    ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                                    productDetailsFragment.idProduct = products.get(position).id;
                                    productDetailsFragment.sectionParent = products.get(position).sid;
                                    ft.add(R.id.frame_layout, productDetailsFragment).addToBackStack("").commit();

                                }
                            });
                        } catch (Exception de) {
                            de.printStackTrace();
                        }

                        progressDialog.dismiss();

                        try {

                            adapter.setOnItemBindListener(new UniversalAdapter2.OnItemBindListener() {

                                @Override
                                public void onBind(ViewDataBinding binding, int position) {


                                    ItemHorizentalProductBinding itemHorizentalProductBinding = (ItemHorizentalProductBinding) binding;

                                    if (products.get(position).discount.equals("0")) {

                                        itemHorizentalProductBinding.productPrice.setVisibility(View.INVISIBLE);

                                    }


                                    int intPage = Integer.parseInt(page);

                                    if (position + 1 == intPage * 10) {

                                        intPage++;

                                        getProducts(intPage + "", "10", featuredProduct, popularProduct, idParent, orderProduct, quantity, discount);

                                    }

                                }
                            });
                        } catch (NumberFormatException fe) {
                            fe.printStackTrace();
                        }

                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<List<ProductDetails>> call, Throwable t) {

                progressDialog.dismiss();

                new android.app.AlertDialog.Builder(getContext()).setMessage("عدم ارتباط با اینترنت")
                        .setPositiveButton("تلاش مجدد", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                getProducts("1", "10", featuredProduct, popularProduct, idParent, orderProduct, quantity, discount);


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 300) {

            if (data != null) {

                adapter = null;

                products = new ArrayList<>();
                adapter = new UniversalAdapter2(R.layout.item_horizental_product, products, BR.product);
                binding.productRecyclerView.setAdapter(adapter);
                gridLayoutManager = new GridLayoutManager(getContext(), 1);
                binding.productRecyclerView.setLayoutManager(gridLayoutManager);

                checkRadioButtom = data.getStringExtra("checkRadioButtom");

                getProducts("1", "10", data.getStringExtra("featuredProduct"), data.getStringExtra("popularProduct")
                        , data.getStringExtra("idParent"), data.getStringExtra("orderProduct"), data.getStringExtra("quantity"), data.getStringExtra("discount"));

            }
        }


    }

}
