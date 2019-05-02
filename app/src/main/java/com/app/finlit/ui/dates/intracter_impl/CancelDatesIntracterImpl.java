package com.app.finlit.ui.dates.intracter_impl;

import com.app.finlit.data.Injector;
import com.app.finlit.data.InterfaceApi;
import com.app.finlit.data.models.CancelDatesModel;
import com.app.finlit.data.models.DateStatusModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.dates.contract.CancelDatesContract;
import com.app.finlit.ui.dates.contract.DatesContract;
import com.app.finlit.utils.Constants;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelDatesIntracterImpl implements CancelDatesContract.Intractor {

    private InterfaceApi api;

    public CancelDatesIntracterImpl() {
        api = Injector.provideApi();
    }


    @Override
    public void getCancelDates(String Id, final CancelDatesContract.OnCompleteListener callback) {
        api.getCancelDates(Id).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessCancelDates(response.body());
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
    public void getEditDates(String Id, final CancelDatesContract.OnCompleteListener callback) {
        api.geteditDates(Id).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessEditDates(response.body());
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
