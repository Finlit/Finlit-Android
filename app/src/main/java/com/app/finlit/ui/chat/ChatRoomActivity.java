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
import com.app.finlit.data.models.ChatModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.chat.adapter.ChatRoomAdapter;
import com.app.finlit.ui.chat.adapter.SingleChatAdapter;
import com.app.finlit.ui.home.MainActivity;
import com.app.finlit.utils.Constants;
import com.app.finlit.utils.listeners.OnActionListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatRoomActivity extends BaseActivity {

    @BindView(R.id.tv_heading)
    public TextView tvTitle;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    private ChatRoomAdapter adapter;
    private List<ChatModel> list;
    private OnActionListener<ChatModel> onActionListener;



    public static void start(Context context, UserModel userModel) {
        Intent intent = new Intent(context, ChatRoomActivity.class);
        intent.putExtra(Constants.USER_MODEL, userModel);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_chat_room;
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
        tvTitle.setText("CHATROOM123");
    }

    private void initAdapter() {
        ChatModel m1 = new ChatModel();
        ChatModel m2 = new ChatModel();
        ChatModel m3 = new ChatModel();
        ChatModel m4 = new ChatModel();
        ChatModel m5 = new ChatModel();
        ChatModel m6 = new ChatModel();
        ChatModel m7 = new ChatModel();
        list.add(m1);
        list.add(m2);
        list.add(m3);
        list.add(m4);
        list.add(m5);
        list.add(m6);
        list.add(m7);



        adapter = new ChatRoomAdapter(this, list, onActionListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void initListner(){
        onActionListener = new OnActionListener<ChatModel>() {
            @Override
            public void notify(ChatModel model, int position) {

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
