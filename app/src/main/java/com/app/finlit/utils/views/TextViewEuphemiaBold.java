package com.app.finlit.utils.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TextViewEuphemiaBold extends AppCompatTextView {
    private Context context;

    public TextViewEuphemiaBold(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        this.context=context;
        init();
    }

    private void init()
    {
        Typeface typeface= Typeface.createFromAsset(context.getAssets(), "euphemia_bold.ttf");
        setTypeface(typeface);
    }
}
