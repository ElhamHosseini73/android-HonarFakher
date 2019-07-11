package ir.rayapars.crush724.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.classes.ModelColor;


/**
 * Created by ali ahmadian on 5/26/2019.
 */

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

    public List<ModelColor> list;
    Context context;
    public List<ModelColor> listAdd = new ArrayList<>();
    boolean isFrist=true;

    public ColorAdapter(Context context, List<ModelColor> list) {
        this.list = list;
        this.context = context;
        listAdd = list;
    }

    @Override
    public ColorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final ModelColor color = list.get(position);
        holder.colorName.setText(color.getTitle());
        if (isFrist){
            if (position==0){

                if (Build.VERSION.SDK_INT >= 21) {
                    holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.border_blue,context.getTheme()));
                }else {
                    holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.border_blue));
                }

                list.get(position).select=1;
            }
        }

        if (list.get(position).select == 1) {

            if (Build.VERSION.SDK_INT >= 21) {
                holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.border_blue,context.getTheme()));
            }else {
                holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.border_blue));
            }

        } else {

            if (Build.VERSION.SDK_INT >= 21) {
                holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.border_linear_gray,context.getTheme()));
            }else {
                holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.border_linear_gray));
            }

        }

        if (color.getCode() == null) {

            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[]{ContextCompat.getColor(context, R.color.color1),
                            ContextCompat.getColor(context, R.color.color2),
                            ContextCompat.getColor(context, R.color.red),
                            ContextCompat.getColor(context, R.color.color4)});
            holder.color.setBackgroundDrawable(gradientDrawable);

        } else {
            holder.color.setBackgroundColor(Color.rgb(color.getCode().getRed(), color.getCode().getGreen(), color.getCode().getBlue()));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFrist=false;

                for (int i = 0; i < list.size(); i++) {

                    list.get(i).select=0;
                }
                list.get(position).select = 1;


                notifyDataSetChanged();
            }

        });

    }

    public static Color getColorByName(String name) {
        try {
            return (Color) Color.class.getField(name.toUpperCase()).get(null);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout color;
        TextView colorName;

        public ViewHolder(View itemView) {
            super(itemView);
            color = (LinearLayout) itemView.findViewById(R.id.color);
            colorName = (TextView) itemView.findViewById(R.id.nameColor);
        }
    }
}
