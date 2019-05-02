package com.app.finlit.ui.authenticate.presentor_impl;

import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.authenticate.contract.UserApiContract;
import com.app.finlit.ui.authenticate.intractor_impl.UserApiIntractorImpl;
import com.app.finlit.utils.SharedPreferenceHelper;

public class UserApiPresenterImpl implements UserApiContract.Presentor, UserApiContract.OnCompleteListener {
    private UserApiContract.Intractor intractor;
    private UserApiContract.View view;
    private SharedPreferenceHelper preferenceHelper;

    public UserApiPresenterImpl(UserApiContract.View view){
        this.view = view;
        intractor = new UserApiIntractorImpl();
        preferenceHelper = SharedPreferenceHelper.getInstance();
    }

    @Override
    public void addProfile(UserModel model) {
        String userId = SharedPreferenceHelper.getInstance().getUserId();
        intractor.addProfile(userId, model, this);
    }

    @Override
    public void updatePassword(UserModel model) {
        String userId = SharedPreferenceHelper.getInstance().getUserId();
        intractor.updatePassword(userId, model, this);
    }

    @Override
    public void onSuccessAddProfile(DataResponse<UserModel> response) {
        UserModel model = response.getData();
        preferenceHelper.saveUserId(model.getId());
        preferenceHelper.saveGender(model.getGender());
        preferenceHelper.saveName(model.getName());
        if (model.getEmail()!=null)
            preferenceHelper.saveEmail(model.getEmail());

        if(response.getData().getImgUrl()!=null){
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

        if (model.getCreatedAt()!=null)
            preferenceHelper.saveCreatedAt(model.getCreatedAt());
        if (model.getInterest()!=null)
            preferenceHelper.saveInterests(model.getInterest());

        view.onSuccessAddProfile(response);
    }

    @Override
    public void onSuccessUpdatePassword(DataResponse<UserModel> response) {
        view.onSuccessUpdatePassword(response);
    }

    @Override
    public void onFailure(String error) {
        view.onFailure(error);

    }
}

