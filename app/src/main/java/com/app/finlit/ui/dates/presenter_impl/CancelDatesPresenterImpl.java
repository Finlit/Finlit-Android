package com.app.finlit.ui.dates.presenter_impl;

import com.app.finlit.data.models.CancelDatesModel;
import com.app.finlit.data.models.DateStatusModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.dates.contract.CancelDatesContract;
import com.app.finlit.ui.dates.contract.DatesContract;
import com.app.finlit.ui.dates.intracter_impl.CancelDatesIntracterImpl;
import com.app.finlit.ui.dates.intracter_impl.DatesIntracterImpl;
import com.app.finlit.utils.Constants;



public class CancelDatesPresenterImpl implements CancelDatesContract.Presenter, CancelDatesContract.OnCompleteListener {
    private CancelDatesContract.View view;
    private CancelDatesContract.Intractor intractor;

    public CancelDatesPresenterImpl(CancelDatesContract.View view) {
        this.view = view;
        intractor = new CancelDatesIntracterImpl();
    }


    @Override
    public void getCancelDates(String Id) {
        intractor.getCancelDates(Id,this);
    }

    @Override
    public void getEditDates(String Id) {
        intractor.getEditDates(Id,this);
    }


    @Override
    public void onSuccessCancelDates(DataResponse response) {
       view.onSuccessCancelDates(response);
    }

    @Override
    public void onSuccessEditDates(DataResponse response) {
        view.onSuccessEditDates(response);

    }

    @Override
    public void onFailure(String error) {

        if (view!=null)
            view.onFailure(error);

    }
}
