package com.example.passwordstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PassInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_info);
        Intent intent = getIntent();
        String AppName = intent.getStringExtra("AppName");
        String PassWord = intent.getStringExtra("PassWord");
        String UserName = intent.getStringExtra("UserName");
        String Category = intent.getStringExtra("Category");
        TextView Name = findViewById(R.id.textView2);
        TextView Pass = findViewById(R.id.textView3);
        TextView Cat = findViewById(R.id.textView4);
        TextView User = findViewById(R.id.textView5);
        Name.setText("AppName -> " + AppName);
        Pass.setText("PassWord -> " + PassWord);
        Cat.setText("Category -> " + Category);
        User.setText("UserName -> " + UserName);
        PassView passView = findViewById(R.id.PassView);
        passView.setPassWord(AppName,PassWord,UserName,Category);
    }
}