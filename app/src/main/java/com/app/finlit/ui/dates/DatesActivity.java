package com.app.finlit.ui.dates;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.StatusModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.dates.adapter.DatesPagerAdapter;
import com.app.finlit.ui.home.contract.FavouriteContract;
import com.app.finlit.ui.home.presentor_impl.FavouritePresenter;
import com.app.finlit.utils.SharedPreferenceHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class DatesActivity extends BaseActivity implements TabLayout.OnTabSelectedListener,
    AvailableFragment.OnFragmentInteractionListener, PendingFragment.OnFragmentInteractionListener,
    ConfirmFragment.OnFragmentInteractionListener, FavouriteContract.View {

    @BindView(R.id.tv_heading)
    public TextView tvTitle;

    @BindView(R.id.tab_layout)
    public TabLayout tabLayout;
    @BindView(R.id.viewPager)
    public ViewPager viewPager;

    @BindView(R.id.rel_welcome)
    public RelativeLayout relativeLayoutWelcome;

    @BindView(R.id.switch_date_date)
    public Switch date_switch;

    private SharedPreferenceHelper preferenceHelper;
    private FavouriteContract.Presenter presenter;



    public static void start(Context context){
        Intent intent = new Intent(context, DatesActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getContentId() {
        return R.layout.activity_dates;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initFields();
        preferenceHelper=SharedPreferenceHelper.getInstance();
        presenter=new FavouritePresenter(this);

        if(preferenceHelper.getDates()){
            if(preferenceHelper.getDates())
                date_switch.setChecked(true);
            else
                date_switch.setChecked(false);
    }
    }



    @OnClick(R.id.switch_date_date)
    public void onClickDate() {
        StatusModel model = new StatusModel();
        preferenceHelper.saveDates(!preferenceHelper.getDates());
        if (preferenceHelper.getDates()) {
            if (preferenceHelper.getDates()) {
                date_switch.setChecked(true);
                model.setStatus("on");
                presenter.createFindMeDate(preferenceHelper.getUserId(), model);
            }
            else
                date_switch.setChecked(false);
        }
    }


    @OnClick(R.id.iv_left)
    public void onClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.iv_right)
    public void onClickFilterDates(){
        startActivity(new Intent(DatesActivity.this,FilterDatingActivity.class));
    }

    private void initToolbar() {
        tvTitle.setText("DATES");
    }

    private void initFields() {
        // Hide status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        relativeLayoutWelcome.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                relativeLayoutWelcome.setVisibility(View.GONE);
                // Show status bar
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        },15000);
        initTabLayout();
        initViewPager();
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("Available"));
        tabLayout.addTab(tabLayout.newTab().setText("Pending"));
        tabLayout.addTab(tabLayout.newTab().setText("Confirmed"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(this);
        changeTabFonts();
    }

    private void initViewPager() {
        DatesPagerAdapter adapter = new DatesPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(2);
    }


    private void changeTabFonts() {
        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "euphemia_bold.ttf");

        ViewGroup viewGroup = (ViewGroup) tabLayout.getChildAt(0);
        int tabCount = viewGroup.getChildCount();

        for (int i = 0; i < tabCount; i++) {
            ViewGroup viewGroupTab = (ViewGroup) viewGroup.getChildAt(i);
            int tabChildCount = viewGroupTab.getChildCount();
            for (int j = 0; j < tabChildCount; j++) {
                View tabViewChild = viewGroupTab.getChildAt(j);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(typeface);
                }
            }
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        //viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSuccess(DataResponse response) {

    }

    @Override
    public void onSuccessFindmeDate(DataResponse response) {
        if(isFinishing()){
            return;
        }

    }

    @Override
    public void onSuccessUnFavourite(DataResponse response) {

    }

    @Override
    public void onFailure(String message) {

    }
}
