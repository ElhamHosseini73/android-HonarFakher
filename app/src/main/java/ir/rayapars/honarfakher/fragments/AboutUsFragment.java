package ir.rayapars.honarfakher.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ir.rayapars.honarfakher.classes.About;
import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.databinding.FragmentAboutUsBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUsFragment extends Fragment {

    View x;
    FragmentAboutUsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAboutUsBinding.inflate(getLayoutInflater());
        x = binding.getRoot();
        x.setClickable(true);
        x.setFocusable(true);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("درباره ما");

        about();
        return x;
    }

    List<About> about;

    private void about(){

        Call<List<About>> call= App.apiInterface.about(PublicVariable.key);
        call.enqueue(new Callback<List<About>>() {
            @Override
            public void onResponse(Call<List<About>> call, Response<List<About>> response) {
                about=response.body();
                binding.email.setText(about.get(0).email);
                binding.call.setText(about.get(0).tel);
                binding.mobile.setText(about.get(0).mobile);
            }

            @Override
            public void onFailure(Call<List<About>> call, Throwable t) {

            }
        });
    }
}
