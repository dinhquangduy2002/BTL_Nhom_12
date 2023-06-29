package com.pnhue.myfoodapp.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pnhue.myfoodapp.R;
import com.pnhue.myfoodapp.models.NewsModel;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Context context;
    List<NewsModel> newsModelList;

    public NewsAdapter() {
    }

    public NewsAdapter(Context context, List<NewsModel> newsModelList) {
        this.context = context;
        this.newsModelList = newsModelList;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView time;
        TextView title;
        TextView des;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.news_img);
            time = itemView.findViewById(R.id.news_time);
            title = itemView.findViewById(R.id.news_title);
            des = itemView.findViewById(R.id.news_des);
        }
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.new_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        Glide.with(context).load(newsModelList.get(position).getNewImage()).into(holder.img);
        holder.time.setText(newsModelList.get(position).getTime());
        holder.title.setText(newsModelList.get(position).getName_news());
        holder.des.setText(newsModelList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return newsModelList.size();
    }
}
