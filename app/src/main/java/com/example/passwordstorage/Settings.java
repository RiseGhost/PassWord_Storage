package com.example.passwordstorage;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Switch;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener((event) -> {finish();});
        SharedPreferences sharedPreferences = getSharedPreferences("biometric",MODE_PRIVATE);
        String bio = sharedPreferences.getString("bio","");
        Switch biometric = findViewById(R.id.bio);
        biometric.setOnCheckedChangeListener((buttonView,isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (isChecked)
                editor.putString("bio","true");
            else
                editor.putString("bio","false");
            editor.apply();
        });
        if(bio.equals("true"))      biometric.setChecked(true);
    }
}