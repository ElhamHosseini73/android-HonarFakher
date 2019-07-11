package ir.rayapars.honarfakher.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.Customer;
import ir.rayapars.honarfakher.classes.ModelCity;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.classes.ServerMsg;
import ir.rayapars.honarfakher.classes.Setting;
import ir.rayapars.honarfakher.classes.SpinnerItems_cities;
import ir.rayapars.honarfakher.classes.SpinnerItems_counties;
import ir.rayapars.honarfakher.classes.SpinnerItems_days;
import ir.rayapars.honarfakher.classes.SpinnerItems_months;
import ir.rayapars.honarfakher.classes.SpinnerItems_years;
import ir.rayapars.honarfakher.databinding.FragmentPersonalInformationBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalInformationFragment extends Fragment {

    View x;

    public boolean isFirst = true;

    String stateId;
    FragmentPersonalInformationBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPersonalInformationBinding.inflate(getLayoutInflater());

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

        binding.toolbar.setTitle("ویرایش اطلاعات");

        //SpinnerItems_days is a list with two elements, id and title. It can be found in classes
        List<SpinnerItems_days> spinnerItemDays = new ArrayList<>();
        spinnerItemDays.add(new SpinnerItems_days("1", "1"));
        spinnerItemDays.add(new SpinnerItems_days("2", "2"));

        ArrayAdapter spinnerAdopter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItemDays);
        spinnerAdopter.setDropDownViewResource(R.layout.item_spinner_style_long);
        binding.mySpinner.setAdapter(spinnerAdopter);


        //SpinnerItems_months is a list with two elements, id and title. It can be found in classes
        List<SpinnerItems_months> spinnerItemsMonths = new ArrayList<>();
        spinnerItemsMonths.add(new SpinnerItems_months("1", "فروردین"));
        spinnerItemsMonths.add(new SpinnerItems_months("2", "اردیبهشت"));
        spinnerItemsMonths.add(new SpinnerItems_months("3", "خرداد"));
        spinnerItemsMonths.add(new SpinnerItems_months("4", "تیر  "));
        spinnerItemsMonths.add(new SpinnerItems_months("5", "مرداد"));
        spinnerItemsMonths.add(new SpinnerItems_months("6", " شهریور"));
        spinnerItemsMonths.add(new SpinnerItems_months("7", "مهر "));
        spinnerItemsMonths.add(new SpinnerItems_months("8", "آبان "));
        spinnerItemsMonths.add(new SpinnerItems_months("9", "آذر "));
        spinnerItemsMonths.add(new SpinnerItems_months("10", "دی "));
        spinnerItemsMonths.add(new SpinnerItems_months("11", "بهمن "));
        spinnerItemsMonths.add(new SpinnerItems_months("12", "اسفند "));

        ArrayAdapter spinnerAdopter2 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItemsMonths);
        spinnerAdopter2.setDropDownViewResource(R.layout.item_spinner_style_long);
        binding.mySpinner2.setAdapter(spinnerAdopter2);

        //SpinnerItems_years is a list with two elements, id and title. It can be found in classes
        List<SpinnerItems_years> spinnerItemsYears = new ArrayList<>();

        spinnerItemsYears.add(new SpinnerItems_years("1", "1371"));
        spinnerItemsYears.add(new SpinnerItems_years("2", "1372"));
        spinnerItemsYears.add(new SpinnerItems_years("3", "1373"));
        spinnerItemsYears.add(new SpinnerItems_years("4", "1374"));

        ArrayAdapter spinnerAdopter3 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItemsYears);
        spinnerAdopter3.setDropDownViewResource(R.layout.item_spinner_style_long);
        binding.mySpinner3.setAdapter(spinnerAdopter3);

        final AppCompatRadioButton mButton = binding.maleRadioButton;
        final AppCompatRadioButton fButton = binding.femaleRadioButton;

        mButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (mButton.isChecked()) {
                    fButton.setChecked(false);
                }
            }
        });

        fButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (fButton.isChecked()) {
                    mButton.setChecked(false);
                }
            }
        });



        binding.greenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                update_customer();

            }
        });

        Setting s = Select.from(Setting.class).first();
        retrieve(s.uid, s.MDU);

        binding.mySpinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItems_counties spinnerCity = (SpinnerItems_counties) binding.mySpinner4.getSelectedItem();
                getCities(spinnerCity.id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        city();
        return x;
    }

    //customers_info
    public String cityId;
    public String StateId;
    List<Customer> customer;

    private void retrieve(String uid, String MDU) {

        Call<List<Customer>> call = App.apiInterface.retrieve(PublicVariable.key, uid, MDU);

        call.enqueue(new Callback<List<Customer>>() {

            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {

                customer = response.body();
                getStates();
                binding.setCust(customer.get(0));

                if (customer.get(0).gender!=null) {

                    if (customer.get(0).gender.equals("2")) {

                        binding.femaleRadioButton.setChecked(true);

                    } else {

                        binding.maleRadioButton.setChecked(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });
    }

    List<Customer> registerList;

    private void update_customer() {

        Setting s = Select.from(Setting.class).first();
        SpinnerItems_cities spinnerCity = (SpinnerItems_cities) binding.mySpinner5.getSelectedItem();
        SpinnerItems_counties spinnerCounties = (SpinnerItems_counties) binding.mySpinner4.getSelectedItem();
        String gender = "1";
        String newsletter = "1";

        if (binding.femaleRadioButton.isChecked()) {

            gender = "2";
        } else {
            gender = "1";
        }

        if (binding.yesRadioButton.isChecked()) {
            newsletter = "1";
        } else {
            newsletter = "0";
        }

        Call<ServerMsg> call = App.apiInterface.update_customer(PublicVariable.key,
                s.uid,
                s.MDU,
                binding.editTextFirstName.getText().toString(),
                binding.editTextEmail.getText().toString(),
                gender,
                binding.editTextPhone.getText().toString(),
                binding.editTextMobilePhone.getText().toString(),
                spinnerCounties.id,
                String.valueOf(spinnerCity.id),
                binding.editTextAddress.getText().toString(),
                binding.editTextPostalCode.getText().toString());

        call.enqueue(new Callback<ServerMsg>() {

            @Override
            public void onResponse(Call<ServerMsg> call, Response<ServerMsg> response) {

                if (response.isSuccessful()) {

                    if (response.body().status.equals("0")) {

                        Toast.makeText(x.getContext(), "" + response.body().msg, Toast.LENGTH_SHORT).show();

                    } else {

                        Toast toast = Toast.makeText(getContext(), "" + response.body().msg, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();

                        getFragmentManager().popBackStack();

                    }
                } else {

                    Toast.makeText(x.getContext(), "خطا در ارتباط با سرور", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ServerMsg> call, Throwable t) {

                Toast.makeText(x.getContext(), "NO Internet", Toast.LENGTH_SHORT).show();

            }
        });
    }

    List<SpinnerItems_counties> listOfStates;

    public void getStates() {
        Call<List<SpinnerItems_counties>> call = App.apiInterface.getStates(PublicVariable.key);
        call.enqueue(new Callback<List<SpinnerItems_counties>>() {
            @Override
            public void onResponse(Call<List<SpinnerItems_counties>> call, Response<List<SpinnerItems_counties>> response) {
                listOfStates = response.body();
                ArrayAdapter spinnerAdopter4 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, listOfStates);
                spinnerAdopter4.setDropDownViewResource(R.layout.item_spinner_style_long);
                binding.mySpinner4.setAdapter(spinnerAdopter4);
                if (isFirst) {
                    for (int i = 0; i < listOfStates.size(); i++) {
                        if (customer.get(0).state_id!=null) {
                            if (customer.get(0).state_id.equals(listOfStates.get(i).id)) {
                                binding.mySpinner4.setSelection(i);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<SpinnerItems_counties>> call, Throwable t) {
            }
        });
    }


    void city(){

        Call<List<ModelCity>>call  = App.apiInterface.city(PublicVariable.key,"1");
        call.enqueue(new Callback<List<ModelCity>>() {
            @Override
            public void onResponse(Call<List<ModelCity>> call, Response<List<ModelCity>> response) {

                if (response.isSuccessful()){

                    Toast.makeText(getContext(), response.body().size()+"", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getContext(), "خطا از سمت سرور", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ModelCity>> call, Throwable t) {

                Toast.makeText(getContext(), "adsasd", Toast.LENGTH_SHORT).show();
                t.getMessage();
            }
        });

    }


    List<SpinnerItems_cities> listOfCities;

    public void getCities(String state) {
        Call<List<SpinnerItems_cities>> call = App.apiInterface.getCities(PublicVariable.key, state);
        call.enqueue(new Callback<List<SpinnerItems_cities>>() {
            @Override
            public void onResponse(Call<List<SpinnerItems_cities>> call, Response<List<SpinnerItems_cities>> response) {

                    listOfCities = response.body();
                    ArrayAdapter spinnerAdopter5 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, listOfCities);
                    spinnerAdopter5.setDropDownViewResource(R.layout.item_spinner_style_long);
                    binding.mySpinner5.setAdapter(spinnerAdopter5);
                    if (isFirst) {
                        for (int i = 0; i < listOfCities.size(); i++) {
                            if (customer.get(0).city_id!=null) {
                                if (customer.get(0).city_id.equals(String.valueOf(listOfCities.get(i).id))) {
                                    binding.mySpinner5.setSelection(i);
                                    isFirst = false;
                                }
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

}
