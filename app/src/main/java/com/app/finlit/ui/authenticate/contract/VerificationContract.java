package com.app.finlit.ui.authenticate.contract;

import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.params.ValidateParams;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.base.BasePresenter;

public interface VerificationContract {

    interface View  {

        void onSuccessVerification(UserModel model);

        void onSuccessResendCode(UserModel model);

        void onFailure(String message);
    }

    interface Presentor extends BasePresenter {

        void validatePin(ValidateParams params);

        void resendCode(UserModel userModel);
    }

    interface Intractor {

        void validatePin(ValidateParams params, OnCompleteListener callback);

        void resendCode(UserModel userModel, OnCompleteListener callback);
    }

    interface OnCompleteListener{

        void onSuccessVerification(DataResponse<UserModel> response);

        void onSuccessResendCode(DataResponse<UserModel> response);

        void onFailure(String error);
    }

}
