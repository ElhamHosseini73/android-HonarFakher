package ir.rayapars.honarfakher.adapters;

import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.classes.ProductDetails;
import ir.rayapars.honarfakher.databinding.ItemWondersBinding;
import ir.rayapars.honarfakher.fragments.ProductDetailsFragment;

public class HorizontalWondersAdapter extends RecyclerView.Adapter<HorizontalWondersAdapter.MyHolder> {

    List<ProductDetails> list;
    AppCompatActivity activity;

    public HorizontalWondersAdapter(List<ProductDetails> list, AppCompatActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public HorizontalWondersAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemWondersBinding binding = ItemWondersBinding.inflate(layoutInflater, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalWondersAdapter.MyHolder holder, int position) {

        final ProductDetails item = list.get(position);
        holder.binding.setProduct(item);
        holder.binding.productImage.setImageURI((Uri) item.image);
        holder.binding.productPrice.setPaintFlags(holder.binding.productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                //  productDetailsFragment.product=item;
                transaction.add(R.id.frame_layout, productDetailsFragment).addToBackStack("").commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ItemWondersBinding binding;

        public MyHolder(ItemWondersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
