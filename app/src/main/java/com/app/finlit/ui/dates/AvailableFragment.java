package com.app.finlit.ui.dates;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.DateStatusModel;
import com.app.finlit.data.models.StatusModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.authenticate.ViewProfileActivity;
import com.app.finlit.ui.dates.adapter.DatesAdapterAvailable;
import com.app.finlit.ui.dates.contract.DatesContract;
import com.app.finlit.ui.dates.presenter_impl.DatesPresenterImpl;
import com.app.finlit.ui.home.contract.FavouriteContract;
import com.app.finlit.ui.home.contract.GetUsersContract;
import com.app.finlit.ui.home.presentor_impl.FavouritePresenter;
import com.app.finlit.ui.home.presentor_impl.GetUsersPresentorImpl;
import com.app.finlit.utils.Constants;
import com.app.finlit.utils.Filters;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;
import com.app.finlit.utils.Utility;
import com.app.finlit.utils.listeners.OnActionListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.app.Activity.RESULT_OK;


public class AvailableFragment extends Fragment implements DatesContract.View, GetUsersContract.View, FavouriteContract.Presenter, FavouriteContract.View {

    private static final int FILTER_REQUEST = 101;
    private FusedLocationProviderClient client;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.tv_no_result)
    public TextView tv_no_result;
    private UserModel userModel;
    Double lat, lng;


    private DatesAdapterAvailable adapter;
    private Map<String, String> queries;

    private DatesContract.Presenter presenter;
    private GetUsersContract.Presentor presenterUser;
    private FavouriteContract.Presenter findDatePresenter;


    private List<DateStatusModel> list;
    private OnActionListener<DateStatusModel> onActionListener;
    private OnActionListener<DateStatusModel> onActionListenerNothanks;
    private OnActionListener<DateStatusModel> onActionListenerProfile;


    private SharedPreferenceHelper preferenceHelper;


    private OnFragmentInteractionListener mListener;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;

    public AvailableFragment() {
    }

    public static AvailableFragment newInstance() {
        AvailableFragment fragment = new AvailableFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_available, container, false);
        ButterKnife.bind(this, view);
        if (Filters.getInstance() != null)
            Filters.getInstance().reset();
        list = new ArrayList<>();
        queries = new HashMap<>();
        getLatLong();


        userModel=new UserModel();

        presenter = new DatesPresenterImpl(this);
        presenterUser = new GetUsersPresentorImpl(this);
        findDatePresenter = new FavouritePresenter(this);
        preferenceHelper = SharedPreferenceHelper.getInstance();
        preferenceHelper.savefilter(false);
        iniFindDateApi();
        Filters.init();
        iniListerer();
        initFields();
        initAdapter();
        iniApi();
        //iniFilterApi();

        return view;
    }

    private void iniFindDateApi() {

        StatusModel statusModel=new StatusModel();
        if (preferenceHelper.getDates() == true) {
            statusModel.setStatus("on");
            Log.e("status",statusModel.getStatus());
            findDatePresenter.createFindMeDate(preferenceHelper.getUserId(), statusModel);
        }
        else{
            statusModel.setStatus("off");
            Log.e("statusoff",statusModel.getStatus());
            findDatePresenter.createFindMeDate(preferenceHelper.getUserId(), statusModel);
        }
    }

    private void iniListerer() {
        onActionListener = new OnActionListener<DateStatusModel>() {
            @Override
            public void notify(DateStatusModel model, int position) {
                Intent intent = new Intent(getActivity(), CreateDateActivity.class);
                intent.putExtra(Constants.DATE_STATUS_MODEL, model);
                startActivity(intent);

            }
        };

        onActionListenerNothanks = new OnActionListener<DateStatusModel>() {
            @Override
            public void notify(DateStatusModel model, int position) {
                presenter.getIntrest(model.getId());   // no thanks api hit
            }
        };
        onActionListenerProfile = new OnActionListener<DateStatusModel>() {
            @Override
            public void notify(DateStatusModel model, int position) {
                Intent intent = new Intent(getActivity(), ViewProfileActivity.class);
//                UserModel userModel=new UserModel();
//                intent.putExtra(Constants.USER_MODEL,userModel);
                intent.putExtra("from", "here");
                intent.putExtra("userID", model.getId());
                startActivity(intent);
            }
        };


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Filters.getInstance() != null)
                    Filters.getInstance().reset();
                iniApi();
                // iniFilterApi();
            }
        });

    }

