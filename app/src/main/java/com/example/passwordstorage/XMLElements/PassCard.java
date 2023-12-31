package com.example.passwordstorage.XMLElements;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.passwordstorage.HomePage;
import com.example.passwordstorage.Login;
import com.example.passwordstorage.PassInfo;
import com.example.passwordstorage.PassWord;
import com.example.passwordstorage.R;
import com.example.passwordstorage.SecuryCifra;

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
    private com.example.passwordstorage.PassWord passWord;
    private View card;
    private int Theme = 0;
    private HomePage activity;
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

    public PassCard(Context context, HomePage activity, PassWord passWord){
        super(context);
        this.activity = activity;
        init();
        setPassWord(passWord);
        setBackgroundCard();
    }

    private void init(){
        this.setOnClickListener((event) -> {
            if(passWord.getLock())  activity.ValidateWithBiometric(passWord,PassWord);
            else {
                Intent intent = new Intent(getContext(), PassInfo.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ID", passWord.getId());
                intent.putExtra("AppName", passWord.getAppName());
                intent.putExtra("PassWord", PassWord);
                intent.putExtra("UserName", passWord.getUserName());
                intent.putExtra("Category", passWord.getCategory());
                intent.putExtra("Theme",passWord.getTheme());
                getContext().startActivity(intent);
            }
        });
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

    public void setPassWord(PassWord pass){
        passWord = pass;
        try{
            this.PassWord = new String(new SecuryCifra(getContext()).Decifra(pass.getPass()), StandardCharsets.UTF_8);
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

    private void setBackgroundCard(){
        if (passWord.getTheme() == 1)           setBackground(ContextCompat.getDrawable(getContext(),R.drawable.passcardbg_1));
        else if(passWord.getTheme() == 2)       setBackground(ContextCompat.getDrawable(getContext(),R.drawable.passcardbg_2));
        else if(passWord.getTheme() == 3)       setBackground(ContextCompat.getDrawable(getContext(),R.drawable.passcardbg_3));
        else if(passWord.getTheme() == 4)       setBackground(ContextCompat.getDrawable(getContext(),R.drawable.passcardbg_4));
        else                                    setBackground(ContextCompat.getDrawable(getContext(),R.drawable.passcardbg_1));
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

    private void ValidateWithBiometric(){
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticação biométrica")
                .setSubtitle("Use sua impressão digital para desbloquear")
                .setNegativeButtonText("Cancelar")
                .build();


        BiometricPrompt biometricPrompt = new BiometricPrompt((FragmentActivity) getContext(), ContextCompat.getMainExecutor(getContext()), new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Intent intent = new Intent(getContext(), PassInfo.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ID", passWord.getId());
                intent.putExtra("AppName", passWord.getAppName());
                intent.putExtra("PassWord", PassWord);
                intent.putExtra("UserName", passWord.getUserName());
                intent.putExtra("Category", passWord.getCategory());
                intent.putExtra("Theme",passWord.getTheme());
                getContext().startActivity(intent);
            }
        });

        biometricPrompt.authenticate(promptInfo);
    }
}