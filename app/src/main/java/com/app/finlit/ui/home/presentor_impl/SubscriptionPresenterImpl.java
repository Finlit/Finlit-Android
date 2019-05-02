package com.app.finlit.ui.home.presentor_impl;

import com.app.finlit.data.models.ActivePlanModel;
import com.app.finlit.data.models.SubscriptionModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.home.contract.SubscriptionContract;
import com.app.finlit.ui.home.intractor_impl.SubscriptionIntractorImpl;

/**
 * Created by MANISH-PC on 10/29/2018.
 */

public class SubscriptionPresenterImpl implements SubscriptionContract.Presenter, SubscriptionContract.OnCompleteListener {

    private SubscriptionContract.Intractor intractor;
    private SubscriptionContract.View view;

    public SubscriptionPresenterImpl(SubscriptionContract.View view) {
        this.view = view;
        intractor = new SubscriptionIntractorImpl();
    }

    @Override
    public void getSubscriptionDetail() {
        intractor.getSubscriptionDetail(this);
    }

    @Override
    public void createPlan(SubscriptionModel model) {
        intractor.createPlan(model, this);
    }

    @Override
    public void getAcivePlan() {
        intractor.getAcivePlan(this);
    }

    @Override
    public void onSuccess(PageResponse<SubscriptionModel> response) {
        view.onSuccess(response);
    }

    @Override
    public void onSuccessCreatePlan(DataResponse response) {
        view.onSuccessCreatePlan(response);
    }

    @Override
    public void onSuccessActivePlan(DataResponse<ActivePlanModel> response) {
        view.onSuccessActivePlan(response);
    }

    @Override
    public void onFailure(String message) {
        view.onFailure(message);
    }
}
