package com.app.finlit.ui.authenticate.intractor_impl;

import com.app.finlit.data.Injector;
import com.app.finlit.data.InterfaceApi;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.authenticate.contract.SignInContract;
import com.app.finlit.utils.App;
import com.app.finlit.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInIntractorImpl implements SignInContract.Intractor{

    private InterfaceApi api;

    public SignInIntractorImpl(){
        api = Injector.provideApi();
    }

    @Override
    public void signIn(UserModel user, final SignInContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.signIn(user).enqueue(new Callback<DataResponse<UserModel>>() {
            @Override
            public void onResponse(Call<DataResponse<UserModel>> call, Response<DataResponse<UserModel>> response) {

                if(response.isSuccessful() && response.body().isSuccess){
                    callback.onSuccessSignIn(response.body());
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
}


