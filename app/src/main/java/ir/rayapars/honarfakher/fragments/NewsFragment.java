package ir.rayapars.honarfakher.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.rayapars.honarfakher.DialogFragments.LoadingDialog;
import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.News;
import ir.rayapars.honarfakher.classes.NewsLIst;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.databinding.FragmentNewsBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {

    View x;
    public String id;
    List<News> news;
    FragmentNewsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentNewsBinding.inflate(getLayoutInflater());
        x = binding.getRoot();
        x.setClickable(true);
        x.setFocusable(true);

        news = new ArrayList<>();

        getNews("1", "10", id);
        return x;

    }


    private void getNews(final String page, final String perPage, final String id) {

        final LoadingDialog progressDialog = new LoadingDialog();
        progressDialog.show(getFragmentManager(), "");
        progressDialog.setCancelable(false);

        Call<NewsLIst> call = App.apiInterface.getNews(PublicVariable.key, page, perPage, id);

        call.enqueue(new Callback<NewsLIst>() {

            @Override
            public void onResponse(Call<NewsLIst> call, Response<NewsLIst> response) {

                progressDialog.dismiss();

                if (response.body().status.equals("1")) {

                    List<News> temp = response.body().news;
                    news.addAll(temp);

                    binding.txt.setText(news.get(0).fullText);
                    binding.img.setImageURI(news.get(0).image);

                } else {

                    Toast.makeText(x.getContext(), "" + response.body().msg, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NewsLIst> call, Throwable t) {

                progressDialog.dismiss();
            }

        });
    }

}
