package com.example.passwordstorage;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SecuryCifra {
    SecretKey secretKey;
    IvParameterSpec ivParam;
    private static String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwyz0123456789?=;.+*´`~<>ªº";
    public SecuryCifra(Context context){
        try{
            SharedPreferences sharedPreferences = context.getSharedPreferences("cipher",0);
            String sk = sharedPreferences.getString("secretkey","");
            String iv = sharedPreferences.getString("iv","");
            secretKey = new SecretKeySpec(Base64.getDecoder().decode(sk),"AES");
            ivParam = new IvParameterSpec(Base64.getDecoder().decode(iv));
        }   catch (Exception e){
        }
    }

    public SecretKey getSecretKey(){return secretKey;}
    public IvParameterSpec getIvParam(){return ivParam;}

    public byte[] Cifra(byte[] x) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,secretKey,ivParam);
        return cipher.doFinal(x);
    }

    public byte[] Decifra(byte[] x) throws InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,secretKey,ivParam);
        return cipher.doFinal(x);
    }

    public String StringToHash(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        messageDigest.update(str.getBytes());
        return new String(messageDigest.digest());
    }

    public String makePassWord(int size){
        return (size == 0) ? "" : alfabeto.charAt((new Random()).nextInt(74)) + makePassWord(size-1);
    }
}
