package com.app.finlit.ui.chat.presenter_impl;

import com.app.finlit.data.models.ChatModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.chat.contract.ChatContract;
import com.app.finlit.ui.chat.intractor_impl.ChatIntractorImpl;
import com.app.finlit.utils.Constants;

/**
 * Created by MANISH-PC on 10/18/2018.
 */

public class ChatPresenterImpl implements ChatContract.Presenter, ChatContract.OnCompleteListener {

    private ChatContract.Intractor intractor;
    private ChatContract.View view;

    public ChatPresenterImpl(ChatContract.View view) {
        this.view = view;
        intractor = new ChatIntractorImpl();
    }

    @Override
    public void getChats(int pageNo) {
        intractor.getChats(Constants.PER_PAGE, pageNo, this);
    }

    @Override
    public void createChat(ChatModel model) {
        intractor.createChat(model, this);
    }

    @Override
    public void setZeroUnreadCount(String chatId) {
        intractor.setZeroUnreadCount(chatId, this);
    }

    @Override
    public void increaseUnreadCount(String chatId, ChatModel model) {
        intractor.increaseUnreadCount(chatId, model, this);
    }

    @Override
    public void deleteChat(String chatId) {
        intractor.deleteChat(chatId, this);
    }

    @Override
    public void blockUser(String chatId) {
        intractor.blockUser(chatId, this);
    }

    @Override
    public void unBlockUser(String chatId) {
        intractor.unBlockUser(chatId, this);
    }

    @Override
    public void onSuccessChats(PageResponse<ChatModel> response) {
        view.onSuccessChats(response);
    }

    @Override
    public void onSuccessCreateChat(DataResponse<ChatModel> response) {
        view.onSuccessCreateChat(response);
    }

    @Override
    public void onSuccessSetZeroCount(DataResponse response) {
        view.onSuccessSetZeroCount(response);
    }

    @Override
    public void onSuccessIncreUnreadCount(DataResponse response) {
        view.onSuccessIncreUnreadCount(response);
    }

    @Override
    public void onSuccessDelete(DataResponse response) {
        view.onSuccessDelete(response);
    }

    @Override
    public void onSuccessBlock(DataResponse response) {
        view.onSuccessBlock(response);
    }

    @Override
    public void onSuccessUnBlock(DataResponse response) {
        view.onSuccessUnBlock(response);
    }

    @Override
    public void onFailure(String message) {
        view.onFailure(message);
    }
}
