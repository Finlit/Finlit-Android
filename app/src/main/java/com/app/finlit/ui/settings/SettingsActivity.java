package com.app.finlit.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.BlogModel;
import com.app.finlit.data.models.GetCommentsModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.authenticate.QuestionsActivity;
import com.app.finlit.ui.authenticate.SignInActivity;
import com.app.finlit.ui.authenticate.UpdatePasswordActivity;
import com.app.finlit.ui.authenticate.adapter.SpinnerAdapter;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.blog.contract.BlogContract;
import com.app.finlit.ui.home.MainActivity;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.iv_left)
    public ImageView ivLeft;

    @BindView(R.id.tv_heading)
    public TextView txtHeading;

    @BindView(R.id.switch_notification)
    public Switch switchNotification;

    @BindView(R.id.switch_sound)
    public Switch switchSound;

    @BindView(R.id.spinner_settings_matchtype)
    public Spinner spinnerMatch;

    @BindView(R.id.switch_date)
    public Switch switchDates;

    private SharedPreferenceHelper preferenceHelper;

    UserModel userModel;

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceHelper = SharedPreferenceHelper.getInstance();
        if(preferenceHelper.getDates())
            switchDates.setChecked(true);
        else
            switchDates.setChecked(false);

        initToolbar();
        initSpinner();
        initFields();


    }


    private void initSpinner() {
        List<String> list = new ArrayList<>();
        list.add("GENDER");
        list.add("MALE");
        list.add("FEMALE");
        //list.add("BOTH");
        SpinnerAdapter adapter = new SpinnerAdapter(this, list);
        spinnerMatch.setAdapter(adapter);
        spinnerMatch.setOnItemSelectedListener(this);
    }

    private void initFields() {
        if (preferenceHelper.getNotification())
            switchNotification.setChecked(true);
        else
            switchNotification.setChecked(false);

        if (preferenceHelper.getSound())
            switchSound.setChecked(true);
        else
            switchSound.setChecked(false);

        if(preferenceHelper.getDates())
            switchDates.setChecked(true);
        else
            switchDates.setChecked(false);

        if (preferenceHelper.getMatchgender() != null) {
            if (preferenceHelper.getMatchgender().equals("MALE")) {
                spinnerMatch.setSelection(1);
            }
            if (preferenceHelper.getMatchgender().equals("FEMALE")) {
                spinnerMatch.setSelection(2);
            }
//            if (preferenceHelper.getMatchgender().equals("BOTH")) {
//                spinnerMatch.setSelection(3);
//            }
        }
    }

    private void initToolbar() {
        txtHeading.setText("SETTINGS");
    }

    @OnClick(R.id.rel_change_password)
    public void onClickChangePass() {
        UpdatePasswordActivity.start(this);
    }

    @OnClick(R.id.rel_take_quiz)
    public void onClickQuiz() {
        Intent intent = new Intent(this, QuestionsActivity.class);
        intent.putExtra("UniqId", "from_setting");
        startActivity(intent);
    }

    @OnClick(R.id.rel_Blocked_users)
    public void onClickUnblocked() {

        startActivity(new Intent(SettingsActivity.this,BlockedUsersActivity.class));

    }

    @OnClick(R.id.terms)
    public void OnClickTerms(){
        startActivity(new Intent(SettingsActivity.this,TermandConditionActivity.class));
    }

    @OnClick(R.id.privacy)
    public void OnClickPrivacy(){
        startActivity(new Intent(SettingsActivity.this,PrivacyPolicyActivity.class));
    }


    @OnClick(R.id.support)
    public void OnClicksupport(){
        startActivity(new Intent(SettingsActivity.this,SupportActivity.class));
    }


    @OnClick(R.id.rel_logout)
    public void onClickLogout() {
        Intent intent = new Intent("finish_activity");
        sendBroadcast(intent);
        SharedPreferenceHelper.getInstance().clearAll();
        finish();
        SignInActivity.start(this);
    }

    @OnClick(R.id.iv_left)
    public void onClickLeft() {
        onBackPressed();
    }

    @OnClick(R.id.switch_notification)
    public void onClickNotification() {
        preferenceHelper.saveNotification(!preferenceHelper.getNotification());
    }

    @OnClick(R.id.switch_date)
    public void onClickDate()
    {
        preferenceHelper.saveDates(!preferenceHelper.getDates());
    }

    @OnClick(R.id.switch_sound)
    public void onClickSound() {
        preferenceHelper.saveSound(!preferenceHelper.getSound());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 1) {
            preferenceHelper.saveMatchGender("MALE");
        }
        if (position == 2) {
            preferenceHelper.saveMatchGender("FEMALE");
        }
//        if (position == 3) {
//            preferenceHelper.saveMatchGender("BOTH");
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
