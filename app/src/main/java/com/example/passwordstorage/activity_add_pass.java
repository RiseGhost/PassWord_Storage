package com.example.passwordstorage;

import static java.lang.Math.PI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.passwordstorage.XMLElements.PassView;

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
}