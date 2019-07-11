package ir.rayapars.honarfakher.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orm.query.Select;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.activities.ZarinPalActivity;
import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.Checkout;
import ir.rayapars.honarfakher.classes.CheckoutOrder;
import ir.rayapars.honarfakher.classes.Customer;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.classes.Setting;
import ir.rayapars.honarfakher.databinding.FragmentPaymentBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentFragment extends Fragment {

    FragmentPaymentBinding binding;
    boolean check = true;
    boolean checkSelect = false;
    public long price;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentPaymentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        view.setClickable(true);
        view.setFocusable(true);


        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


        binding.btnNaqdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check=true;
                binding.btnNaqdi.setTextColor(getResources().getColor(R.color.white));
                binding.btnOnline.setTextColor(getResources().getColor(R.color.black));
                binding.btnNaqdi.setBackground(getResources().getDrawable(R.drawable.select_btn));
                binding.btnOnline.setBackground(getResources().getDrawable(R.drawable.unselect_btn));

            }
        });

        binding.btnOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check=false;
                binding.btnOnline.setTextColor(getResources().getColor(R.color.white));
                binding.btnNaqdi.setTextColor(getResources().getColor(R.color.black));
                binding.btnOnline.setBackground(getResources().getDrawable(R.drawable.select_btn));
                binding.btnNaqdi.setBackground(getResources().getDrawable(R.drawable.unselect_btn));

            }
        });


        binding.btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkSelect=true;
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.frame_layout, new InformationFragment()).addToBackStack("").commit();

            }
        });


        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting s = Select.from(Setting.class).first();
                if (!checkSelect){

                    checkout(s.uid, s.MDU, customer.get(0).full_name,
                            customer.get(0).address, customer.get(0).state_id, customer.get(0).city_id,
                            customer.get(0).postal_code, customer.get(0).tel, customer.get(0).mobile);

                }
                else {

                    if (!check) {

                        if (s != null) {
                            if (InformationFragment.binding.addressPishFarz.isChecked()) {

                                checkout(s.uid, s.MDU, customer.get(0).full_name,
                                        customer.get(0).address, customer.get(0).state_id, customer.get(0).city_id,
                                        customer.get(0).postal_code, customer.get(0).tel, customer.get(0).mobile);
                            } else {
                                checkout(s.uid, s.MDU,
                                        InformationFragment.binding.edtFamilyName.getText().toString()
                                        , InformationFragment.binding.edtAddress.getText().toString()
                                        , InformationFragment.listOfStates.get(InformationFragment.binding.spinnerOstan.getSelectedItemPosition()).id,
                                        InformationFragment.listOfCities.get(InformationFragment.binding.spinnerCity.getSelectedItemPosition()).id,
                                        InformationFragment.binding.edtCodePost.getText().toString(),
                                        InformationFragment.binding.edtTelephone.getText().toString(),
                                        InformationFragment.binding.edtPhone.getText().toString());
                            }

                        }

                    }
                }
            }
        });

        Setting s = Select.from(Setting.class).first();
        retrieve(s.uid, s.MDU);


        return view;
    }


    public List<Customer> customer;

    private void retrieve(String uid, String MDU) {

        Call<List<Customer>> call = App.apiInterface.retrieve(PublicVariable.key, uid, MDU);

        call.enqueue(new Callback<List<Customer>>() {

            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {

                customer = response.body();

            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });
    }


    private void checkout(String uid, String MDU, String name, String address,
                          String idOstan, String idCity, String codePost, String tel, String phone) {

        List<CheckoutOrder> order = new ArrayList<>();

        for (int i = 0; i < App.pd.size(); i++) {
            order.add(new CheckoutOrder(App.pd.get(i).getId(), App.pd.get(i).numberOfProduct + ""));
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<CheckoutOrder>>() {
        }.getType();
        String strOrder = gson.toJson(order, type);
        Call<Checkout> call = App.apiInterface.checkout(PublicVariable.key,
                strOrder,
                uid,
                MDU,
                "1",
                name,
                address, idOstan,
                idCity,
                codePost, tel,
                phone);

        call.enqueue(new Callback<Checkout>() {
            @Override
            public void onResponse(Call<Checkout> call, Response<Checkout> response) {

                Intent zarAct = new Intent(getContext(), ZarinPalActivity.class);
                zarAct.putExtra("order_id", response.body().order_id);
                zarAct.putExtra("invoice_number", response.body().invoice_number);
                zarAct.putExtra("amount", price + "");
                ZarinPalActivity.isFirst = true;
                startActivity(zarAct);
            }

            @Override
            public void onFailure(Call<Checkout> call, Throwable t) {


            }
        });
    }
}
