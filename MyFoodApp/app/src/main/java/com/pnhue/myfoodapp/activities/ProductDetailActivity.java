package com.pnhue.myfoodapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pnhue.myfoodapp.R;
import com.pnhue.myfoodapp.adapters.ProDuctImagesAdapter;
import com.pnhue.myfoodapp.models.CartModel;
import com.pnhue.myfoodapp.models.HomeProductModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    ImageView img, addItem, removeItem;
    TextView name, price, des, unit, quantity;
    Button add, buy;
    int totalQuantity = 1;
    int totalPrice = 0;

    HomeProductModel homeProductModel = null;
    RecyclerView rcvProDuctImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        rcvProDuctImages = findViewById(R.id.rcvProductImages);


        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof HomeProductModel) {
            homeProductModel = (HomeProductModel) object;
        }
        try {
            String[] listImages = homeProductModel.getProductImages().split(",");
            ProDuctImagesAdapter adapter = new ProDuctImagesAdapter(this, listImages);
            rcvProDuctImages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            rcvProDuctImages.setAdapter(adapter);

        } catch (Exception e) {

        }


        img = findViewById(R.id.detail_img);
        name = findViewById(R.id.detail_name);
        price = findViewById(R.id.detail_price);
        des = findViewById(R.id.detail_des);
        unit = findViewById(R.id.detail_unit);
        quantity = findViewById(R.id.detail_quantity);
        addItem = findViewById(R.id.detail_add_quantity);
        removeItem = findViewById(R.id.detail_remove_quantity);
        add = findViewById(R.id.detail_btn_add_cart);
        buy = findViewById(R.id.detail_btn_buy);

        if (homeProductModel != null) {
            Glide.with(getApplicationContext()).load(homeProductModel.getProductImage()).into(img);
            name.setText(homeProductModel.getName());
            price.setText(String.valueOf(homeProductModel.getPrice()));
            des.setText(homeProductModel.getDes());
            unit.setText(homeProductModel.getUnit());

            totalPrice = homeProductModel.getPrice() * totalQuantity;
        }

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity < 10) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = homeProductModel.getPrice() * totalQuantity;
                }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity > 1) {
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = homeProductModel.getPrice() * totalQuantity;
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedToCart();
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double priceAll = totalQuantity * homeProductModel.getPrice();
                showDiaLogThanhToan(priceAll);
            }
        });
    }

    private void addedToCart() {
        firestore.collection("CurrentUser").
                document(auth.getCurrentUser().getUid())
                .collection("AddToCart")
                .whereEqualTo("id", homeProductModel.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.getResult().size() > 0) {
                            Toast.makeText(ProductDetailActivity.this, "Lớn hơn 1", Toast.LENGTH_SHORT).show();
                            for(QueryDocumentSnapshot dc : task.getResult()) {
                                CartModel cartModel = dc.toObject(CartModel.class);
                                int currentQuality = cartModel.getTotalQuantity();
                                cartModel.setTotalQuantity(currentQuality + Integer.parseInt(quantity.getText().toString()));
                                firestore.collection("CurrentUser").
                                        document(auth.getCurrentUser().getUid())
                                        .collection("AddToCart")
                                        .document(dc.getId())
                                        .set(cartModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(ProductDetailActivity.this, "Added To A Cart", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(ProductDetailActivity.this, "Nhỏ hơn 1", Toast.LENGTH_SHORT).show();
                            final HashMap<String, Object> cartMap = new HashMap<>();
                            cartMap.put("id", homeProductModel.getId());
                            cartMap.put("img", homeProductModel.getProductImages());
                            cartMap.put("name", homeProductModel.getName());
                            cartMap.put("price", Integer.parseInt(price.getText().toString()));
                            cartMap.put("totalQuantity", Integer.parseInt(quantity.getText().toString()));
                            cartMap.put("totalPrice", totalPrice);
                            firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                                    .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            Toast.makeText(ProductDetailActivity.this, "Added To A Cart", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                        }
                    }
                });


    }

    private void showDiaLogThanhToan(double priceAll) {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.fragment_thanhtoan);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        TextView txtTongTien = dialog.findViewById(R.id.txtTongTien);
        EditText editDiaChi = dialog.findViewById(R.id.edtDiaChi);
        EditText editHoten = dialog.findViewById(R.id.editHoten);
        EditText edtEmail = dialog.findViewById(R.id.edtEmail);
        EditText edtSDT = dialog.findViewById(R.id.edtSDT);

        txtTongTien.setText("Tổng Tiền: " + priceAll + " V.N.D");

        dialog.findViewById(R.id.btnxacnhan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = editHoten.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String sdt = edtSDT.getText().toString().trim();
                String dc = editDiaChi.getText().toString().trim();

                if (hoten.length() > 0) {
                    if (dc.length() > 0) {
                        if (email.length() > 0) {
                            if (sdt.length() > 9 && sdt.length() < 12) {
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                String date = sdf.format(Calendar.getInstance().getTime());
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("hoten", hoten);
                                hashMap.put("email", email);
                                hashMap.put("diachi", dc);
                                hashMap.put("sdt", sdt);
                                hashMap.put("tongtien", priceAll);
                                hashMap.put("ngaythanhtoan", date);
                                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                                        .collection("Order").add(hashMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if(task.isSuccessful()){
                                                    showToast("Thanh toán thành công !");
                                                    dialog.cancel();
                                                }else{
                                                    showToast("Thanh toán thất bại !");
                                                }
                                            }
                                        });
                            }else{
                                showToast("SDt không hợp lệ");
                            }
                        } else {
                            showToast("Vui lòng nhập email !");
                        }
                    } else {
                        showToast("Vui lòng nhập địa chỉ !");
                    }
                } else {
                    showToast("Vui lòng nhập họ tên !");
                }

            }
        });


    }

    void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}