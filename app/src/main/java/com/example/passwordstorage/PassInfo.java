package com.example.passwordstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.passwordstorage.XMLElements.PassView;

public class PassInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_info);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener((event) -> {finish();});
        Intent intent = getIntent();
        String appName = intent.getStringExtra("AppName");
        String passWord = intent.getStringExtra("PassWord");
        String userName = intent.getStringExtra("UserName");
        String category = intent.getStringExtra("Category");
        int theme = intent.getIntExtra("Theme", 1);
        PassView passView = findViewById(R.id.PassView);
        passView.setPassWord(appName, passWord, userName, category, theme);
    }
}