package com.app.finlit.utils;

import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


public class ToastUtils {

    public static void shortToast(@StringRes int text) {
        shortToast(App.getInstance().getString(text));
    }

    public static void shortToast(String text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void longToast(@StringRes int text) {
        longToast(App.getInstance().getString(text));
    }

    public static void longToast(String text) {
        show(text, Toast.LENGTH_LONG);
    }

    private static Toast makeToast(String text, @ToastLength int length) {
        return Toast.makeText(App.getInstance(), text, length);
    }

    public static void logError(String tag, Exception e) {
        String errorMsg = !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : "";
        Log.e(tag, errorMsg, e);
    }

    private static void show(String text, @ToastLength int length) {
        makeToast(text, length).show();
    }

    @IntDef({ Toast.LENGTH_LONG, Toast.LENGTH_SHORT })
    private @interface ToastLength {

    }
}
