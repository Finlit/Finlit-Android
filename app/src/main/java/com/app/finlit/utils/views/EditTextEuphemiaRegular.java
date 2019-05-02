package com.app.finlit.utils.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class EditTextEuphemiaRegular extends android.support.v7.widget.AppCompatEditText
{
    private Context context;

    public EditTextEuphemiaRegular(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        this.context=context;
        init();
    }

    private void init()
    {
        Typeface typeface= Typeface.createFromAsset(context.getAssets(), "euphemia_regular.ttf");
        setTypeface(typeface);
    }

}