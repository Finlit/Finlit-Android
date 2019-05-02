package com.app.finlit.ui.authenticate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.authenticate.contract.SignInContract;
import com.app.finlit.ui.authenticate.presentor_impl.SignInPresentorImpl;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.home.MainActivity;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;
import com.app.finlit.utils.Validations;
import com.app.finlit.utils.helpers.FacebookHelper;
import com.facebook.FacebookException;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

import static com.app.finlit.utils.Utility.hideSoftKeyboard;

public class SignInActivity extends BaseActivity implements SignInContract.View,
        FacebookHelper.FacebookHelperCallback{

    @BindView(R.id.iv_sign_in_back)
    public ImageView ivBack;

    @BindView(R.id.et_sign_in_email)
    public EditText etEmail;

    @BindView(R.id.et_sign_in_password)
    public EditText etPassword;

    @BindView(R.id.txt_sign_in_signIn)
    public TextView txtSignIn;

    @BindView(R.id.txt_sign_in_signUp)
    public TextView txtSignUp;

    @BindView(R.id.lay_sign_in_main)
    public RelativeLayout layMain;

    private SharedPreferenceHelper preferenceHelper;
    private FacebookHelper facebookHelper;
    private SignInContract.Presentor presentor;

    public static void start(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceHelper = SharedPreferenceHelper.getInstance();
        presentor = new SignInPresentorImpl(this);
        facebookHelper = new FacebookHelper(this, this);
        layMain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    hideSoftKeyboard(SignInActivity.this);
                }
            }
        });
    }

    @OnClick(R.id.txt_sign_in_signUp)
    public void onClickSignUp(){
        SignUpActivity.start(this);
    }

    @OnClick(R.id.txt_sign_in_signIn)
    public void onClickSignIn(){
        if(new Validations().validateSignInForm(etEmail,etPassword)) {
            UserModel userModel = new UserModel();
            userModel.setEmail(etEmail.getText().toString().trim());
            userModel.setPassword(etPassword.getText().toString().trim());
            showProgress();
            presentor.signIn(userModel);
        }
    }

    @OnClick(R.id.iv_sign_in_facebook)
    public void onClickFacebook(){
        facebookHelper.logout();
        facebookHelper.login(this);
    }

    @OnEditorAction(R.id.et_sign_in_password)
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        switch (actionId) {

            case EditorInfo.IME_ACTION_DONE:

                onClickSignIn();
                return true;
        }

        return false;
    }

    @OnClick(R.id.iv_sign_in_back)
    public void onClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.tv_forget_password)
    public void onClickForget(){
        ForgotPasswordActivity.start(this);
    }

    @Override
    public void onSuccessSignIn(DataResponse<UserModel> response) {
        if(isFinishing()){
            return;
        }
        hideProgress();
        SharedPreferenceHelper.getInstance().saveProfileCompltd(true);
        SharedPreferenceHelper.getInstance().saveIsResultCompleted(true);
        MainActivity.start(this);
        finish();
    }

    @Override
    public void onFailure(String error) {
        if(isFinishing()){
            return;
        }
        hideProgress();
        ToastUtils.longToast(error);
    }

    @Override
    public void onSuccessFacebook(Bundle bundle) {
        UserModel userModel = new UserModel();
        userModel.setFacebookId(bundle.getString(FacebookHelper.FACEBOOK_ID));

        showProgress();
        presentor.signIn(userModel);
    }

    @Override
    public void onCancelFacebook() {
        ToastUtils.longToast("Facebook login cancel");
    }

    @Override
    public void onErrorFacebook(FacebookException ex) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookHelper.onResult(requestCode, resultCode, data);
    }
}
