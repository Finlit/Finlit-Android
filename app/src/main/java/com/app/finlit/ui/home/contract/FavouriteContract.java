package com.app.finlit.ui.home.contract;

import com.app.finlit.data.models.StatusModel;
import com.app.finlit.data.shared.DataResponse;

/**
 * Created by MANISH-PC on 10/17/2018.
 */

public interface FavouriteContract {

    interface View {

        void onSuccess(DataResponse response);

        void onSuccessFindmeDate(DataResponse response);


        void onSuccessUnFavourite(DataResponse response);

        void onFailure(String message);
    }

    interface Presenter {

        void createFavourite(String userId);
        void createFindMeDate( String userId,StatusModel statusModel);
        void createUnFavourite(String userId);
    }

    interface Intractor {

        void createFavourite(String userId, OnCompleteListener callback);
        void createFindMeDate(String userId,StatusModel statusModel, OnCompleteListener callback);
        void createUnFavourite(String userId, OnCompleteListener callback);
    }

    interface OnCompleteListener{

        void onSuccess(DataResponse response);
        void onSuccessFindmeDate(DataResponse response);
        void onSuccessUnFavourite(DataResponse response);

        void onFailure(String message);
    }
}
