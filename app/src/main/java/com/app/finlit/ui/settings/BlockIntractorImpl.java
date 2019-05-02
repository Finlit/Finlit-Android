package com.app.finlit.ui.settings;

import com.app.finlit.data.Injector;
import com.app.finlit.data.InterfaceApi;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.utils.App;
import com.app.finlit.utils.Constants;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MANISH-PC on 8/28/2018.
 */

public class BlockIntractorImpl implements BlockContract.Intractor {
    private InterfaceApi api;

    public BlockIntractorImpl(){
        api = Injector.provideApi();
    }

    @Override
    public void getBlockUsers(final BlockContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.getBlockedUsers().enqueue(new Callback<PageResponse<UserModel>>() {
            @Override
            public void onResponse(Call<PageResponse<UserModel>> call, Response<PageResponse<UserModel>> response) {
                if(response.isSuccessful() && response.body().getItems()!=null){
                    callback.onSuccessUsers(response.body());
                } else {
                    callback.onFailure(Constants.SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<PageResponse<UserModel>> call, Throwable t) {
                callback.onFailure(Constants.SERVER_ERROR);
            }
        });
    }

    @Override
    public void blockUser(UserModel model, final BlockContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.blockUser(model).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful()){
                    callback.onSuccessBlock(response.body());
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
    public void unblockUser(UserModel model, final BlockContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.unBlockUser(model).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful()){
                    callback.onSuccessUnblock(response.body());
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
