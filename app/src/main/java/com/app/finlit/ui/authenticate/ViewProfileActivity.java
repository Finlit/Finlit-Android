package com.app.finlit.ui.authenticate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.BlogModel;
import com.app.finlit.data.models.ChatModel;
import com.app.finlit.data.models.DateStatusModel;
import com.app.finlit.data.models.GetCommentsModel;
import com.app.finlit.data.models.ParticipantsModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.blog.contract.BlogContract;
import com.app.finlit.ui.chat.SingleChatActivity;
import com.app.finlit.ui.chat.contract.ChatContract;
import com.app.finlit.ui.chat.presenter_impl.ChatPresenterImpl;
import com.app.finlit.ui.dates.DatesActivity;
import com.app.finlit.ui.home.contract.GetUsersContract;
import com.app.finlit.ui.home.presentor_impl.GetUsersPresentorImpl;
import com.app.finlit.ui.settings.BlockContract;
import com.app.finlit.ui.settings.BlockPresenterImpl;
import com.app.finlit.utils.Constants;
import com.app.finlit.utils.DateHelper;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;
import com.app.finlit.utils.Utils;
import com.app.finlit.utils.image.ImageLoaderUtils;
import com.cloudinary.utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ViewProfileActivity extends BaseActivity implements GetUsersContract.View,
        ChatContract.View, BlogContract.View, BlockContract.View {

    @BindView(R.id.tv_heading)
    public TextView tvTitle;

    @BindView(R.id.tv_gender_title)
    public TextView tvGenderTitle;
    @BindView(R.id.iv_mask)
    public ImageView ivMask;
    @BindView(R.id.iv_profile)
    public ImageView ivProfile;
    @BindView(R.id.iv_add_profile_background)
    public ImageView ivBackground;
//    @BindView(R.id.iv_result)
//    public ImageView ivResult;
//    @BindView(R.id.tv_time)
//    public TextView tvTime;
    @BindView(R.id.tv_question)
    public TextView tvQuestion;
    @BindView(R.id.tv_gender)
    public TextView tvGender;
    @BindView(R.id.tv_age)
    public TextView tvAge;
    @BindView(R.id.tv_location)
    public TextView tvLocation;

    @BindView(R.id.tv_block_user)
    public TextView tv_block_User;

    @BindView(R.id.tv_about)
    public TextView tvAbout;
    @BindView(R.id.iv_open_Dates)
    public ImageView iv_dates;
    private String from,userID,test;

    private GetUsersContract.Presentor presentor;
    private ChatContract.Presenter chatPresenter;
    private BlockContract.Presenter blockPresenter;

    private UserModel userModel;
    private DateStatusModel dateStatusModel;


    public static void start(Context context, UserModel userModel) {
        Intent intent = new Intent(context, ViewProfileActivity.class);
        intent.putExtra(Constants.USER_MODEL, userModel);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_view_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blockPresenter=new BlockPresenterImpl(this);
        presentor = new GetUsersPresentorImpl(this);
        chatPresenter = new ChatPresenterImpl(this);


        userModel = (UserModel) getIntent().getSerializableExtra(Constants.USER_MODEL);
        from=getIntent().getStringExtra("from");




        showProgress();
        onClickBlock ();
        if(from!=null){
            userID=getIntent().getStringExtra("userID");
            iv_dates.setVisibility(View.GONE);
            presentor.getUser(userID);
        }
        else{
            presentor.getUser(userModel.getId());
        }


    }

    private void setUiData(){
        try {
            if (userModel.getImgUrl() != null) {
                ivMask.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(userModel.getImgUrl(), ivProfile,
                        ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS);
                ImageLoader.getInstance().displayImage(userModel.getImgUrl(), ivBackground,
                        ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS);
            }
            tvTitle.setText(userModel.getName());
            if (userModel.getAboutUs() != null)
                tvAbout.setText(userModel.getAboutUs());
            if (userModel.getQuestion() != null)
                tvQuestion.setText(StringUtils.join(userModel.getQuestion(), ",\n"));
            if (userModel.getGender() != null)
                tvGender.setText(Utils.capSentences(userModel.getGender()));

            tvAge.setText(String.valueOf(userModel.getAgeGroup()));
            if (userModel.getLocation() != null)
                tvLocation.setText(userModel.getLocation().getAddress());


//            if (userModel.getProfileType() != null) {
//                if (userModel.getProfileType().equals("novice"))
//                    ivResult.setBackgroundResource(R.mipmap.result_novice_small);
//                else if (userModel.getProfileType().equals("proficent"))
//                    ivResult.setBackgroundResource(R.mipmap.result_proficent_small);
//                else
//                    ivResult.setBackgroundResource(R.mipmap.result_expert_small);
//            }

            if (userModel.getGender().equalsIgnoreCase("male"))
                tvGenderTitle.setText("About Him");
            else
                tvGenderTitle.setText("About Her");

//            if(userModel.getCreatedAt()!=null){
//                tvTime.setText(DateHelper.getDate(userModel.getCreatedAt()));
//            } else if (SharedPreferenceHelper.getInstance().getCreatedAt()!=null) {
//                tvTime.setText(DateHelper.getDate(SharedPreferenceHelper.getInstance().getCreatedAt()));
//            }
        }catch (Exception e){}
    }


    @OnClick(R.id.iv_left)
    public void onClickLeft(){
        onBackPressed();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
        binder = null;
    }

    private void onClickBlock () {
        tv_block_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final AlertDialog.Builder builder=new AlertDialog.Builder(ViewProfileActivity.this);
                 builder.setTitle("Confirmation");
                 builder.setMessage("Are you sure you want to block this user?");

                 builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         UserModel model = new UserModel();
                         model.setUserId(userModel.getId());
                         showProgress();
                         blockPresenter.blockUser(model);
                     }
                 });

                 builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {

                     }
                 });
                 builder.show();

            }
        });
    }

    @OnClick(R.id.iv_open_Dates)
    public void onClickDates(){
        startActivity(new Intent(ViewProfileActivity.this, DatesActivity.class));
    }

    @OnClick(R.id.iv_message)
    public void onClickMessage(){

        showProgress();
        ChatModel chatModel = new ChatModel();
        chatModel.setChatType("normal");
        ParticipantsModel participantsModel = new ParticipantsModel();
        participantsModel.setUserId(userModel.getId());
        List<ParticipantsModel> participantsModelList = new ArrayList<>();
        participantsModelList.add(participantsModel);

        chatModel.setParticipants(participantsModelList);

        chatPresenter.createChat(chatModel);
    }

    @Override
    public void onSuccessGetUsers(PageResponse<UserModel> response) {

    }

    @Override
    public void onSuccessGetFilter(PageResponse<UserModel> response) {

    }

    @Override
    public void onSuccessUser(DataResponse<UserModel> response) {
        if (isFinishing()){
            return;
        }
        hideProgress();
        this.userModel = response.getData();
        setUiData();
    }



    @Override
    public void onSuccessChats(PageResponse<ChatModel> response) {

    }

    @Override
    public void onSuccessCreateChat(DataResponse<ChatModel> response) {
        if (isFinishing()){
            return;
        }

        hideProgress();
        userModel.setChatId(response.getData().getId());
        if (response.getData().getParticipants()!=null) {
            if (SharedPreferenceHelper.getInstance().getUserId().equals(response.getData().getParticipants().get(0).getUser().getId())) {;
                userModel.isBlocked = response.getData().getParticipants().get(0).isBlocked;
            }
            else {
                userModel.isBlocked = response.getData().getParticipants().get(1).isBlocked;
            }
        }
        SingleChatActivity.start(this, userModel, null);
    }

    @Override
    public void onSuccessSetZeroCount(DataResponse response) {

    }

    @Override
    public void onSuccessIncreUnreadCount(DataResponse response) {

    }

    @Override
    public void onSuccessDelete(DataResponse response) {

    }

    @Override
    public void onSuccessUsers(PageResponse<UserModel> response) {

    }

    @Override
    public void onSuccessBlock(DataResponse response) {

        if (isFinishing()){
            return;
        }
        hideProgress();

        if (response.isSuccess){
            ToastUtils.shortToast("Blocked Successfully");
            userModel.isBlocked = true;
            finish();
        }

    }

    @Override
    public void onSuccessUnblock(DataResponse response) {

    }

    @Override
    public void onSuccessUnBlock(DataResponse response) {

    }

    @Override
    public void onSuccessGetDetail(PageResponse<BlogModel> response) {

    }

    @Override
    public void onSuccessGetComments(PageResponse<GetCommentsModel> response) {

    }

    @Override
    public void onSuccessLike(BlogModel model) {

    }

    @Override
    public void onSuccessDislike(DataResponse response) {

    }

    @Override
    public void onSucessCreateComment(GetCommentsModel model) {

    }

    @Override
    public void onFailure(String message) {
        if (isFinishing()){
            return;
        }
        hideProgress();
        ToastUtils.shortToast(message);
    }
}
