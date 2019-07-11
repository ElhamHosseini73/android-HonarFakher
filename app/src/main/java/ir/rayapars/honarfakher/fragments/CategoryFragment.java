package ir.rayapars.honarfakher.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ir.rayapars.honarfakher.adapters.MyPagerAdapter;
import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.classes.Sections;
import ir.rayapars.honarfakher.databinding.FragmentCategoryBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    View x;
    List<Sections> categories = new ArrayList<>();

    FragmentCategoryBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentCategoryBinding.inflate(getLayoutInflater());

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

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("دسته بندی محصولات");

        getSections("0");

        binding.viewPager.setSaveFromParentEnabled(false);

        return x;
    }

    private void getSections(String parent) {


        Call<List<Sections>> call = App.apiInterface.getSections(PublicVariable.key, parent);
        call.enqueue(new Callback<List<Sections>>() {

            @Override
            public void onResponse(Call<List<Sections>> call, Response<List<Sections>> response) {

                categories = response.body();


                MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), categories, getContext());
                binding.viewPager.setAdapter(myPagerAdapter);

            }

            @Override
            public void onFailure(Call<List<Sections>> call, Throwable t) {

            }


        });
    }

}
