package com.app.finlit.ui.authenticate.intractor_impl;

import com.app.finlit.data.Injector;
import com.app.finlit.data.InterfaceApi;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.params.ValidateParams;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.authenticate.contract.VerificationContract;
import com.app.finlit.utils.App;
import com.app.finlit.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationIntractorImpl implements VerificationContract.Intractor{

    private InterfaceApi api;

    public VerificationIntractorImpl(){

        api = Injector.provideApi();
    }


    @Override
    public void validatePin(ValidateParams params, final VerificationContract.OnCompleteListener callback) {

        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.validatePin(params).enqueue(new Callback<DataResponse<UserModel>>() {
            @Override
            public void onResponse(Call<DataResponse<UserModel>> call, Response<DataResponse<UserModel>> response) {
                if(response.isSuccessful() && response.body().getData() != null){
                    callback.onSuccessVerification(response.body());
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
    public void resendCode(UserModel userModel, final VerificationContract.OnCompleteListener callback) {

        api.resendCode(userModel).enqueue(new Callback<DataResponse<UserModel>>() {
            @Override
            public void onResponse(Call<DataResponse<UserModel>> call, Response<DataResponse<UserModel>> response) {
                if(response.isSuccessful() && response.body().getData() != null){
                    callback.onSuccessResendCode(response.body());
                } else {
                    callback.onFailure(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<DataResponse<UserModel>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}

