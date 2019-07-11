package ir.rayapars.honarfakher.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.List;

import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.LoginMsg;
import ir.rayapars.honarfakher.classes.ModelPerRegister;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.classes.Setting;
import ir.rayapars.honarfakher.databinding.FragmentLoginBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    public interface OnLoginSuccessListener {
        void onSuccess();
    }

    OnLoginSuccessListener onLoginSuccessListener;

    public void setOnLoginSuccessListener(OnLoginSuccessListener h) {
        onLoginSuccessListener = h;
    }


    VerifyFragment verifyFragment = new VerifyFragment();
    View x;

    FragmentLoginBinding binding;

    public boolean isLogin = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        x = binding.getRoot();
        x.setClickable(true);
        x.setFocusable(true);


        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.user.getText())) {

                    Toast toast = Toast.makeText(getContext(), "لطفا شماره موبایل خود را وارد کنید", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                } else if (TextUtils.isEmpty(binding.pass.getText())) {

                    Toast toast = Toast.makeText(getContext(), " انتخاب گذرواژه برای ثبت نام اجباری است", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                } else {
                    verifyFragment.phone = binding.user.getText().toString();

                }
            }
        });

        binding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(binding.user.getText())) {

                    Toast toast = Toast.makeText(getContext(), "لطفا شماره موبایل خود را وارد کنید", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();

                } else if (TextUtils.isEmpty(binding.pass.getText())) {

                    Toast toast = Toast.makeText(getContext(), " لطفا گذرواژه خود را وارد کنید", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                } else {


                }
            }
        });


        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.user.getText().length() == 11) {
                    if (isLogin) {
                        login();
                    } else {
                        perRgister();
                    }

                } else {
                    Toast.makeText(getContext(), "شماره تلفن را به درستی وارد کنید", Toast.LENGTH_SHORT).show();

                }


            }
        });


        binding.btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction f = getFragmentManager().beginTransaction();
                f.add(R.id.frame_layout, new MainFragment()).commit();
            }
        });

        binding.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transactionCart = getFragmentManager().beginTransaction();
                transactionCart.add(R.id.frame_layout, new ForgotPasswordFragment()).addToBackStack("").commit();

            }
        });

        binding.cart.setOnClickListener(new View.OnClickListener() {
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

        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction f = getFragmentManager().beginTransaction();
                f.add(R.id.frame_layout, new SearchFragment()).addToBackStack("").commit();
            }
        });
        return x;
    }


    void perRgister() {

        Call<List<ModelPerRegister>> call = App.apiInterface.perRegister(PublicVariable.key, binding.user.getText().toString());

        call.enqueue(new Callback<List<ModelPerRegister>>() {
            @Override
            public void onResponse(Call<List<ModelPerRegister>> call, Response<List<ModelPerRegister>> response) {
                if (response.isSuccessful()) {

                    if (response.body().get(0).status.equals("1")) {

                        FragmentTransaction f = getFragmentManager().beginTransaction();
                        RegisterFragment fragment = new RegisterFragment();
                        fragment.phone = binding.user.getText().toString();
                        f.add(R.id.frame_layout, fragment).addToBackStack("").commit();

                    } else if (response.body().get(0).status.equals("0")) {

                        Toast.makeText(getContext(), "رمز عبور خود را وارد کنید", Toast.LENGTH_SHORT).show();
                        isLogin = true;
                        binding.pass.setVisibility(View.VISIBLE);
                        binding.txtForgot.setVisibility(View.VISIBLE);
                        binding.user.setEnabled(false);

                    }
                }


            }

            @Override
            public void onFailure(Call<List<ModelPerRegister>> call, Throwable t) {

            }
        });


    }

    void login() {

        Call<List<LoginMsg>> call = App.apiInterface.login(PublicVariable.key, binding.user.getText().toString()
                , binding.pass.getText().toString());

        call.enqueue(new Callback<List<LoginMsg>>() {
            @Override
            public void onResponse(Call<List<LoginMsg>> call, Response<List<LoginMsg>> response) {

                if (response.isSuccessful()) {

                    if (response.body().get(0).status.equals("1")) {
                        Setting setting = new Setting();
                        setting.uid = response.body().get(0).userId;
                        setting.MDU = response.body().get(0).MDU;
                        setting.save();

                        Toast.makeText(getContext(), "ثبت نام با موفقیت انجام شد", Toast.LENGTH_SHORT).show();

                        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {
                            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        }

                        FragmentTransaction transactionLogin = getFragmentManager().beginTransaction();
                        transactionLogin.add(R.id.frame_layout, new MainFragment()).commit();

                    } else {
                        Toast.makeText(getContext(), response.body().get(0).msg, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "خطا از سمت سرور", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<LoginMsg>> call, Throwable t) {

            }
        });


    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
