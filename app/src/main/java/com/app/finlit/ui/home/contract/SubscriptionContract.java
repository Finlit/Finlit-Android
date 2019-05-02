package com.app.finlit.ui.home.contract;

import com.app.finlit.data.models.ActivePlanModel;
import com.app.finlit.data.models.SubscriptionModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;

/**
 * Created by MANISH-PC on 10/29/2018.
 */

public interface SubscriptionContract {

    interface View {

        void onSuccess(PageResponse<SubscriptionModel> response);

        void onSuccessCreatePlan(DataResponse response);

        void onSuccessActivePlan(DataResponse<ActivePlanModel> response);

        void onFailure(String message);
    }

    interface Presenter {

        void getSubscriptionDetail();

        void createPlan(SubscriptionModel model);

        void getAcivePlan();
    }

    interface Intractor {

        void getSubscriptionDetail(OnCompleteListener callback);

        void createPlan(SubscriptionModel model, OnCompleteListener callback);

        void getAcivePlan(OnCompleteListener callback);
    }

    interface OnCompleteListener{

        void onSuccess(PageResponse<SubscriptionModel> response);

        void onSuccessCreatePlan(DataResponse response);

        void onSuccessActivePlan(DataResponse<ActivePlanModel> response);

        void onFailure(String message);
    }
}
