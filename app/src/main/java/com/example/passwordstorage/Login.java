package com.example.passwordstorage;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.SecureRandom;
import java.util.Base64;

public class Login extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    TextView pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button submit = findViewById(R.id.Submit);
        pass = findViewById(R.id.NumberPass);
        TextViewSetFunc(findViewById(R.id.btn1));
        TextViewSetFunc(findViewById(R.id.btn2));
        TextViewSetFunc(findViewById(R.id.btn3));
        TextViewSetFunc(findViewById(R.id.btn4));
        TextViewSetFunc(findViewById(R.id.btn5));
        TextViewSetFunc(findViewById(R.id.btn6));
        TextViewSetFunc(findViewById(R.id.btn7));
        TextViewSetFunc(findViewById(R.id.btn8));
        TextViewSetFunc(findViewById(R.id.btn9));
        TextViewSetFunc(findViewById(R.id.btn0));
        findViewById(R.id.btnclean).setOnClickListener((event) -> {
            try {
               pass.setText(pass.getText().toString().substring(0,pass.getText().length() - 1));
            }   catch (Exception e){

            }
        });
        submit.setOnClickListener((event) -> {
            if(PassIsRegister())    ValidatePass(pass.getText().toString());
            else                    CreatePassWord(pass.getText().toString());
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        if (PassIsRegister()){
            TextView info = findViewById(R.id.info);
            info.setText("");
        }
    }

    public void TextViewSetFunc(TextView textView){
        textView.setOnClickListener((event) -> {pass.setText(pass.getText().toString() + textView.getText().toString());});
    }

    private Boolean PassIsRegister(){
        String pass = sharedPreferences.getString("pass","");
        return !pass.equals("");
    }

    private void CreatePassWord(String pass) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pass",pass);
        editor.apply();
        InitCypher();
        startActivity(new Intent(Login.this,HomePage.class));
        finish();
    }

    //Create the SecretKey and de Inicial Vector
    private void InitCypher(){
        SharedPreferences sh = getSharedPreferences("cipher",MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        byte[] secretkey = new byte[32];
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(secretkey);
        new SecureRandom().nextBytes(iv);
        editor.putString("secretkey",Base64.getEncoder().encodeToString(secretkey));
        editor.putString("iv",Base64.getEncoder().encodeToString(iv));
        editor.apply();
    }

    private void ValidatePass(String pass){
        String passave = sharedPreferences.getString("pass","");
        if(passave.equals(pass)){
            startActivity(new Intent(Login.this, HomePage.class));
            finish();
        }
        else
            Toast.makeText(Login.this,"Pass Invalid",Toast.LENGTH_SHORT).show();
    }
}