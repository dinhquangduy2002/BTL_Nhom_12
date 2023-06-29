package com.pnhue.myfoodapp.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.pnhue.myfoodapp.R;
import com.pnhue.myfoodapp.activities.ProductDetailActivity;
import com.pnhue.myfoodapp.models.ContactModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ContactFragment extends Fragment {
    EditText Name, Phone, Email, Comment;
    Button btnSend;
    TextView imgMail, imgCall;
    FirebaseFirestore db;
    FirebaseAuth auth;


//    public ContactFragment() {
//        // Required empty public constructor
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contact, container, false);
        Name = root.findViewById(R.id.contact_name);
        Phone = root.findViewById(R.id.contact_phone);
        Email = root.findViewById(R.id.contact_email);
        Comment = root.findViewById(R.id.contact_comment);
        btnSend = root.findViewById(R.id.contact_btn_send);
        imgCall = root.findViewById(R.id.idCall);
        imgMail = root.findViewById(R.id.idEmail);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        //xu ly click
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = Email.getText().toString();
                String userName = Name.getText().toString();
                String phone = Phone.getText().toString();
                String text = Comment.getText().toString();
                if (userEmail.length() == 0|| text.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập Email!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userName.length() == 0|| text.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập họ và tên!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (text.length() == 0|| text.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập nội dung cần liên hệ!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.length() == 0|| text.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập số điện thoại!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                final HashMap<String, Object> ContactMap = new HashMap<>();
                ContactMap.put("name", Name.getText().toString());
                ContactMap.put("email",Email.getText().toString());
                ContactMap.put("phone", Phone.getText().toString());
                ContactMap.put("comment", Comment.getText().toString());

                db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("Contact").add(ContactMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(getContext(), "Gửi liên hệ thành công", Toast.LENGTH_SHORT).show();

                            }
                        });
                Name.setText("");
                Phone.setText("");
                Email.setText("");
                Comment.setText("");
            }
        });

        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //khai bao intent an
                Intent callintent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:19008523"));
//                //yeu cau su dong y cua nguoi dung
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                //v.getContext().startActivity(intent);
                startActivity(callintent);
            }
        });
        imgMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:toongteam@gmail.com")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, "");
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                startActivity(intent);
            }
        });
        return root;
    }

}
