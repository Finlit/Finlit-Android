package com.app.finlit.ui.home.intractor_impl;

import com.app.finlit.data.Injector;
import com.app.finlit.data.InterfaceApi;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.home.contract.GetUsersContract;
import com.app.finlit.utils.App;
import com.app.finlit.utils.Constants;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUsersIntracatorImpl implements GetUsersContract.Intractor{

    private InterfaceApi api;

    public GetUsersIntracatorImpl(){
        api = Injector.provideApi();
    }

    @Override
    public void getUsers(Map<String,String> queries, final GetUsersContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.getUsers(queries).enqueue(new Callback<PageResponse<UserModel>>() {
            @Override
            public void onResponse(Call<PageResponse<UserModel>> call, Response<PageResponse<UserModel>> response) {

                if(response.isSuccessful() && response.body().getItems() != null){
                    callback.onSuccessGetUsers(response.body());
                } else {
                    callback.onFailure(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<PageResponse<UserModel>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getFilter(Map<String, String> queries, final GetUsersContract.OnCompleteListener callback) {
        api.getDateFilter(queries).enqueue(new Callback<PageResponse<UserModel>>() {
            @Override
            public void onResponse(Call<PageResponse<UserModel>> call, Response<PageResponse<UserModel>> response) {
                if(response.isSuccessful() && response.body().getItems() != null){
                    callback.onSuccessGetFilter(response.body());
                } else {
                    callback.onFailure(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<PageResponse<UserModel>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getUser(String userId, final GetUsersContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.getUserById(userId).enqueue(new Callback<DataResponse<UserModel>>() {
            @Override
            public void onResponse(Call<DataResponse<UserModel>> call, Response<DataResponse<UserModel>> response) {
                if(response.isSuccessful() && response.body().getData() != null){
                    callback.onSuccessUser(response.body());
                } else {
                    callback.onFailure(Constants.SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<DataResponse<UserModel>> call, Throwable t) {
                callback.onFailure(Constants.SERVER_ERROR);
            }
        });
    }
}

