package com.app.finlit.ui.authenticate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.home.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.iv_left)
    public ImageView ivLeft;
    @BindView(R.id.tv_heading)
    public TextView tvTitle;


    public static void start(Context context) {
        Intent intent = new Intent(context, WelcomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
    }

    private void initToolbar() {
        ivLeft.setVisibility(View.GONE);
        tvTitle.setText("WELCOME");
    }

    @OnClick(R.id.tv_next)
    public void onClickNext(){
        QuestionsActivity.start(this);
    }

    @Override
    public void onBackPressed() {
    }
}
