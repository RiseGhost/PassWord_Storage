package com.example.passwordstorage.XMLElements;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ListView;

import androidx.core.content.ContextCompat;

import com.example.passwordstorage.AdapterPassWord;
import com.example.passwordstorage.HomePage;
import com.example.passwordstorage.R;
import com.example.passwordstorage.database.dao.dao;

public class Catgory_BTN extends androidx.appcompat.widget.AppCompatRadioButton {
    private static AdapterPassWord adapter;
    private ListView listView;
    private dao Dao;
    private HomePage activity;
    public Catgory_BTN(Context context) {
        super(context);
        init();
    }

    public Catgory_BTN(Context context, String Text, ListView listView, dao Dao, HomePage activity){
        super(context);
        setText(Text);
        this.listView = listView;
        this.Dao = Dao;
        this.activity = activity;
        init();
    }

    public Catgory_BTN(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Catgory_BTN(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setClickable(true);
        if (getText().toString().equals(""))    setText("Category");
        setTextColor(Color.WHITE);
        setStyle(getText().toString(),false);
        setTextSize(16);
        setGravity(Gravity.CENTER);
        setOnCheckedChangeListener((buttonView, isChecked) -> {
            setStyle(getText().toString(),isChecked);
            if(isChecked)       new Query();
        });
    }

    public void setStyle(String Category, boolean isChecked){
        if (Category.equals("All")){
            if (isChecked){
                setBackground(ContextCompat.getDrawable(getContext(), R.drawable.radio_category_all_select));
                setTextColor(Color.BLACK);
            }
            else{
                setBackground(ContextCompat.getDrawable(getContext(),R.drawable.radio_category_all));
                setTextColor(Color.WHITE);
            }
        }
        else if(Category.equals("Social")){
            if(isChecked)     setBackground(ContextCompat.getDrawable(getContext(),R.drawable.radio_category_social_select));
            else                setBackground(ContextCompat.getDrawable(getContext(),R.drawable.radio_category_social));
        }
        else if(Category.equals("Games")){
            if(isChecked)     setBackground(ContextCompat.getDrawable(getContext(),R.drawable.radio_category_games_select));
            else                setBackground(ContextCompat.getDrawable(getContext(),R.drawable.radio_category_games));
        }
        else if(Category.equals("Web")){
            if (isChecked)    setBackground(ContextCompat.getDrawable(getContext(),R.drawable.radio_category_web_select));
            else                setBackground(ContextCompat.getDrawable(getContext(),R.drawable.radio_category_web));
        }
        else if(Category.equals("Other")){
            if (isChecked)    setBackground(ContextCompat.getDrawable(getContext(),R.drawable.radio_category_other_select));
            else                setBackground(ContextCompat.getDrawable(getContext(),R.drawable.radio_category_other));
        }
    }

    private void applyQuery(){
        adapter = new AdapterPassWord(getContext(), android.R.layout.simple_list_item_1,Dao.getAll(),activity);;
        if(getText().toString().equals("Social"))
            adapter = new AdapterPassWord(getContext(), android.R.layout.simple_list_item_1,Dao.getSocial(),activity);
        else if(getText().toString().equals("Games"))
            adapter = new AdapterPassWord(getContext(), android.R.layout.simple_list_item_1,Dao.getGames(),activity);
        else if(getText().toString().equals("Web"))
            adapter = new AdapterPassWord(getContext(), android.R.layout.simple_list_item_1,Dao.getWeb(),activity);
        else if(getText().toString().equals("Other"))
            adapter = new AdapterPassWord(getContext(), android.R.layout.simple_list_item_1,Dao.getOther(),activity);
        android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
        handler.post(() -> {listView.setAdapter(adapter);});
    }
    private class Query extends Thread{
        public Query(){
            start();
        }
        @Override
        public void run(){
            applyQuery();
        }
    }
}
