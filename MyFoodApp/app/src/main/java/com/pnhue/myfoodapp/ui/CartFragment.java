package com.pnhue.myfoodapp.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pnhue.myfoodapp.R;
import com.pnhue.myfoodapp.activities.PaymentActivity;
import com.pnhue.myfoodapp.activities.ProductDetailActivity;
import com.pnhue.myfoodapp.adapters.CartAdapter;
import com.pnhue.myfoodapp.models.CartModel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CartFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth auth;


    Button btnPay;
    TextView totalAmount;
    RecyclerView cartRec;
    List<CartModel> cartModelList;
    CartAdapter cartAdap;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        totalAmount = root.findViewById(R.id.cart_value);

        cartRec = root.findViewById(R.id.cart_rec);
        cartRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        cartModelList = new ArrayList<>();
        cartAdap = new CartAdapter(getActivity());
        cartAdap.setCartModelList(cartModelList);
        cartRec.setAdapter(cartAdap);


        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot document : task.getResult()){
                                String documentId = document.getId();
                                CartModel cartModel = document.toObject(CartModel.class);
                                cartModel.setDocumentId(documentId);
                                cartModelList.add(cartModel);
                                cartAdap.setCartModelList(cartModelList);
                                calTotalPrice();
                            }
                        }
                    }
                });

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            String documentId;
                            CartModel cartModel = null;
                            switch (dc.getType()) {
                                case MODIFIED:
                                    calTotalPrice();
                                    break;
                                case REMOVED:
                                    documentId = dc.getDocument().getId();
                                    for(CartModel cart : cartModelList) {
                                        if(cart.getDocumentId().equals(documentId)) {
                                            cartModel = cart;
                                        }
                                    }
                                    if(cartModel != null) {
                                        cartModelList.remove(cartModel);
                                    }
                                    cartAdap.setCartModelList(cartModelList);
                                    calTotalPrice();
                                    break;
                            }
                        }
                    }

                });


        btnPay = root.findViewById(R.id.cart_btn_pay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double priceAll = 0;
                for (CartModel cartModel : cartModelList) {
                    priceAll += cartModel.getPrice() * cartModel.getTotalQuantity();
                }
                showDiaLogThanhToan(priceAll);
                //Intent intent = new Intent(getContext(), PaymentActivity.class);
                //intent.putExtra("itemList", (Serializable) cartModelList);
                //startActivity(intent);
            }
        });
        return root;
    }
    private void showDiaLogThanhToan(double priceAll) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.fragment_thanhtoan);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        TextView txtTongTien  = dialog.findViewById(R.id.txtTongTien);
        EditText editDiaChi = dialog.findViewById(R.id.edtDiaChi);
        EditText editHoten = dialog.findViewById(R.id.editHoten);
        EditText edtEmail = dialog.findViewById(R.id.edtEmail);
        EditText edtSDT = dialog.findViewById(R.id.edtSDT);

        txtTongTien.setText("Tổng Tiền: "+priceAll+" V.N.D");

        dialog.findViewById(R.id.btnxacnhan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = editHoten.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String sdt = edtSDT.getText().toString().trim();
                String dc = editDiaChi.getText().toString().trim();
                if(hoten.length()>0){
                    if(dc.length()>0){
                        if(email.length()>0){
                            if(sdt.length()>9 && sdt.length()<12){
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                String date = sdf.format(Calendar.getInstance().getTime());
                                HashMap<String,Object> hashMap= new HashMap<>();
                                hashMap.put("hoten",hoten);
                                hashMap.put("email",email);
                                hashMap.put("diachi",dc);
                                hashMap.put("sdt",sdt);
                                hashMap.put("tongtien",priceAll);
                                hashMap.put("ngaythanhtoan",date);
                                db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                                        .collection("Order").add(hashMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if(task.isSuccessful()){
                                                    //Toast.makeText(this, "Vui lòng nhập Email!!", Toast.LENGTH_SHORT).show();
                                                    //return;
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
                        }else{
                            showToast("Vui lòng nhập email !");
                        }
                    }else{
                        showToast("Vui lòng nhập địa chỉ !");
                    }
                }else{
                    showToast("Vui lòng nhập họ tên !");
                }

            }
        });
    }
    void showToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void calTotalPrice() {
        int total = 0;
        for(CartModel cart :cartModelList ) {
            total += cart.getPrice() * cart.getTotalQuantity();
        }

        totalAmount.setText(String.valueOf(total));
    }



}