package ir.rayapars.honarfakher.fragments;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ir.rayapars.honarfakher.BR;
import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.adapters.UniversalAdapter2;
import ir.rayapars.honarfakher.classes.WishList;
import ir.rayapars.honarfakher.databinding.FragmentWishListBinding;

public class WishListFragment extends Fragment {

    View v;
    FragmentWishListBinding binding;
    UniversalAdapter2 adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentWishListBinding.inflate(getLayoutInflater());
        v = binding.getRoot();
        v.setFocusable(true);
        v.setFocusable(true);


        final List<WishList> wishList = WishList.listAll(WishList.class);

        if (wishList.size() == 0) {

            Toast.makeText(getContext(), "لیست علاقه مندی خالی است.", Toast.LENGTH_SHORT).show();

        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.myRecyclerView.setLayoutManager(layoutManager);
        adapter = new UniversalAdapter2(R.layout.items_wish_list, wishList, BR.var);
        binding.myRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new UniversalAdapter2.OnItemClickListener() {
            @Override
            public void onClick(ViewDataBinding binding, int position) {

                FragmentTransaction t = getFragmentManager().beginTransaction();
                ProductDetailsFragment detailsFragment = new ProductDetailsFragment();
                detailsFragment.idProduct=wishList.get(position).pid;
                t.add(R.id.frame_layout, detailsFragment).addToBackStack(" ").commit();

            }
        });


        adapter.setOnItemBindListener(new UniversalAdapter2.OnItemBindListener() {
            @Override
            public void onBind(final ViewDataBinding binding, final int position) {

                View view = binding.getRoot();
                TextView delete = view.findViewById(R.id.txtDetails);

                delete.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        List<WishList> wishList = WishList.listAll(WishList.class);
                        wishList.get(position).delete();

                        List<WishList> wishList1 = WishList.listAll(WishList.class);

                        if (wishList1.size() == 0) {

                            Toast.makeText(getContext(), "لیست علاقه مندی خالی است.", Toast.LENGTH_SHORT).show();

                            adapter.setList(wishList1);
                            adapter.notifyDataSetChanged();

                        } else {

                            adapter.setList(wishList1);
                            adapter.notifyDataSetChanged();

                        }
                    }
                });
            }
        });


        //اضافه کردن دکمه بازگشت در toolbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().popBackStack();
            }
        });

        binding.toolbar.setTitle("مانتو بلوط");

        return v;
    }
}