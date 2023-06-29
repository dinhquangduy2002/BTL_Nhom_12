package com.pnhue.myfoodapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pnhue.myfoodapp.R;
import com.pnhue.myfoodapp.activities.ProductSaleOffDetailActivity;
import com.pnhue.myfoodapp.models.HomeProductSaleOffModel;


import java.util.List;

public class HomeProductSaleOffAdapter extends RecyclerView.Adapter<HomeProductSaleOffAdapter.ViewHolder> {

    Context context;
    List<HomeProductSaleOffModel> homeProductSaleOffList;

    public HomeProductSaleOffAdapter(Context context, List<HomeProductSaleOffModel> homeProductList) {
        this.context = context;
        this.homeProductSaleOffList = homeProductList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_product_sale_off_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvPercent.setText(homeProductSaleOffList.get(position).getPercentSaleOff());
        Glide.with(context).load(homeProductSaleOffList.get(position).getProductImage()).into(holder.imageProduct);
        holder.tvName.setText(homeProductSaleOffList.get(position).getName());
        holder.tvPrice.setText(String.valueOf(homeProductSaleOffList.get(position).getPrice()));
        holder.tvPriceOld.setText(String.valueOf(homeProductSaleOffList.get(position).getPriceOld()));
        holder.tvPriceOld.setPaintFlags(holder.tvPriceOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductSaleOffDetailActivity.class);
                intent.putExtra("detail", homeProductSaleOffList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeProductSaleOffList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvPercent;
        ImageView imageProduct;
        TextView tvName;
        TextView tvPrice;
        TextView tvPriceOld;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPercent = itemView.findViewById(R.id.product_percent);
            imageProduct = itemView.findViewById(R.id.procduct_img);
            tvName = itemView.findViewById(R.id.product_name);
            tvPrice = itemView.findViewById(R.id.product_price);
            tvPriceOld = itemView.findViewById(R.id.procduct_price_old);
        }
    }
}
