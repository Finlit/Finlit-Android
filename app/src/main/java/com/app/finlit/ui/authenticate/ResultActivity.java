package com.app.finlit.ui.authenticate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.authenticate.contract.UserApiContract;
import com.app.finlit.ui.authenticate.presentor_impl.UserApiPresenterImpl;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.home.MainActivity;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class ResultActivity extends BaseActivity implements UserApiContract.View{

    @BindView(R.id.iv_left)
    public ImageView ivLeft;
    @BindView(R.id.tv_heading)
    public TextView tvTitle;

    @BindView(R.id.rel_result)
    public RelativeLayout relativeLayoutResult;

    private int count, totalQuestion;

    private UserApiContract.Presentor presentor;
    private String result;

    public static void start(Context context, int count, int totalQuestion) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra("count", count);
        intent.putExtra("totalQuestion", totalQuestion);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presentor = new UserApiPresenterImpl(this);
        count = getIntent().getIntExtra("count", 0);
        totalQuestion = getIntent().getIntExtra("totalQuestion", 0);
        initToolbar();
        initFields();
        SharedPreferenceHelper.getInstance().saveIsResultCompleted(true);
    }

    private void initToolbar() {
        ivLeft.setVisibility(View.GONE);
        tvTitle.setText("CONGRATS");
    }

    private void initFields(){
        if (totalQuestion == 6) {
            if (count <= 2) {
                relativeLayoutResult.setBackgroundResource(R.mipmap.result_novice);
                result = "novice";
            } else if (count >= 3 && count <= 4) {
                relativeLayoutResult.setBackgroundResource(R.mipmap.result_profiecnt);
                result = "proficent";
            } else {
                relativeLayoutResult.setBackgroundResource(R.mipmap.result_expert);
                result = "expert";
            }
        }else {
            if (count <= 3) {
                relativeLayoutResult.setBackgroundResource(R.mipmap.result_novice);
                result = "novice";
            } else if (count >=4  && count <= 7) {
                relativeLayoutResult.setBackgroundResource(R.mipmap.result_profiecnt);
                result = "proficent";
            } else {
                relativeLayoutResult.setBackgroundResource(R.mipmap.result_expert);
                result = "expert";
            }
        }
    }

    @OnClick(R.id.tv_thanks)
    public void onClickThanks(){
        showProgress();
        SharedPreferenceHelper.getInstance().saveResult(result);
        UserModel model = new UserModel();
        model.setProfileType(result);
        presentor.updatePassword(model);

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onSuccessAddProfile(DataResponse<UserModel> response) {

    }

    @Override
    public void onSuccessUpdatePassword(DataResponse<UserModel> response) {
        if (isFinishing()){
            return;
        }
        if (totalQuestion == 10){
            SharedPreferenceHelper.getInstance().savePaidQuiz(false);
        }
        finish();
        MainActivity.start(this);
    }

    @Override
    public void onFailure(String error) {
        if (isFinishing()){
            return;
        }
        ToastUtils.shortToast(error);
    }
}
