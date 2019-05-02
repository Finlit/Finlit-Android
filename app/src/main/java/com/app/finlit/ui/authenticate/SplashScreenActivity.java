package com.app.finlit.ui.authenticate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.TourModel;
import com.app.finlit.ui.authenticate.adapter.RecyclerIndicatorAdapter;
import com.app.finlit.ui.authenticate.adapter.SplashImagesAdapter;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.home.MainActivity;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.listeners.OnActionListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SplashScreenActivity extends BaseActivity {

    @BindView(R.id.rel_splash)
    public RelativeLayout relativeLayout;
    @BindView(R.id.iv_splash)
    public ImageView ivSplash;

    private SharedPreferenceHelper preferenceHelper;

    public static void start(Context context) {
        Intent intent = new Intent(context, SplashScreenActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_splash_screen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceHelper = SharedPreferenceHelper.getInstance();
        initStatusBar();
        initFields();
    }

    private void initStatusBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void initFields(){
        ivSplash.postDelayed(new Runnable() {
            @Override
            public void run() {
                resolveNext();
            }
        }, 2000);
    }
    

    private void resolveNext() {
        if (preferenceHelper.getUserToken()!=null && preferenceHelper.getProfileCompltd()){
            MainActivity.start(this);
        }//else if (preferenceHelper.getProfileCompltd() && !preferenceHelper.getIsResultCompltd()){
          //  WelcomeActivity.start(this);
       // }
//        else if(preferenceHelper.getFacebookAccessToken()!=null){
//            startActivity(new Intent(SplashScreenActivity.this,AddProfileActivity.class));
//        }
        else{
            finish();
            overridePendingTransition(0, 0);
            SignUpActivity.start(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
        binder = null;
    }

}
