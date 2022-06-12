package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.RegisterInfo.RegisterPhone;
import com.example.finalproject.RegisterInfo.RegisterSex;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEmail,mPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button register = findViewById(R.id.register);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        authStateListener = firebaseAuth -> {
            final FirebaseUser user = mAuth.getCurrentUser();
            if(user!=null){
                startActivity(new Intent(RegisterActivity.this, RegisterPhone.class));
                finish();
            }
        };
        register.setOnClickListener(view -> {
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(RegisterActivity.this, "Bạn chưa nhập email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }else {
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(RegisterActivity.this, "Bạn điền mail không đúng dịnh dạng", Toast.LENGTH_SHORT).show();
                }else if(password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Mật khẩu phải lớn hơn 6 chữ số", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, task -> {
                        if (!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            }catch (FirebaseAuthUserCollisionException existEmail){
                                Toast.makeText(RegisterActivity.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            String uid = mAuth.getCurrentUser().getUid();
                            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                            Map userInfo = new HashMap();
                            userInfo.put("profileImageUrl","default");
                            userInfo.put("phone","default");
                            userInfo.put("name","default");
                            userInfo.put("birthDay","19/10/2000");
                            userInfo.put("userSex","Nam");
                            userInfo.put("enemySex","Nữ");
                            userInfo.put("distance","20");
                            userInfo.put("introduce","");
                            userInfo.put("school","");
                            userInfo.put("address","");
                            db.updateChildren(userInfo);
                        }
                    });
                }
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }
}