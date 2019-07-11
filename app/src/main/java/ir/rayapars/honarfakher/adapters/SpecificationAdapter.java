package ir.rayapars.honarfakher.adapters;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ir.rayapars.honarfakher.classes.Template;

public class SpecificationAdapter extends RecyclerView.Adapter<SpecificationAdapter.MyHolder> {
    List<Template> list;
    AppCompatActivity activity;

    public SpecificationAdapter(List<Template> list, AppCompatActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SpecificationAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SpecificationAdapter.MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public MyHolder(View itemView) {
            super(itemView);
        }
    }
}
