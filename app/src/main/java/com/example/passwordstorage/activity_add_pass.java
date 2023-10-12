package com.example.passwordstorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.passwordstorage.XMLElements.PassView;
import com.example.passwordstorage.database.dao.dao;
import com.example.passwordstorage.database.database;

public class activity_add_pass extends AppCompatActivity {
    private PassView passView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pass);
        passView = findViewById(R.id.passview);
        passView.setCategory("Social");
        RadioButton rb1 = findViewById(R.id.Social);
        rb1.setChecked(true);
        setRadioFunc(rb1);
        setRadioFunc(findViewById(R.id.Games));
        setRadioFunc(findViewById(R.id.Web));
        setRadioFunc(findViewById(R.id.Other));
        RadioButton theme1 = findViewById(R.id.theme1);
        theme1.setChecked(true);
        setRadioThemeFunc(theme1,ContextCompat.getDrawable(getApplicationContext(),R.drawable.radio_theme_1),ContextCompat.getDrawable(getApplicationContext(),R.drawable.radio_theme_1_select),1);
        setRadioThemeFunc(findViewById(R.id.theme2),ContextCompat.getDrawable(getApplicationContext(),R.drawable.radio_theme_2),ContextCompat.getDrawable(getApplicationContext(),R.drawable.radio_theme_2_select),2);
        setRadioThemeFunc(findViewById(R.id.theme3),ContextCompat.getDrawable(getApplicationContext(),R.drawable.radio_theme_3),ContextCompat.getDrawable(getApplicationContext(),R.drawable.radio_theme_3_select),3);
        setRadioThemeFunc(findViewById(R.id.theme4),ContextCompat.getDrawable(getApplicationContext(),R.drawable.radio_theme_4),ContextCompat.getDrawable(getApplicationContext(),R.drawable.radio_theme_4_select),4);

        //Programing the interaction of text inputs with PassView
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        EditText ETAppName = findViewById(R.id.name);
        ETAppName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0)      passView.setAppName(s.toString());
                else                    passView.setAppName("AppName");
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        EditText ETUserName = findViewById(R.id.username);
        ETUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0)      passView.setUserName(s.toString());
                else                    passView.setUserName("UserName");
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        EditText ETPassWord = findViewById(R.id.pass);
        ETPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0)      passView.setPass(s.toString());
                else                    passView.setPass("********");
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        findViewById(R.id.Insert).setOnClickListener((event) -> {
            if (ETAppName.getText().toString().equals("")){
                Toast.makeText(activity_add_pass.this,"AppName Invalid",Toast.LENGTH_SHORT).show();
                return;
            }
            if (ETUserName.getText().toString().equals("")){
                Toast.makeText(activity_add_pass.this,"UserName Invalid",Toast.LENGTH_SHORT).show();
                return;
            }
            if (ETPassWord.getText().toString().equals("")){
                Toast.makeText(activity_add_pass.this,"PassWord Invalid",Toast.LENGTH_SHORT).show();
                return;
            }
            new DB();
        });
    }

    public void setRadioFunc(RadioButton btn){
        btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    passView.setCategory(btn.getText().toString());
                    btn.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.radio_category));
                }
                else                btn.setBackground(null);
            }
        });
    }

    public void setRadioThemeFunc(RadioButton btn, Drawable NotSelect, Drawable Select,int Theme){
        btn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                btn.setBackground(Select);
                passView.setBackgroundView(Theme);
            }
            else            btn.setBackground(NotSelect);
        });
    }

    private class DB extends Thread{

        public DB(){start();}

        @Override
        public void run(){
            try {
                database db = Room.databaseBuilder(getApplicationContext(), database.class,"password").build();
                dao Dao = db.getPassWordDao();
                Dao.Insert(passView.BuildPassWord());
                finish();
            }   catch (Exception e){}
        }
    }
}