package com.app.finlit.ui.authenticate.presentor_impl;



import com.app.finlit.data.params.ImageModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.authenticate.contract.ImageContract;
import com.app.finlit.ui.authenticate.intractor_impl.ImageIntractorImpl;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

/**
 * Created by MANISH-PC on 7/5/2018.
 */

public class ImagePresenterImpl implements ImageContract.Presenter, ImageContract.OnCompleteListener {

    private ImageContract.View view;
    private ImageContract.Intractor intractor;

    public ImagePresenterImpl(ImageContract.View view) {
        this.view = view;
        intractor = new ImageIntractorImpl();
    }


    @Override
    public void uploadImage(String id, String imagePath) {
        intractor.uploadImage(id, imagePath, this);
    }



    @Override
    public void onSuccess(DataResponse<ImageModel> response) {
        if (response.getData()!=null && response.getData().getImgUrl()!=null) {
            SharedPreferenceHelper.getInstance().saveProfilePic(response.getData().getImgUrl());
            DiskCacheUtils.removeFromCache(response.getData().getImgUrl(), ImageLoader.getInstance().getDiskCache());
            MemoryCacheUtils.removeFromCache(response.getData().getImgUrl(), ImageLoader.getInstance().getMemoryCache());
        }
        view.onSuccess(response);
    }


    @Override
    public void onError(String message) {
            view.onFailure(message);
    }

}
