package com.app.finlit.ui.findmatch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.ChatModel;
import com.app.finlit.data.models.ParticipantsModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.authenticate.ViewProfileActivity;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.chat.SingleChatActivity;
import com.app.finlit.ui.chat.contract.ChatContract;
import com.app.finlit.ui.chat.presenter_impl.ChatPresenterImpl;
import com.app.finlit.ui.home.contract.FavouriteContract;
import com.app.finlit.ui.home.contract.GetUsersContract;
import com.app.finlit.ui.home.presentor_impl.GetUsersPresentorImpl;
import com.app.finlit.ui.nearby.adapter.CarouselAdapter;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;
import com.app.finlit.utils.listeners.OnActionListener;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.app.finlit.utils.Utility.hideSoftKeyboard;

public class FindMatchActivity extends BaseActivity implements GetUsersContract.View,
        FavouriteContract.View, ChatContract.View,CardStackListener {

    @BindView(R.id.tv_heading)
    public TextView tvTitle;
//
//    @BindView(R.id.recycler_view)
//    public RecyclerView recyclerView;
//    @BindView(R.id.swipeRefreshLayout)
//    public SwipeRefreshLayout swipeRefreshLayout;
//
//    @BindView(R.id.et_search)
//    public EditText etSearch;

//    private CircleAdapter adapter;
//    private List<UserModel> list;
//    private OnActionListener<UserModel> onActionListener;
//    private OnActionListener<UserModel> onActionListenerMessage;
    private OnActionListener<UserModel> onActionListenerViewProfile;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;

    private GetUsersContract.Presentor presenter;
    private Map<String, String> queries ;
    private String searchText;

//    private FavouriteContract.Presenter favPresenter;
    private ChatContract.Presenter chatPresenter;

    private UserModel userModel;
    private SharedPreferenceHelper preferenceHelper;

    private CardStackView cardStackView;
    private List<UserModel> list;
    private CardStackLayoutManager manager;
    private CarouselAdapter adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, FindMatchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_find_match;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        preferenceHelper= SharedPreferenceHelper.getInstance();
        presenter = new GetUsersPresentorImpl(this);
//        favPresenter = new FavouritePresenter(this);
        chatPresenter = new ChatPresenterImpl(this);

        initToolbar();
        initListner();
        initAdapter();
//        initFields();
        Map<String, String> quiries = new HashMap<>();
        quiries.put("serverPaging", "false");
        if(preferenceHelper.getMatchgender()!=null){
            if(preferenceHelper.getMatchgender().equals("MALE")){
                quiries.put("gender", "male");
            }
            else if(preferenceHelper.getMatchgender().equals("FEMALE")){
                quiries.put("gender", "female");
            }
        }
        showProgress();
        presenter.getUsers(quiries);

    }

    private void initToolbar() {
        tvTitle.setText("FIND MATCH");
    }

    private void initFields(){
//        swipeRefreshLayout.setRefreshing(true);
        queries = new HashMap<>();
        isLoading = false;
        isLastPage = false;
        currentPage = PAGE_START;
        //queries.put("filter", "favourite");
        queries.put("pageNo", String.valueOf(currentPage));
        if (searchText!=null)
            queries.put("name", searchText);
        if(preferenceHelper.getMatchgender()!=null){
            if(preferenceHelper.getMatchgender().equals("MALE")){
                queries.put("gender", "male");
            }
            else if(preferenceHelper.getMatchgender().equals("FEMALE")){
                queries.put("gender", "female");
            }
        }
        presenter.getUsers(queries);
    }

    private void initAdapter() {

        adapter = new CarouselAdapter(this, list, onActionListenerViewProfile);
        manager = new CardStackLayoutManager(this, this);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
            }
        });
        cardStackView= findViewById(R.id.card_stack_view);
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);

        SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .build();
        manager.setSwipeAnimationSetting(setting);


