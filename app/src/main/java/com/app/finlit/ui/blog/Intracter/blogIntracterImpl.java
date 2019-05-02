package com.app.finlit.ui.blog.Intracter;

import android.util.Log;

import com.app.finlit.data.Injector;
import com.app.finlit.data.InterfaceApi;
import com.app.finlit.data.models.BlogModel;
import com.app.finlit.data.models.CommentModel;
import com.app.finlit.data.models.GetCommentsModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.blog.contract.BlogContract;
import com.app.finlit.utils.App;
import com.app.finlit.utils.Constants;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class blogIntracterImpl implements BlogContract.Intracter {

    private InterfaceApi api;

    public blogIntracterImpl() {
        api = Injector.provideApi();
    }


    @Override
    public void getBlogDetail(final BlogContract.OnCompleteListener callback) {

        if (!App.hasNetwork()) {
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }
        api.getBlogs().enqueue(new Callback<PageResponse<BlogModel>>() {
            @Override
            public void onResponse(Call<PageResponse<BlogModel>> call, Response<PageResponse<BlogModel>> response) {
                if (response.isSuccessful() && response.body().getItems() != null) {
                    callback.onSuccessGetDetail(response.body());
                } else {
                    callback.onFailure(Constants.SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<PageResponse<BlogModel>> call, Throwable t) {

                callback.onFailure(Constants.SERVER_ERROR);

            }
        });

    }

    @Override
    public void getComments(Map<String, String> map,final BlogContract.OnCompleteListener callback) {
        if (!App.hasNetwork()) {
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.getComments(map).enqueue(new Callback<PageResponse<GetCommentsModel>>() {
            @Override
            public void onResponse(Call<PageResponse<GetCommentsModel>> call, Response<PageResponse<GetCommentsModel>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessGetComments(response.body());
                } else {
                    callback.onFailure(Constants.SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<PageResponse<GetCommentsModel>> call, Throwable t) {
                callback.onFailure(Constants.SERVER_ERROR);

            }
        });

    }

    @Override
    public void createLike(String id,  final BlogContract.OnCompleteListener callback) {

        if (!App.hasNetwork()) {
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        Log.e("testss","like");
        api.createLike(id).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {

                if (response.isSuccessful()) {
                    callback.onSuccessLike(response.body());
                } else {
                    callback.onFailure(Constants.SERVER_ERROR);
                }

            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {

                callback.onFailure(Constants.SERVER_ERROR);

            }
        });

    }

    @Override
    public void createDisLike(String id, final BlogContract.OnCompleteListener callback) {

        if (!App.hasNetwork()) {
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.createDislike(id).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessDislike(response.body());
                } else {
                    callback.onFailure(Constants.SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                callback.onFailure(Constants.SERVER_ERROR);


            }
        });

    }

    @Override
    public void createComment(String id, GetCommentsModel model, final BlogContract.OnCompleteListener callback) {
        if (!App.hasNetwork()) {
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.createComment(model).enqueue(new Callback<DataResponse<GetCommentsModel>>() {
            @Override
            public void onResponse(Call<DataResponse<GetCommentsModel>> call, Response<DataResponse<GetCommentsModel>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessComment(response.body());
                } else {
                    callback.onFailure(Constants.SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<DataResponse<GetCommentsModel>> call, Throwable t) {
                callback.onFailure(Constants.SERVER_ERROR);

            }
        });
    }

}

