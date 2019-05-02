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
import com.app.finlit.ui.authenticate.contract.SignUpContract;
import com.app.finlit.ui.authenticate.presentor_impl.SignUpPresentorImpl;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;
import com.app.finlit.utils.helpers.FacebookHelper;
import com.facebook.FacebookException;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

import static com.app.finlit.utils.Utility.hideSoftKeyboard;

public class ForgotPasswordActivity extends BaseActivity implements SignUpContract.View,
        FacebookHelper.FacebookHelperCallback {

    @BindView(R.id.et_email)
    public EditText etEmail;

    @BindView(R.id.iv_sign_up_facebook)
    public ImageView ivFacebook;

    @BindView(R.id.tv_sign_up)
    public TextView txtSignUp;


    @BindView(R.id.lay_sign_up_main)
    public RelativeLayout layMain;

    private boolean isFacebookLogin;
    private UserModel fbModel= null, userModel= null;
    private FacebookHelper facebookHelper;
    private boolean facebook=false;
    private SharedPreferenceHelper preferenceHelper;
    private final int SIGNUP = 101;

    private SignUpContract.Presentor presentor;

    public static void start(Context context) {
        Intent intent = new Intent(context, ForgotPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceHelper= SharedPreferenceHelper.getInstance();
        presentor= new SignUpPresentorImpl(this);
        facebookHelper = new FacebookHelper(this, this);
        fbModel= new UserModel();
        userModel = new UserModel();
        layMain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    hideSoftKeyboard(ForgotPasswordActivity.this);
                }
            }
        });
    }


    @OnClick(R.id.tv_sign_up)
    public void onClickSignUp(){

        String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";

        if(etEmail.getText().toString().trim().isEmpty()){
            etEmail.setError("Please Enter Email");
            etEmail.requestFocus();
            return;
        }

        if(!etEmail.getText().toString().trim().matches(regex)){
            etEmail.setError("Please Enter Valid Email");
            etEmail.requestFocus();
            return;
        }
        else{
            showProgress();
            userModel.setEmail(etEmail.getText().toString());
            presentor.forgetPassword(userModel);
        }
    }

    @OnEditorAction(R.id.et_email)
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        switch (actionId) {

            case EditorInfo.IME_ACTION_DONE:

                onClickSignUp();
                return true;
        }

        return false;
    }

    @OnClick(R.id.iv_sign_up_facebook)
    public void onClickFb(){
        facebookHelper.logout();
        facebookHelper.login(this);
        isFacebookLogin = true;
    }

    @Override
    public void onSuccessFacebook(Bundle bundle) {
        fbModel.setFacebookId(bundle.getString(FacebookHelper.FACEBOOK_ID));
        fbModel.setName(bundle.getString(FacebookHelper.FIRST_NAME) + " " + bundle.getString(FacebookHelper.LAST_NAME));
        fbModel.setEmail( bundle.getString(FacebookHelper.EMAIL));
        fbModel.setGender(bundle.getString(FacebookHelper.GENDER));
        fbModel.setImgUrl(bundle.getString(FacebookHelper.PROFILE_PIC));
        facebook=true;
        presentor.forgetPassword(fbModel);
//        if(fbModel.getName()!=null){
//            preferenceHelper.saveName(fbModel.getName());
//        }
//        if(fbModel.getEmail()!=null){
//            preferenceHelper.saveEmail(fbModel.getEmail());
//        }
//        if(fbModel.getGender()!=null){
//            preferenceHelper.saveGender(fbModel.getGender());
//        }
//        if(fbModel.getFacebookId()!=null){
//            preferenceHelper.saveFacebookUserId(fbModel.getFacebookId());
//        }
//        if(fbModel.getImgUrl()!=null){
//            preferenceHelper.saveProfilePic(fbModel.getImgUrl());
//        }
    }

    @Override
    public void onCancelFacebook() {
        ToastUtils.longToast("Facebook login cancel");
        isFacebookLogin = false;
    }

    @Override
    public void onErrorFacebook(FacebookException ex) {
        ToastUtils.longToast(ex.getMessage());
        isFacebookLogin = false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (isFacebookLogin) {
            facebookHelper.onResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSuccessSignUp(DataResponse<UserModel> response) {


    }

    @Override
    public void onSuccessForget(DataResponse<UserModel> response) {
        if(isFinishing()){
            return;
        }
        hideProgress();
        if (isFacebookLogin)
            SecuritySetupActivity.start(this);
        else{
            Intent intent = new Intent(this, VerificationActivity.class);
            intent.putExtra("UniqId", "fromForget");
            startActivity(intent);
        }
    }

    @Override
    public void onFailure(String error) {
        if(isFinishing()){
            return;
        }
        hideProgress();
        ToastUtils.longToast(error);
        isFacebookLogin = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
        binder = null;
    }

    @OnClick(R.id.iv_sign_up_back)
    public void onClickLeft(){
        onBackPressed();
    }
}
