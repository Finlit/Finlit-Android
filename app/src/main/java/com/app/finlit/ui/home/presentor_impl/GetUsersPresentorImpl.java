package com.app.finlit.ui.home.presentor_impl;

import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.home.contract.GetUsersContract;
import com.app.finlit.ui.home.intractor_impl.GetUsersIntracatorImpl;
import com.app.finlit.utils.Constants;
import com.app.finlit.utils.SharedPreferenceHelper;

import java.util.Map;

public class GetUsersPresentorImpl implements GetUsersContract.Presentor, GetUsersContract.OnCompleteListener{

    private GetUsersContract.Intractor intractor;
    private GetUsersContract.View view;
    private SharedPreferenceHelper preferenceHelper;

    public GetUsersPresentorImpl(GetUsersContract.View view){
        this.view = view;
        intractor = new GetUsersIntracatorImpl();
        preferenceHelper = SharedPreferenceHelper.getInstance();
    }

    @Override
    public void getUsers(Map<String,String> queries) {
        queries.put("pageSize", String.valueOf(Constants.PER_PAGE));
        intractor.getUsers( queries,this);
    }

    @Override
    public void getFilter(Map<String, String> queries) {
        queries.put("pageSize", String.valueOf(Constants.PER_PAGE));
        intractor.getFilter( queries,this);

    }

    @Override
    public void getUser(String userId) {
        intractor.getUser(userId, this);
    }

    @Override
    public void onSuccessGetUsers(PageResponse<UserModel> response) {
        view.onSuccessGetUsers(response);
    }

    @Override
    public void onSuccessGetFilter(PageResponse<UserModel> response) {
        view.onSuccessGetFilter(response);

    }

    @Override
    public void onSuccessUser(DataResponse<UserModel> response) {
        view.onSuccessUser(response);
    }

    @Override
    public void onFailure(String error) {
        view.onFailure(error);
    }

}
