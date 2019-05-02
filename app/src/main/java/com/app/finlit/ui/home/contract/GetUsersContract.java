package com.app.finlit.ui.home.contract;

import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.base.BasePresenter;

import java.util.Map;

public interface GetUsersContract {

    interface View {

        void onSuccessGetUsers(PageResponse<UserModel> response);
        void onSuccessGetFilter(PageResponse<UserModel> response);

        void onSuccessUser(DataResponse<UserModel> response);

        void onFailure(String message);
    }

    interface Presentor extends BasePresenter {

        void getUsers(Map<String,String> queries);
        void getFilter(Map<String,String> queries);

        void getUser(String userId);
    }

    interface Intractor {

        void getUsers(Map<String,String> queries,OnCompleteListener callback);
        void getFilter(Map<String,String> queries,OnCompleteListener callback);

        void getUser(String userId,OnCompleteListener callback);
    }

    interface OnCompleteListener{

        void onSuccessGetUsers(PageResponse<UserModel> response);
        void onSuccessGetFilter(PageResponse<UserModel> response);
        void onSuccessUser(DataResponse<UserModel> response);

        void onFailure(String error);
    }

}
