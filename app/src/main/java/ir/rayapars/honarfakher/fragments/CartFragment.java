package ir.rayapars.honarfakher.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.databinding.FragmentCartBinding;

public class CartFragment extends Fragment {
    View x;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentCartBinding binding = FragmentCartBinding.inflate(getLayoutInflater());
        x = binding.getRoot();
        x.setFocusable(true);
        x.setClickable(true);

        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.frame_layout, new MainFragment()).commit();
            }
        });

        return x;
    }
}
