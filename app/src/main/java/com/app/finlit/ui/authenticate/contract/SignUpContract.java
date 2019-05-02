package com.app.finlit.ui.authenticate.contract;

import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.base.BasePresenter;

public interface SignUpContract {

    interface View {

        void onSuccessSignUp(DataResponse<UserModel> response);

        void onSuccessForget(DataResponse<UserModel> response);

        void onFailure(String error);
    }

    interface Presentor extends BasePresenter {

        void signUp(UserModel user);

        void forgetPassword(UserModel model);
    }

    interface Intractor {

        void signUp(UserModel user, OnCompleteListener callback);

        void forgetPassword(UserModel model, OnCompleteListener callback);
    }

    interface OnCompleteListener{

        void onSuccessSignUp(DataResponse<UserModel> response);

        void onSuccessForget(DataResponse<UserModel> response);

        void onFailure(String error);
    }
}
