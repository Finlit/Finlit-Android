package com.app.finlit.ui.dates;

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
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.authenticate.ViewProfileActivity;
import com.app.finlit.ui.dates.adapter.DatesAdapterAvailable;
import com.app.finlit.ui.dates.adapter.DatesAdapterSent;
import com.app.finlit.ui.dates.contract.CancelDatesContract;
import com.app.finlit.ui.dates.contract.DatesContract;
import com.app.finlit.ui.dates.presenter_impl.CancelDatesPresenterImpl;
import com.app.finlit.ui.dates.presenter_impl.DatesPresenterImpl;
import com.app.finlit.utils.Filters;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;
import com.app.finlit.utils.Utility;
import com.app.finlit.utils.listeners.OnActionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SentRequestFragment extends Fragment implements DatesContract.View, CancelDatesContract.View {

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.tv_no_result)
    public TextView tv_no_result;

    private DatesAdapterSent adapter;
    private List<DateStatusModel> list;

    private Map<String, String> queries;
    private DatesContract.Presenter presenter;
    private CancelDatesContract.Presenter cancelPresenter;
    private SharedPreferenceHelper preferenceHelper;


    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;



    private OnActionListener<DateStatusModel> onActionListener;
    private OnActionListener<DateStatusModel> onActionListenerProfile;


    public static SentRequestFragment newInstance() {
        SentRequestFragment fragment = new SentRequestFragment();
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
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sentrequest_fragment, container, false);
        ButterKnife.bind(this, view);
        iniListener();

        queries=new HashMap<>();
        presenter=new DatesPresenterImpl(this);
        cancelPresenter=new CancelDatesPresenterImpl(this);
        preferenceHelper=SharedPreferenceHelper.getInstance();
        preferenceHelper.savefilter(false);
        list = new ArrayList<>();
        iniAdapter();

        initFields();
        iniApi();

        return view;
    }


    private void iniApi() {
        swipeRefreshLayout.setRefreshing(true);
        isLoading = false;
        isLastPage = false;
      //  currentPage = PAGE_START;
      //  queries.put("filter", "isSendr");
        if (!Filters.getInstance().getFilter().isEmpty()) {
            queries = Filters.getInstance().getFilter();
        } else {
            queries.put("range", "1000");
        }

        queries.put("filter","isSend");
        presenter.getDates(queries);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if(preferenceHelper.getfilter()==true){
//            swipeRefreshLayout.setRefreshing(true);
//            isLoading = false;
//            isLastPage = false;
//            //  currentPage = PAGE_START;
//            //  queries.put("filter", "isSendr");
//            if (!Filters.getInstance().getFilter().isEmpty()) {
//                queries = Filters.getInstance().getFilter();
//            } else {
//                queries.put("range", "1000");
//            }
//
//            queries.put("filter","isSend");
//            presenter.getDates(queries);
//        }
        //preferenceHelper.savefilter(false);
    }

    private void initFields() {
        list = new ArrayList<>();
    }

    private void iniListener() {
        onActionListener =new OnActionListener<DateStatusModel>() {
            @Override
            public void notify(DateStatusModel model, int position) {

                cancelPresenter.getCancelDates(model.getUserid());

            }
        };
        onActionListenerProfile=new OnActionListener<DateStatusModel>() {
            @Override
            public void notify(DateStatusModel model, int position) {
                Intent intent= new Intent(getActivity(), ViewProfileActivity.class);
//                UserModel userModel=new UserModel();
//                intent.putExtra(Constants.USER_MODEL,userModel);
                intent.putExtra("from","here");
                intent.putExtra("userID",model.getUserid());
                startActivity(intent);
            }
        };

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                iniApi();
            }
        });


    }

    private void iniAdapter() {
        adapter = new DatesAdapterSent(getActivity(), list, onActionListener,onActionListenerProfile);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

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
         if(!isVisible()) {
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
