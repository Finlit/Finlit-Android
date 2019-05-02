package com.app.finlit.ui.dates.contract;

import com.app.finlit.data.models.BlogModel;
import com.app.finlit.data.models.DateStatusModel;
import com.app.finlit.data.shared.PageResponse;

import java.util.Map;


public interface GetPendingDatesContract  {
    interface View{
        void onSuccessGetPending(PageResponse<DateStatusModel>response);
        void onFailure(String error);
    }

    interface Presenter{
        void getPendingDates(Map<String, String> queries);

    }

    interface Intracter{
        void getPendingDates(Map<String, String> queries,OnCompleteListener callback);

    }

    interface OnCompleteListener{
        void onSuccessGetPending(PageResponse<DateStatusModel> response);
        void onFailure(String error);
    }


}
