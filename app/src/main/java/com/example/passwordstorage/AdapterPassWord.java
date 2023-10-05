package com.example.passwordstorage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AdapterPassWord extends ArrayAdapter<PassWord> {
    private final Context context;
    private int resource;
    private final List<PassWord> PassWords;
    SecretKey secretKey;
    IvParameterSpec ivParam;

    public AdapterPassWord(@NonNull Context context, int resource, @NonNull List<PassWord> PassWords, SecretKey secretKey, IvParameterSpec ivParam) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.PassWords = PassWords;
        this.secretKey = secretKey;
        this.ivParam = ivParam;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View conterView, @NonNull ViewGroup parent){
        if (position == getCount() - 1)  //Last posicion plus 1, add a buttom to add new PassWord:
            return LayoutInflater.from(context).inflate(R.layout.activity_main,null);
        else
            return new PassCard(context,PassWords.get(position),secretKey,ivParam);
    }

    //Retorna mais 1 doque o tamanho original da lista porque no fim da lista é necessários adicinar uma view que terá o conteúdo para adicionar uma nova PassWord:
    @Override
    public int getCount(){return PassWords.size() + 1;}
}