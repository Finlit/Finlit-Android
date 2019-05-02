package com.app.finlit.ui.notification.contract;

import com.app.finlit.data.models.NotificationModel;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.base.BasePresenter;

public interface GetNotificationsContract {

    interface View {

        void onSuccessGetNotifications(PageResponse<NotificationModel> response);

        void onFailure(String error);
    }

    interface Presentor  {

        void getNotifications(int pageNo);
    }

    interface Intractor {

        void getNotifications(int pageNo,int pageSize,OnCompleteListener callback);
    }

    interface OnCompleteListener{

        void onSuccessGetNotifications(PageResponse<NotificationModel> response);

        void onFailure(String error);
    }
}
