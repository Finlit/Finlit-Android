package com.app.finlit.ui.nearby;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.app.finlit.ui.home.contract.GetUsersContract;
import com.app.finlit.ui.home.presentor_impl.GetUsersPresentorImpl;
import com.app.finlit.ui.nearby.adapter.NearByAdapter;
import com.app.finlit.ui.nearby.adapter.PlaceArrayAdapter;
import com.app.finlit.utils.Filters;
import com.app.finlit.utils.LocationUtils;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;
import com.app.finlit.utils.Utility;
import com.app.finlit.utils.listeners.OnActionListener;
import com.app.finlit.utils.views.ResponsiveScrollView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.app.finlit.utils.Utility.hideSoftKeyboard;

public class NearByActivity extends BaseActivity implements GetUsersContract.View,
        ChatContract.View {
    private FusedLocationProviderClient client;
    private GoogleMap map;
    private static final int FILTER_REQUEST = 101;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private GoogleApiClient mGoogleApiClient;


    LatLng latLng;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    @BindView(R.id.iv_left)
    public ImageView ivLeft;
    @BindView(R.id.tv_heading)
    public TextView tvTitle;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.scrollView)
    public ResponsiveScrollView scrollView;

    @BindView(R.id.tv_no_result)
    public TextView tvNoResult;
    private Geocoder geocoder;
    private List<Address> addresses;
    Double lat, lng;
    private NearByAdapter adapter;
    private List<UserModel> list;
    private OnActionListener<UserModel> onActionListener;
    private OnActionListener<UserModel> onActionListenerViewProfile;
    private SharedPreferenceHelper preferenceHelper;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    private Location location;

    private Map<String, String> queries;


    private GetUsersContract.Presentor presenter;
    private ChatContract.Presenter chatPresenter;

    private UserModel userModel;
    private String gender;

    public static void start(Context context) {
        Intent intent = new Intent(context, NearByActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_near_by;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLocation();
        list = new ArrayList<>();
        presenter = new GetUsersPresentorImpl(this);
        chatPresenter = new ChatPresenterImpl(this);
        preferenceHelper = SharedPreferenceHelper.getInstance();
        if (Filters.getInstance() != null)
            Filters.getInstance().reset();
        getLatLong();
        initToolbar();
        initListner();
        initAdapter();
        //initFields();
    }

    public void getLocation() {

//
//        Intent autocompleteIntent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, getPlaceFields())
//                .setInitialQuery("")
//                .setCountry("")
//                .build(ProfileActivity.this);
//        startActivityForResult(autocompleteIntent, AUTOCOMPLETE_REQUEST_CODE2);
        LocationUtils.askToTurnOnLocation(this);
        SmartLocation.with(this).location()
                .oneFix()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        NearByActivity.this.location = location;
                        if (location != null) {
                            geocoder = new Geocoder(getApplicationContext(), Locale.ENGLISH);
                        }
                        try {
                            assert location != null;
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (addresses != null) {
                            String address = addresses.get(0).getAddressLine(0);
//                            city = addresses.get(0).getLocality();
                            // txtLocation.setText(address);
                        }
                    }
                });
    }

    private void initToolbar() {
        tvTitle.setText("NEAR BY");
    }

    private void initFields() {
        swipeRefreshLayout.setRefreshing(true);
        queries = new HashMap<>();
        isLoading = false;
        isLastPage = false;
        currentPage = PAGE_START;
        if (Filters.getInstance() != null && !Filters.getInstance().getFilter().isEmpty()) {
            queries = Filters.getInstance().getFilter();
        }
        else{
            queries.put("range", "5000");
        }
        if (preferenceHelper.getMatchgender() != null) {
            if (preferenceHelper.getMatchgender().equals("MALE")) {
                gender = "male";
            } else if (preferenceHelper.getMatchgender().equals("FEMALE")) {
                gender = "female";
            }
            queries.put("gender", gender);

//            if (preferenceHelper.getMatchgender().equals("BOTH")) {
//                gender = "both";
        }
      //  Toast.makeText(NearByActivity.this, String.valueOf(lat) + "LONG11111---------" +String.valueOf(lng) , Toast.LENGTH_SHORT).show();
       // queries.put("latitude", String.valueOf(lat));
        //queries.put("longitude", String.valueOf(lng));
        queries.put("pageNo", String.valueOf(currentPage));
        presenter.getUsers(queries);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        swipeRefreshLayout.setRefreshing(true);
//        queries = new HashMap<>();
//        isLoading = false;
//        isLastPage = false;
//        currentPage = PAGE_START;
//        if (Filters.getInstance() != null && !Filters.getInstance().getFilter().isEmpty()) {
//            queries = Filters.getInstance().getFilter();
//        } else {
//            queries.put("range", "100000");
//        }
//        if (preferenceHelper.getMatchgender() != null) {
//            if (preferenceHelper.getMatchgender().equals("MALE")) {
//                gender = "male";
//            } else if (preferenceHelper.getMatchgender().equals("FEMALE")) {
//                gender = "female";
//            }
//            queries.put("gender", gender);
//
//
////            if (preferenceHelper.getMatchgender().equals("BOTH")) {
////                gender = "both";
////            }
//        }
//        queries.put("pageNo", String.valueOf(currentPage));
//        presenter.getUsers(queries);
//
//    }

    private void initAdapter() {
        adapter = new NearByAdapter(this, list, onActionListener, onActionListenerViewProfile);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }


    private void initListner() {
        onActionListener = new OnActionListener<UserModel>() {
            @Override
            public void notify(UserModel model, int position) {
                showProgress();
                userModel = model;
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
                ViewProfileActivity.start(NearByActivity.this, model);
            }
        };

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Filters.getInstance() != null)
                    Filters.getInstance().reset();

                initFields();
            }
        });

      //
        //  scrollView.setOnEndScrollListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
        binder = null;
    }

    @OnClick(R.id.iv_left)
    public void onClickLeft() {
        onBackPressed();
    }

    @OnClick(R.id.iv_filter)
    public void onClickFilter() {
        startActivityForResult(new Intent(this, FilterActivity.class), FILTER_REQUEST);
    }

