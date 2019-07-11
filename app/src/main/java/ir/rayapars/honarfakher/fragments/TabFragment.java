package ir.rayapars.honarfakher.fragments;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ir.rayapars.honarfakher.BR;
import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.adapters.UniversalAdapter2;
import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.classes.Sections;
import ir.rayapars.honarfakher.databinding.FragmentTabBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabFragment extends Fragment {
    View x;
    public String parentID;

    FragmentTabBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTabBinding.inflate(getLayoutInflater());

        x = binding.getRoot();
        x.setFocusable(true);
        x.setClickable(true);
        getSections(parentID);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return x;
    }

    private void getSections(String parent) {

        Call<List<Sections>> call = App.apiInterface.getSections(PublicVariable.key, parent);

        call.enqueue(new Callback<List<Sections>>() {

            @Override
            public void onResponse(Call<List<Sections>> call, final Response<List<Sections>> response) {
                List<Sections> sections = new ArrayList<>();
                sections = response.body();


                UniversalAdapter2 adapter = new UniversalAdapter2(R.layout.item_category, sections, BR.category);
                binding.recyclerView.setAdapter(adapter);

                adapter.setOnItemClickListener(new UniversalAdapter2.OnItemClickListener() {
                    @Override
                    public void onClick(ViewDataBinding binding, int position) {

                        SubCategoryFragment subCategoryFragment=new SubCategoryFragment();
                        FragmentTransaction transactionLogin = getActivity().getSupportFragmentManager().beginTransaction();
                        subCategoryFragment.parent=response.body().get(position);
                        subCategoryFragment.parentID=response.body().get(0).id;

                        transactionLogin.add(R.id.frame_layout, subCategoryFragment).addToBackStack("").commit();

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Sections>> call, Throwable t) {

            }
        });
    }

}
