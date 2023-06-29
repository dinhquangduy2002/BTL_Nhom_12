package com.pnhue.myfoodapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pnhue.myfoodapp.R;
import com.pnhue.myfoodapp.activities.ProductByTypeActivity;
import com.pnhue.myfoodapp.adapters.HomeCategoryAdapter;
import com.pnhue.myfoodapp.adapters.HomeProductAdapter;
import com.pnhue.myfoodapp.adapters.HomeProductSaleOffAdapter;
import com.pnhue.myfoodapp.models.HomeCategoryModel;
import com.pnhue.myfoodapp.models.HomeProductModel;
import com.pnhue.myfoodapp.models.HomeProductSaleOffModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    FirebaseFirestore db;

    RecyclerView homeCateRec, homeSaleOffProductRec, homeProductFruitRec, homeProductMeatRec, homeProductVegRec;
    //category
    HomeCategoryAdapter homeCateAdap;
    List<HomeCategoryModel> homeCateModelList;

    //Product
    HomeProductSaleOffAdapter homeProducSaleOffAdap;
    HomeProductAdapter homeProductAdap;
    List<HomeProductSaleOffModel> homeProductSaleOffList; //sale off
    List<HomeProductSaleOffModel> homeProductEggList; //egg
    List<HomeProductModel> homeProductFruitList; //fruit
    List<HomeProductModel> homeProductVegList;
    List<HomeProductModel> homeProductMeatList;

    //Search
    EditText search;
    List<HomeProductModel> listSearch;
    RecyclerView searchRec;

    //Button
    Button btnFruit, btnMeat, btnVeg;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();

        //Category
        homeCateRec = root.findViewById(R.id.home_cate_rec);
        homeCateRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        homeCateModelList = new ArrayList<>();
        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategoryModel homeCategoryModel = document.toObject(HomeCategoryModel.class);
                                homeCateModelList.add(homeCategoryModel);
                                //Log.e("Tag", document.getId() + "=>" + document.getData());
                            }
                            homeCateAdap = new HomeCategoryAdapter(getActivity(), homeCateModelList);
                            homeCateRec.setAdapter(homeCateAdap);
                        } else {
                            Toast.makeText(getActivity(), "Error: "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //Product
                //Sale Off
        homeSaleOffProductRec = root.findViewById(R.id.home_product_sale_off_rec);
        homeSaleOffProductRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        homeProductSaleOffList = new ArrayList<>();
        db.collection("ProductSaleOff")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeProductSaleOffModel homeProductModel = document.toObject(HomeProductSaleOffModel.class);

                                homeProductSaleOffList.add(homeProductModel);
                            }
                            homeProducSaleOffAdap = new HomeProductSaleOffAdapter(getActivity(), homeProductSaleOffList);
                            homeSaleOffProductRec.setAdapter(homeProducSaleOffAdap);
                        } else {
                            Toast.makeText(getActivity(), "Error: "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //Fruit
        homeProductFruitRec = root.findViewById(R.id.home_product_fruit_rec);
        homeProductFruitRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        homeProductFruitList = new ArrayList<>();
        db.collection("ProductFruit")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeProductModel homeProductModel = document.toObject(HomeProductModel.class);
                                homeProductModel.setId(document.getId());
                                homeProductFruitList.add(homeProductModel);
                            }
                            homeProductAdap = new HomeProductAdapter(getActivity(), homeProductFruitList);
                            homeProductFruitRec.setAdapter(homeProductAdap);
                        } else {
                            Toast.makeText(getActivity(), "Error: "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //Meat
        homeProductMeatRec = root.findViewById(R.id.home_product_meat_rec);
        homeProductMeatRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        homeProductMeatList = new ArrayList<>();
        db.collection("ProductMeat")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeProductModel homeProductModel = document.toObject(HomeProductModel.class);
                                homeProductModel.setId(document.getId());
                                homeProductMeatList.add(homeProductModel);
                            }
                            homeProductAdap = new HomeProductAdapter(getActivity(), homeProductMeatList);
                            homeProductMeatRec.setAdapter(homeProductAdap);
                        } else {
                            Toast.makeText(getActivity(), "Error: "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //Vegetable
        homeProductVegRec = root.findViewById(R.id.home_product_veg_rec);
        homeProductVegRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        homeProductVegList =  new ArrayList<>();
        db.collection("ProductVeg")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeProductModel homeProductModel = document.toObject(HomeProductModel.class);
                                homeProductModel.setId(document.getId());
                                homeProductVegList.add(homeProductModel);
                            }
                            homeProductAdap = new HomeProductAdapter(getActivity(), homeProductVegList);
                            homeProductVegRec.setAdapter(homeProductAdap);
                        } else {
                            Toast.makeText(getActivity(), "Error: "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        //Search
        search = root.findViewById(R.id.home_search_box);
        searchRec = root.findViewById(R.id.home_search_rec);
        searchRec.setLayoutManager(new GridLayoutManager(getContext(), 2));
        listSearch = new ArrayList<>();
        homeProductAdap = new HomeProductAdapter(getContext(), listSearch);
        searchRec.setAdapter(homeProductAdap);
        searchRec.setHasFixedSize(true);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    listSearch.clear();
                    HomeProductAdapter x = new HomeProductAdapter(getContext(), listSearch);
                    searchRec.setAdapter(x);
                }else{
                    searchProduct(s.toString());
                }
            }
        });

        btnFruit = root.findViewById(R.id.home_btn_fruit);
        btnFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductByTypeActivity.class);
                startActivity(intent);
            }
        });


        return root;
    }
    private void searchProduct(String name) {
        if(name.isEmpty()) {
            return ;
        }
            db.collection("AllProducts")
                    .orderBy("name")
                    .startAt(name).endAt(name + "\uf8ff")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful() && task.getResult()!=null){
                                listSearch.clear();
                                for(DocumentSnapshot doc: task.getResult()){
                                    Log.e("add", "123");
                                    HomeProductModel homeProductModel = doc.toObject(HomeProductModel.class);
                                    homeProductModel.setId(doc.getId());
                                    listSearch.add(homeProductModel);

                                }
                                HomeProductAdapter x = new HomeProductAdapter(getContext(), listSearch);
                                searchRec.setAdapter(x);
                            }
                        }
                    });
    }

}