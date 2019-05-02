package com.app.finlit.ui.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.NotificationModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.settings.adapter.BlockedAdapter;
import com.app.finlit.utils.ToastUtils;
import com.app.finlit.utils.listeners.OnActionListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BlockedUsersActivity extends BaseActivity implements BlockContract.View {

    @BindView(R.id.tv_heading)
    public TextView tv_title;

    @BindView(R.id.recycler_block)
    public RecyclerView recyclerView;

    private BlockedAdapter adapter;
    private List<UserModel> list;
    private OnActionListener<UserModel> onActionListener, onActionListenerUnblock;
    private BlockContract.Presenter presenter;
    UserModel userModel;

    @Override
    protected int getContentId() {
        return R.layout.activity_blocked_users;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_title.setText("Blocked Users");
        showProgress();
        iniListener();
        presenter = new BlockPresenterImpl(this);
        userModel = new UserModel();


        list = new ArrayList<>();
        iniApi();
        iniAdapter();


    }

    private void iniApi() {
        presenter.getBlockedUsers();
    }

    @OnClick(R.id.iv_left)
    public void onClickBack() {
        onBackPressed();
    }

    private void iniListener() {
        onActionListener = new OnActionListener<UserModel>() {
            @Override
            public void notify(UserModel model, int position) {
                model.setUserId(model.getId());
                showProgress();
                presenter.unblockUser(model);
            }

        };
    }


    private void iniAdapter() {
        adapter = new BlockedAdapter(this, list, onActionListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onSuccessUsers(PageResponse<UserModel> response) {
        if (isFinishing()) {
            return;
        }
        hideProgress();
        adapter.clear();
        adapter.addAll(response.getItems());

    }

    @Override
    public void onSuccessBlock(DataResponse response) {

    }

    @Override
    public void onSuccessUnblock(DataResponse response) {
        if (isFinishing()) {
            return;
        }
        hideProgress();

        if (response.isSuccess) {
            ToastUtils.shortToast("UnBlocked Successfully");
            //userModel.isBlocked = false;
        }
    }

    @Override
    public void onFailure(String error) {

    }
}
