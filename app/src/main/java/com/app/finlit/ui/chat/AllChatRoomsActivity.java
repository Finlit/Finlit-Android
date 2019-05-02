package com.app.finlit.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.chat.adapter.AllChatRoomAdapter;
import com.app.finlit.ui.chat.adapter.ChatAdapter;
import com.app.finlit.utils.listeners.OnActionListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AllChatRoomsActivity extends BaseActivity {

    @BindView(R.id.tv_heading)
    public TextView tvTitle;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    private AllChatRoomAdapter adapter;
    private List<UserModel> list;
    private OnActionListener<UserModel> onActionListener;

    public static void start(Context context) {
        Intent intent = new Intent(context, AllChatRoomsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_all_chat_rooms;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();

        initToolbar();
        initListner();
        initAdapter();
    }

    private void initToolbar() {
        tvTitle.setText("CHAT ROOMS");
    }

    private void initAdapter() {
        UserModel m1 = new UserModel();
        UserModel m2 = new UserModel();
        UserModel m3 = new UserModel();
        UserModel m4 = new UserModel();
        UserModel m5 = new UserModel();
        UserModel m6 = new UserModel();
        UserModel m7 = new UserModel();
        list.add(m1);
        list.add(m2);
        list.add(m3);
        list.add(m4);
        list.add(m5);
        list.add(m6);
        list.add(m7);



        adapter = new AllChatRoomAdapter(this, list, onActionListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void initListner(){
        onActionListener = new OnActionListener<UserModel>() {
            @Override
            public void notify(UserModel model, int position) {
                ChatRoomActivity.start(AllChatRoomsActivity.this, model);
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
        binder = null;
    }

    @OnClick(R.id.iv_left)
    public void onClickLeft(){
        onBackPressed();
    }
}
