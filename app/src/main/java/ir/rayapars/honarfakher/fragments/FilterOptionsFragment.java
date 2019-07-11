package ir.rayapars.honarfakher.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ir.rayapars.honarfakher.databinding.FragmentFilterOptionsBinding;

public class FilterOptionsFragment extends Fragment {

    View x;
    public String id, featuredProduct, orderProduct, popularProduct, quantity, discount, checkRadioButtom;
    FragmentFilterOptionsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentFilterOptionsBinding.inflate(getLayoutInflater());

        x = binding.getRoot();
        x.setFocusable(true);
        x.setClickable(true);

        if (checkRadioButtom.equals("discount")) {

            binding.discount.setChecked(true);
        }

        if (checkRadioButtom.equals("exist")) {

            binding.exist.setChecked(true);
        }

        if (checkRadioButtom.equals("two")) {

            binding.discount.setChecked(true);
            binding.exist.setChecked(true);

        }

        binding.btnFind.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (binding.exist.isChecked() && binding.discount.isChecked()) {

                    getFragmentManager().popBackStack();

                    Intent intent = new Intent();
                    intent.putExtra("featuredProduct", featuredProduct);
                    intent.putExtra("popularProduct", popularProduct);
                    intent.putExtra("idParent", id);
                    intent.putExtra("orderProduct", orderProduct);
                    intent.putExtra("quantity", "1");
                    intent.putExtra("discount", "1");
                    intent.putExtra("checkRadioButtom", "two");


                    int resultCode = 300;
                    getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);

                } else if (binding.discount.isChecked()) {

                    getFragmentManager().popBackStack();

                    Intent intent = new Intent();
                    intent.putExtra("featuredProduct", featuredProduct);
                    intent.putExtra("popularProduct", popularProduct);
                    intent.putExtra("idParent", id);
                    intent.putExtra("orderProduct", orderProduct);
                    intent.putExtra("quantity", "");
                    intent.putExtra("discount", "1");
                    intent.putExtra("checkRadioButtom", "discount");

                    int resultCode = 300;
                    getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);

                } else if (binding.exist.isChecked()) {

                    getFragmentManager().popBackStack();

                    Intent intent = new Intent();
                    intent.putExtra("featuredProduct", featuredProduct);
                    intent.putExtra("popularProduct", popularProduct);
                    intent.putExtra("idParent", id);
                    intent.putExtra("orderProduct", orderProduct);
                    intent.putExtra("quantity", "1");
                    intent.putExtra("discount", "");
                    intent.putExtra("checkRadioButtom", "exist");

                    int resultCode = 300;
                    getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);

                } else {

                    Toast.makeText(getContext(), "لطفا گزینه مورد نظر خود را انتخاب کنید", Toast.LENGTH_LONG).show();
           
                }

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
