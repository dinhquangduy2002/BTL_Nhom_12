package com.pnhue.myfoodapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.pnhue.myfoodapp.MainActivity;
import com.pnhue.myfoodapp.R;
import com.pnhue.myfoodapp.models.UserModel;

public class RegistrationActivity extends AppCompatActivity {

    EditText name, email, pass;
    Button btnRegister;
    TextView signIn;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        name = findViewById(R.id.edt_full_name);
        email = findViewById(R.id.edt_email_login);
        pass = findViewById(R.id.edt_password_login);
        btnRegister = findViewById(R.id.btn_sign_in);
        signIn = findViewById(R.id.tv_sign_in);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
    }

    private void createUser() {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPass = pass.getText().toString();
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "Vui lòng nhập họ tên!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Vui lòng nhập email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userPass)){
            Toast.makeText(this, "Vui lòng nhập password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPass.length() < 6){
            Toast.makeText(this, "Password phải từ 6 ký tự trở lên!", Toast.LENGTH_SHORT).show();
            return;
        }
        //create User
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    UserModel userModel = new UserModel(userName, userEmail, userPass);
                    String id = task.getResult().getUser().getUid();
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    intent.putExtra("userId", id);
                    firebaseDatabase.getReference().child("Admin").child(id).setValue(userModel);
                    Toast.makeText(RegistrationActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else{
                    Toast.makeText(RegistrationActivity.this, "Error: "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}