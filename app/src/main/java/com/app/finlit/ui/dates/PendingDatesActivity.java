package com.app.finlit.ui.dates;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.dates.adapter.DatesPagerAdapter;
import com.app.finlit.ui.nearby.FilterActivity;
import com.app.finlit.utils.Filters;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PendingDatesActivity extends BaseActivity implements TabLayout.OnTabSelectedListener,
        PendingFragment.OnFragmentInteractionListener, SentRequestFragment.OnFragmentInteractionListener {

    private static final int FILTER_REQUEST = 101;
    @BindView(R.id.tab_layout)
    public TabLayout tabLayout;
    @BindView(R.id.viewPager)
    public ViewPager viewPager;
    @BindView(R.id.tv_heading)
    public TextView tv_Heading;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;

    private Map<String, String> queries;


    @Override
    protected int getContentId() {
        return R.layout.activity_pending_dates;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_pending_dates);
        if (Filters.getInstance() != null)
            Filters.getInstance().reset();

        initTabLayout();
        initViewPager();
        iniheading();
        iniFields();
    }

    private void iniFields() {
        queries = new HashMap<>();
        if (Filters.getInstance() != null && !Filters.getInstance().getFilter().isEmpty()) {
            queries = Filters.getInstance().getFilter();
        } else {
            queries.put("range", "1000");
        }
        queries.put("pageNo", String.valueOf(currentPage));
        // presenter.getUsers(queries);
    }

    private void iniheading() {
        tv_Heading.setText("PENDING DATES");
    }

    @OnClick(R.id.iv_left)
    public void onClickBack() {
        onBackPressed();
    }

    @OnClick(R.id.iv_right)
    public void onClickRight() {
        startActivityForResult(new Intent(this, FilterDatingActivity.class), FILTER_REQUEST);
    }

    private void initViewPager() {
        DatesPagerAdapter adapter = new DatesPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(2);
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("Received"));
        tabLayout.addTab(tabLayout.newTab().setText("Sent"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(this);
        changeTabFonts();
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

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == FILTER_REQUEST) {
                iniFields();
            }
        }
    }
}
