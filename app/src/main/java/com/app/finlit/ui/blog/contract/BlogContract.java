package com.app.finlit.ui.blog.contract;

import com.app.finlit.data.models.BlogModel;
import com.app.finlit.data.models.GetCommentsModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;

import java.util.Map;

public interface BlogContract {

    interface View {
        void onSuccessGetDetail(PageResponse<BlogModel> response);
        void onSuccessGetComments(PageResponse<GetCommentsModel> response);

        void onSuccessLike(BlogModel model);
        void onSuccessDislike(DataResponse response);
        void onSucessCreateComment(GetCommentsModel model);
        void onFailure(String error);

    }

    interface Presenter {

        void getBlogDetail();
        void getComments(Map<String, String> map);
        void createLike(String id);
        void createDisLike(String id);
        void createComment(String id, GetCommentsModel model);


    }

    interface Intracter {
        void getBlogDetail(OnCompleteListener callback);
        void getComments(Map<String, String> map,OnCompleteListener callback);
        void createLike(String id, OnCompleteListener callback);
        void createDisLike(String id, OnCompleteListener callback);
        void createComment(String id, GetCommentsModel model, OnCompleteListener callback);


    }

    interface OnCompleteListener {

        void onSuccessGetDetail(PageResponse<BlogModel> response);
        void onSuccessLike(DataResponse<BlogModel> response);
        void onSuccessDislike(DataResponse response);
        void onSuccessComment(DataResponse<GetCommentsModel> response);
        void onSuccessGetComments(PageResponse<GetCommentsModel>response);

        void onFailure(String error);


    }

}
