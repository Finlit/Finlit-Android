package com.app.finlit.ui.authenticate.presentor_impl;

import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.params.ValidateParams;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.authenticate.contract.VerificationContract;
import com.app.finlit.ui.authenticate.intractor_impl.VerificationIntractorImpl;
import com.app.finlit.utils.SharedPreferenceHelper;

public class VerificationPresentorImpl implements VerificationContract.Presentor, VerificationContract.OnCompleteListener{

    private VerificationContract.Intractor intractor;
    private VerificationContract.View view;
    private SharedPreferenceHelper preferenceHelper;

    public VerificationPresentorImpl(VerificationContract.View view){
        this.view = view;
        intractor = new VerificationIntractorImpl();
        this.preferenceHelper = SharedPreferenceHelper.getInstance();
    }


    @Override
    public void validatePin(ValidateParams params) {
        String userId = SharedPreferenceHelper.getInstance().getUserId();
        params.setUserId(userId);
        intractor.validatePin(params, this);
    }

    @Override
    public void resendCode(UserModel userModel) {
        intractor.resendCode(userModel,this);
    }


    @Override
    public void onSuccessVerification(DataResponse<UserModel> response) {

        UserModel model = response.getData();
        preferenceHelper.saveUserToken(model.getToken());
        preferenceHelper.saveUserId(model.getId());
        if (model.getUsername()!=null)
            preferenceHelper.saveUsername(model.getUsername());

        view.onSuccessVerification(model);
    }

    @Override
    public void onSuccessResendCode(DataResponse<UserModel> response) {
        UserModel model = response.getData();
        view.onSuccessResendCode(model);
    }

    @Override
    public void onFailure(String error) {
        view.onFailure(error);
    }
}
