package com.app.finlit.ui.dates.intracter_impl;

import com.app.finlit.data.Injector;
import com.app.finlit.data.InterfaceApi;
import com.app.finlit.data.models.DateStatusModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.dates.contract.DatesContract;
import com.app.finlit.utils.App;
import com.app.finlit.utils.Constants;

import org.checkerframework.checker.units.qual.C;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatesIntracterImpl implements DatesContract.Intractor {

    private InterfaceApi api;

    public DatesIntracterImpl() {
        api = Injector.provideApi();
    }

    @Override
    public void getDates(Map<String, String> queries, final DatesContract.OnCompleteListener callback) {

        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.getDates(queries).enqueue(new Callback<PageResponse<DateStatusModel>>() {
            @Override
            public void onResponse(Call<PageResponse<DateStatusModel>> call, Response<PageResponse<DateStatusModel>> response) {
                if (response.isSuccessful() && response.body().getItems() != null) {
                    callback.onSuccessDates(response.body());
                } else {
                    callback.onFailure(Constants.NETWORK_ERROR);
                }
            }

            @Override
            public void onFailure(Call<PageResponse<DateStatusModel>> call, Throwable t) {
                callback.onFailure(Constants.SERVER_ERROR);

            }
        });
    }

    @Override
    public void getIntrest(String id, final DatesContract.OnCompleteListener callback) {

        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }
        api.getintrest(id).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessIntrest(response.body());
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
    public void getConfirm(String id, final DatesContract.OnCompleteListener callback) {

        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }


        api.getconfirmDates(id).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful()){
                    callback.onSuccessConfirm(response.body());
                }
                else{
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
    public void SendDatesRequest(String id,DateStatusModel model,  final DatesContract.OnCompleteListener callback) {


        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.sendDates(id,model).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessSendDatesRequest(response.body());
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


}
