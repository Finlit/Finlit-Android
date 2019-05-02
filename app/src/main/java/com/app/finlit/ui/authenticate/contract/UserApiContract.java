package com.app.finlit.ui.authenticate.contract;

import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.base.BasePresenter;

public interface UserApiContract {

    interface View  {

        void onSuccessAddProfile(DataResponse<UserModel> response);

        void onSuccessUpdatePassword(DataResponse<UserModel> response);

        void onFailure(String error);
    }

    interface Presentor extends BasePresenter {

        void addProfile(UserModel userModel);

        void updatePassword(UserModel model);
    }

    interface Intractor {

        void addProfile(String id, UserModel model, OnCompleteListener callback);

        void updatePassword(String id, UserModel model, OnCompleteListener callback);

    }

    interface OnCompleteListener{

        void onSuccessAddProfile(DataResponse<UserModel> response);

        void onSuccessUpdatePassword(DataResponse<UserModel> response);

        void onFailure(String error);
    }

}
