package ir.rayapars.honarfakher.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.orm.query.Select;

import java.util.List;

import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.Customer;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.classes.Setting;
import ir.rayapars.honarfakher.databinding.FragmentProfileBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    public interface ExitListener {
        void onSuccess();
    }

    ExitListener exitListener;

    public void setExitListener(ExitListener exitListener) {
        this.exitListener = exitListener;
    }


    View x;
    FragmentProfileBinding binding;
    public String name = "کاربر عزیز به پروفایلت خوش آمدی";
    public String email = " ";

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
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
        binding.toolbar.setTitle("پروفایل");


        binding.editBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentTransaction t = getFragmentManager().beginTransaction();
                t.add(R.id.frame_layout, new PersonalInformationFragment()).addToBackStack("").commit();

            }
        });

        binding.butPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction tt = getFragmentManager().beginTransaction();
                tt.add(R.id.frame_layout, new ChangePasswordFragment()).addToBackStack("").commit();
            }
        });


        binding.orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.frame_layout, new OrdersListFragment()).addToBackStack("").commit();
            }
        });


        binding.btnWishList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                ft2.add(R.id.frame_layout, new WishListFragment()).addToBackStack("").commit();

            }

        });


        binding.btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Setting.deleteAll(Setting.class);
                exitListener.onSuccess();
                getFragmentManager().popBackStackImmediate();
            }
        });


        Setting s = Select.from(Setting.class).first();
        retrieve(s.uid, s.MDU);
        return x;
    }

    List<Customer> customer;

    private void retrieve(String uid, String MDU) {

        Call<List<Customer>> call = App.apiInterface.retrieve(PublicVariable.key, uid, MDU);
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {

                if (response.isSuccessful()) {

                    customer = response.body();

                    if (customer.get(0).full_name!=null || customer.get(0).full_name != null) {

                        binding.nameAndMailTextView.setText(customer.get(0).full_name);

                        binding.nameAndMailTextView.setVisibility(View.VISIBLE);
                        binding.mailTextView.setVisibility(View.VISIBLE);
                    }
                    binding.mailTextView.setText(customer.get(0).mobile);

                } else {
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Toast.makeText(x.getContext(), ";", Toast.LENGTH_SHORT).show();
            }
        });
/*        call.enqueue(new Callback<List<Customer>>() {

            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                
                if(response.isSuccessful()){

                    *//*if (response.body().get(0).full_name != null) {
                        customer = response.body();
                        name = response.body().get(0).full_name;
                        binding.nameAndMailTextView.setText(name);
                        email = response.body().get(0).email;
                        binding.mailTextView.setText(email);
                    }*//*
                    
                }else {

                    Toast.makeText(x.getContext(), "", Toast.LENGTH_SHORT).show();

                }
                
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });*/
    }

}

