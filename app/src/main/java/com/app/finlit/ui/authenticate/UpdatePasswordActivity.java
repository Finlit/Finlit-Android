package com.app.finlit.ui.authenticate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.app.finlit.R;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.authenticate.contract.UserApiContract;
import com.app.finlit.ui.authenticate.presentor_impl.UserApiPresenterImpl;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdatePasswordActivity extends BaseActivity implements UserApiContract.View {


    @BindView(R.id.et_password)
    public EditText etPassword;

    @BindView(R.id.et_new_password)
    public EditText etNewPassword;

    @BindView(R.id.et_reenter_password)
    public EditText etReenterPassword;

    public UserApiContract.Presentor presenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, UpdatePasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_update_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UserApiPresenterImpl(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
        binder= null;
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
        model.setPassword(etPassword.getText().toString());
        model.setNewPassword(etNewPassword.getText().toString());
        showProgress();
        presenter.updatePassword(model);
    }

    private boolean isValidate(){
        if (etPassword.getText().toString().isEmpty()){
            etPassword.requestFocus();
            etPassword.setError("Enter old password");
            return false;
        }
        if (etPassword.getText().toString().trim().length() < 6) {
            etPassword.setError("Password should be atleast of 6 charactors");
            etPassword.requestFocus();
            return false;
        }
        if (etNewPassword.getText().toString().isEmpty()){
            etNewPassword.requestFocus();
            etNewPassword.setError("Enter new password");
            return false;
        }
        if (etNewPassword.getText().toString().trim().length() < 6) {
            etNewPassword.setError("Password should be atleast of 6 charactors");
            etNewPassword.requestFocus();
            return false;
        }
        if (etReenterPassword.getText().toString().isEmpty()){
            etReenterPassword.requestFocus();
            etReenterPassword.setError("Enter new password again");
            return false;
        }
        if (etReenterPassword.getText().toString().trim().length() < 6) {
            etReenterPassword.setError("Password should be atleast of 6 charactors");
            etReenterPassword.requestFocus();
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
        hideProgress();
        if(!response.isSuccess){
            ToastUtils.shortToast("Incorrect Old Password");
        }
        else{
            ToastUtils.shortToast("Updated successfully");
            finish();
        }
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
