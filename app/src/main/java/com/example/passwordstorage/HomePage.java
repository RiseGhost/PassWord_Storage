package com.example.passwordstorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.example.passwordstorage.XMLElements.Catgory_BTN;
import com.example.passwordstorage.database.dao.dao;
import com.example.passwordstorage.database.database;

import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class HomePage extends AppCompatActivity {
    private static SecretKey secretKey;
    private static IvParameterSpec ivParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        secretKey = new SecuryCifra(getApplicationContext()).getSecretKey();
        ivParam = new SecuryCifra(getApplicationContext()).getIvParam();
        new DB();
    }

    @Override
    public void onResume(){
        super.onResume();
        new DB();
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
                //Dao.Insert(new PassWord("LoL",Cifra("Theo2020".getBytes()),"TheoMt59","Games",2));
                //Dao.Insert(new PassWord("Twitter",Cifra("Jose Miguel".getBytes()),"Jose@1234","Social",1));
                List<PassWord> passWords = Dao.getAll();
                String str = passWords.toString();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    //Toast.makeText(getApplicationContext(),String.valueOf(passWords.size()),Toast.LENGTH_SHORT).show();
                    ListView listView = findViewById(R.id.list);
                    RadioGroup CategorySelect = findViewById(R.id.CategorySelect);
                    CategorySelect.removeAllViews();
                    Catgory_BTN All = new Catgory_BTN(getApplicationContext(),"All",listView,Dao);
                    CategorySelect.addView(All);
                    CategorySelect.addView(new Catgory_BTN(getApplicationContext(),"Social",listView,Dao));
                    CategorySelect.addView(new Catgory_BTN(getApplicationContext(),"Games",listView,Dao));
                    CategorySelect.addView(new Catgory_BTN(getApplicationContext(),"Web",listView,Dao));
                    CategorySelect.addView(new Catgory_BTN(getApplicationContext(),"Other",listView,Dao));
                    All.setChecked(true);
                });
            }   catch (Exception e){
                Log.e("RoomDB",e.getMessage());
            }
        }
    }
}