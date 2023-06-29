package com.pnhue.myfoodapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.pnhue.myfoodapp.R;
import com.pnhue.myfoodapp.adapters.HomeProductAdapter;
import com.pnhue.myfoodapp.models.HomeProductModel;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductByTypeActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    RecyclerView productTypeRec;
    HomeProductAdapter productAdap;
    List<HomeProductModel> productList;
    Spinner spinerSort;
    String first = "Sắp xếp";
    String[] itemSapXep = {"Sắp xếp","Giá Thấp - Cao","Giá Cao - Thấp","Tên"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_type);


        firestore = FirebaseFirestore.getInstance();
        String type = getIntent().getStringExtra("type");
        productTypeRec = findViewById(R.id.product_type_rec);
        productTypeRec.setLayoutManager(new GridLayoutManager(this, 2));
        productList = new ArrayList<>();
        productAdap = new HomeProductAdapter(this, productList);
        productTypeRec.setAdapter(productAdap);

        //Fruit
        if (type != null && type.equalsIgnoreCase("Fruit")) {
            firestore.collection("AllProducts").whereEqualTo("type", "Fruit").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        HomeProductModel homeProductModel = documentSnapshot.toObject(HomeProductModel.class);
                        homeProductModel.setProductImages(documentSnapshot.getString("productImages"));
                        productList.add(homeProductModel);
                        productAdap.notifyDataSetChanged();
                    }
                }
            });
        }

        //Vegetable
        if (type != null && type.equalsIgnoreCase("Veg")) {
            firestore.collection("AllProducts").whereEqualTo("type", "Veg").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        HomeProductModel homeProductModel = documentSnapshot.toObject(HomeProductModel.class);
                        homeProductModel.setProductImages(documentSnapshot.getString("productImages"));
                        productList.add(homeProductModel);
                        productAdap.notifyDataSetChanged();
                    }
                }
            });
        }

        //Meat
        if (type != null && type.equalsIgnoreCase("Meat")) {
            firestore.collection("AllProducts").whereEqualTo("type", "Meat").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        HomeProductModel homeProductModel = documentSnapshot.toObject(HomeProductModel.class);
                        homeProductModel.setProductImages(documentSnapshot.getString("productImages"));
                        productList.add(homeProductModel);
                        productAdap.notifyDataSetChanged();
                    }
                }
            });
        }

        //Dry
        if (type != null && type.equalsIgnoreCase("Dry")) {
            firestore.collection("AllProducts").whereEqualTo("type", "Dry").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        HomeProductModel homeProductModel = documentSnapshot.toObject(HomeProductModel.class);
                        homeProductModel.setProductImages(documentSnapshot.getString("productImages"));
                        productList.add(homeProductModel);
                        productAdap.notifyDataSetChanged();
                    }
                }
            });
        }

        //Egg
        if (type != null && type.equalsIgnoreCase("Egg")) {
            firestore.collection("AllProducts").whereEqualTo("type", "Egg").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        HomeProductModel homeProductModel = documentSnapshot.toObject(HomeProductModel.class);
                        homeProductModel.setProductImages(documentSnapshot.getString("productImages"));
                        productList.add(homeProductModel);
                        productAdap.notifyDataSetChanged();
                    }
                }
            });
        }

        //Drink
        if (type != null && type.equalsIgnoreCase("Drink")) {
            firestore.collection("AllProducts").whereEqualTo("type", "Drink").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        HomeProductModel homeProductModel = documentSnapshot.toObject(HomeProductModel.class);
                        homeProductModel.setProductImages(documentSnapshot.getString("productImages"));
                        productList.add(homeProductModel);
                        productAdap.notifyDataSetChanged();
                    }
                }
            });
        }
        spinerSort = findViewById(R.id.spinerSort);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, itemSapXep);
        spinerSort.setAdapter(adapter);
        spinerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Comparator<HomeProductModel> homeProductModelComparator = null;
                if (position > 0) {
                    switch (position) {
                        case 1:
                            homeProductModelComparator = new Comparator<HomeProductModel>() {
                                @Override
                                public int compare(HomeProductModel o1, HomeProductModel o2) {
                                    return o1.getPrice() - o2.getPrice();
                                }
                            };
                            break;
                        case 2:
                            homeProductModelComparator = new Comparator<HomeProductModel>() {
                                @Override
                                public int compare(HomeProductModel o1, HomeProductModel o2) {
                                    return o2.getPrice() - o1.getPrice();
                                }
                            };
                            break;
                        case 3:
                            homeProductModelComparator = new Comparator<HomeProductModel>() {
                                @Override
                                public int compare(HomeProductModel o1, HomeProductModel o2) {
                                    return o1.getName().compareToIgnoreCase(o2.getName());
                                }
                            };
                            break;

                    }
                    Collections.sort(productList, homeProductModelComparator);
                    productAdap.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}