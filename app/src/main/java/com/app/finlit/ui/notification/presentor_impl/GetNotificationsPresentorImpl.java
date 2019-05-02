package com.app.finlit.ui.notification.presentor_impl;

import com.app.finlit.data.models.NotificationModel;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.notification.contract.GetNotificationsContract;
import com.app.finlit.ui.notification.intractor_impl.GetNotificationsIntractorImpl;
import com.app.finlit.utils.Constants;
import com.app.finlit.utils.SharedPreferenceHelper;

public class GetNotificationsPresentorImpl implements GetNotificationsContract.Presentor, GetNotificationsContract.OnCompleteListener {
    private GetNotificationsContract.Intractor intractor;
    private GetNotificationsContract.View view;
    private SharedPreferenceHelper preferenceHelper;

    public GetNotificationsPresentorImpl(GetNotificationsContract.View view){
        this.view = view;
        intractor = new GetNotificationsIntractorImpl();
        preferenceHelper = SharedPreferenceHelper.getInstance();
    }

    @Override
    public void getNotifications(int pageNo) {
        intractor.getNotifications(pageNo,Constants.PER_PAGE, this);
    }

    @Override
    public void onSuccessGetNotifications(PageResponse<NotificationModel> response) {
        view.onSuccessGetNotifications(response);
    }

    @Override
    public void onFailure(String error) {
        view.onFailure(error);
    }

}
