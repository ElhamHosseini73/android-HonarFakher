package ir.rayapars.honarfakher.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ir.rayapars.honarfakher.R;


public class UniversalAdapter2 extends RecyclerView.Adapter<UniversalAdapter2.MyHolder> {

    int viewID;
    List<?> list;
    int variableID;
    OnItemClickListener onItemClickListener;
    OnItemBindListener onItemBindListener;

    public UniversalAdapter2(int viewID, List<?> list, int variableID) {

        this.viewID = viewID;
        this.list = list;
        this.variableID = variableID;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public UniversalAdapter2.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewID, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final UniversalAdapter2.MyHolder holder, final int position) {

        if (viewID == R.layout.item_wonders) {

        }

        holder.binding.setVariable(variableID, list.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    onItemClickListener.onClick(holder.binding, position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        try {

            onItemBindListener.onBind(holder.binding, position);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;

        public MyHolder(ViewDataBinding binding) {

            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {

        this.onItemClickListener = onItemClickListener;

    }

    public void setOnItemBindListener(OnItemBindListener onItemBindListener) {
        this.onItemBindListener = onItemBindListener;
    }

    public interface OnItemClickListener {

        void onClick(ViewDataBinding binding, int position);

    }

    public interface OnItemBindListener {
        void onBind(ViewDataBinding binding, int position);

    }
}
