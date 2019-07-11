package ir.rayapars.honarfakher.adapters;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import ir.rayapars.honarfakher.classes.Category;
import ir.rayapars.honarfakher.databinding.ItemHorizontalCategoryBinding;

public class HorizontalCategoryAdapter extends RecyclerView.Adapter<HorizontalCategoryAdapter.MyHolder> {
    private List<Category> list;
    AppCompatActivity activity;

    public HorizontalCategoryAdapter(List<Category> list, AppCompatActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public HorizontalCategoryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        ItemHorizontalCategoryBinding binding=ItemHorizontalCategoryBinding.inflate(layoutInflater,parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalCategoryAdapter.MyHolder holder, int position) {

        Category item=list.get(position);
        //holder.binding.setCategory(item);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ItemHorizontalCategoryBinding binding;
        public MyHolder( ItemHorizontalCategoryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
