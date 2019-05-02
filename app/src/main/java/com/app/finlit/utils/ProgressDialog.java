package com.app.finlit.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import com.app.finlit.R;


public class ProgressDialog {
    private static Dialog dialog;

    public static void show(Context context) {

        if(dialog != null){
            dialog = null;
        }
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_progress);
        dialog.show();
    }

    public static void hide() {
        if(dialog != null){
            dialog.hide();
            dialog = null;
        }
    }
}