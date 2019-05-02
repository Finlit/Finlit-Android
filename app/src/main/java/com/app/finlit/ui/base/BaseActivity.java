package com.app.finlit.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.app.finlit.utils.ProgressDialog;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getContentId();

    protected Unbinder binder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentId() != 0) {
            setContentView(getContentId());
            binder = ButterKnife.bind(this);
        }
    }

    // hide keyboard
    protected void hideKeyboard(View view){
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void switchFragment(int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(id, fragment).commitAllowingStateLoss();
    }

    protected void showProgress(){
        ProgressDialog.show(this);
    }

    protected void hideProgress(){
        ProgressDialog.hide();
    }

}
