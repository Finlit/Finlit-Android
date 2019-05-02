package com.app.finlit.ui.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.ChatModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.repositories.ChatRepositories;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.chat.adapter.ChatAdapter;
import com.app.finlit.ui.chat.adapter.SingleChatAdapter;
import com.app.finlit.ui.chat.contract.ChatContract;
import com.app.finlit.ui.chat.presenter_impl.ChatPresenterImpl;
import com.app.finlit.ui.nearby.FilterActivity;
import com.app.finlit.ui.settings.BlockContract;
import com.app.finlit.ui.settings.BlockPresenterImpl;
import com.app.finlit.utils.Constants;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;
import com.app.finlit.utils.helpers.DatabaseHelper;
import com.app.finlit.utils.listeners.OnActionListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.app.finlit.utils.Utility.hideSoftKeyboard;

public class SingleChatActivity extends BaseActivity implements ChatContract.View, BlockContract.View {


    @BindView(R.id.tv_heading)
    public TextView tvTitle;
    @BindView(R.id.iv_right)
    public ImageView ivRight;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    @BindView(R.id.et_message)
    public EditText etMessage;

    @BindView(R.id.container)
    public LinearLayout linearContainer;
    @BindView(R.id.linear_below)
    public RelativeLayout relativeLayoutBelow;

    private SingleChatAdapter adapter;
    private List<ChatModel> list;
    private OnActionListener<ChatModel> onActionListener;

    private UserModel userModel;

    private ChatRepositories.OnCompleteListener onCompleteListener;
    private ChatRepositories chatRepositories;
    private DatabaseHelper databaseHelper;

    private SharedPreferenceHelper preferenceHelper;
    private ChatContract.Presenter presenter;

    private BlockContract.Presenter blockPresenter;
    private UserModel model;


    public static void start(Activity context, UserModel userModel, Integer REQUEST_CODE) {
        Intent intent = new Intent(context, SingleChatActivity.class);
        intent.putExtra(Constants.USER_MODEL, userModel);
        if (REQUEST_CODE!=null)
            context.startActivityForResult(intent, REQUEST_CODE);
        else
            context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_single_chat;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<>();
        presenter = new ChatPresenterImpl(this);
        blockPresenter=new BlockPresenterImpl( this);

        userModel = (UserModel) getIntent().getSerializableExtra(Constants.USER_MODEL);
        databaseHelper = DatabaseHelper.getInstance();
        preferenceHelper = SharedPreferenceHelper.getInstance();
        chatRepositories = databaseHelper.getChatRepository();

        presenter.setZeroUnreadCount(userModel.getChatId());


        initToolbar();
        initListner();
        initAdapter();
        initChats();


    }

    private void initToolbar() {
        tvTitle.setText(userModel.getName());
    }

    private void initAdapter() {
        adapter = new SingleChatAdapter(this, list, onActionListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void initChats(){
        chatRepositories.getChats(userModel.getChatId(), onCompleteListener);
//        if (userModel.isBlocked)
//            relativeLayoutBelow.setVisibility(View.GONE);
//        else
//            relativeLayoutBelow.setVisibility(View.VISIBLE);
    }

    private void initListner(){
        onActionListener = new OnActionListener<ChatModel>() {
            @Override
            public void notify(ChatModel model, int position) {

            }
        };

        onCompleteListener = new ChatRepositories.OnCompleteListener() {
            @Override
            public void onSuccessCreateChat() {

            }

            @Override
            public void onSuccessChat(List<ChatModel> list) {
                if (isFinishing()){
                    return;
                }
                hideProgress();
                Collections.reverse(list);
                adapter.clear();
                adapter.addAll(list);
            }

            @Override
            public void onFailed(String error) {
                if (isFinishing()){
                    return;
                }
                hideProgress();

                ToastUtils.shortToast(error);
            }
        };

        linearContainer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    hideSoftKeyboard(SingleChatActivity.this);
                }
            }
        });
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

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @OnClick(R.id.iv_right)
    public void onClickRight(){
        final PopupMenu popup = new PopupMenu(this, ivRight, Gravity.TOP);
        //popup.getMenuInflater().inflate(R.menu.menu_comment, popup.getMenu());

        if (userModel.isBlocked)
            popup.getMenu().add("UnBlock");
        else
            popup.getMenu().add("Block");

        popup.getMenu().add("Report");
        popup.getMenu().add("Cancel");

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getTitle().toString()) {

                    case "Block":
                        showProgress();
                        model = new UserModel();
                        model.setUserId(userModel.getId());
                        blockPresenter.blockUser(model);
                        break;


                    case "UnBlock":
                        showProgress();
                        model.setUserId(userModel.getId());
                        blockPresenter.unblockUser(model);
                        break;

                    case "Report":
                        ToastUtils.shortToast("Reported Successfully");
                        popup.dismiss();


                    case "Cancel":
                        popup.dismiss();
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    @OnClick(R.id.iv_send)
    public void onClickSend(){
        if (etMessage.getText().toString().trim().isEmpty()){
            ToastUtils.shortToast("Enter message");
            return;
        }

        ChatModel chatModel = new ChatModel();
        chatModel.setMyId(preferenceHelper.getUserId());
        chatModel.setOpponentId(userModel.getId());
        chatModel.setMessage(etMessage.getText().toString());
        chatModel.setId(userModel.getChatId());

        if (preferenceHelper.getProfilePic()!=null)
            chatModel.setImgUrl(preferenceHelper.getProfilePic());

        chatRepositories.createChat(chatModel, onCompleteListener);
//        etMessage.clearFocus();
//        hideKeyboard(getCurrentFocus());
        chatModel.setLastMessage(etMessage.getText().toString());
        presenter.increaseUnreadCount(userModel.getChatId(), chatModel);
        etMessage.setText("");
    }

    @Override
    public void onSuccessChats(PageResponse<ChatModel> response) {

    }

    @Override
    public void onSuccessCreateChat(DataResponse<ChatModel> response) {

    }

    @Override
    public void onSuccessSetZeroCount(DataResponse response) {

    }

    @Override
    public void onSuccessIncreUnreadCount(DataResponse response) {

    }

    @Override
    public void onSuccessDelete(DataResponse response) {

    }

    @Override
    public void onSuccessUsers(PageResponse<UserModel> response) {

    }

    @Override
    public void onSuccessBlock(DataResponse response) {
        if (isFinishing()){
            return;
        }
        hideProgress();
        userModel.isBlocked = true;
        relativeLayoutBelow.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessUnblock(DataResponse response) {
        if(isFinishing()){
            return;
        }
        hideProgress();
        userModel.isBlocked = false;
        relativeLayoutBelow.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessUnBlock(DataResponse response) {
        if (isFinishing()){
            return;
        }
        hideProgress();
//        userModel.isBlocked = false;
//        relativeLayoutBelow.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailure(String message) {
        if (isFinishing()){
            return;
        }

        hideProgress();
        ToastUtils.shortToast(message);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if ( v instanceof EditText) {
//                Rect outRect = new Rect();
//                v.getGlobalVisibleRect(outRect);
//                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
//                    v.clearFocus();
//                    hideSoftKeyboard(this);
//                }
//            }
//        }
//        return super.dispatchTouchEvent( event );
//    }
}
