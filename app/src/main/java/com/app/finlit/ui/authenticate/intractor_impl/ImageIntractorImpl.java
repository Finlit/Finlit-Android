package com.app.finlit.ui.authenticate.intractor_impl;




import com.app.finlit.data.Injector;
import com.app.finlit.data.InterfaceApi;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.params.ImageModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.authenticate.contract.ImageContract;
import com.app.finlit.utils.App;
import com.app.finlit.utils.Constants;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MANISH-PC on 7/5/2018.
 */

public class ImageIntractorImpl implements ImageContract.Intractor {

    private InterfaceApi api;
    public ImageIntractorImpl(){


        api = Injector.provideApi();
    }
    @Override
    public void uploadImage(String id ,String imagePath, final ImageContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onError(Constants.NETWORK_ERROR);
            return;
        }

        MultipartBody.Part imageFileBody = null;

        if (!imagePath.isEmpty() && !imagePath.equals("")) {

            File file = new File(imagePath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            imageFileBody = MultipartBody.Part.createFormData("media", file.getName(), requestBody);
        }

        api.uploadUserImage(id, imageFileBody).enqueue(new Callback<DataResponse<ImageModel>>() {
            @Override
            public void onResponse(Call<DataResponse<ImageModel>> call, Response<DataResponse<ImageModel>> response) {
                if(response.isSuccessful() && response.body().getData()!=null){
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(Constants.SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<DataResponse<ImageModel>> call, Throwable t) {
                callback.onError(Constants.SERVER_ERROR);
            }
        });


    }

}
