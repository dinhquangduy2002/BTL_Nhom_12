package com.pnhue.myfoodapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pnhue.myfoodapp.R;
import com.pnhue.myfoodapp.models.CartModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    Context context;
    List<CartModel> cartModelList;


    public CartAdapter(Context context) {
        this.context = context;
        this.cartModelList = cartModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public void setCartModelList(List<CartModel> cartModelList) {
        this.cartModelList = cartModelList;
        notifyDataSetChanged();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView img, cartSub, cartAdd;
        TextView name, price, totalQuantity, deleteItem;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cart_img);
            name = itemView.findViewById(R.id.cart_name);
            price = itemView.findViewById(R.id.cart_price);
            totalQuantity = itemView.findViewById(R.id.cart_quantity);
            deleteItem = itemView.findViewById(R.id.cart_delete);
            cartSub = itemView.findViewById(R.id.cart_sub);
            cartAdd = itemView.findViewById(R.id.cart_add);
        }
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(cartModelList.get(position).getName());
        holder.price.setText(String.valueOf(cartModelList.get(position).getPrice()));
        holder.totalQuantity.setText(String.valueOf(cartModelList.get(position).getTotalQuantity()));
        Glide.with(context).load(cartModelList.get(position).getImg()).into(holder.img);

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("AddToCart").document(cartModelList.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(context, "Item delete", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Error: "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        holder.cartAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quality = Integer.parseInt(holder.totalQuantity.getText().toString());
                quality += 1;
                holder.totalQuantity.setText(String.valueOf(quality));
                cartModelList.get(position).setTotalQuantity(quality);
                UpdateTotalPrice(position);
            }
        });
        holder.cartSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quality = Integer.parseInt(holder.totalQuantity.getText().toString());
                quality -= 1;
                holder.totalQuantity.setText(String.valueOf(quality));
                cartModelList.get(position).setTotalQuantity(quality);
                UpdateTotalPrice(position);
            }
        });

    }

    private void UpdateTotalPrice(int position) {
        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").document(cartModelList.get(position).getDocumentId())
                .set(cartModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }
}
