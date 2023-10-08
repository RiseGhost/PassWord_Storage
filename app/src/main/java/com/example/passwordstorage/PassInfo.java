package com.example.passwordstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.passwordstorage.XMLElements.PassView;

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
        PassView passView = findViewById(R.id.PassView);
        passView.setPassWord(AppName,PassWord,UserName,Category);
    }
}