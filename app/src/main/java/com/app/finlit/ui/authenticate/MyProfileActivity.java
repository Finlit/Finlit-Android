package com.app.finlit.ui.authenticate;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.InterestModel;
import com.app.finlit.ui.authenticate.adapter.InterestAdapter;
import com.app.finlit.ui.authenticate.adapter.MyInterestAdapter;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.chat.ChatActivity;
import com.app.finlit.ui.dates.DatesActivity;
import com.app.finlit.utils.DateHelper;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;
import com.app.finlit.utils.Utils;
import com.app.finlit.utils.image.ImageLoaderUtils;
import com.cloudinary.utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyProfileActivity extends BaseActivity {

    @BindView(R.id.tv_heading)
    public TextView tvTitle;

    @BindView(R.id.iv_mask)
    public ImageView ivMask;
    @BindView(R.id.iv_profile)
    public ImageView ivProfile;
    @BindView(R.id.iv_add_profile_background)
    public ImageView ivBackground;
//    @BindView(R.id.iv_result)
//    public ImageView ivResult;
////    @BindView(R.id.tv_time)
//    public TextView tvTime;
    @BindView(R.id.tv_question)
    public TextView tvQuestion;
    @BindView(R.id.tv_gender)
    public TextView tvGender;
    @BindView(R.id.tv_age)
    public TextView tvAge;
    @BindView(R.id.tv_location)
    public TextView tvLocation;
    @BindView(R.id.tv_interests)
    public TextView tvInterests;
//    @BindView(R.id.tv_time)
//    public TextView tv_time;

    @BindView(R.id.tv_about)
    public TextView tvAbout;

    private SharedPreferenceHelper preferenceHelper;

    private List<InterestModel> selectedInterest, interestList;
    private RecyclerView recyclerViewInterest;
    private Dialog dialog= null;
    private TextView txtDone, txtCancel, tvDone, tvCancle;


    public static void start(Context context) {
        Intent intent = new Intent(context, MyProfileActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_my_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceHelper = SharedPreferenceHelper.getInstance();
        selectedInterest = new ArrayList<>();

        initFields();
    }

    private void initFields() {
        if (preferenceHelper.getProfilePic() != null) {
            ivMask.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(preferenceHelper.getProfilePic(), ivProfile,
                    ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS);
            ImageLoader.getInstance().displayImage(preferenceHelper.getProfilePic(), ivBackground,
                    ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS);
        }
        tvTitle.setText(preferenceHelper.getName());
        if (preferenceHelper.getAbout() != null)
            tvAbout.setText(preferenceHelper.getAbout());
        if (preferenceHelper.getQuestion() != null)
            tvQuestion.setText(StringUtils.join(preferenceHelper.getQuestion(), ",\n"));
        if (preferenceHelper.getGender() != null)
            tvGender.setText(Utils.capSentences(preferenceHelper.getGender()));
        if (preferenceHelper.getAge() != null)
            tvAge.setText(preferenceHelper.getAge());
        if (preferenceHelper.getLocation() != null)
            tvLocation.setText(preferenceHelper.getLocation());

//        if (preferenceHelper.getCreatedAt() != null)
//            tv_time.setText(DateHelper.getDate(preferenceHelper.getCreatedAt()));

//
//        if (preferenceHelper.getResult() != null) {
//            if (preferenceHelper.getResult().equals("novice"))
//                ivResult.setBackgroundResource(R.mipmap.result_novice_small);
//            else if (preferenceHelper.getResult().equals("proficent"))
//                ivResult.setBackgroundResource(R.mipmap.result_proficent_small);
//            else
//                ivResult.setBackgroundResource(R.mipmap.result_expert_small);

            if (preferenceHelper.getInterests() != null) {
                selectedInterest.addAll(preferenceHelper.getInterests());
            } else {
                tvInterests.setText("Not selected.");
            }
        }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
        binder = null;
    }

    @OnClick({R.id.iv_edit_pofile, R.id.tv_edit})
    public void onClickEditProfile(){
        Intent intent = new Intent(this, AddProfileActivity.class);
        intent.putExtra("UniqId", "fromMyProfile");
        startActivity(intent);
    }

    @OnClick(R.id.iv_left)
    public void onClickLeft(){
        onBackPressed();
    }

    @OnClick(R.id.tv_interests)
    public void onClickInterests(){
        if (selectedInterest.isEmpty()){
            return;
        }
        interestList = new ArrayList<>();
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
        dialog.getWindow().setLayout(width,height);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setContentView(R.layout.dialoge_interest);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        recyclerViewInterest = dialog.findViewById(R.id.recycler_interest);
        tvDone= dialog.findViewById(R.id.txt_dialog_done);
        tvCancle = dialog.findViewById(R.id.txt_dialog_cancel);
        initListenerInterest();
        initDialogInterest();
    }

    private void initListenerInterest() {
        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void initDialogInterest(){
        interestList.clear();
        String[] providers = getResources().getStringArray(R.array.interest);

        List<String> stringListInterest = new ArrayList<>();
        stringListInterest.addAll(Arrays.asList(providers));


        for (int i = 0; i<14; i++){
            InterestModel model = new InterestModel();
            model.setQuestion(stringListInterest.get(i));
            interestList.add(model);
        }

        if (selectedInterest.size()>0){
            for (InterestModel model : selectedInterest){

                for (InterestModel innerModel : interestList){
                    if (model.getQuestion().equals(innerModel.getQuestion())){
                        innerModel.setAnswer(model.getAnswer());
                        innerModel.setSelected(true);
                        break;
                    }
                }
            }
        }
        MyInterestAdapter interestAdapter= new MyInterestAdapter(this, interestList);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        recyclerViewInterest.setLayoutManager(gridLayoutManager);
        recyclerViewInterest.setAdapter(interestAdapter);
        recyclerViewInterest.setNestedScrollingEnabled(false);
    }
    @OnClick(R.id.iv_message_profile)
    public void onClickMessage(){
        startActivity(new Intent(MyProfileActivity.this, ChatActivity.class));
    }
    @OnClick(R.id.iv_open_dates_message)
    public void onClickDates(){
        startActivity(new Intent(MyProfileActivity.this, DatesActivity.class));
    }


}
