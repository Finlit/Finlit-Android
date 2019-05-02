package com.app.finlit.ui.notification.intractor_impl;

import com.app.finlit.data.Injector;
import com.app.finlit.data.InterfaceApi;
import com.app.finlit.data.models.NotificationModel;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.notification.contract.GetNotificationsContract;
import com.app.finlit.utils.App;
import com.app.finlit.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetNotificationsIntractorImpl implements GetNotificationsContract.Intractor {

    private InterfaceApi api;

    public GetNotificationsIntractorImpl(){
        api = Injector.provideApi();
    }

    @Override
    public void getNotifications(int pageNo,int pageSize,final GetNotificationsContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.getNotifications(pageNo,pageSize).enqueue(new Callback<PageResponse<NotificationModel>>() {
            @Override
            public void onResponse(Call<PageResponse<NotificationModel>> call, Response<PageResponse<NotificationModel>> response) {
                if(response.isSuccessful() && response.body().getItems() != null){
                    callback.onSuccessGetNotifications(response.body());
                } else {
                    callback.onFailure(response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<PageResponse<NotificationModel>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });

    }
}
