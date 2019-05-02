package com.app.finlit.ui.home.intractor_impl;

import com.app.finlit.data.Injector;
import com.app.finlit.data.InterfaceApi;
import com.app.finlit.data.models.StatusModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.home.contract.FavouriteContract;
import com.app.finlit.utils.App;
import com.app.finlit.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MANISH-PC on 10/17/2018.
 */

public class FavouriteIntractorImpl implements FavouriteContract.Intractor {

    private InterfaceApi api;

    public FavouriteIntractorImpl(){
        api = Injector.provideApi();
    }

    @Override
    public void createFavourite(String userId, final FavouriteContract.OnCompleteListener callback) {

        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.createFavourite(userId).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful()){
                    callback.onSuccess(response.body());
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
    public void createFindMeDate(String userId, StatusModel statusModel, final FavouriteContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }
        api.findMeDate(userId,statusModel).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessFindmeDate(response.body());
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
    public void createUnFavourite(String userId, final FavouriteContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.createUnFavourite(userId).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful()){
                    callback.onSuccessUnFavourite(response.body());
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
