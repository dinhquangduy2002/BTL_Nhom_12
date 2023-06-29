package com.pnhue.myfoodapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pnhue.myfoodapp.R;
import com.pnhue.myfoodapp.models.CartModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        List<CartModel> list = (ArrayList<CartModel>) getIntent().getSerializableExtra("itemList");
        if(list != null && list.size() > 0){
            for(CartModel model:list){
                final HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("img", model.getImg());
                cartMap.put("name", model.getName());
                cartMap.put("price", model.getPrice());
                cartMap.put("totalQuantity", model.getTotalQuantity());
                cartMap.put("totalPrice", model.getTotalPrice());

                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("Order").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(PaymentActivity.this, "Ordered", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
            }
        }

    }
}