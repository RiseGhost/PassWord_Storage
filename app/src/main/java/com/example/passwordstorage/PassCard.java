package com.example.passwordstorage;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class PassCard extends LinearLayout {
    private String PassWord = "";
    private PassWord passWord;
    private View card;
    public PassCard(Context context) {
        super(context);
        init();
    }

    public PassCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PassCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PassCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public PassCard(Context context, PassWord passWord, SecretKey secretKey, IvParameterSpec ivParam){
        super(context);
        init();
        setPassWord(passWord,secretKey,ivParam);
    }

    private void init(){
        this.setOnClickListener((event) -> {
            Intent intent = new Intent(getContext(),PassInfo.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("ID", passWord.getId());
            intent.putExtra("AppName", passWord.getAppName());
            intent.putExtra("PassWord", PassWord);
            intent.putExtra("UserName", passWord.getUserName());
            intent.putExtra("Category", passWord.getCategory());
            getContext().startActivity(intent);
        });
        setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.passcardbg));
        LayoutInflater inflater = LayoutInflater.from(getContext());

        card = inflater.inflate(R.layout.pass_card,null);
        ImageView eye = card.findViewById(R.id.eye);
        eye.setImageResource(R.drawable.olho);
        //Ajustar o Layout para que ocupe toda a Width do PassCard
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        //*************************************************
        card.setLayoutParams(layoutParams);

        addView(card);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    public void setPassWord(PassWord pass, SecretKey secretKey, IvParameterSpec IvParam){
        passWord = pass;
        try{
            this.PassWord = new String(Decifra(pass.getPass(), secretKey, IvParam), StandardCharsets.UTF_8);
        }   catch (Exception e){
            //this.PassWord = pass.getPass().toString();
        }
        TextView TVAppName = card.findViewById(R.id.AppName);
        TVAppName.setText(pass.getAppName());
        TextView TVCategory = card.findViewById(R.id.Category);
        TVCategory.setText(pass.getCategory());
        SetCategoryColor(card.findViewById(R.id.view2));
    }

    //Set Catogory Color View:
    private void SetCategoryColor(View view){
        view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.circle));
        if (passWord.getCategory().equals("Social"))          view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.category_social));
        else if (passWord.getCategory().equals("Games"))      view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.category_games));
        else if (passWord.getCategory().equals("Web"))        view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.category_web));
        else if (passWord.getCategory().equals("Other"))      view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.category_other));
    }

    public String getAppName(){return passWord.getAppName();}
    public String getPassWord(){return PassWord;}
    public String getUserName(){return passWord.getUserName();}
    public String getCategory(){return passWord.getCategory();}

    private byte[] Decifra(byte[] x, SecretKey secretKey, IvParameterSpec ivParam) throws InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,secretKey,ivParam);
        return cipher.doFinal(x);
    }
}