package com.app.finlit.ui.chat.contract;

import com.app.finlit.data.models.ChatModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;

/**
 * Created by MANISH-PC on 10/18/2018.
 */

public interface ChatContract {

    interface View {

        void onSuccessChats(PageResponse<ChatModel> response);

        void onSuccessCreateChat(DataResponse<ChatModel> response);

        void onSuccessSetZeroCount(DataResponse response);

        void onSuccessIncreUnreadCount(DataResponse response);

        void onSuccessDelete(DataResponse response);

        void onSuccessBlock(DataResponse response);

        void onSuccessUnBlock(DataResponse response);

        void onFailure(String message);
    }


    interface Presenter {

        void getChats(int pageNo);

        void createChat(ChatModel model);

        void setZeroUnreadCount(String chatId);

        void increaseUnreadCount(String chatId, ChatModel model);

        void deleteChat(String chatId);

        void blockUser(String chatId);

        void unBlockUser(String chatId);
    }

    interface Intractor {

        void getChats(int pageSize, int pageNo, OnCompleteListener callback);

        void createChat(ChatModel model, OnCompleteListener callback);

        void setZeroUnreadCount(String chatId, OnCompleteListener callback);

        void increaseUnreadCount(String chatId, ChatModel model, OnCompleteListener callback);

        void deleteChat(String chatId,  OnCompleteListener callback);

        void blockUser(String chatId,  OnCompleteListener callback);

        void unBlockUser(String chatId,  OnCompleteListener callback);
    }

    interface OnCompleteListener{

        void onSuccessChats(PageResponse<ChatModel> response);

        void onSuccessCreateChat(DataResponse<ChatModel> response);

        void onSuccessSetZeroCount(DataResponse response);

        void onSuccessIncreUnreadCount(DataResponse response);

        void onSuccessDelete(DataResponse response);

        void onSuccessBlock(DataResponse response);

        void onSuccessUnBlock(DataResponse response);

        void onFailure(String message);
    }
}
