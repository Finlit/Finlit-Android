package com.app.finlit.utils.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by MANISH-PC on 10/4/2018.
 */

public class EditTextCalibri extends android.support.v7.widget.AppCompatEditText
{
    private Context context;

    public EditTextCalibri(Context context, AttributeSet attrs)
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
