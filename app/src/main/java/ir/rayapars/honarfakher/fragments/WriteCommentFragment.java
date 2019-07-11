package ir.rayapars.honarfakher.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.LoginMsg;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.databinding.FragmentWriteCommentBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class WriteCommentFragment extends Fragment {

    View x;
    public String idProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final FragmentWriteCommentBinding binding = FragmentWriteCommentBinding.inflate(getLayoutInflater());

        x = binding.getRoot();
        x.setClickable(true);
        x.setFocusable(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbar.setTitle("ثبت نقد و بررسی");

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().popBackStack();

            }
        });


        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.comment.getText().toString().trim().equals("") || binding.title.getText().toString().trim().equals("")) {

                    Toast.makeText(x.getContext(), "لطفا فیلدهای ضروری را کامل نمایید.", Toast.LENGTH_SHORT).show();

                } else {

                    AddComment(binding.title.getText() + "", binding.comment.getText() + "", idProduct, "2");

                }

            }
        });

        return x;
    }


    public void AddComment(String name, String comment, String idProduct, String rating) {

        Call<List<LoginMsg>> call = App.apiInterface.addComment(PublicVariable.key, name, comment, idProduct, rating);

        call.enqueue(new Callback<List<LoginMsg>>() {

            @Override
            public void onResponse(Call<List<LoginMsg>> call, Response<List<LoginMsg>> response) {

                if (response.body().get(0).status.equals("1")) {

                    Toast.makeText(x.getContext(), "" + response.body().get(0).msg, Toast.LENGTH_SHORT).show();

                    try {

                        onActivityResult(getTargetRequestCode(), RESULT_OK, null);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    getActivity().getSupportFragmentManager().popBackStack();

                } else {

                    Toast.makeText(x.getContext(), "" + response.body().get(0).msg, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<LoginMsg>> call, Throwable t) {

            }

        });
    }
}
