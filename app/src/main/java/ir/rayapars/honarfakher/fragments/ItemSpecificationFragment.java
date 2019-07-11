package ir.rayapars.honarfakher.fragments;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ir.rayapars.honarfakher.BR;
import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.adapters.UniversalAdapter2;
import ir.rayapars.honarfakher.classes.MySpicification;
import ir.rayapars.honarfakher.databinding.FragmentItemSpecificationBinding;
import ir.rayapars.honarfakher.databinding.ItemSpecificationBinding;

public class ItemSpecificationFragment extends Fragment {
    View v;

    public List<MySpicification> ms;
    public String title;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentItemSpecificationBinding binding = FragmentItemSpecificationBinding.inflate(getLayoutInflater());
        v = binding.getRoot();
        v.setFocusable(true);
        v.setFocusable(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        binding.toolbar.setTitle("مشخصات");
        binding.title.setText(title);

        binding.myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        UniversalAdapter2 adapter2 = new UniversalAdapter2(R.layout.item_specification, ms, BR.cat);
        binding.myRecyclerView.setAdapter(adapter2);

        adapter2.setOnItemBindListener(new UniversalAdapter2.OnItemBindListener() {
            @Override
            public void onBind(ViewDataBinding binding, int position) {
                ItemSpecificationBinding bind = (ItemSpecificationBinding) binding;
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                bind.recyclerView.setLayoutManager(layoutManager);
                UniversalAdapter2 adapter3 = new UniversalAdapter2(R.layout.item_specification1, ms.get(position).specs, BR.mySpec);
                bind.recyclerView.setAdapter(adapter3);
                adapter3.setOnItemBindListener(new UniversalAdapter2.OnItemBindListener() {
                    @Override
                    public void onBind(ViewDataBinding binding, int position) {
                    }
                });
            }
        });
        return v;
    }
}
