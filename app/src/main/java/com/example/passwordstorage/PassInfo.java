package com.example.passwordstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.passwordstorage.Dialogs.RemovePassWord;
import com.example.passwordstorage.XMLElements.PassView;
public class PassInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_info);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener((event) -> {finish();});
        Intent intent = getIntent();
        int id = intent.getIntExtra("ID",0);
        String appName = intent.getStringExtra("AppName");
        String passWord = intent.getStringExtra("PassWord");
        String userName = intent.getStringExtra("UserName");
        String category = intent.getStringExtra("Category");
        int theme = intent.getIntExtra("Theme", 1);
        PassView passView = findViewById(R.id.PassView);
        Button Remove = findViewById(R.id.remove);
        passView.setPassWord(appName, passWord, userName, category, theme);
        Remove.setOnClickListener((event) -> {
            try {
                PassWord pass = passView.BuildPassWord();
                pass.setId(id);
                new RemovePassWord(this,pass);
            } catch (Exception e) {
                Log.d("Remove Pass Create",e.getMessage());
            }
        });
    }
}