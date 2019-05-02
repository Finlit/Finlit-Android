package com.app.finlit.ui.home.intractor_impl;

import com.app.finlit.data.Injector;
import com.app.finlit.data.InterfaceApi;
import com.app.finlit.data.models.ActivePlanModel;
import com.app.finlit.data.models.SubscriptionModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.home.contract.SubscriptionContract;
import com.app.finlit.utils.App;
import com.app.finlit.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MANISH-PC on 10/29/2018.
 */

public class SubscriptionIntractorImpl implements SubscriptionContract.Intractor{

    private InterfaceApi api;

    public SubscriptionIntractorImpl(){
        api = Injector.provideApi();
    }

    @Override
    public void getSubscriptionDetail(final SubscriptionContract.OnCompleteListener callback) {

        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.getSubscriptionDetail().enqueue(new Callback<PageResponse<SubscriptionModel>>() {
            @Override
            public void onResponse(Call<PageResponse<SubscriptionModel>> call, Response<PageResponse<SubscriptionModel>> response) {
                if(response.isSuccessful() && response.body().getItems() != null){
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(Constants.NETWORK_ERROR);
                }
            }

            @Override
            public void onFailure(Call<PageResponse<SubscriptionModel>> call, Throwable t) {
                callback.onFailure(Constants.SERVER_ERROR);
            }
        });
    }

    @Override
    public void createPlan(SubscriptionModel model, final SubscriptionContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.createPlan(model).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful() && response.body().getData() != null){
                    callback.onSuccessCreatePlan(response.body());
                } else {
                    callback.onFailure(Constants.SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                callback.onFailure(Constants.SERVER_ERROR);
            }
        });
    }

    @Override
    public void getAcivePlan(final SubscriptionContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.getActivePlan().enqueue(new Callback<DataResponse<ActivePlanModel>>() {
            @Override
            public void onResponse(Call<DataResponse<ActivePlanModel>> call, Response<DataResponse<ActivePlanModel>> response) {
                if(response.isSuccessful() && response.body().isSuccess && response.body().getData() != null){
                    callback.onSuccessActivePlan(response.body());
                } else {
                    callback.onFailure(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<DataResponse<ActivePlanModel>> call, Throwable t) {
                callback.onFailure(Constants.SERVER_ERROR);
            }
        });
    }
}
