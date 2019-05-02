package com.app.finlit.ui.authenticate.intractor_impl;

import com.app.finlit.data.Injector;
import com.app.finlit.data.InterfaceApi;
import com.app.finlit.data.models.QuestionsModel;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.authenticate.QuestionsActivity;
import com.app.finlit.ui.authenticate.contract.GetQuestionsContract;
import com.app.finlit.utils.App;
import com.app.finlit.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetQuestionsIntractorImpl implements GetQuestionsContract.Intractor {

    private InterfaceApi api;

    public GetQuestionsIntractorImpl(){
        api = Injector.provideApi();
    }

    @Override
    public void getQuestions(int questionType, final GetQuestionsContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.getQuestions(questionType).enqueue(new Callback<PageResponse<QuestionsModel>>() {
            @Override
            public void onResponse(Call<PageResponse<QuestionsModel>> call, Response<PageResponse<QuestionsModel>> response) {
                if(response.isSuccessful() && response.body().getItems() != null){
                    callback.onSuccessGetQuestions(response.body());
                } else {
                    callback.onFailure(response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<PageResponse<QuestionsModel>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });

    }
}
