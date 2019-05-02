package com.app.finlit.ui.authenticate.intractor_impl;

import com.app.finlit.data.Injector;
import com.app.finlit.data.InterfaceApi;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.authenticate.contract.UserApiContract;
import com.app.finlit.utils.App;
import com.app.finlit.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserApiIntractorImpl implements UserApiContract.Intractor {

    private InterfaceApi api;

    public UserApiIntractorImpl(){
        api = Injector.provideApi();
    }

    @Override
    public void addProfile(String id, UserModel model, final UserApiContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.updateProfile(id, model).enqueue(new Callback<DataResponse<UserModel>>() {
            @Override
            public void onResponse(Call<DataResponse<UserModel>> call, Response<DataResponse<UserModel>> response) {
                if(response.isSuccessful() && response.body().getData() != null){
                    callback.onSuccessAddProfile(response.body());
                } else {
                    callback.onFailure(response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<DataResponse<UserModel>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });

    }

    @Override
    public void updatePassword(String id, UserModel model, final UserApiContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.updateProfile(id, model).enqueue(new Callback<DataResponse<UserModel>>() {
            @Override
            public void onResponse(Call<DataResponse<UserModel>> call, Response<DataResponse<UserModel>> response) {
                if(response.isSuccessful() && response.body().getData() != null){
                    callback.onSuccessUpdatePassword(response.body());
                }
                else if(response.isSuccessful() && !response.body().isSuccess){
                    callback.onSuccessUpdatePassword(response.body());
                } else {
                    callback.onFailure(Constants.SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<DataResponse<UserModel>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}


