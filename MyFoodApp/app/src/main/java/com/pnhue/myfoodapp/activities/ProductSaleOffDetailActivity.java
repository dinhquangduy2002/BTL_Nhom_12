package com.pnhue.myfoodapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pnhue.myfoodapp.R;
import com.pnhue.myfoodapp.models.HomeProductSaleOffModel;

public class ProductSaleOffDetailActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    ImageView img, addItem, removeItem;
    TextView name, price, priceOld, save, des, quantity;
    Button add, buy;
    int totalQuantity = 1;
    int totalPrice = 0;
    Toolbar toolbar;
    HomeProductSaleOffModel homeProductSaleOffModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_sale_off_detail);

        /*toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        firestore = FirebaseFirestore.getInstance();

        final Object object = getIntent().getSerializableExtra("detail");
        if(object instanceof HomeProductSaleOffModel){
            homeProductSaleOffModel = (HomeProductSaleOffModel) object;
        }

        img = findViewById(R.id.detail_img);
        name = findViewById(R.id.detail_name);
        price = findViewById(R.id.detail_price);
        priceOld = findViewById(R.id.detail_priceOld);
        save = findViewById(R.id.detail_save);
        des = findViewById(R.id.detail_des);
        quantity = findViewById(R.id.detail_quantity);
        addItem = findViewById(R.id.detail_add_quantity);
        removeItem = findViewById(R.id.detail_remove_quantity);
        add = findViewById(R.id.detail_btn_add_cart);
        buy = findViewById(R.id.detail_btn_buy);

        if(homeProductSaleOffModel != null){
            Glide.with(getApplicationContext()).load(homeProductSaleOffModel.getProductImage()).into(img);
            name.setText(homeProductSaleOffModel.getName());
            price.setText(String.valueOf(homeProductSaleOffModel.getPrice()));
            priceOld.setText(String.valueOf(homeProductSaleOffModel.getPriceOld()));
            priceOld.setPaintFlags(priceOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            des.setText(homeProductSaleOffModel.getDes());
            save.setText(homeProductSaleOffModel.getSave());

            totalPrice = homeProductSaleOffModel.getPrice() * totalQuantity;

        }

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity < 10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity > 1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedToCart();
            }
        });

    }

    private void addedToCart() {

    }
}