package com.app.finlit.ui.authenticate;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.params.ValidateParams;
import com.app.finlit.ui.authenticate.contract.VerificationContract;
import com.app.finlit.ui.authenticate.presentor_impl.VerificationPresentorImpl;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;
import com.app.finlit.utils.views.VerificationEdittext;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

import static com.app.finlit.utils.Utility.hideSoftKeyboard;

public class VerificationActivity extends BaseActivity implements VerificationEdittext.HandleDismissingKeyboard,
        VerificationContract.View{

    @BindView(R.id.iv_verification_back)
    public ImageView ivBack;

    @BindView(R.id.lay_verification_main)
    public RelativeLayout layMain;

    @BindView(R.id.first)
    public VerificationEdittext firstCharacter;

    @BindView(R.id.second)
    public VerificationEdittext secondCharacter;

    @BindView(R.id.third)
    public VerificationEdittext thirdCharacter;

    @BindView(R.id.fourth)
    public VerificationEdittext fourthCharacter;

    @BindView(R.id.fifth)
    public VerificationEdittext fifthCharacter;

    @BindView(R.id.sixth)
    public VerificationEdittext sixthCharacter;

    private boolean sixth = false;
    private StringBuilder stringBuilder;
    private String codeValueOne;
    private String codeValueTwo;
    private String codeValueThree;
    private String codeValueFour;
    private String codeValueFive;
    private String codeValueSix;
    private String verificationCode;
    public  String otp="";
    private SharedPreferenceHelper preferenceHelper;
    private VerificationContract.Presentor presentor;

    private String fromData;

    public static void start(Context context) {
        Intent intent = new Intent(context, VerificationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_verification;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceHelper = SharedPreferenceHelper.getInstance();
        presentor = new VerificationPresentorImpl(this);
        fromData = getIntent().getStringExtra("UniqId");
        layMain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    hideSoftKeyboard( VerificationActivity.this);
                }
            }
        });
        initFields();
    }
    private void initFields() {
        stringBuilder = new StringBuilder();
        stringBuilder.append("000000");
        initListener();
    }

    private void initListener() {

        firstCharacter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                firstCharacter.setBackgroundResource(R.mipmap.icon_verification);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (firstCharacter.getText().length() == 1) {
                    stringBuilder.setCharAt(0, s.charAt(0));
                    secondCharacter.requestFocus();
//                    firstCharacter.setBackgroundResource(R.mipmap.icon_verification);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                onFirstCodeChanged(s.toString());
            }
        });

        secondCharacter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                secondCharacter.setBackgroundResource(R.mipmap.icon_verification);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (secondCharacter.getText().length() == 1) {
                    stringBuilder.setCharAt(1, s.charAt(0));
                    thirdCharacter.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                onSecondCodeChanged(s.toString());
            }
        });

        thirdCharacter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                thirdCharacter.setBackgroundResource(R.mipmap.icon_verification);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (thirdCharacter.getText().length() == 1) {
                    stringBuilder.setCharAt(2, s.charAt(0));
                    fourthCharacter.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                onThirdCodeChanged(s.toString());
            }
        });

        fourthCharacter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                fourthCharacter.setBackgroundResource(R.mipmap.icon_verification);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (fourthCharacter.length() == 1) {
                    stringBuilder.setCharAt(3, s.charAt(0));
                    fifthCharacter.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                onForthCodeChanged(s.toString());
            }
        });

        fifthCharacter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                fifthCharacter.setBackgroundResource(R.mipmap.icon_verification);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (fifthCharacter.length() == 1) {
                    stringBuilder.setCharAt(4, s.charAt(0));
                    sixthCharacter.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                onFifthCodeChanged(s.toString());
            }
        });

        sixthCharacter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                sixthCharacter.setBackgroundResource(R.mipmap.icon_verification);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (sixthCharacter.length() == 1) {
                    stringBuilder.setCharAt(5, s.charAt(0));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                onSixthCodeChanged(s.toString());
                onClickVerify();
//                Utility.hideSoftKeyboard(VerificationActivity.this);
            }
        });

        secondCharacter.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() != KeyEvent.ACTION_DOWN) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (secondCharacter.getText().length() == 0 /*&& second*/){
                                firstCharacter.requestFocus();
                                secondCharacter.setBackgroundResource(R.mipmap.icon_verification_light);
                            }
                        }
                    }, 50);
                }
                return false;
            }
        });

        thirdCharacter.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() != KeyEvent.ACTION_DOWN) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (thirdCharacter.getText().length() == 0 /*&& third*/){
                                secondCharacter.requestFocus();
                                secondCharacter.setSelection(secondCharacter.length());
                                thirdCharacter.setBackgroundResource(R.mipmap.icon_verification_light);
                            }
                        }
                    }, 50);
                }
                return false;
            }
        });

        fourthCharacter.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() != KeyEvent.ACTION_DOWN) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (fourthCharacter.getText().length() == 0 /*&& fourth*/){
                                thirdCharacter.requestFocus();
                                thirdCharacter.setSelection(thirdCharacter.length());
                                fourthCharacter.setBackgroundResource(R.mipmap.icon_verification_light);
                            }
                        }
                    }, 50);
                }
                return false;
            }
        });

        fifthCharacter.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() != KeyEvent.ACTION_DOWN) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (fifthCharacter.getText().length() == 0 /*&& fifth*/){
                                fourthCharacter.requestFocus();
                                fourthCharacter.setSelection(fourthCharacter.length());
                                fifthCharacter.setBackgroundResource(R.mipmap.icon_verification_light);
                            }
                        }
                    }, 50);
                }
                return false;
            }
        });


        sixthCharacter.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() != KeyEvent.ACTION_DOWN) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                             if (sixthCharacter.getText().length() == 0 /*&& fourth*/){
                                fifthCharacter.requestFocus();
                                fifthCharacter.setSelection(fifthCharacter.length());
                                sixthCharacter.setBackgroundResource(R.mipmap.icon_verification_light);
                            }
                        }
                    }, 50);
                }
                return false;
            }
        });

        firstCharacter.setHandleDismissingKeyboard(this);
        secondCharacter.setHandleDismissingKeyboard(this);
        thirdCharacter.setHandleDismissingKeyboard(this);
        fourthCharacter.setHandleDismissingKeyboard(this);
        fifthCharacter.setHandleDismissingKeyboard(this);
    }

    @OnClick(R.id.iv_verification_back)
    public void onClickLeft(){
        onBackPressed();
    }

    @OnClick(R.id.tv_submit)
    public void onClickVerify(){
        if(!validateCode()){
            return;
        }
        showProgress();
        ValidateParams params = new ValidateParams();
        params.setUserId(SharedPreferenceHelper.getInstance().getUserId());
        params.setActivationCode(verificationCode);
        presentor.validatePin(params);

    }

    @OnClick(R.id.tv_resend)
    public void onClickResend(){
        UserModel model= new UserModel();
        model.setEmail(preferenceHelper.getEmail());
        showProgress();
        presentor.resendCode(model);
    }

    private boolean validateCode() {

        if (codeValueOne == null || codeValueOne.length() == 0) {
            ToastUtils.longToast("Please Enter Valid Code");
            return false;
        }
        if (codeValueTwo == null || codeValueTwo.length() == 0) {
            ToastUtils.longToast("Please Enter Valid Code");
            return false;
        }
        if (codeValueThree == null || codeValueThree.length() == 0) {
            ToastUtils.longToast("Please Enter Valid Code");
            return false;
        }
        if (codeValueFour == null || codeValueFour.length() == 0) {
            ToastUtils.longToast("Please Enter Valid Code");
            return false;
        }
        if (codeValueFive == null || codeValueFive.length() == 0) {
            ToastUtils.longToast("Please Enter Valid Code");
            return false;
        }
        if (codeValueSix == null || codeValueSix.length() == 0) {
            ToastUtils.longToast("Please Enter Valid Code");
            return false;
        }
        verificationCode = codeValueOne+codeValueTwo+codeValueThree+codeValueFour+codeValueFive+codeValueSix;
        return true;
    }

    @OnEditorAction({R.id.first, R.id.second, R.id.third, R.id.fourth,R.id.fifth,R.id.sixth})
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (actionId) {
            case EditorInfo.IME_ACTION_GO:
                if(validateCode()){
                    onClickVerify();
                }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
        binder = null;
    }

    public void onFirstCodeChanged(String val) {
        this.codeValueOne = val;
    }

    public void onSecondCodeChanged(String val) {
        this.codeValueTwo = val;
    }

    public void onThirdCodeChanged(String val) {
        this.codeValueThree = val;
    }

    public void onForthCodeChanged(String val) {
        this.codeValueFour = val;
    }

    public void onFifthCodeChanged(String val) {
        this.codeValueFive = val;
    }

    public void onSixthCodeChanged(String val) {
        this.codeValueSix = val;
    }

    @Override
    public void dismissKeyboard() {

    }

    @Override
    public void onSuccessVerification(UserModel model) {
        if (isFinishing()){
            return;
        }
        hideProgress();
        finish();
        if (fromData == null)
            AddProfileActivity.start(this);
        else
            SecuritySetupActivity.start(this);
    }

    @Override
    public void onSuccessResendCode(UserModel model) {

    }

    @Override
    public void onFailure(String message) {
        if (isFinishing()){
            return;
        }
        hideProgress();
        ToastUtils.shortToast(message);
    }

}
