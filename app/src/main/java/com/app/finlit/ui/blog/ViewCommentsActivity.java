package com.app.finlit.ui.blog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.BlogModel;
import com.app.finlit.data.models.GetCommentsModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.blog.Presenter.blogPresenterImpl;
import com.app.finlit.ui.blog.adapter.CommentAdapter;
import com.app.finlit.ui.blog.adapter.MultiViewPostAdapter;
import com.app.finlit.ui.blog.contract.BlogContract;
import com.app.finlit.utils.Constants;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;
import com.app.finlit.utils.image.ImageLoaderUtils;
import com.app.finlit.utils.listeners.OnActionListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ViewCommentsActivity extends BaseActivity implements BlogContract.View {

    @BindView(R.id.tv_heading)
    public TextView tv_Heading;

    @BindView(R.id.tv_description)
    public TextView tv_description;

//    @BindView(R.id.iv_myprofile)
//    public ImageView myProfile;

    @BindView(R.id.recycle_comments)
    public RecyclerView recyclerView;

    @BindView(R.id.tv_titleblog)
    public TextView tv_title;

    @BindView(R.id.tv_by_title)
    public TextView tv_by_Title;

    @BindView(R.id.postimage)
    public ImageView iv_Postimage;
    @BindView(R.id.iv_comment)
    public ImageView iv_comment;

    @BindView(R.id.et_comments)
    public EditText et_comment;

    private SharedPreferenceHelper preferenceHelper;
    private String imagePath;



    private BlogContract.Presenter presenter;

    private CommentAdapter adapter;
    private List<GetCommentsModel> userList;
    private OnActionListener<GetCommentsModel> onActionListener,onActionListenercomment;

    BlogModel blogModel;


    @Override
    protected int getContentId() {
        return R.layout.activity_view_comments;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blogModel = (BlogModel) getIntent().getSerializableExtra(Constants.BLOG_MODEL);     // GETTING WHOLE MODEL FROM BACK ACTIVITY

        presenter = new blogPresenterImpl(this);
        preferenceHelper = SharedPreferenceHelper.getInstance();
        userList = new ArrayList<>();



        iniHeading();
        iniFields();
        iniApi();
        iniAdapter(userList);


    }

    private void iniAdapter(List<GetCommentsModel> userList) {
        adapter = new CommentAdapter(this, userList, onActionListener,onActionListenercomment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.iv_send)
    public void onClickSend() {

        if (et_comment.getText().toString().isEmpty()) {
            return;
        }
        et_comment.clearFocus();
        hideKeyboard(getCurrentFocus());
        GetCommentsModel getCommentsModel = new GetCommentsModel();
        getCommentsModel.setBlogId(getIntent().getStringExtra("id"));
        getCommentsModel.setText(et_comment.getText().toString());

        et_comment.setText("");
        et_comment.clearFocus();

        showProgress();

         presenter.createComment(getIntent().getStringExtra("id"),getCommentsModel);
    }


    private void iniFields() {

        tv_description.setText(blogModel.getDescription());
        tv_title.setText(blogModel.getTitle());
        if(blogModel.getUser().getName()!= null) {
            tv_by_Title.setText("by " + blogModel.getUser().getName());
        }
        if (blogModel.getImgUrl() != null) {
            displayImageIcons(blogModel.getImgUrl());
        } else {
            iv_Postimage.setImageResource(R.mipmap.ic_launcher);
        }
        if (preferenceHelper.getProfilePic() != null) {
            displayImageIconss(preferenceHelper.getProfilePic());
        } else {
            iv_comment.setImageResource(R.mipmap.icon_myprofile);
        }

    }


    private void displayImageIcons(String attachUrl) {
        ImageLoader.getInstance().displayImage(attachUrl, iv_Postimage,
                ImageLoaderUtils.UIL_ICON_DISPLAY_OPTIONS);
    }

    private void displayImageIconss(String attachUrl) {
        ImageLoader.getInstance().displayImage(attachUrl, iv_comment,
                ImageLoaderUtils.UIL_ICON_DISPLAY_OPTIONS);
    }



    private void iniApi() {

        showProgress();
        Map<String, String> map = new HashMap<>();
        map.put("blogId", getIntent().getStringExtra("id"));
        presenter.getComments(map);

    }

    private void iniHeading() {
        tv_Heading.setText("COMMENTS");

    }

    @OnClick(R.id.iv_left)
    public void onClickLeft() {
        onBackPressed();

    }


    @Override
    public void onSuccessGetDetail(PageResponse<BlogModel> response) {

    }

    @Override
    public void onSuccessGetComments(PageResponse<GetCommentsModel> response) {
        if (isFinishing()) {
            return;
        }
        hideProgress();
       // presenter.createComment(getCommentsModel.getId(),getCommentsModel);

        //adapter.clear();
        iniAdapter(response.getItems());
        //adapter.addAll(response.getItems());



//        response.getItems().get(0).getBlog().getDescription();
//        tv_description.setText( response.getItems().get(0).getBlog().getDescription());

    }


    @Override
    public void onSuccessLike(BlogModel model) {

    }

    @Override
    public void onSuccessDislike(DataResponse response) {

    }

    @Override
    public void onSucessCreateComment(GetCommentsModel model) {
        if (isFinishing()) {
            return;
        }
        hideProgress();

        if (model != null) {
            adapter.add(model);
        }
        blogModel.setCommentCount(Integer.valueOf(blogModel.getCommentCount()) + 1);
    }

    @Override
    public void onFailure(String error) {

    }
}
