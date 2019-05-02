package com.app.finlit.ui.authenticate.presentor_impl;

import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.authenticate.contract.SignInContract;
import com.app.finlit.ui.authenticate.intractor_impl.SignInIntractorImpl;
import com.app.finlit.utils.SharedPreferenceHelper;

public class SignInPresentorImpl  implements SignInContract.Presentor, SignInContract.OnCompleteListener{

    private SignInContract.Intractor intractor;
    private SignInContract.View view;
    private SharedPreferenceHelper preferenceHelper;

    public SignInPresentorImpl(SignInContract.View view){
        this.view = view;
        intractor = new SignInIntractorImpl();
        preferenceHelper = SharedPreferenceHelper.getInstance();
    }

    @Override
    public void signIn(UserModel user) {
        user.setDeviceId(preferenceHelper.getFCMToken());
        user.setDeviceType("android");
        intractor.signIn(user, this);
    }

    @Override
    public void onSuccessSignIn(DataResponse<UserModel> response) {

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
        if(model.getImgUrl()!=null){
            preferenceHelper.saveProfilePic(model.getImgUrl());
        }
        if(model.getFacebookId()!=null){
            preferenceHelper.saveFacebookUserId(model.getFacebookId());
        }

        if (model.getLocation()!=null ){
            preferenceHelper.saveLocation(model.getLocation().getAddress());
        }
        preferenceHelper.saveAge(String.valueOf(model.getAgeGroup()));

        if (model.getAboutUs()!=null)
            preferenceHelper.saveAbout(model.getAboutUs());
        if (model.getQuestion()!=null)
            preferenceHelper.saveQuestion(model.getQuestion());

        if (model.getProfileType()!=null)
            preferenceHelper.saveResult(model.getProfileType());

        if (model.getCreatedAt()!=null)
            preferenceHelper.saveCreatedAt(model.getCreatedAt());

        if (model.getInterest()!=null)
            preferenceHelper.saveInterests(model.getInterest());

        preferenceHelper.saveGender(model.getGender());
        view.onSuccessSignIn(response);
    }

    @Override
    public void onFailure(String error) {
        view.onFailure(error);
    }

}

