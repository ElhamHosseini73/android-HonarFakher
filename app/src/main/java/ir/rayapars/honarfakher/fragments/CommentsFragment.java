package ir.rayapars.honarfakher.fragments;

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

import java.util.List;

import ir.rayapars.honarfakher.BR;
import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.adapters.UniversalAdapter2;
import ir.rayapars.honarfakher.classes.ProductDetails;
import ir.rayapars.honarfakher.classes.Setting;
import ir.rayapars.honarfakher.databinding.FragmentCommentsBinding;

public class CommentsFragment extends Fragment {
    View x;

    public ProductDetails productDetails;
    public String title;
    FragmentCommentsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentCommentsBinding.inflate(getLayoutInflater());
        x = binding.getRoot();
        x.setClickable(true);
        x.setFocusable(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbar.setTitle("نظرات کاربران");
        binding.countComment.setText("از مجموعه " + productDetails.comments + " رای ثبت شده.");

        binding.score.setText(productDetails.rate + " از 5 ");
        float rate = Float.parseFloat(productDetails.rate);

        binding.rateBar.setRating(rate);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        binding.title.setText(title);

        UniversalAdapter2 adapter = new UniversalAdapter2(R.layout.item_comment, productDetails.reviews, BR.comment);
        binding.commentsRecyclerView.setLayoutManager(layoutManager);
        binding.commentsRecyclerView.setAdapter(adapter);

        binding.floatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Setting> list = Setting.listAll(Setting.class);

                if (list.size() == 0) {

                    FragmentTransaction transactionLogin = getFragmentManager().beginTransaction();

                    LoginFragment loginFragment = new LoginFragment();

                    loginFragment.setOnLoginSuccessListener(new LoginFragment.OnLoginSuccessListener() {
                        @Override
                        public void onSuccess() {

                        }
                    });

                    transactionLogin.add(R.id.frame_layout, loginFragment).addToBackStack("").commit();

                } else {

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    WriteCommentFragment writeCommentFragment = new WriteCommentFragment();
                    writeCommentFragment.idProduct = productDetails.getId();
                    fragmentTransaction.add(R.id.frame_layout, writeCommentFragment).addToBackStack("").commit();

                }

            }
        });

        return x;

    }

}
