package com.app.finlit.utils.listeners;

import android.view.View;

/**
 * Created by MANISH-PC on 8/10/2018.
 */

public interface OnActionListnerComment<T> {
    void notify(int position, View view, T model);
}
