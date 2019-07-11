package ir.rayapars.honarfakher.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.List;

import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.LoginMsg;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.classes.Setting;
import ir.rayapars.honarfakher.databinding.FragmentVerifyBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyFragment extends Fragment {
    public String phone;

    public interface OnRegisterSuccessListener{
        void onSuccess();
    }
    OnRegisterSuccessListener onRegisterSuccessListener;
    public void setOnRegisterSuccessListener(OnRegisterSuccessListener onRegisterSuccessListener) {
        this.onRegisterSuccessListener = onRegisterSuccessListener;
    }

    View x;
    //public String mobile;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        final FragmentVerifyBinding binding= FragmentVerifyBinding.inflate(getLayoutInflater());
        x = binding.getRoot();
        x.setFocusable(true);
        x.setFocusable(true);

        //اضافه کردن دکمه بازگشت در toolbar
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        binding.toolbar.setTitle("مانتو بلوط");

        //binding.txtPhone.setText(mobile);
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                register(phone,binding.txtCode.getText().toString());
            }
        });
        return x;
    }

    //register
    List<LoginMsg> registerList;
    private void register(String mobile, String verify_code){
        Call<List<LoginMsg>> call= App.apiInterface.register(PublicVariable.key,mobile,verify_code);
        call.enqueue(new Callback<List<LoginMsg>>() {
            @Override
            public void onResponse(Call<List<LoginMsg>> call, Response<List<LoginMsg>> response) {
                registerList = response.body();
                Toast.makeText(getContext(),registerList.get(0).msg, Toast.LENGTH_LONG).show();
                if(registerList.get(0).status.equals("1")){
                    onRegisterSuccessListener.onSuccess();
                    hideKeyboard(getActivity());
                    Setting s= new Setting();
                    s.MDU=registerList.get(0).MDU;
                    s.uid=registerList.get(0).userId;
                    s.save();
                    FragmentTransaction ft=getFragmentManager().beginTransaction();
                    ft.add(R.id.frame_layout, new MainFragment()).addToBackStack("").commit();
                }
            }
            @Override
            public void onFailure(Call<List<LoginMsg>> call, Throwable t) {

                Toast.makeText(getContext(),registerList.get(0).msg, Toast.LENGTH_LONG).show();
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
