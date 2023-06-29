package com.pnhue.myfoodapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pnhue.myfoodapp.R;
import com.pnhue.myfoodapp.activities.ProductByTypeActivity;
import com.pnhue.myfoodapp.models.HomeCategoryModel;

import java.util.List;
import java.util.Locale;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.HomeCategoryViewHolder> {


    Context context;
    List<HomeCategoryModel> listHomeCate;

    public HomeCategoryAdapter(Context context, List<HomeCategoryModel> listHomeCate) {
        this.context = context;
        this.listHomeCate = listHomeCate;
    }

    @NonNull
    @Override
    public HomeCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeCategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(listHomeCate.get(position).getImg()).into(holder.imageCate);
        holder.textCate.setText(listHomeCate.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductByTypeActivity.class);
                intent.putExtra("type", listHomeCate.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listHomeCate.size();
    }

    public class HomeCategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView imageCate;
        TextView textCate;

        public HomeCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCate = itemView.findViewById(R.id.cate_img);
            textCate = itemView.findViewById(R.id.cate_text);
        }
    }

}
