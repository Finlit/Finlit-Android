package com.app.finlit.ui.authenticate.contract;

import com.app.finlit.data.models.QuestionsModel;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.authenticate.QuestionsActivity;
import com.app.finlit.ui.base.BasePresenter;

import java.util.List;

public interface GetQuestionsContract {

    interface View  {

        void onSuccessGetQuestions(List<QuestionsModel> response);

        void onFailure(String error);
    }

    interface Presentor extends BasePresenter {

        void getQuestions(int questionType);
    }

    interface Intractor {

        void getQuestions(int questionType, OnCompleteListener callback);

    }

    interface OnCompleteListener{

        void onSuccessGetQuestions(PageResponse<QuestionsModel> response);

        void onFailure(String error);
    }

}
