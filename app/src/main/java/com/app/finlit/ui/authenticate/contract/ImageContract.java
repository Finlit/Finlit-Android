package com.app.finlit.ui.authenticate.contract;


import com.app.finlit.data.params.ImageModel;
import com.app.finlit.data.shared.DataResponse;

/**
 * Created by MANISH-PC on 7/5/2018.
 */

public interface ImageContract {

    interface View {
        void onSuccess(DataResponse<ImageModel> response);

        void onFailure(String error);
    }


    interface Presenter {
        void uploadImage(String id, String imagePath);
    }

    interface Intractor {

        void uploadImage(String id, String imagePath, OnCompleteListener callback);
    }

    interface OnCompleteListener{
        void onSuccess(DataResponse<ImageModel> response);

        void onError(String message);
    }
}
