package ir.rayapars.honarfakher.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.orm.query.Select;

import java.util.List;

import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.LoginMsg;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.classes.Setting;
import ir.rayapars.honarfakher.databinding.FragmentChangePasswordBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment {

    View x;
    FragmentChangePasswordBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChangePasswordBinding.inflate(getLayoutInflater());
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

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.txtEditNew.getText().toString().equals(binding.txtRepeat.getText().toString())) {

                    if (binding.txtEditOld.getText().equals("")) {

                        Toast.makeText(x.getContext(), "اطلاعات وارد شده کامل نمی باشد", Toast.LENGTH_SHORT).show();

                    } else {

                        Setting s = Select.from(Setting.class).first();
                        change_password(s.uid, s.MDU, binding.txtEditNew.getText().toString(), binding.txtEditOld.getText().toString());
                    }


                } else {

                    Toast.makeText(x.getContext(), "تکرار کلمه عبور اشتباه است", Toast.LENGTH_SHORT).show();

                }
            }
        });

        binding.toolbar.setTitle("تغییر رمز عبور");

        return x;
    }

    List<LoginMsg> changePass;

    private void change_password(String id, String MDU, String password, String oldPassword) {

        Call<List<LoginMsg>> call = App.apiInterface.change_password(PublicVariable.key, id, MDU, password, oldPassword);

        call.enqueue(new Callback<List<LoginMsg>>() {

            @Override
            public void onResponse(Call<List<LoginMsg>> call, Response<List<LoginMsg>> response) {

                if (response.isSuccessful()) {

                    if (response.body().get(0).status.equals("0")) {

                        Toast.makeText(x.getContext(), response.body().get(0).msg, Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(x.getContext(), response.body().get(0).msg, Toast.LENGTH_LONG).show();
                        getFragmentManager().popBackStack();

                    }

                    //  changePass = response.body();

                } else {

                    Toast.makeText(getContext(), "خطا در ارتباط با سرور", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LoginMsg>> call, Throwable t) {

                Toast.makeText(getContext(), "NO INTERNET", Toast.LENGTH_LONG).show();

            }
        });
    }
}
