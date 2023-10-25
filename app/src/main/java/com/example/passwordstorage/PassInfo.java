package com.example.passwordstorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.passwordstorage.Dialogs.RemovePassWord;
import com.example.passwordstorage.XMLElements.PassView;
import com.example.passwordstorage.database.dao.dao;
import com.example.passwordstorage.database.database;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class PassInfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_info);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener((event) -> {finish();});
        Intent intent = getIntent();
        int id = intent.getIntExtra("ID",0);
        String appName = intent.getStringExtra("AppName");
        String passWord = intent.getStringExtra("PassWord");
        String userName = intent.getStringExtra("UserName");
        String category = intent.getStringExtra("Category");
        int theme = intent.getIntExtra("Theme", 1);
        PassView passView = findViewById(R.id.PassView);
        Button Remove = findViewById(R.id.remove);
        passView.setPassWord(appName, passWord, userName, category, theme);
        Remove.setOnClickListener((event) -> {
            PassWord pass = passView.getPassWord();
            pass.setId(id);
            new RemovePassWord(this,pass);
        });
        Switch LockBiometric = findViewById(R.id.bio);
        LockBiometric.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            PassWord pass = passView.getPassWord();
            pass.setId(id);
            if (isChecked)
                pass.setLock(true);
            else
                pass.setLock(false);
            new DB(id,LockBiometric,true,pass);
        }));
        new DB(id,LockBiometric,false,null);
    }

    private class DB extends Thread{
        private final int id;
        private final Switch aSwitch;
        private final Boolean update;
        private final PassWord passWord;
        public DB(int id, Switch aSwitch, Boolean update, PassWord passWord){
            this.id = id;
            this.aSwitch = aSwitch;
            this.update = update;
            this.passWord = passWord;
            start();
        }

        @Override
        public void run(){
            database db = Room.databaseBuilder(getApplicationContext(), database.class,"password").build();
            dao Dao = db.getPassWordDao();
            if(update){
                Dao.Update(passWord);
            }
            else{
                if (Dao.getPassID(id).getLock())    aSwitch.setChecked(true);
                else                                aSwitch.setChecked(false);
            }
        }
    }
}