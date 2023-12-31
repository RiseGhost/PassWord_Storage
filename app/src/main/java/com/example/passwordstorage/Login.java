package com.example.passwordstorage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Login extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private TextView pass;
    private Boolean ChangePIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button submit = findViewById(R.id.Submit);
        pass = findViewById(R.id.NumberPass);
        ChangePIN = getIntent().getBooleanExtra("PIN", false);
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
            if(PassIsRegister() && !ChangePIN)      ValidatePass(pass.getText().toString());
            else                                    CreatePassWord(pass.getText().toString());
        });

        SharedPreferences sharedPreferences1 = getSharedPreferences("biometric", MODE_PRIVATE);
        String bio = sharedPreferences1.getString("bio","");
        if (bio.equals("true") && !ChangePIN)     ValidateWithBiometric();
    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        if (PassIsRegister() && !ChangePIN){
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
        try{
            editor.putString("pass",new SecuryCifra(getApplicationContext()).StringToHash(pass));
        }   catch (NoSuchAlgorithmException e){
            Log.d("Hash error", e.getMessage());
        }
        editor.apply();
        if (!ChangePIN)     InitCypher();
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
        try{
            if(passave.equals(new SecuryCifra(getApplicationContext()).StringToHash(pass))){
                startActivity(new Intent(Login.this, HomePage.class));
                finish();
            }
            else
                Toast.makeText(Login.this,"Pass Invalid",Toast.LENGTH_SHORT).show();
        }   catch (NoSuchAlgorithmException e){
            Log.d("Hash error", e.getMessage());
        }
    }

    private void ValidateWithBiometric(){
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticação biométrica")
                .setSubtitle("Use sua impressão digital para desbloquear")
                .setNegativeButtonText("Cancelar")
                .build();
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, ContextCompat.getMainExecutor(this), new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(Login.this,"Error, pls try pin", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                startActivity(new Intent(Login.this, HomePage.class));
                finish();
            }
        });

        biometricPrompt.authenticate(promptInfo);
    }
}