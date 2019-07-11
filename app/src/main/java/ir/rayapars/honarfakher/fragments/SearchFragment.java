package ir.rayapars.honarfakher.fragments;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import ir.rayapars.honarfakher.BR;
import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.adapters.UniversalAdapter2;
import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.ProductDetails;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.classes.Search;
import ir.rayapars.honarfakher.databinding.FragmentSearchBinding;
import ir.rayapars.honarfakher.databinding.ItemsSearchBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    View x;
    FragmentSearchBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(getLayoutInflater());
        x = binding.getRoot();
        x.setFocusable(true);
        x.setClickable(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.myRecyclerView.setLayoutManager(layoutManager);

        binding.btnVoiceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                {
                    try {
                        String language = "fa-IR";
                        Intent voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        voiceIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "لطفا صحبت کنید");
                        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, language);
                        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);
                        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, language);
                        voiceIntent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, language);
                        startActivityForResult(voiceIntent, 2018);

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }
        });

        //btnBack
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().popBackStackImmediate();
            }
        });


        //search

        binding.editTextSearch.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                search("1", "10", binding.editTextSearch.getText().toString());
                return false;
            }
        });

        return x;
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(SimpleDraweeView draweeImage, String url) {
        if (url != null) {
            draweeImage.setImageURI(url);
        }
    }

    List<Search> search = new ArrayList<>();
    List<ProductDetails> product = new ArrayList<>();
    String txtExist = " دارد";

    private void search(String page, String perPage, String q) {

        Call<List<Search>> call = App.apiInterface.search(PublicVariable.key, page, perPage, q);

        call.enqueue(new Callback<List<Search>>() {
            @Override
            public void onResponse(Call<List<Search>> call, final Response<List<Search>> response) {

                search = response.body();

                if (search.size() == 0) {

                    Toast.makeText(x.getContext(), "متاسفانه این محصول در حال حاضر وجود ندارد", Toast.LENGTH_SHORT).show();

                } else {

                    try {

                        if (search.get(0).status.equals("0")) {

                            Toast.makeText(x.getContext(), "" + search.get(0).msg, Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {


                        int exist = 0;
                        try {
                            exist = Integer.parseInt(search.get(0).exist);
                        } catch (NumberFormatException ee) {
                            ee.printStackTrace();
                        }

                        try {
                            switch (exist) {

                                case 0:
                                    txtExist = "متاسفانه این محصول در حال حاضر وجود ندارد";
                                    break;

                                case 1:
                                    txtExist = "وجود دارد";
                                    break;
                            }
                        } catch (Exception de) {
                            de.printStackTrace();
                        }

                        UniversalAdapter2 adapter = new UniversalAdapter2(R.layout.items_search, search, BR.var);
                        adapter.setOnItemBindListener(new UniversalAdapter2.OnItemBindListener() {

                            @Override
                            public void onBind(ViewDataBinding binding, int position) {

                                ItemsSearchBinding b = ((ItemsSearchBinding) binding);
                                b.txtExist.setText(txtExist);
                                if (search.get(position).discount.equals("0")) {

                                    b.txtPrice.setVisibility(View.GONE);
                                }

                            }
                        });

                        adapter.setOnItemClickListener(new UniversalAdapter2.OnItemClickListener() {
                            @Override
                            public void onClick(ViewDataBinding binding, int position) {

                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_out_right, android.R.anim.slide_out_right);
                                ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                                productDetailsFragment.idProduct = search.get(position).id;
                                productDetailsFragment.sectionParent = search.get(position).sId;
                                ft.add(R.id.frame_layout, productDetailsFragment).addToBackStack("").commit();

                            }
                        });

                        binding.myRecyclerView.setAdapter(adapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Search>> call, Throwable t) {
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 2018) {
                Object photo = data.getExtras();
                ArrayList<String> textMatchList = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                binding.editTextSearch.setText(binding.editTextSearch.getText().toString() + " " + textMatchList.get(0));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