//    @OnClick(R.id.tv_quick_view)
//    public void onClickQuickView(){
//        QuickViewActivity.start(this);
//    }

    @Override
    public void onSuccessGetUsers(PageResponse<UserModel> response) {
        if (isFinishing()) {
            return;
        }
        if (response.getItems().isEmpty())
            tvNoResult.setVisibility(View.VISIBLE);
        else
            tvNoResult.setVisibility(View.GONE);

        if (currentPage == PAGE_START) {
            if (swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);

            adapter.clear();
            adapter.addAll(response.getItems());
            TOTAL_PAGES = Utility.getTotalPages(response.getTotalRecords(), response.getPageSize());
            if (currentPage < TOTAL_PAGES) {
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
        if (isFinishing()) {
            return;
        }

        hideProgress();
        userModel.setChatId(response.getData().getId());
        if (response.getData().getParticipants() != null) {
            if (SharedPreferenceHelper.getInstance().getUserId().equals(response.getData().getParticipants().get(0).getUser().getId())) {
                ;
                userModel.isBlocked = response.getData().getParticipants().get(0).isBlocked;
            } else {
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
        if (isFinishing()) {
            return;
        }
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        ToastUtils.shortToast(message);
    }

//    @Override
//    public void onEndScroll() {
//        if (!isLoading && !isLastPage) {
//            isLoading = true;
//            currentPage += 1;
//            queries.put("pageNo", String.valueOf(currentPage));
//            presenter.getUsers(queries);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == FILTER_REQUEST) {
                initFields();
            }
        }
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(NearByActivity.this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    private void getLatLong() {
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(NearByActivity.this);
        if (ActivityCompat.checkSelfPermission(NearByActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        client.getLastLocation().addOnSuccessListener(NearByActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                     //Toast.makeText(NearByActivity.this, String.valueOf(lat) + "LONG---------" +String.valueOf(lng) , Toast.LENGTH_SHORT).show();
                    initFields();
                }

            }
        });

    }


}
