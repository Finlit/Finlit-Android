package com.app.finlit.ui.dates;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.DateStatusModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.authenticate.ViewProfileActivity;
import com.app.finlit.ui.dates.adapter.DatesAdapterAvailable;
import com.app.finlit.ui.dates.adapter.DatesAdapterConfirm;
import com.app.finlit.ui.dates.adapter.DatesAdapterPending;
import com.app.finlit.ui.dates.contract.CancelDatesContract;
import com.app.finlit.ui.dates.contract.DatesContract;
import com.app.finlit.ui.dates.presenter_impl.CancelDatesPresenterImpl;
import com.app.finlit.ui.dates.presenter_impl.DatesPresenterImpl;
import com.app.finlit.utils.Constants;
import com.app.finlit.utils.Filters;
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
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


public class ConfirmFragment extends Fragment implements DatesContract.View, CancelDatesContract.View {

    private static final int FILTER_REQUEST = 101;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.tv_no_result)
    public TextView tv_no_result;


    private List<DateStatusModel> list;
    private OnActionListener<DateStatusModel> onActionListener;
    private OnActionListener<DateStatusModel> onActionListenerCancelDate;
    private OnActionListener<DateStatusModel> onActionListenerProfile;


    private DatesAdapterConfirm adapter;
    private Map<String, String> queries;
    private DatesContract.Presenter presenter;
    private CancelDatesContract.Presenter cancelPresenter;


    private OnFragmentInteractionListener mListener;

    private SharedPreferenceHelper preferenceHelper;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;

    public ConfirmFragment() {
        // Required empty public constructor
    }

    public static ConfirmFragment newInstance() {
        ConfirmFragment fragment = new ConfirmFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);
        ButterKnife.bind(this, view);
        if (Filters.getInstance() != null)
            Filters.getInstance().reset();
        iniListener();


        initAdapter();

        list = new ArrayList<>();
        queries = new HashMap<>();
        presenter = new DatesPresenterImpl(this);
        cancelPresenter = new CancelDatesPresenterImpl(this);
        preferenceHelper=SharedPreferenceHelper.getInstance();
        preferenceHelper.savefilter(false);

        Filters.init();
        initFields();
        initAdapter();
        iniApi();
        return view;
    }

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

        //  queries.put("userId", SharedPreferenceHelper.getInstance().getUserId());
        queries.put("filter", "isConfirmed");
        //  queries.put("pageNo", String.valueOf(currentPage));
        presenter.getDates(queries);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (preferenceHelper.getfilter() == true) {
//            swipeRefreshLayout.setRefreshing(true);
//            isLoading = false;
//            isLastPage = false;
//            currentPage = PAGE_START;
//            if (!Filters.getInstance().getFilter().isEmpty()) {
//                queries = Filters.getInstance().getFilter();
//            } else {
//                queries.put("range", "1000");
//            }
//
//            //  queries.put("userId", SharedPreferenceHelper.getInstance().getUserId());
//            queries.put("filter", "isConfirmed");
//            //  queries.put("pageNo", String.valueOf(currentPage));
//            presenter.getDates(queries);
//        }
//        preferenceHelper.savefilter(false);
    }


    private void iniListener() {

        onActionListener = new OnActionListener<DateStatusModel>() {
            @Override
            public void notify(DateStatusModel model, int position) {
                Intent intent = new Intent(getActivity(), CreateDateActivity.class);
                intent.putExtra(Constants.DATE_STATUS_MODEL, model);
                startActivity(intent);
            }
        };
        onActionListenerCancelDate = new OnActionListener<DateStatusModel>() {
            @Override
            public void notify(DateStatusModel model, int position) {
                cancelPresenter.getEditDates(model.getId());
                ToastUtils.longToast("Your Date has been cancelled");

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
            }
        });

    }

    private void initFields() {
        list = new ArrayList<>();
    }

    private void initAdapter() {

        adapter = new DatesAdapterConfirm(getActivity(), list, onActionListener, onActionListenerCancelDate, onActionListenerProfile);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

//        recyclerView.addOnScrollListener(new CustomScrollListener(layoutManager) {
//            @Override
//            protected void loadMoreItems() {
//
//                isLoading = true;
//                currentPage += 1;
//                queries = new HashMap<>();//Increment page index to load the next one
//
//              //  queries.put("userId", SharedPreferenceHelper.getInstance().getUserId());
//                queries.put("filter", "isConfirmed");
//               // queries.put("pageNo", String.valueOf(currentPage));
//                presenter.getDates(queries);
//
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

    }

    @Override
    public void onSuccessSendDatesRequest(DataResponse response) {

    }

    @Override
    public void onSuccessConfirm(DataResponse response) {

    }

    @Override
    public void onSuccessCancelDates(DataResponse response) {
        if (!isVisible()) {
            return;
        }
        iniListener();

    }

    @Override
    public void onSuccessEditDates(DataResponse response) {

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
