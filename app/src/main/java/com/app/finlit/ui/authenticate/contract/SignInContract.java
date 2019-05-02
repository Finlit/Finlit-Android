package com.app.finlit.ui.authenticate.contract;

import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.base.BasePresenter;

public interface SignInContract {

    interface View {

        void onSuccessSignIn(DataResponse<UserModel> response);

        void onFailure(String error);
    }

    interface Presentor extends BasePresenter {

        void signIn(UserModel user);
    }

    interface Intractor {

        void signIn(UserModel user, OnCompleteListener callback);
    }

    interface OnCompleteListener{

        void onSuccessSignIn(DataResponse<UserModel> response);

        void onFailure(String error);
    }
}
