package com.pnhue.myfoodapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pnhue.myfoodapp.R;

public class ProDuctImagesAdapter extends RecyclerView.Adapter<ProDuctImagesAdapter.ViewHolder> {

    Context context;
    String[] homeProductList;

    public ProDuctImagesAdapter(Context context, String[] homeProductList) {
        this.context = context;
        this.homeProductList = homeProductList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_product_item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(homeProductList[position].trim()).into(holder.imgProduct);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, ProductDetailActivity.class);
//                intent.putExtra("detail", homeProductList.get(position));
//                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return homeProductList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.procduct_img);

        }
    }
}
