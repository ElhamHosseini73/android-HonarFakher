package ir.rayapars.honarfakher.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.orm.query.Select;

import java.util.List;

import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.Customer;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.classes.Setting;
import ir.rayapars.honarfakher.classes.SpinnerItems_cities;
import ir.rayapars.honarfakher.classes.SpinnerItems_counties;
import ir.rayapars.honarfakher.databinding.FragmentInformationBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationFragment extends Fragment {

   public static FragmentInformationBinding binding;

    public long price;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentInformationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        view.setFocusable(true);
        view.setClickable(true);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        if (binding.addressPishFarz.isChecked()) {

            binding.edtFamilyName.setVisibility(View.GONE);
            binding.familyName.setVisibility(View.GONE);
            binding.txtPhone.setVisibility(View.GONE);
            binding.edtPhone.setVisibility(View.GONE);
            binding.txtTelephone.setVisibility(View.GONE);
            binding.edtTelephone.setVisibility(View.GONE);
            binding.txtaddress.setVisibility(View.GONE);
            binding.edtAddress.setVisibility(View.GONE);
            binding.txtCodePost.setVisibility(View.GONE);
            binding.txtostan.setVisibility(View.GONE);
            binding.spinnerOstan.setVisibility(View.GONE);
            binding.txtCity.setVisibility(View.GONE);
            binding.spinnerCity.setVisibility(View.GONE);
            binding.edtCodePost.setVisibility(View.GONE);
        }

        binding.checkAddress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (binding.addressPishFarz.isChecked()){


                    binding.edtFamilyName.setVisibility(View.GONE);
                    binding.familyName.setVisibility(View.GONE);
                    binding.txtPhone.setVisibility(View.GONE);
                    binding.edtPhone.setVisibility(View.GONE);
                    binding.txtTelephone.setVisibility(View.GONE);
                    binding.edtTelephone.setVisibility(View.GONE);
                    binding.txtaddress.setVisibility(View.GONE);
                    binding.edtAddress.setVisibility(View.GONE);
                    binding.txtCodePost.setVisibility(View.GONE);
                    binding.txtostan.setVisibility(View.GONE);
                    binding.spinnerOstan.setVisibility(View.GONE);
                    binding.txtCity.setVisibility(View.GONE);
                    binding.spinnerCity.setVisibility(View.GONE);
                    binding.edtCodePost.setVisibility(View.GONE);
                }
                else {

                    binding.edtFamilyName.setVisibility(View.VISIBLE);
                    binding.familyName.setVisibility(View.VISIBLE);
                    binding.txtPhone.setVisibility(View.VISIBLE);
                    binding.edtPhone.setVisibility(View.VISIBLE);
                    binding.txtTelephone.setVisibility(View.VISIBLE);
                    binding.edtTelephone.setVisibility(View.VISIBLE);
                    binding.txtaddress.setVisibility(View.VISIBLE);
                    binding.edtAddress.setVisibility(View.VISIBLE);
                    binding.txtCodePost.setVisibility(View.VISIBLE);
                    binding.txtostan.setVisibility(View.VISIBLE);
                    binding.spinnerOstan.setVisibility(View.VISIBLE);
                    binding.txtCity.setVisibility(View.VISIBLE);
                    binding.spinnerCity.setVisibility(View.VISIBLE);
                    binding.edtCodePost.setVisibility(View.VISIBLE);
                }

            }
        });


        binding.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean check = true;
                if (binding.edtFamilyName.getText().length() == 0) {
                    check = false;
                    Toast.makeText(getContext(), "نام و نام خانوادگی را وارد کنید", Toast.LENGTH_SHORT).show();
                } else if (binding.edtPhone.getText().length() == 0) {
                    check = false;
                    Toast.makeText(getContext(), " شماره همراه را وارد کنید", Toast.LENGTH_SHORT).show();
                } else if (binding.edtAddress.getText().length() == 0) {
                    check = false;
                    Toast.makeText(getContext(), "  ادرس را وارد کنید", Toast.LENGTH_SHORT).show();
                }
                if (check) {

                    getFragmentManager().popBackStack();
//
//                    Setting s = Select.from(Setting.class).first();
//                    checkout(s.uid, s.MDU);
                }

            }
        });
        Setting s = Select.from(Setting.class).first();
        retrieve(s.uid, s.MDU);


        binding.spinnerOstan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SpinnerItems_counties spinnerCity = (SpinnerItems_counties) binding.spinnerOstan.getSelectedItem();

                getCities(spinnerCity.id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

   public static List<SpinnerItems_cities> listOfCities;

    public void getCities(String state) {
        Call<List<SpinnerItems_cities>> call = App.apiInterface.getCities(PublicVariable.key, state);
        call.enqueue(new Callback<List<SpinnerItems_cities>>() {
            @Override
            public void onResponse(Call<List<SpinnerItems_cities>> call, Response<List<SpinnerItems_cities>> response) {

                listOfCities = response.body();
                ArrayAdapter spinnerAdopter5 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, listOfCities);
                spinnerAdopter5.setDropDownViewResource(R.layout.item_spinner_style_long);
                binding.spinnerCity.setAdapter(spinnerAdopter5);

                for (int i = 0; i < listOfCities.size(); i++) {
                    if (customer.get(0).city_id != null) {
                        if (customer.get(0).city_id.equals(String.valueOf(listOfCities.get(i).id))) {
                            binding.spinnerCity.setSelection(i);
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<List<SpinnerItems_cities>> call, Throwable t) {

                Toast.makeText(getContext(), "asd", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static  List<SpinnerItems_counties> listOfStates;

    void states() {

        Call<List<SpinnerItems_counties>> call = App.apiInterface.getStates(PublicVariable.key);

        call.enqueue(new Callback<List<SpinnerItems_counties>>() {
            @Override
            public void onResponse(Call<List<SpinnerItems_counties>> call, Response<List<SpinnerItems_counties>> response) {

                if (response.isSuccessful()) {

                    listOfStates = response.body();
                    ArrayAdapter spinnerAdopter4 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, listOfStates);
                    spinnerAdopter4.setDropDownViewResource(R.layout.item_spinner_style_long);
                    binding.spinnerOstan.setAdapter(spinnerAdopter4);

                    for (int i = 0; i < listOfStates.size(); i++) {
                        if (customer.get(0).state_id != null) {
                            if (customer.get(0).state_id.equals(listOfStates.get(i).id)) {
                                binding.spinnerOstan.setSelection(i);
                            }
                        }
                    }


                } else {

                }

            }

            @Override
            public void onFailure(Call<List<SpinnerItems_counties>> call, Throwable t) {

            }
        });

    }

   public  List<Customer> customer;

    private void retrieve(String uid, String MDU) {

        Call<List<Customer>> call = App.apiInterface.retrieve(PublicVariable.key, uid, MDU);

        call.enqueue(new Callback<List<Customer>>() {

            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {

                customer = response.body();
                states();
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });
    }




}
