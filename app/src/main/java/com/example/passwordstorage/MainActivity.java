package com.example.passwordstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
            String pass = sharedPreferences.getString("pass","");
            if (!pass.equals("")){
                startActivity(new Intent(this, Login.class));
                finish();
            }
            findViewById(R.id.Next).setOnClickListener((event) -> {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            });
        }   catch (Exception e){
            Log.d("file", e.getMessage());
        }
    }
}