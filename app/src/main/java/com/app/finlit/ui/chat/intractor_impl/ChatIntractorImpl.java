package com.app.finlit.ui.chat.intractor_impl;

import com.app.finlit.data.Injector;
import com.app.finlit.data.InterfaceApi;
import com.app.finlit.data.models.ChatModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.chat.contract.ChatContract;
import com.app.finlit.utils.App;
import com.app.finlit.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MANISH-PC on 10/18/2018.
 */

public class ChatIntractorImpl implements ChatContract.Intractor {

    private InterfaceApi api;

    public ChatIntractorImpl(){
        api = Injector.provideApi();
    }

    @Override
    public void getChats(int pageSize, int pageNo, final ChatContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.getChats(pageSize, pageNo).enqueue(new Callback<PageResponse<ChatModel>>() {
            @Override
            public void onResponse(Call<PageResponse<ChatModel>> call, Response<PageResponse<ChatModel>> response) {
                if(response.isSuccessful() && response.body().getItems()!=null){
                    callback.onSuccessChats(response.body());
                } else {
                    callback.onFailure(response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<PageResponse<ChatModel>> call, Throwable t) {
                callback.onFailure(Constants.SERVER_ERROR);
            }
        });
    }

    @Override
    public void createChat(ChatModel model, final ChatContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.createChat(model).enqueue(new Callback<DataResponse<ChatModel>>() {
            @Override
            public void onResponse(Call<DataResponse<ChatModel>> call, Response<DataResponse<ChatModel>> response) {
                if(response.isSuccessful() && response.body().getData()!=null){
                    callback.onSuccessCreateChat(response.body());
                } else {
                    callback.onFailure(response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<DataResponse<ChatModel>> call, Throwable t) {
                callback.onFailure(Constants.SERVER_ERROR);
            }
        });
    }

    @Override
    public void setZeroUnreadCount(String chatId, final ChatContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.setZeroUnreadCount(chatId).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful() && response.body().isSuccess){
                    callback.onSuccessSetZeroCount(response.body());
                } else {
                    callback.onFailure(response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                callback.onFailure(Constants.SERVER_ERROR);
            }
        });
    }

    @Override
    public void increaseUnreadCount(String chatId, ChatModel model, final ChatContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.increaseUnreadCount(chatId, model).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful() && response.body().isSuccess){
                    callback.onSuccessIncreUnreadCount(response.body());
                } else {
                    callback.onFailure(response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                callback.onFailure(Constants.SERVER_ERROR);
            }
        });
    }

    @Override
    public void deleteChat(String chatId, final ChatContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.deleteChat(chatId).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful() && response.body().isSuccess){
                    callback.onSuccessDelete(response.body());
                } else {
                    callback.onFailure(response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                callback.onFailure(Constants.SERVER_ERROR);
            }
        });
    }

    @Override
    public void blockUser(String chatId, final ChatContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.blockUser(chatId).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful() && response.body().isSuccess){
                    callback.onSuccessBlock(response.body());
                } else {
                    callback.onFailure(response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                callback.onFailure(Constants.SERVER_ERROR);
            }
        });
    }

    @Override
    public void unBlockUser(String chatId, final ChatContract.OnCompleteListener callback) {
        if(!App.hasNetwork()){
            callback.onFailure(Constants.NETWORK_ERROR);
            return;
        }

        api.unBlockUser(chatId).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful() && response.body().isSuccess){
                    callback.onSuccessUnBlock(response.body());
                } else {
                    callback.onFailure(response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                callback.onFailure(Constants.SERVER_ERROR);
            }
        });
    }
}
