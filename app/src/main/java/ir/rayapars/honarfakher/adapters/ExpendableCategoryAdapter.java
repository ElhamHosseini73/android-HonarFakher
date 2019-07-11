package ir.rayapars.honarfakher.adapters;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

import ir.rayapars.honarfakher.classes.Category;
import ir.rayapars.honarfakher.classes.ExpenCategory;
import ir.rayapars.honarfakher.databinding.ItemExpandableCategoryBinding;


public class ExpendableCategoryAdapter extends ExpandableRecyclerViewAdapter<ExpendableCategoryAdapter.CategoryViewHolder, ExpendableCategoryAdapter.SubCategoryViewHolder> {

    AppCompatActivity activity;

    public ExpendableCategoryAdapter(List<? extends ExpandableGroup> groups, AppCompatActivity activity1) {

        super(groups);
        this.activity = activity1;

    }

    @Override
    public CategoryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemExpandableCategoryBinding binding = ItemExpandableCategoryBinding.inflate(layoutInflater, parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public SubCategoryViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemExpandableCategoryBinding binding = ItemExpandableCategoryBinding.inflate(layoutInflater, parent, false);
        return new SubCategoryViewHolder(binding);
    }

    @Override
    public void onBindChildViewHolder(SubCategoryViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Category category = ((ExpenCategory) group).getItems().get(childIndex);
        holder.binding.title.setText(category.getTitle());
    }

    @Override
    public void onBindGroupViewHolder(CategoryViewHolder holder, int flatPosition, ExpandableGroup group) {

        holder.binding.title.setText(group.getTitle());

        if (group.getItemCount() == 0) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }
    }


    public class CategoryViewHolder extends GroupViewHolder {

        ItemExpandableCategoryBinding binding;

        public CategoryViewHolder(ItemExpandableCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }

    public class SubCategoryViewHolder extends ChildViewHolder {

        ItemExpandableCategoryBinding binding;

        public SubCategoryViewHolder(ItemExpandableCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}

