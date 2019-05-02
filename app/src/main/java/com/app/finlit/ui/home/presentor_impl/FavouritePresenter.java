package com.app.finlit.ui.home.presentor_impl;

import com.app.finlit.data.models.StatusModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.home.contract.FavouriteContract;
import com.app.finlit.ui.home.intractor_impl.FavouriteIntractorImpl;

/**
 * Created by MANISH-PC on 10/17/2018.
 */

public class FavouritePresenter implements FavouriteContract.Presenter, FavouriteContract.OnCompleteListener {

    private FavouriteContract.Intractor intractor;
    private FavouriteContract.View view;

    public FavouritePresenter(FavouriteContract.View view) {
        this.view = view;
        intractor = new FavouriteIntractorImpl();
    }

    @Override
    public void createFavourite(String userId) {
        intractor.createFavourite(userId, this);
    }

    @Override
    public void createFindMeDate(String userId, StatusModel statusModel) {
        intractor.createFindMeDate(userId,statusModel,this);
    }


    @Override
    public void createUnFavourite(String userId) {
        intractor.createUnFavourite(userId, this);
    }

    @Override
    public void onSuccess(DataResponse response) {
        view.onSuccess(response);
    }

    @Override
    public void onSuccessFindmeDate(DataResponse response) {
        view.onSuccessFindmeDate(response);
    }

    @Override
    public void onSuccessUnFavourite(DataResponse response) {
        view.onSuccess(response);
    }

    @Override
    public void onFailure(String message) {
        view.onFailure(message);
    }
}
