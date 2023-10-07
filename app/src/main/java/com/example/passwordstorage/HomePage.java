package com.example.passwordstorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.passwordstorage.database.dao.dao;
import com.example.passwordstorage.database.database;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class HomePage extends AppCompatActivity {
    private static SecretKey secretKey;
    private static IvParameterSpec ivParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ListView listView = findViewById(R.id.list);
        SharedPreferences sharedPreferences = getSharedPreferences("cipher",MODE_PRIVATE);
        String sk = sharedPreferences.getString("secretkey","");
        String iv = sharedPreferences.getString("iv","");
        try {
            secretKey = new SecretKeySpec(Base64.getDecoder().decode(sk),"AES");
            ivParam = new IvParameterSpec(Base64.getDecoder().decode(iv));
            String str = "Cona <3";
            byte[] c = Cifra(str.getBytes(StandardCharsets.US_ASCII));
            byte[] d = Decifra(c);
            Toast.makeText(this, new String(c,StandardCharsets.US_ASCII),Toast.LENGTH_SHORT).show();
            Toast.makeText(this,new String(d,StandardCharsets.US_ASCII),Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("Cifra",e.getMessage());
        }
        new DB();
    }

    private byte[] Cifra(byte[] x) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,secretKey,ivParam);
        return cipher.doFinal(x);
    }

    private byte[] Decifra(byte[] x) throws InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,secretKey,ivParam);
        return cipher.doFinal(x);
    }

    private class DB extends Thread{
        public DB(){
            start();
        }

        @Override
        public void run(){
            try {
                database db = Room.databaseBuilder(getApplicationContext(),database.class,"password").build();
                dao Dao = db.getPassWordDao();
                //Dao.Insert(new PassWord("Lol",Cifra("Theo2020".getBytes()),"TheoMt59","Games"));
                List<PassWord> passWords = Dao.getAll();
                String str = passWords.toString();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    Toast.makeText(getApplicationContext(),String.valueOf(passWords.size()),Toast.LENGTH_SHORT).show();
                    ListView listView = findViewById(R.id.list);
                    AdapterPassWord adapter = new AdapterPassWord(getApplicationContext(), android.R.layout.simple_list_item_1,passWords,secretKey,ivParam);
                    listView.setAdapter(adapter);
                });
            }   catch (Exception e){
                Log.e("RoomDB",e.getMessage());
            }
        }
    }
}