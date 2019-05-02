package com.app.finlit.ui.blog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.BlogModel;
import com.app.finlit.data.models.GetCommentsModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.blog.Presenter.blogPresenterImpl;
import com.app.finlit.ui.blog.adapter.MultiViewPostAdapter;
import com.app.finlit.ui.blog.contract.BlogContract;
import com.app.finlit.utils.Constants;
import com.app.finlit.utils.ToastUtils;

import com.app.finlit.utils.listeners.OnActionListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyBlogActivity extends BaseActivity implements BlogContract.View {

    private static final int DETAIL_REQUEST = 100;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;


    @BindView(R.id.recycler_blog)
    public RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.tv_heading)
    public TextView tvHeading;


    private BlogContract.Presenter presenter;
    BlogModel blogModel;

    private MultiViewPostAdapter adapter;
    private List<BlogModel> userList;
    private OnActionListener<BlogModel> onActionListener, onActionListenerLike, onActionListenerShare, onActionListenerComments, onActionListenerMore;


    @Override
    protected int getContentId() {
        return R.layout.activity_my_blog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new blogPresenterImpl(this);
        userList = new ArrayList<>();

        iniApi();
        iniHeading();
        iniListner();
        initAdapter();

    }

    private void iniListner() {
        onActionListenerLike = new OnActionListener<BlogModel>() {
            @Override
            public void notify(BlogModel model, int position) {

                if (model.isLike() == true) {
                    presenter.createLike(model.getId());
                } else
                    presenter.createDisLike(model.getId());
            }
        };

        onActionListenerShare = new OnActionListener<BlogModel>() {
            @Override
            public void notify(BlogModel model, int position) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, ""+model.getLink());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_with)));
            }
        };

        onActionListenerComments = new OnActionListener<BlogModel>() {
            @Override
            public void notify(BlogModel model, int position) {

                Intent intent = new Intent(MyBlogActivity.this, ViewCommentsActivity.class);
                intent.putExtra(Constants.BLOG_MODEL, model);
                intent.putExtra("id", model.getId());
                startActivity(intent);
            }
        };
        onActionListener = new OnActionListener<BlogModel>() {
            @Override
            public void notify(BlogModel model, int position) {
                Intent intent = new Intent(MyBlogActivity.this, ViewCommentsActivity.class);
                intent.putExtra(Constants.BLOG_MODEL, model);
                intent.putExtra("id", model.getId());
                startActivity(intent);

            }
        };

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                iniApi();
            }
        });


    }

    private void iniHeading() {
        tvHeading.setText("MY BLOGS");
    }

    @OnClick(R.id.iv_left)
    public void onClickBack() {
        onBackPressed();
    }

    private void iniApi() {
        swipeRefreshLayout.setRefreshing(true);
        isLoading = false;
        isLastPage = false;
        currentPage = PAGE_START;
        presenter.getBlogDetail();

    }

    @Override
    protected void onResume() {
        swipeRefreshLayout.setRefreshing(true);
        isLoading = false;
        isLastPage = false;
        currentPage = PAGE_START;

        presenter.getBlogDetail();
        super.onResume();
    }

    private void initAdapter() {
        adapter = new MultiViewPostAdapter(this, userList, onActionListener, onActionListenerLike,
                onActionListenerShare, onActionListenerComments, onActionListenerMore);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onSuccessGetDetail(PageResponse<BlogModel> response) {
        if (isFinishing()) {
            return;
        }

        if (currentPage == PAGE_START) {
            if (swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
            adapter.clear();
            adapter.addAll(response.getItems());

        }
    }

    @Override
    public void onSuccessGetComments(PageResponse<GetCommentsModel> response) {

    }


    @Override
    public void onSuccessLike(BlogModel model) {
        if (isFinishing()) {
            return;
        }

    }

    @Override
    public void onSuccessDislike(DataResponse response) {

    }

    @Override
    public void onSucessCreateComment(GetCommentsModel model) {

    }


    @Override
    public void onFailure(String error) {
        if (isFinishing()) {
            return;
        }

        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        ToastUtils.shortToast(error);
    }
}
