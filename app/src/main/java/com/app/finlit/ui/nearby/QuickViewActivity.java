package com.app.finlit.ui.nearby;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.app.finlit.R;
import com.app.finlit.data.models.ChatModel;
import com.app.finlit.data.models.ParticipantsModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.chat.SingleChatActivity;
import com.app.finlit.ui.chat.contract.ChatContract;
import com.app.finlit.ui.chat.presenter_impl.ChatPresenterImpl;
import com.app.finlit.ui.home.contract.GetUsersContract;
import com.app.finlit.ui.home.presentor_impl.GetUsersPresentorImpl;
import com.app.finlit.ui.nearby.adapter.CardAdapter;
import com.app.finlit.ui.nearby.adapter.CarouselAdapter;
import com.app.finlit.ui.nearby.adapter.CarouselEffectTransformer;
import com.app.finlit.ui.nearby.adapter.CarouselPagerAdapter;
import com.app.finlit.utils.Constants;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class QuickViewActivity extends BaseActivity implements ViewPager.OnPageChangeListener,
        CarouselFragment.OnFragmentInteractionListener, GetUsersContract.View, ChatContract.View,
        CardStackListener{

    public static final int ADAPTER_TYPE_TOP = 1;
    public static final int ADAPTER_TYPE_BOTTOM = 2;

//    @BindView(R.id.viewPagerbackground)
//    public ViewPager viewPagerBack;
//    @BindView(R.id.viewpagerTop)
//    public ViewPager viewPagerTop;

    private CardStackView cardStackView;

    private List<UserModel> list;

    private GetUsersContract.Presentor presenter;
    private ChatContract.Presenter chatPresenter;

    private UserModel userModel;
    private CardStackLayoutManager manager;
    private CarouselAdapter adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, QuickViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_quick_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        presenter = new GetUsersPresentorImpl(this);
        chatPresenter = new ChatPresenterImpl(this);

            initAdapter();
//        init();
        Map<String, String> quiries = new HashMap<>();
        quiries.put("serverPaging", "false");
        showProgress();
        presenter.getUsers(quiries);
    }

    private void initAdapter() {


        adapter = new CarouselAdapter(this, list, null);
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

    }

//    private void init() {
//        viewPagerTop.setClipChildren(false);
//        viewPagerTop.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.pager_margin));
//        viewPagerTop.setOffscreenPageLimit(3);
//        viewPagerTop.setPageTransformer(false, new CarouselEffectTransformer(this)); // Set transformer
//    }
//
//    private void initViewPager(){
//        // Set Top ViewPager Adapter
//        CarouselPagerAdapter adapter = new CarouselPagerAdapter(getSupportFragmentManager(), list, ADAPTER_TYPE_TOP);
//        viewPagerTop.setAdapter(adapter);
//
//        // Set Background ViewPager Adapter
//        CarouselPagerAdapter adapterBackground = new CarouselPagerAdapter(getSupportFragmentManager(), list, ADAPTER_TYPE_BOTTOM);
//        viewPagerBack.setAdapter(adapterBackground);
//    }


    @Override
    public void onBackPressed() {
        adapter= null;
        super.onBackPressed();
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        int width = viewPagerBack.getWidth();
//        viewPagerBack.scrollTo((int) (width * position + width * positionOffset), 0);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        if (state == ViewPager.SCROLL_STATE_IDLE) {
//            viewPagerBack.setCurrentItem(viewPagerBack.getCurrentItem());
//        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @OnClick(R.id.tv_next)
    public void onClickNext(){
//        int currentItem = viewPagerTop.getCurrentItem();
//        if (currentItem<list.size()-1)
//            viewPagerTop.setCurrentItem(++currentItem);

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
    public void onSuccessGetUsers(PageResponse<UserModel> response) {
        if (isFinishing()){
            return;
        }
        hideProgress();
        list.addAll(response.getItems());
        adapter.notifyDataSetChanged();
//        initAdapter();
//        initViewPager();
    }

    @Override
    public void onSuccessGetFilter(PageResponse<UserModel> response) {

    }

    @Override
    public void onSuccessUser(DataResponse<UserModel> response) {

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
                userModel.isBlocked = response.getData().getParticipants().get(0).isBlocked;
            }
            else {
                userModel.isBlocked = response.getData().getParticipants().get(1).isBlocked;
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
        if(isFinishing()){
            return;
        }
        hideProgress();
        ToastUtils.shortToast(message);
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {
        Log.d("CardStackView", "onCardDragging: d = " + direction.name() + ", r = " + ratio);
    }

    @Override
    public void onCardSwiped(Direction direction) {
        Log.d("CardStackView", "onCardSwiped: p = " + manager.getTopPosition() + ", d = " + direction);

        if(direction.name().equals("Right")){
            onClickMessage();
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
