package com.app.finlit.ui.authenticate.presentor_impl;

import com.app.finlit.data.models.QuestionsModel;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.authenticate.contract.GetQuestionsContract;
import com.app.finlit.ui.authenticate.intractor_impl.GetQuestionsIntractorImpl;
import com.app.finlit.utils.SharedPreferenceHelper;

public class GetQuestionsPresentorImpl implements GetQuestionsContract.Presentor, GetQuestionsContract.OnCompleteListener {
    private GetQuestionsContract.Intractor intractor;
    private GetQuestionsContract.View view;
    private SharedPreferenceHelper preferenceHelper;

    public GetQuestionsPresentorImpl(GetQuestionsContract.View view){
        this.view = view;
        intractor = new GetQuestionsIntractorImpl();
        preferenceHelper = SharedPreferenceHelper.getInstance();
    }

    @Override
    public void getQuestions(int questionType) {
        String userId = SharedPreferenceHelper.getInstance().getUserId();
        intractor.getQuestions(questionType,  this);
    }

    @Override
    public void onSuccessGetQuestions(PageResponse<QuestionsModel> response) {
        view.onSuccessGetQuestions(response.getItems());
    }

    @Override
    public void onFailure(String error) {
        view.onFailure(error);
    }
}