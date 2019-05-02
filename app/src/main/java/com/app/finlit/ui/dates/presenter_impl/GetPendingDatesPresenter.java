package com.app.finlit.ui.dates.presenter_impl;

import com.app.finlit.data.models.DateStatusModel;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.dates.contract.DatesContract;
import com.app.finlit.ui.dates.contract.GetPendingDatesContract;
import com.app.finlit.ui.dates.intracter_impl.DatesIntracterImpl;
import com.app.finlit.ui.dates.intracter_impl.GetPendingDatesIntracter;
import com.app.finlit.utils.Constants;

import java.util.Map;

public class GetPendingDatesPresenter implements GetPendingDatesContract.Presenter, GetPendingDatesContract.OnCompleteListener {

    private GetPendingDatesContract.Intracter intractor;
    private GetPendingDatesContract.View view;

    public GetPendingDatesPresenter(GetPendingDatesContract.View view) {
        this.view = view;
        intractor = new GetPendingDatesIntracter();
    }

    @Override
    public void getPendingDates(Map<String, String> queries) {
        intractor.getPendingDates(queries, this);
        queries.put("pageSize", String.valueOf(Constants.PER_PAGE));

    }

    @Override
    public void onSuccessGetPending(PageResponse<DateStatusModel> response) {
        view.onSuccessGetPending(response);
    }

    @Override
    public void onFailure(String error) {
            view.onFailure(error);
    }
    }

