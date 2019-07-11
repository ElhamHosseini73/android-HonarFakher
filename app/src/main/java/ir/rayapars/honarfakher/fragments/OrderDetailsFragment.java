package ir.rayapars.honarfakher.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orm.query.Select;

import ir.rayapars.honarfakher.BR;
import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.adapters.UniversalAdapter2;
import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.OrderInfo;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.classes.Setting;
import ir.rayapars.honarfakher.databinding.FragmentOrderDetailsBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsFragment extends Fragment {
    View v;
    public String orderId, status;
    FragmentOrderDetailsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOrderDetailsBinding.inflate(getLayoutInflater());
        v = binding.getRoot();
        v.setFocusable(true);
        v.setClickable(true);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar.setTitle("جزئیات سفارش");
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.myRecyclerView.setLayoutManager(layoutManager);
        Setting s = Select.from(Setting.class).first();

        int statusNumber = Integer.parseInt(status);
        String txtStatus;

        switch (statusNumber) {

            case 0:
                txtStatus = "معلق";
                binding.txt.setText(txtStatus);
                break;
            case 1:
                txtStatus = "در حال بررسی";
                binding.txt.setText(txtStatus);
                break;
            case 2:
                txtStatus = "پرداخت شده";
                binding.txt.setText(txtStatus);
                break;
            case 3:
                txtStatus = "در حال ارسال";
                binding.txt.setText(txtStatus);
                break;
            case 4:
                txtStatus = "ارسال شده";
                binding.txt.setText(txtStatus);
                break;

            case 5:
                txtStatus = "تحویل داده شده";
                binding.txt.setText(txtStatus);
                break;
            case 6:
                txtStatus = "لغو شده";
                binding.txt.setText(txtStatus);
                break;

        }


        getOrderDetails(s.uid, s.MDU, orderId);

        return v;
    }

    OrderInfo order;

    private void getOrderDetails(String uid, String MDU, String id) {

        Call<OrderInfo> call = App.apiInterface.getOrderDetails(PublicVariable.key, uid, MDU, id);
        call.enqueue(new Callback<OrderInfo>() {

            @Override
            public void onResponse(Call<OrderInfo> call, Response<OrderInfo> response) {

                order = response.body();
                UniversalAdapter2 adapter = new UniversalAdapter2(R.layout.item_order_details, order.products, BR.var);
                binding.myRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<OrderInfo> call, Throwable t) {

            }
        });
    }

}
