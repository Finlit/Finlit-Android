package com.app.finlit.ui.dates.contract;

import com.app.finlit.data.models.CancelDatesModel;
import com.app.finlit.data.models.ChatModel;
import com.app.finlit.data.models.DateStatusModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.chat.contract.ChatContract;

import java.util.Map;

public interface CancelDatesContract {

    interface View{
        void onSuccessCancelDates(DataResponse response);
        void onSuccessEditDates(DataResponse response);
        void onFailure(String error);

    }
    interface Presenter{
        void getCancelDates(String Id);
        void getEditDates(String Id);
    }

    interface Intractor{
        void getCancelDates(String Id, OnCompleteListener callback);
        void getEditDates(String Id, OnCompleteListener callback);
    }

    interface OnCompleteListener{
        void onSuccessCancelDates(DataResponse response);
        void onSuccessEditDates(DataResponse response);
        void onFailure(String error);

    }
}
