package com.example.passwordstorage.XMLElements;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.passwordstorage.R;
import com.example.passwordstorage.activity_add_pass;

import java.time.format.TextStyle;

public class BTNadd extends androidx.appcompat.widget.AppCompatTextView {
    public BTNadd(Context context) {
        super(context);
        init();
    }

    public BTNadd(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BTNadd(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BTNadd(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context);
        init();
    }

    private void init(){
        this.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.add_pass));
        setTextSize(35);
        setTextColor(Color.WHITE);
        setText("+");
        setTypeface(this.getTypeface(), Typeface.BOLD);
        setGravity(Gravity.CENTER);
        setOnClickListener((event) -> {
            Intent intent = new Intent(getContext(), activity_add_pass.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        });
    }
}
