package ir.rayapars.honarfakher.fragments;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import ir.rayapars.honarfakher.BR;
import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.adapters.UniversalAdapter2;
import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.OrdersList;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.classes.Setting;
import ir.rayapars.honarfakher.databinding.FragmentOrdersListBinding;
import ir.rayapars.honarfakher.databinding.ItemsOrdersListBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersListFragment extends Fragment {

    View v;
    FragmentOrdersListBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentOrdersListBinding.inflate(getLayoutInflater());
        v = binding.getRoot();
        v.setFocusable(true);
        v.setClickable(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.myRecyclerView.setLayoutManager(layoutManager);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().popBackStack();

            }
        });

        binding.toolbar.setTitle("سوابق سفارش");
        Setting s = Select.from(Setting.class).first();


        getOrders(s.uid, s.MDU);

        return v;
    }

    List<OrdersList> ordersList = new ArrayList<>();
    String txtStatus = "پرداخت";

    private void getOrders(String uid, String MDU) {

        Call<List<OrdersList>> call = App.apiInterface.getOrders(PublicVariable.key, uid, MDU);

        call.enqueue(new Callback<List<OrdersList>>() {

            @Override
            public void onResponse(Call<List<OrdersList>> call, final Response<List<OrdersList>> response) {

                if (response.isSuccessful()) {

                    ordersList = response.body();

                    if (ordersList.get(0).id != null) {

                        int statusNumber = Integer.parseInt(ordersList.get(0).status);

                        switch (statusNumber) {

                            case 0:
                                txtStatus = "معلق";
                                break;
                            case 1:
                                txtStatus = "در حال بررسی";
                                break;
                            case 2:
                                txtStatus = "پرداخت شده";
                                break;
                            case 3:
                                txtStatus = "در حال ارسال";
                                break;
                            case 4:
                                txtStatus = "ارسال شده";
                                break;

                            case 5:
                                txtStatus = "تحویل داده شده";
                                break;
                            case 6:
                                txtStatus = "لغو شده";
                                break;

                        }

                        UniversalAdapter2 adapter = new UniversalAdapter2(R.layout.items_orders_list, ordersList, BR.oli);

                        adapter.setOnItemBindListener(new UniversalAdapter2.OnItemBindListener() {
                            @Override
                            public void onBind(ViewDataBinding binding, int position) {

                                ItemsOrdersListBinding b = ((ItemsOrdersListBinding) binding);
                                b.status.setText(txtStatus);


                                b.goToDetailsTextView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        FragmentTransaction f = getFragmentManager().beginTransaction();
                                        OrderDetailsFragment orderDetailsFragment = new OrderDetailsFragment();
                                        orderDetailsFragment.orderId = response.body().get(0).id;
                                        orderDetailsFragment.status = response.body().get(0).status;
                                        f.add(R.id.frame_layout, orderDetailsFragment).addToBackStack("").commit();

                                    }
                                });
                            }
                        });
                        binding.myRecyclerView.setAdapter(adapter);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<OrdersList>> call, Throwable t) {
            }
        });
    }


}
