package com.app.finlit.ui.authenticate.presentor_impl;

import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.authenticate.contract.SignUpContract;
import com.app.finlit.ui.authenticate.intractor_impl.SignUpIntractorImpl;
import com.app.finlit.utils.SharedPreferenceHelper;

public class SignUpPresentorImpl implements SignUpContract.Presentor, SignUpContract.OnCompleteListener{

    private SignUpContract.Intractor intractor;
    private SignUpContract.View view;
    private SharedPreferenceHelper preferenceHelper;

    public SignUpPresentorImpl(SignUpContract.View view){
        this.view = view;
        intractor = new SignUpIntractorImpl();
        preferenceHelper = SharedPreferenceHelper.getInstance();
    }

    @Override
    public void signUp(UserModel user) {
        String deviceId = preferenceHelper.getFCMToken();

        user.setDeviceId(deviceId == null ? "token" : deviceId);
        user.setDeviceType("android");
        intractor.signUp(user, this);
    }

    @Override
    public void forgetPassword(UserModel model) {
        intractor.forgetPassword(model, this);
    }

    @Override
    public void onSuccessSignUp(DataResponse<UserModel> response) {

        UserModel model = response.getData();
        if(model.getToken()!=null){
            preferenceHelper.saveUserToken(model.getToken());
        }
        if(model.getId()!=null){
            preferenceHelper.saveUserId(model.getId());
        }
        if(model.getEmail()!=null){
            preferenceHelper.saveEmail(model.getEmail());
        }
        if(model.getName()!=null){
            preferenceHelper.saveName(model.getName());
        }
        if (model.getUsername()!=null)
            preferenceHelper.saveUsername(model.getUsername());
        if(model.getImgUrl()!=null){
            preferenceHelper.saveProfilePic(model.getImgUrl());
        }
        if(model.getFacebookId()!=null){
            preferenceHelper.saveFacebookUserId(model.getFacebookId());
        }
        view.onSuccessSignUp(response);
    }

    @Override
    public void onSuccessForget(DataResponse<UserModel> response) {
        UserModel model = response.getData();
        if (model.getToken()!=null)
            preferenceHelper.saveUserToken(model.getToken());

        if (model.getUsername()!=null)
            preferenceHelper.saveUsername(model.getUsername());

        preferenceHelper.saveUserId(model.getId());
        view.onSuccessForget(response);
    }

    @Override
    public void onFailure(String error) {
        view.onFailure(error);
    }

}


