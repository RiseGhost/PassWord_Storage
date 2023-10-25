package com.example.passwordstorage;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.passwordstorage.XMLElements.BTNadd;
import com.example.passwordstorage.XMLElements.PassCard;

import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AdapterPassWord extends ArrayAdapter<PassWord> {
    private final Context context;
    private int resource;
    private final List<PassWord> PassWords;
    private final HomePage activity;

    public AdapterPassWord(@NonNull Context context, int resource, @NonNull List<PassWord> PassWords, HomePage activity) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.activity = activity;
        this.PassWords = PassWords;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View conterView, @NonNull ViewGroup parent){
        if (position == getCount() - 1){    //Last posicion plus 1, add a buttom to add new PassWord:
            return new BTNadd(getContext());
        }
        else
            return new PassCard(context,activity,PassWords.get(position));
    }

    //Retorna mais 1 doque o tamanho original da lista porque no fim da lista é necessários adicinar uma view que terá o conteúdo para adicionar uma nova PassWord:
    @Override
    public int getCount(){return PassWords.size() + 1;}
}