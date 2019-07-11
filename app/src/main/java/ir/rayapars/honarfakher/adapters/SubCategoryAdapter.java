package ir.rayapars.honarfakher.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.classes.Category;
import ir.rayapars.honarfakher.databinding.ItemCategoryBinding;
import ir.rayapars.honarfakher.fragments.SubCategoryFragment;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyHolder> {
    List<Category> list;
    AppCompatActivity activity;

    public SubCategoryAdapter(List<Category> list, AppCompatActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SubCategoryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        ItemCategoryBinding binding=ItemCategoryBinding.inflate(layoutInflater,parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.MyHolder holder, int position) {
        final Category item=list.get(position);
       // holder.binding.setCategory(item);
        holder.binding.image.setImageURI(item.getIcon());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
                SubCategoryFragment subCategoryFragment=  new SubCategoryFragment();
             //   subCategoryFragment.parent=item;
                transaction.add(R.id.frame_layout,subCategoryFragment).addToBackStack("").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ItemCategoryBinding binding;
        public MyHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
