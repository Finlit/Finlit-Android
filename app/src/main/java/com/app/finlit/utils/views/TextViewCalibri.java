package com.app.finlit.utils.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by MANISH-PC on 10/4/2018.
 */

public class TextViewCalibri extends AppCompatTextView {
    private Context context;

    public TextViewCalibri(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        this.context=context;
        init();
    }

    private void init()
    {
        Typeface typeface= Typeface.createFromAsset(context.getAssets(), "calibri.ttf");
        setTypeface(typeface);
    }
}
