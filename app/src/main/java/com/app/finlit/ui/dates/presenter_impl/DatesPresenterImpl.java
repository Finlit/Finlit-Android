package com.app.finlit.ui.dates.presenter_impl;

import com.app.finlit.data.models.DateStatusModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.dates.PendingFragment;
import com.app.finlit.ui.dates.contract.DatesContract;
import com.app.finlit.ui.dates.intracter_impl.DatesIntracterImpl;
import com.app.finlit.utils.Constants;

import java.util.Map;

public class DatesPresenterImpl implements DatesContract.Presenter, DatesContract.OnCompleteListener {

    private DatesContract.Intractor intractor;
    private DatesContract.View view;

    public DatesPresenterImpl(DatesContract.View view) {
        this.view = view;
        intractor = new DatesIntracterImpl();
    }


    @Override
    public void getDates(Map<String, String> queries) {

        intractor.getDates(queries,this);
        queries.put("pageSize", String.valueOf(Constants.PER_PAGE));


    }

    @Override
    public void getIntrest(String id) {
        intractor.getIntrest(id,this);

    }

    @Override
    public void SendDatesRequest(String id,DateStatusModel model) {
        intractor.SendDatesRequest(id,model,this);

    }

    @Override
    public void getConfirm(String id) {
        intractor.getConfirm(id,this);

    }


    @Override
    public void onSuccessDates(PageResponse<DateStatusModel> response) {
        view.onSuccessDates(response);
    }

    @Override
    public void onSuccessConfirm(DataResponse response) {
        view.onSuccessConfirm(response);

    }

    @Override
    public void onSuccessSendDatesRequest(DataResponse response) {
        view.onSuccessSendDatesRequest(response);

    }

    @Override
    public void onSuccessIntrest(DataResponse response) {
        view.onSuccessIntrest(response);

    }

    @Override
    public void onFailure(String error) {

        if (view!=null)
        view.onFailure(error);
        }

}



