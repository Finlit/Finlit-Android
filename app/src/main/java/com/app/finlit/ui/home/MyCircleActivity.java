package com.app.finlit.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.ChatModel;
import com.app.finlit.data.models.ParticipantsModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.authenticate.ViewProfileActivity;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.chat.ChatActivity;
import com.app.finlit.ui.chat.SingleChatActivity;
import com.app.finlit.ui.chat.contract.ChatContract;
import com.app.finlit.ui.chat.presenter_impl.ChatPresenterImpl;
import com.app.finlit.ui.home.adapter.CircleAdapter;
import com.app.finlit.ui.home.contract.FavouriteContract;
import com.app.finlit.ui.home.contract.GetUsersContract;
import com.app.finlit.ui.home.presentor_impl.FavouritePresenter;
import com.app.finlit.ui.home.presentor_impl.GetUsersPresentorImpl;
import com.app.finlit.ui.nearby.adapter.NearByAdapter;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;
import com.app.finlit.utils.Utility;
import com.app.finlit.utils.listeners.CustomScrollListener;
import com.app.finlit.utils.listeners.OnActionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

import static com.app.finlit.utils.Utility.hideSoftKeyboard;

public class MyCircleActivity extends BaseActivity implements FavouriteContract.View, ChatContract.View{

    @BindView(R.id.tv_heading)
    public TextView tvTitle;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.et_search)
    public EditText etSearch;

    @BindView(R.id.container)
    public LinearLayout linearContainer;


    private CircleAdapter adapter;
    private List<UserModel> list;
    private OnActionListener<UserModel> onActionListener;
    private OnActionListener<UserModel> onActionListenerMessage;
    private OnActionListener<UserModel> onActionListenerViewProfile;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;

    private String searchText;

    private List<UserModel> searchList;

    private FavouriteContract.Presenter favPresenter;
    private ChatContract.Presenter chatPresenter;

    private UserModel userModel;

    public static void start(Context context) {
        Intent intent = new Intent(context, MyCircleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_my_circle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        searchList = new ArrayList<>();
        favPresenter = new FavouritePresenter(this);
        chatPresenter = new ChatPresenterImpl(this);

        initToolbar();
        initListner();
        initAdapter();
        initFields();

    }

    private void initToolbar() {
        tvTitle.setText("MY MATCHES");
    }

    private void initFields(){
        swipeRefreshLayout.setRefreshing(true);
        isLoading = false;
        isLastPage = false;
        currentPage = PAGE_START;


        chatPresenter.getChats(currentPage);
    }

    private void initAdapter() {
        adapter = new CircleAdapter(this, list, onActionListener, onActionListenerMessage, onActionListenerViewProfile);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new CustomScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
//                isLoading = true;
//                currentPage += 1;
//                chatPresenter.getChats(currentPage);
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
                        initFields();
                        hideKeyboard(getCurrentFocus());
                        return;
                    }
                    searchText = s.toString();
                    initSearch();
                }
            }
        });

        onActionListener = new OnActionListener<UserModel>() {
            @Override
            public void notify(UserModel model, int position) {
                if (!model.isFavourite)
                    favPresenter.createFavourite(model.getId());
                else
                    favPresenter.createUnFavourite(model.getId());


            }
        };

        onActionListenerMessage = new OnActionListener<UserModel>() {
            @Override
            public void notify(UserModel model, int position) {
                userModel = model;
                showProgress();
                ChatModel chatModel = new ChatModel();
                chatModel.setChatType("normal");
                ParticipantsModel participantsModel = new ParticipantsModel();
                participantsModel.setUserId(userModel.getId());
                List<ParticipantsModel> participantsModelList = new ArrayList<>();
                participantsModelList.add(participantsModel);

                chatModel.setParticipants(participantsModelList);

                chatPresenter.createChat(chatModel);
            }
        };

        onActionListenerViewProfile = new OnActionListener<UserModel>() {
            @Override
            public void notify(UserModel model, int position) {
                ViewProfileActivity.start(MyCircleActivity.this, model);
            }
        };

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                hideKeyboard(getCurrentFocus());
                etSearch.clearFocus();
                etSearch.setText("");
                searchText = null;
                initFields();
            }
        });

        linearContainer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    hideSoftKeyboard(MyCircleActivity.this);
                }
            }
        });

    }

    private void initSearch(){
        adapter.clear();
        for (UserModel model : searchList){
            if (model.getName().toLowerCase().contains(searchText.toLowerCase()))
                adapter.add(model);
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
    public void onSuccess(DataResponse response) {

    }

    @Override
    public void onSuccessFindmeDate(DataResponse response) {

    }

    @Override
    public void onSuccessUnFavourite(DataResponse response) {

    }

    @Override
    public void onSuccessChats(PageResponse<ChatModel> response) {
        if (isFinishing()){
            return;
        }

        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);

        adapter.clear();
        searchList.clear();

        for (ChatModel chatModel : response.getItems()){
            UserModel userModel = null;
            if (chatModel.getParticipants().get(0).getUser().getId().equals(SharedPreferenceHelper.getInstance().getUserId())){
                userModel = chatModel.getParticipants().get(1).getUser();
            }else {
                userModel = chatModel.getParticipants().get(0).getUser();
            }
            adapter.add(userModel);
            searchList.add(userModel);
        }

    }

    @Override
    public void onSuccessCreateChat(DataResponse<ChatModel> response) {
        if (isFinishing()){
            return;
        }

        hideProgress();
        userModel.setChatId(response.getData().getId());
        if (response.getData().getParticipants()!=null) {
            if (SharedPreferenceHelper.getInstance().getUserId().equals(response.getData().getParticipants().get(0).getUser().getId())) {;
                userModel.isBlocked = response.getData().getParticipants().get(1).isBlocked;
            }
            else {
                userModel.isBlocked = response.getData().getParticipants().get(0).isBlocked;
            }
        }
        SingleChatActivity.start(this, userModel, null);
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
        ToastUtils.shortToast(message);
    }

    @OnEditorAction(R.id.et_search)
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        switch (actionId) {

            case EditorInfo.IME_ACTION_SEARCH:
                etSearch.clearFocus();
                hideKeyboard(getCurrentFocus());
                return true;
        }

        return false;
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
                    hideSoftKeyboard(this);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }
}
