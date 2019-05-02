package com.app.finlit.ui.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.NotificationModel;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.chat.ChatActivity;
import com.app.finlit.ui.notification.adapter.NotificationAdapter;
import com.app.finlit.ui.notification.contract.GetNotificationsContract;
import com.app.finlit.ui.notification.presentor_impl.GetNotificationsPresentorImpl;
import com.app.finlit.utils.ToastUtils;
import com.app.finlit.utils.Utility;
import com.app.finlit.utils.listeners.OnActionListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NotificationActivity extends BaseActivity implements GetNotificationsContract.View{

    @BindView(R.id.iv_left)
    public ImageView ivLeft;

    @BindView(R.id.tv_heading)
    public TextView txtHeading;

    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;

    @BindView(R.id.tv_no_notification)
    public TextView tvNoNotification;

    private NotificationAdapter adapter;
    private List<NotificationModel> list;
    private OnActionListener<NotificationModel> onActionListener;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;

    private GetNotificationsContract.Presentor presenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, NotificationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_notification;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        presenter = new GetNotificationsPresentorImpl(this);

        initToolbar();
        initListner();
        initAdapter();
        initFields();
    }

    private void initToolbar() {
        txtHeading.setText("NOTIFICATIONS");
    }

    private void initFields() {
        showProgress();
        isLoading = false;
        isLastPage = false;
        currentPage = PAGE_START;
        presenter.getNotifications(currentPage);
    }


    private void initAdapter(){
//        NotificationModel m1 = new NotificationModel();
//        NotificationModel m2 = new NotificationModel();
//        NotificationModel m3 = new NotificationModel();
//        NotificationModel m4 = new NotificationModel();
//        NotificationModel m5 = new NotificationModel();
//        NotificationModel m6 = new NotificationModel();
//        NotificationModel m7 = new NotificationModel();
//
//        list.add(m1);
//        list.add(m2);
//        list.add(m3);
//        list.add(m4);
//        list.add(m5);
//        list.add(m6);
//        list.add(m7);

        adapter = new NotificationAdapter(this, list, onActionListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void initListner() {
        onActionListener = new OnActionListener<NotificationModel>() {
            @Override
            public void notify(NotificationModel model, int position) {
                ChatActivity.start(NotificationActivity.this);
            }
        };
    }

    @OnClick(R.id.iv_left)
    public void onClickLeft(){
        onBackPressed();
    }


    @Override
    public void onSuccessGetNotifications(PageResponse<NotificationModel> response) {
        if (isFinishing()){
            return;
        }
        hideProgress();
        if (response.getItems().isEmpty()){
            tvNoNotification.setVisibility(View.VISIBLE);
        }else {
            tvNoNotification.setVisibility(View.GONE);
        }
        if(currentPage == PAGE_START){
            adapter.clear();
            adapter.addAll(response.getItems());
            TOTAL_PAGES = Utility.getTotalPages(response.getTotalRecords(), response.getPageSize());
            if (currentPage < TOTAL_PAGES){
                adapter.addLoadingFooter();
            } else isLastPage = true;
        } else {
            adapter.removeLoadingFooter();
            isLoading = false;
            adapter.addAll(response.getItems());
            if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
            else isLastPage = true;
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
