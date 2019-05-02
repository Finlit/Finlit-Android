package com.app.finlit.ui.chat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.ChatModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.chat.adapter.ChatAdapter;
import com.app.finlit.ui.chat.contract.ChatContract;
import com.app.finlit.ui.chat.presenter_impl.ChatPresenterImpl;
import com.app.finlit.ui.home.MainActivity;
import com.app.finlit.ui.home.MyCircleActivity;
import com.app.finlit.ui.nearby.adapter.NearByAdapter;
import com.app.finlit.ui.notification.NotificationActivity;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;
import com.app.finlit.utils.Utility;
import com.app.finlit.utils.listeners.CustomScrollListener;
import com.app.finlit.utils.listeners.OnActionListener;
import com.app.finlit.utils.listeners.OnActionListnerComment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

import static com.app.finlit.utils.Utility.hideSoftKeyboard;

public class ChatActivity extends BaseActivity implements ChatContract.View{

    private static final int DETAIL_REQUEST = 101;



    @BindView(R.id.tv_heading)
    public TextView tvTitle;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.et_search)
    public EditText etSearch;




    private ChatAdapter adapter;
    private List<ChatModel> list;
    private OnActionListener<ChatModel> onActionListener;
    private OnActionListnerComment<ChatModel> onActionListnerDelete;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;

    private String searchText;

    private List<ChatModel> searchList;

    private ChatContract.Presenter chatPresenter;
    private SharedPreferenceHelper preferenceHelper;

    public static void start(Context context) {
        Intent intent = new Intent(context, ChatActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int getContentId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        searchList = new ArrayList<>();
        preferenceHelper = SharedPreferenceHelper.getInstance();
        chatPresenter = new ChatPresenterImpl(this);

        initToolbar();
        initListner();
        initAdapter();
        initFields();
    }

    private void initToolbar() {
        tvTitle.setText("CHAT");
    }

    private void initFields(){
        swipeRefreshLayout.setRefreshing(true);
        isLoading = false;
        isLastPage = false;
        currentPage = PAGE_START;


        chatPresenter.getChats(currentPage);
    }

    private void initAdapter() {

        adapter = new ChatAdapter(this, list, onActionListener, onActionListnerDelete);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new CustomScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                chatPresenter.getChats(currentPage);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    private void initListner(){

        onActionListener = new OnActionListener<ChatModel>() {
            @Override
            public void notify(ChatModel model, int position) {
                UserModel userModel = null;
                if (preferenceHelper.getUserId().equals(model.getParticipants().get(0).getUser().getId())) {
                    userModel = model.getParticipants().get(1).getUser();
                    userModel.isBlocked = model.getParticipants().get(1).isBlocked;
                }
                else {
                    userModel = model.getParticipants().get(0).getUser();
                    userModel.isBlocked = model.getParticipants().get(0).isBlocked;
                }

                userModel.setChatId(model.getId());
                userModel.isBlocked = model.getParticipants().get(0).isBlocked;

                SingleChatActivity.start(ChatActivity.this, userModel, DETAIL_REQUEST);
            }
        };

        onActionListnerDelete = new OnActionListnerComment<ChatModel>() {
            @Override
            public void notify(int position, View view, ChatModel model) {
                chatOptions(model);
            }
        };

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                etSearch.setText("");
                initFields();
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etSearch.hasFocus()) {
                    if (s.toString().isEmpty()) {
                        searchText = null;
                        adapter.clear();
                        adapter.addAll(searchList);
                        hideKeyboard(getCurrentFocus());
                        return;
                    }
                    searchText = s.toString();
                    initSearch();
                }
            }
        });
    }

    private void initSearch(){
        adapter.clear();
        for (ChatModel model : searchList){
            if (preferenceHelper.getUserId().equals(model.getParticipants().get(0).getUser().getId())){
                if (model.getParticipants().get(1).getUser().getName().toLowerCase().contains(searchText.toLowerCase())){
                    adapter.add(model);
                }
            }else {
                if (model.getParticipants().get(0).getUser().getName().toLowerCase().contains(searchText.toLowerCase())) {
                    adapter.add(model);
                }
            }
        }
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
    public void onSuccessChats(PageResponse<ChatModel> response) {
        if (isFinishing()){
            return;
        }

        searchList.clear();
        searchList.addAll(response.getItems());
        if(currentPage == PAGE_START){
            if (swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);

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
    public void onSuccessBlock(DataResponse response) {

    }

    @Override
    public void onSuccessUnBlock(DataResponse response) {

    }

    @Override
    public void onFailure(String message) {
        if (isFinishing()){
            return;
        }
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);

        ToastUtils.shortToast(message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == DETAIL_REQUEST) {
                initFields();
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    hideSoftKeyboard(ChatActivity.this);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    public void chatOptions(final ChatModel model){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete this chat?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                chatPresenter.deleteChat(model.getId());
                adapter.remove(model);
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @OnEditorAction(R.id.et_search)
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        switch (actionId) {

            case EditorInfo.IME_ACTION_DONE:
                etSearch.clearFocus();
                hideKeyboard(getCurrentFocus());
                return true;
        }

        return false;
    }
}
