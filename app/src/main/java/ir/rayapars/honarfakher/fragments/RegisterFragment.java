package ir.rayapars.honarfakher.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.ModelRegister;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.classes.Setting;
import ir.rayapars.honarfakher.databinding.FragmentRegisterBinding;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {


    FragmentRegisterBinding binding;
    public String phone;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        view.setFocusable(true);
        view.setClickable(true);

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.pass.getText().length() == 0 && binding.user.getText().length() == 0) {

                    Toast.makeText(getContext(), " مقادیر را وارد کنید", Toast.LENGTH_SHORT).show();
                } else {

                    register();

                }

            }
        });

        return view;
    }

    void register() {

        retrofit2.Call<List<ModelRegister>> call = App.apiInterface.perRegister(PublicVariable.key, phone, binding.user.getText().toString()
                , binding.pass.getText().toString());
        call.enqueue(new Callback<List<ModelRegister>>() {
            @Override
            public void onResponse(retrofit2.Call<List<ModelRegister>> call, Response<List<ModelRegister>> response) {

                if (response.isSuccessful()) {

                    if (response.body().get(0).status.equals("1")) {


                        Setting setting = new Setting();
                        setting.MDU = response.body().get(0).MDU;
                        setting.uid = response.body().get(0).uid;
                        setting.save();

                        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {
                            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        }

                        FragmentTransaction transactionLogin = getFragmentManager().beginTransaction();
                        transactionLogin.add(R.id.frame_layout, new MainFragment()).commit();

                        Toast.makeText(getContext(), "ثبت نام با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Toast.makeText(getContext(), response.body().get(0).msg, Toast.LENGTH_SHORT).show();

                    }

                } else {

                }


            }

            @Override
            public void onFailure(retrofit2.Call<List<ModelRegister>> call, Throwable t) {

            }
        });


    }
}
