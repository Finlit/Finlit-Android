package com.app.finlit.ui.settings;

import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;


/**
 * Created by MANISH-PC on 8/28/2018.
 */

public interface BlockContract {

    interface View  {

        void onSuccessUsers(PageResponse<UserModel> response);

        void onSuccessBlock(DataResponse response);

        void onSuccessUnblock(DataResponse response);

        void onFailure(String error);
    }

    interface Presenter {

        void getBlockedUsers();

        void blockUser(UserModel model);

        void unblockUser(UserModel model);
    }

    interface Intractor {

        void getBlockUsers(OnCompleteListener onCompleteListener);

        void blockUser(UserModel model, OnCompleteListener callback);

        void unblockUser(UserModel model, OnCompleteListener callback);
    }

    interface OnCompleteListener{

        void onSuccessUsers(PageResponse<UserModel> response);

        void onSuccessBlock(DataResponse response);

        void onSuccessUnblock(DataResponse response);

        void onFailure(String message);
    }
}