//    private void iniFilterApi() {
//        queries = new HashMap<>();
//
//        presenterUser.getFilter(queries);
//    }

    private void iniApi() {
        swipeRefreshLayout.setRefreshing(true);
        isLoading = false;
        isLastPage = false;
        currentPage = PAGE_START;
        if (!Filters.getInstance().getFilter().isEmpty()) {
            queries = Filters.getInstance().getFilter();
        } else {
            queries.put("range", "1000");
        }

        queries.put("filter", "isInterest");
        presenter.getDates(queries);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    private void getLatLong() {
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                   // Toast.makeText(getActivity(), String.valueOf(lat) + "LONG---------" + String.valueOf(lng), Toast.LENGTH_SHORT).show();
                    iniApi();
                }

            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        if (preferenceHelper.getfilter() == true) {
            swipeRefreshLayout.setRefreshing(true);
            isLoading = false;
            isLastPage = false;
            currentPage = PAGE_START;

            // queries.put("status", "active");
            if (!Filters.getInstance().getFilter().isEmpty()) {
                queries = Filters.getInstance().getFilter();
            } else {
                queries.put("range", "1000");
            }
            if (preferenceHelper.getDates() == true) {
                // for find me a date qu...
            }


            queries.put("filter", "isInterest");
            // queries.put("pageNo", String.valueOf(currentPage));
            presenter.getDates(queries);

        }
        preferenceHelper.savefilter(false);
    }

    private void initFields() {

        list = new ArrayList<>();
    }


    private void initAdapter() {
//        list.add(new UserModel());
//        list.add(new UserModel());
//        try {
//            Collections.sort( list, new Comparator<DateStatusModel>() {
//                public int compare(DateStatusModel s1, DateStatusModel s2) {
//
//                    return Float.compare( ( Float.parseFloat( s1.getLocation().getAddress()) ), ( Float.parseFloat( s2.getLocation().getAddress() ) ) );
//                }
//            } );
//        } catch (Exception e) {
//        }


        adapter = new DatesAdapterAvailable(getActivity(), list, onActionListener, onActionListenerNothanks, onActionListenerProfile);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
//        recyclerView.addOnScrollListener(new CustomScrollListener(layoutManager) {
//            @Override
//            protected void loadMoreItems() {
//                isLoading = true;
//                currentPage += 1;
//                queries = new HashMap<>();//Increment page index to load the next one
//
//              //  queries.put("userId", SharedPreferenceHelper.getInstance().getUserId());
//                queries.put("status", "active");
//                queries.put("filter", "isInterest");
//               // queries.put("pageNo", String.valueOf(currentPage));
//                presenter.getDates(queries);
//            }
//
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == FILTER_REQUEST) {
                // iniFilterApi();
                iniApi();
            }
        }
    }

    @Override
    public void onSuccessDates(PageResponse<DateStatusModel> response) {

        if (!isVisible()) {
            return;
        }

        if (response.getItems().isEmpty())
            tv_no_result.setVisibility(View.VISIBLE);
        else
            tv_no_result.setVisibility(View.GONE);
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
    public void onSuccessIntrest(DataResponse response) {
        if (!isVisible()) {
            return;
        }


        iniListerer();

    }

    @Override
    public void onSuccessSendDatesRequest(DataResponse response) {


    }

    @Override
    public void onSuccessConfirm(DataResponse response) {

    }


    @Override
    public void onSuccessGetUsers(PageResponse<UserModel> response) {

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
    public void onFailure(String error) {
        if (!isVisible()) {
            return;
        }
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        ToastUtils.shortToast(error);
    }

    @Override
    public void createFavourite(String userId) {

    }

    @Override
    public void createFindMeDate(String userId, StatusModel statusModel) {
        if (!isVisible()) {
            return;
        }
    }


    @Override
    public void createUnFavourite(String userId) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
