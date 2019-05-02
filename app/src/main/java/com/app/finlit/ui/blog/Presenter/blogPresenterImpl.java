package com.app.finlit.ui.blog.Presenter;

import com.app.finlit.data.models.BlogModel;
import com.app.finlit.data.models.GetCommentsModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.blog.Intracter.blogIntracterImpl;
import com.app.finlit.ui.blog.contract.BlogContract;

import java.util.Map;

public class blogPresenterImpl implements BlogContract.Presenter, BlogContract.OnCompleteListener {

    private BlogContract.Intracter intractor;
    private BlogContract.View view;

    public blogPresenterImpl(BlogContract.View view) {
        this.view=view;
        intractor = new blogIntracterImpl();
    }

    @Override
    public void getBlogDetail() {
           intractor.getBlogDetail(this);
    }

    @Override
    public void getComments(Map<String, String> map) {
        intractor.getComments(map,this);

    }

    @Override
    public void createLike(String id) {
        intractor.createLike(id,this);

    }

    @Override
    public void createDisLike(String id) {
        intractor.createDisLike(id,this);


    }

    @Override
    public void createComment(String id, GetCommentsModel model) {
        intractor.createComment(id,model,this);

    }

    @Override
    public void onSuccessGetDetail(PageResponse<BlogModel> response) {
          view.onSuccessGetDetail(response);

    }

    @Override
    public void onSuccessLike(DataResponse<BlogModel> response) {

        view.onSuccessLike(response.getData());

    }

    @Override
    public void onSuccessDislike(DataResponse response) {
        view.onSuccessDislike(response);

    }

    @Override
    public void onSuccessComment(DataResponse<GetCommentsModel> response) {
        view.onSucessCreateComment(response.getData());

    }

    @Override
    public void onSuccessGetComments(PageResponse<GetCommentsModel> response) {
        view.onSuccessGetComments(response);

    }

    @Override
    public void onFailure(String error) {
        view.onFailure(error);

    }
}


