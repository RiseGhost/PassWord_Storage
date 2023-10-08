package com.example.passwordstorage.XMLElements;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import com.example.passwordstorage.PassWord;
import com.example.passwordstorage.R;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class PassView extends LinearLayout {
    private PassWord passWord;
    private String Pass;
    private View view;
    public PassView(Context context) {
        super(context);
        init();
    }

    public PassView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PassView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PassView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init(){
        setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.passcardbg));
        LayoutInflater inflater = LayoutInflater.from(getContext());
        view = inflater.inflate(R.layout.pass_view,null);
        LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        view.setLayoutParams(layoutParams);
        addView(view);
    }

    public void setPassWord(String AppName, String Pass, String UserName, String Category){
        try{
            passWord = new PassWord(AppName,Cifra(Pass.getBytes()),UserName,Category);
            this.Pass = Pass;
            TextView TVTittle = view.findViewById(R.id.AppName);
            TextView TVUserName = view.findViewById(R.id.UserName);
            TextView TVPass = view.findViewById(R.id.Pass);
            TextView TVCategory = view.findViewById(R.id.Category);
            TVTittle.setText(passWord.getAppName());
            TVUserName.setText(UserName);
            TVPass.setText(Pass);
            TVCategory.setText(Category);
            setCategoryColor(view.findViewById(R.id.CategoryView));
        }   catch (Exception e){}
    }

    //Use to set Category View circle color
    private void setCategoryColor(View view){
        if(passWord.getCategory().equals("Social"))    view.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.category_social));
        if(passWord.getCategory().equals("Games"))     view.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.category_games));
        if(passWord.getCategory().equals("Web"))       view.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.category_web));
        if(passWord.getCategory().equals("Other"))     view.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.category_other));
    }

    private byte[] Cifra(byte[] x) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("cipher", Context.MODE_PRIVATE);
        String sk = sharedPreferences.getString("secretkey","");
        String iv = sharedPreferences.getString("iv","");
        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(sk),"AES");
        IvParameterSpec ivParam = new IvParameterSpec(Base64.getDecoder().decode(iv));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,secretKey,ivParam);
        return cipher.doFinal(x);
    }
}
