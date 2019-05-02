package com.app.finlit.ui.authenticate.intractor_impl;

import com.app.finlit.data.Injector;
import com.app.finlit.data.InterfaceApi;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.authenticate.contract.SignUpContract;
import com.app.finlit.utils.App;
import com.app.finlit.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpIntractorImpl implements SignUpContract.Intractor{

    private InterfaceApi api;

    public SignUpIntractorImpl(){
        api = Injector.provideApi();
    }

    @Override
    public void signUp(UserModel user, final SignUpContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.signUp(user).enqueue(new Callback<DataResponse<UserModel>>() {
            @Override
            public void onResponse(Call<DataResponse<UserModel>> call, Response<DataResponse<UserModel>> response) {

                if(response.isSuccessful() && response.body().isSuccess){
                    callback.onSuccessSignUp(response.body());
                } else {
                    callback.onFailure(response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<DataResponse<UserModel>> call, Throwable t) {
                callback.onFailure(Constants.SERVER_ERROR);
            }
        });
    }

    @Override
    public void forgetPassword(UserModel model, final SignUpContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.forgetPassword(model).enqueue(new Callback<DataResponse<UserModel>>() {
            @Override
            public void onResponse(Call<DataResponse<UserModel>> call, Response<DataResponse<UserModel>> response) {
                if(response.isSuccessful() && response.body().isSuccess){
                    callback.onSuccessForget(response.body());
                } else {
                    callback.onFailure(response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<DataResponse<UserModel>> call, Throwable t) {
                callback.onFailure(Constants.SERVER_ERROR);
            }
        });
    }
}

