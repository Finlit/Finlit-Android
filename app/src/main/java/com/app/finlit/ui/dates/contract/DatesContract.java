package com.app.finlit.ui.dates.contract;


import com.app.finlit.data.models.DateStatusModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;

import java.util.Map;

public interface DatesContract {
    interface View {

        void onSuccessDates(PageResponse<DateStatusModel> response);
        void onSuccessIntrest(DataResponse response);
        void onSuccessSendDatesRequest(DataResponse response);
        void onSuccessConfirm(DataResponse response);
        void onFailure(String error);


    }

    interface Presenter{
        void getDates(Map<String, String> queries);
        void getIntrest(String id);
        void SendDatesRequest(String id,DateStatusModel model);
        void  getConfirm(String id);


    }

    interface Intractor{

        void getDates(Map<String, String> queries, OnCompleteListener callback);
        void getIntrest(String id,OnCompleteListener callback);
        void getConfirm(String id,OnCompleteListener callback);
        void SendDatesRequest(String id,DateStatusModel model,OnCompleteListener callback);

    }
    interface OnCompleteListener{
        void onSuccessDates(PageResponse<DateStatusModel> response);
        void onSuccessConfirm(DataResponse response);
        void onSuccessSendDatesRequest(DataResponse response);
        void onSuccessIntrest(DataResponse response);
        void onFailure(String error);
    }



}
