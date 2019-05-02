package com.app.finlit.utils.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TextViewSegoeSemiBold extends AppCompatTextView {
    private Context context;

    public TextViewSegoeSemiBold(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        this.context=context;
        init();
    }

    private void init()
    {
        Typeface typeface= Typeface.createFromAsset(context.getAssets(), "Segoe_Semi_Bold.ttf");
        setTypeface(typeface);
    }
}
