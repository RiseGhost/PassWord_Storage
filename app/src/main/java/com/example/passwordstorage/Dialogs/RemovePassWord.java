package com.example.passwordstorage.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.passwordstorage.PassWord;
import com.example.passwordstorage.R;
import com.example.passwordstorage.database.dao.dao;
import com.example.passwordstorage.database.database;

public class RemovePassWord extends Dialog {
    private final PassWord passWord;
    private final Activity activity;
    public RemovePassWord(Activity activity,PassWord passWord) {
        super(activity);
        this.passWord = passWord;
        this.activity = activity;
        setContentView(R.layout.remove_password_popup);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button Cancel = findViewById(R.id.cancel);
        Cancel.setOnClickListener((event) -> {dismiss();});
        Button Remove = findViewById(R.id.remove);
        Remove.setOnClickListener((event) -> {
            new DB();
        });
        show();
    }

    private class DB extends Thread{
        public DB(){
            start();
        }

        @Override
        public void run(){
            try{
                database db = Room.databaseBuilder(getContext(),database.class,"password").build();
                dao Dao = db.getPassWordDao();
                Dao.Remove(passWord);
                dismiss();
                activity.finish();
            }   catch (Exception e){
                Log.d("RemovePass",e.getMessage());
            }
        }
    }
}
