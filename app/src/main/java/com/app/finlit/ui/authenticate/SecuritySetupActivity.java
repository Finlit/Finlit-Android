package com.app.finlit.ui.authenticate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.authenticate.contract.UserApiContract;
import com.app.finlit.ui.authenticate.presentor_impl.UserApiPresenterImpl;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class SecuritySetupActivity extends BaseActivity implements UserApiContract.View {

    @BindView(R.id.et_new_password)
    public EditText etNewPassword;
    @BindView(R.id.et_reenter_password)
    public EditText etReenterPassword;
    @BindView(R.id.et_user_name)
    public EditText etUserName;

    public UserApiContract.Presentor presenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, SecuritySetupActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_security_setup;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UserApiPresenterImpl(this);
        etUserName.setText(SharedPreferenceHelper.getInstance().getUsername());
        etUserName.setFocusable(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
        binder = null;
    }

    @OnClick(R.id.iv_left)
    public void onClickLeft(){
        onBackPressed();
    }

    @OnClick(R.id.tv_update)
    public void onClickUpdate(){
        if (!isValidate()){
            return;
        }
        UserModel model = new UserModel();
        model.setPassword(etNewPassword.getText().toString());
        showProgress();
        presenter.updatePassword(model);
    }

    @OnEditorAction(R.id.et_reenter_password)
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        switch (actionId) {
            case EditorInfo.IME_ACTION_DONE:

                onClickUpdate();
                return true;
        }
        return false;
    }


    private boolean isValidate(){
        if (etNewPassword.getText().toString().isEmpty()){
            etNewPassword.requestFocus();
            etNewPassword.setError("Enter new password");
            return false;
        }
        if (etReenterPassword.getText().toString().isEmpty()){
            etReenterPassword.requestFocus();
            etReenterPassword.setError("Enter new password again");
            return false;
        }
        if (!etNewPassword.getText().toString().equals(etReenterPassword.getText().toString())){
            etReenterPassword.requestFocus();
            etReenterPassword.setError("Password mismatch");
            return false;
        }
        return true;
    }



    @Override
    public void onSuccessAddProfile(DataResponse<UserModel> response) {

    }

    @Override
    public void onSuccessUpdatePassword(DataResponse<UserModel> response) {
        if (isFinishing()){
            return;
        }
        finish();
        SharedPreferenceHelper.getInstance().saveUserToken(null);
        SignInActivity.start(this);
    }

    @Override
    public void onFailure(String error) {
        if (isFinishing()){
            return;
        }
        hideProgress();
        ToastUtils.shortToast(error);
    }
}
