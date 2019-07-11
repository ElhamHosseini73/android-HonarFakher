package ir.rayapars.honarfakher.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.rayapars.honarfakher.databinding.FragmentSortBinding;

public class SortFragment extends Fragment {

    View x;
    public String id, featuredProduct, orderProduct, popularProduct, quantity, discount, checkRadioButtom;

    FragmentSortBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentSortBinding.inflate(getLayoutInflater());
        x = binding.getRoot();
        x.setFocusable(true);
        x.setClickable(true);

        if (checkRadioButtom.equals("descending")) {

            binding.descending.setChecked(true);
        }
        if (checkRadioButtom.equals("ascending")) {

            binding.ascending.setChecked(true);
        }
        if (checkRadioButtom.equals("popular")) {

            binding.popular.setChecked(true);
        }
        if (checkRadioButtom.equals("news")) {

            binding.news.setChecked(true);
        }

        binding.descending.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                getFragmentManager().popBackStack();


                Intent intent = new Intent();
                intent.putExtra("featuredProduct", featuredProduct);
                intent.putExtra("popularProduct", popularProduct);
                intent.putExtra("idParent", id);
                intent.putExtra("orderProduct", "2");
                intent.putExtra("quantity", quantity);
                intent.putExtra("discount", discount);
                intent.putExtra("checkRadioButtom", "descending");

                int resultCode = 300;
                getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);

            }
        });

        binding.ascending.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                getFragmentManager().popBackStack();

                Intent intent = new Intent();
                intent.putExtra("featuredProduct", featuredProduct);
                intent.putExtra("popularProduct", popularProduct);
                intent.putExtra("idParent", id);
                intent.putExtra("orderProduct", "1");
                intent.putExtra("quantity", quantity);
                intent.putExtra("discount", discount);
                intent.putExtra("checkRadioButtom", "ascending");

                int resultCode = 300;
                getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);


            }
        });


        binding.popular.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                getFragmentManager().popBackStack();

                Intent intent = new Intent();
                intent.putExtra("featuredProduct", featuredProduct);
                intent.putExtra("popularProduct", popularProduct);
                intent.putExtra("idParent", id);
                intent.putExtra("orderProduct", "3");
                intent.putExtra("quantity", quantity);
                intent.putExtra("discount", discount);
                intent.putExtra("checkRadioButtom", "popular");

                int resultCode = 300;
                getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);

            }

        });

        binding.news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFragmentManager().popBackStack();

                Intent intent = new Intent();
                intent.putExtra("featuredProduct", featuredProduct);
                intent.putExtra("popularProduct", popularProduct);
                intent.putExtra("idParent", id);
                intent.putExtra("orderProduct", "4");
                intent.putExtra("quantity", quantity);
                intent.putExtra("discount", discount);
                intent.putExtra("checkRadioButtom", "news");

                int resultCode = 300;
                getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);

            }
        });

        binding.btnClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                getFragmentManager().popBackStackImmediate();

            }
        });

        return x;
    }
}