//        adapter = new CircleAdapter(this, list, onActionListener, onActionListenerMessage, onActionListenerViewProfile);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapter);
//
//        recyclerView.addOnScrollListener(new CustomScrollListener(layoutManager) {
//            @Override
//            protected void loadMoreItems() {
//                isLoading = true;
//                currentPage += 1;
//                queries.put("pageNo", String.valueOf(currentPage));
//                if (searchText!=null)
//                    queries.put("name", searchText);
//                if(preferenceHelper.getMatchgender()!=null){
//                    if(preferenceHelper.getMatchgender().equals("MALE")){
//                        queries.put("gender", "male");
//                    }
//                    else if(preferenceHelper.getMatchgender().equals("FEMALE")){
//                        queries.put("gender", "female");
//                    }
//                }
//                presenter.getUsers(queries);
//            }
//
//            @Override
//            public int getTotalPageCount() {
//                return TOTAL_PAGES;
//            }
//
//            @Override
//            public boolean isLastPage() {
//                return isLastPage;
//            }
//
//            @Override
//            public boolean isLoading() {
//                return isLoading;
//            }
//        });
    }

    private void initListner(){
//        etSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (etSearch.hasFocus()) {
//                    if (s.toString().isEmpty()) {
//                        searchText = null;
//                        initFields();
//                        hideKeyboard(getCurrentFocus());
//                        return;
//                    }
//                    searchText = s.toString();
//                    initFields();
//                }
//            }
//        });
//
//        onActionListener = new OnActionListener<UserModel>() {
//            @Override
//            public void notify(UserModel model, int position) {
//                if (!model.isFavourite)
//                    favPresenter.createFavourite(model.getId());
//                else
//                    favPresenter.createUnFavourite(model.getId());
//            }
//        };
//
//        onActionListenerMessage = new OnActionListener<UserModel>() {
//            @Override
//            public void notify(UserModel model, int position) {
//                userModel = model;
//                showProgress();
//                ChatModel chatModel = new ChatModel();
//                chatModel.setChatType("normal");
//                ParticipantsModel participantsModel = new ParticipantsModel();
//                participantsModel.setUserId(userModel.getId());
//                List<ParticipantsModel> participantsModelList = new ArrayList<>();
//                participantsModelList.add(participantsModel);
//
//                chatModel.setParticipants(participantsModelList);
//
//                chatPresenter.createChat(chatModel);
//            }
//        };
//
        onActionListenerViewProfile = new OnActionListener<UserModel>() {
            @Override
            public void notify(UserModel model, int position) {
                ViewProfileActivity.start(FindMatchActivity.this, model);
            }
        };
//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                hideKeyboard(getCurrentFocus());
//                etSearch.clearFocus();
//                etSearch.setText("");
//                searchText = null;
//                initFields();
//            }
//        });

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
    public void onSuccessGetUsers(PageResponse<UserModel> response) {
        if (isFinishing()){
            return;
        }
        hideProgress();
        list.addAll(response.getItems());
        adapter.notifyDataSetChanged();

//        if(currentPage == PAGE_START){
//            if (swipeRefreshLayout.isRefreshing())
//                swipeRefreshLayout.setRefreshing(false);
//
//            adapter.clear();
//            adapter.addAll(response.getItems());
//            TOTAL_PAGES = Utility.getTotalPages(response.getTotalRecords(), response.getPageSize());
//            if (currentPage < TOTAL_PAGES){
//                adapter.addLoadingFooter();
//            } else isLastPage = true;
//        } else {
//            adapter.removeLoadingFooter();
//            isLoading = false;
//            adapter.addAll(response.getItems());
//            if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
//            else isLastPage = true;
//        }
    }

    @Override
    public void onSuccessGetFilter(PageResponse<UserModel> response) {

    }

    @Override
    public void onSuccessUser(DataResponse<UserModel> response) {

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
        hideProgress();
        ToastUtils.shortToast(message);
    }

//    @OnEditorAction(R.id.et_search)
//    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//
//        switch (actionId) {
//
//            case EditorInfo.IME_ACTION_SEARCH:
//                etSearch.clearFocus();
//                hideKeyboard(getCurrentFocus());
//                return true;
//        }
//
//        return false;
//    }

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

    @OnClick(R.id.tv_next)
    public void onClickNext(){
        SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .build();
        manager.setSwipeAnimationSetting(setting);
        cardStackView.swipe();
    }

    @OnClick(R.id.tv_message)
    public void onClickMessage(){
        if (manager.getTopPosition() > list.size()-1){
            return;
        }
        showProgress();
        userModel = list.get(manager.getTopPosition());
//        userModel = list.get(viewPagerTop.getCurrentItem());
        ChatModel chatModel = new ChatModel();
        chatModel.setChatType("normal");
        ParticipantsModel participantsModel = new ParticipantsModel();
        participantsModel.setUserId(userModel.getId());
        List<ParticipantsModel> participantsModelList = new ArrayList<>();
        participantsModelList.add(participantsModel);

        chatModel.setParticipants(participantsModelList);

        chatPresenter.createChat(chatModel);
    }


    @Override
    public void onCardDragging(Direction direction, float ratio) {
        Log.d("CardStackView", "onCardDragging: d = " + direction.name() + ", r = " + ratio);
    }

    @Override
    public void onCardSwiped(Direction direction) {
        Log.d("CardStackView", "onCardSwiped: p = " + manager.getTopPosition() + ", d = " + direction);

        if(direction.name().equals("Right")){
            showProgress();
            userModel = list.get(manager.getTopPosition()-1);
//        userModel = list.get(viewPagerTop.getCurrentItem());
            ChatModel chatModel = new ChatModel();
            chatModel.setChatType("normal");
            ParticipantsModel participantsModel = new ParticipantsModel();
            participantsModel.setUserId(userModel.getId());
            List<ParticipantsModel> participantsModelList = new ArrayList<>();
            participantsModelList.add(participantsModel);

            chatModel.setParticipants(participantsModelList);

            chatPresenter.createChat(chatModel);
        }
    }

    @Override
    public void onCardRewound() {
        Log.d("CardStackView", "onCardRewound: " + manager.getTopPosition());
    }

    @Override
    public void onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled:" + manager.getTopPosition());
    }
}
