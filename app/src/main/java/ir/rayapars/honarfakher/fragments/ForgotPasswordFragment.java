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
import ir.rayapars.honarfakher.classes.ModelPerRegister;
import ir.rayapars.honarfakher.classes.ModelResetPass;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.classes.Setting;
import ir.rayapars.honarfakher.databinding.FragmentForgotPasswordBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordFragment extends Fragment {


    FragmentForgotPasswordBinding binding;

    boolean checkReset = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentForgotPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        view.setClickable(true);
        view.setFocusable(true);

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.edtPhone.getText().length() == 11) {
                    if (checkReset) {

                        if (binding.newPassword.getText().length() != 0 &&
                                binding.edtCodeConfrim.getText().length() != 0) {
                            resetPassword();
                        }
                        else {
                            Toast.makeText(getContext(), " مقادیر را به درستی وارد کنید", Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        rememberSendPhone();
                    }


                } else {
                    Toast.makeText(getContext(), "شماره تلفن را به درستی وارد کنید", Toast.LENGTH_SHORT).show();
                }


            }
        });


        return view;

    }


    void rememberSendPhone() {

        Call<List<ModelPerRegister>> call = App.apiInterface.remember(PublicVariable.key, binding.edtPhone.getText().toString());

        call.enqueue(new Callback<List<ModelPerRegister>>() {
            @Override
            public void onResponse(Call<List<ModelPerRegister>> call, Response<List<ModelPerRegister>> response) {

                if (response.isSuccessful()) {

                    if (response.body().get(0).status.equals("1")) {

                        checkReset = true;
                        binding.txtConfirm.setVisibility(View.VISIBLE);
                        binding.txtNew.setVisibility(View.VISIBLE);
                        binding.edtPhone.setEnabled(false);
                        Toast.makeText(getContext(), response.body().get(0).msg, Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(getContext(), response.body().get(0).msg, Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getContext(), "خطا از سمت سرور", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ModelPerRegister>> call, Throwable t) {

            }
        });


    }


    void resetPassword() {

        Call<List<ModelResetPass>> call = App.apiInterface.resetPassword(PublicVariable.key, binding.edtPhone.getText().toString()
                , binding.edtCodeConfrim.getText().toString(), binding.newPassword.getText().toString());

        call.enqueue(new Callback<List<ModelResetPass>>() {
            @Override
            public void onResponse(Call<List<ModelResetPass>> call, Response<List<ModelResetPass>> response) {

                if (response.isSuccessful()) {

                    if (response.body().get(0).status.equals("1")) {

                        Setting setting = new Setting();
                        setting.uid = response.body().get(0).uid;
                        setting.MDU = response.body().get(0).MDU;
                        setting.save();

                        Toast.makeText(getContext(), "ثبت نام با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {

                            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();

                        }

                        FragmentTransaction transactionLogin = getFragmentManager().beginTransaction();
                        transactionLogin.add(R.id.frame_layout, new MainFragment()).commit();

                    } else {
                        Toast.makeText(getContext(), response.body().get(0).status, Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(getContext(), "خطا از سمت سرور", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ModelResetPass>> call, Throwable t) {

            }
        });


    }


}
