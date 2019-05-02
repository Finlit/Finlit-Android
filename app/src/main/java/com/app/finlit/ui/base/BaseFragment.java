package com.app.finlit.ui.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.app.finlit.utils.ProgressDialog;


public abstract class BaseFragment extends Fragment {

    protected void showProgress(){
        ProgressDialog.show(getActivity());
    }

    protected void hideProgress(){
        ProgressDialog.hide();
    }
    // hide keyboard
    protected void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
