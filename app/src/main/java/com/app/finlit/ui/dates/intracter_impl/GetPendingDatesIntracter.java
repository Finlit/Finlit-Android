package com.app.finlit.ui.dates.intracter_impl;

import com.app.finlit.data.Injector;
import com.app.finlit.data.InterfaceApi;
import com.app.finlit.data.models.BlogModel;
import com.app.finlit.data.models.DateStatusModel;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.dates.contract.GetPendingDatesContract;
import com.app.finlit.utils.App;
import com.app.finlit.utils.Constants;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPendingDatesIntracter implements GetPendingDatesContract.Intracter {

    private InterfaceApi api;

    public GetPendingDatesIntracter() {
        api = Injector.provideApi();
    }


    @Override
    public void getPendingDates(Map<String, String> queries, final GetPendingDatesContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.getPendingDates(queries).enqueue(new Callback<PageResponse<DateStatusModel>>() {
            @Override
            public void onResponse(Call<PageResponse<DateStatusModel>> call, Response<PageResponse<DateStatusModel>> response) {
                if (response.isSuccessful() && response.body().getItems() != null) {
                    callback.onSuccessGetPending(response.body());
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
}
