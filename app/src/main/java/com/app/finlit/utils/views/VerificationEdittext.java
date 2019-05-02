package com.app.finlit.utils.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.KeyEvent;

public class VerificationEdittext extends android.support.v7.widget.AppCompatEditText {
    private Context context;
    private KeyImeChange keyImeChangeListener;
    private HandleDismissingKeyboard handleDismissingKeyboard;

    public VerificationEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "euphemia_regular.ttf");
        setTypeface(typeface);
    }

    public void setKeyImeChangeListener(KeyImeChange listener) {
        keyImeChangeListener = listener;
    }

    public void setHandleDismissingKeyboard(HandleDismissingKeyboard handleDismissingKeyboard) {
        this.handleDismissingKeyboard = handleDismissingKeyboard;
    }

    public interface HandleDismissingKeyboard {
        public void dismissKeyboard();
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyImeChangeListener != null) {
            keyImeChangeListener.onKeyIme(keyCode, event);
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            handleDismissingKeyboard.dismissKeyboard();
            return false;
        }
        return super.dispatchKeyEvent(event);
    }

    public interface KeyImeChange {

        void onKeyIme(int keyCode, KeyEvent event);
    }
}
