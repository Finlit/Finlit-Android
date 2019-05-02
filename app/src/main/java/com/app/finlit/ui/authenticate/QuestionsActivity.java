package com.app.finlit.ui.authenticate;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.OptionModel;
import com.app.finlit.data.models.QuestionsModel;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.authenticate.adapter.QuestionAnswerAdapter;
import com.app.finlit.ui.authenticate.contract.GetQuestionsContract;
import com.app.finlit.ui.authenticate.presentor_impl.GetQuestionsPresentorImpl;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.home.MainActivity;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;
import com.app.finlit.utils.listeners.CustomScrollListener;
import com.app.finlit.utils.listeners.OnActionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class QuestionsActivity extends BaseActivity implements GetQuestionsContract.View{

    @BindView(R.id.iv_left)
    public ImageView ivLeft;

    @BindView(R.id.tv_heading)
    public TextView txtHeading;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @BindView(R.id.tv_question)
    public TextView txtQues;


    private SharedPreferenceHelper preferenceHelper;
    private GetQuestionsContract.Presentor presentor;
    private Dialog dialog= null;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;

    private QuestionAnswerAdapter adapter;
    private List<OptionModel> list;
    private OnActionListener<OptionModel> onActionListener;

    private List<QuestionsModel> questionsModels;

    private int questionNo = 0, correctAnswerCount = 0;

    private String fromData;


    public static void start(Context context) {
        Intent intent = new Intent(context, QuestionsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_questions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        questionsModels = new ArrayList<>();
        preferenceHelper = SharedPreferenceHelper.getInstance();
        presentor= new GetQuestionsPresentorImpl(this);
        fromData = getIntent().getStringExtra("UniqId");
        initToolbar();
        initListner();
        initAdapter();
        showProgress();
        if (fromData == null)
            presentor.getQuestions(6);
        else
            presentor.getQuestions(10);
    }

    private void initToolbar() {
        txtHeading.setText("FINLIT DATING QUIZ");
        if(fromData == null)
            ivLeft.setVisibility(View.GONE);
    }

    private void initAdapter() {
        adapter = new QuestionAnswerAdapter(this, list, onActionListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void initListner() {
        onActionListener = new OnActionListener<OptionModel>() {
            @Override
            public void notify(OptionModel model, int position) {
                for (OptionModel model1 : list){
                    if (model1.getId().equals(model.getId())){
                        model1.setSelected(true);
                            if (model.isCorrect) {
                                ++correctAnswerCount;
                            } else {
                                if (correctAnswerCount > 0)
                                    --correctAnswerCount;
                            }
                    }else {
                        model1.setSelected(false);
                    }
                }

                adapter.notifyDataSetChanged();
            }
        };
    }

    @OnClick(R.id.tv_questions_next)
    public void onClickNext(){
        boolean selected  = false;
        for (OptionModel model : list){
            if (model.isSelected){
                selected = true;
                break;
            }
        }
        if (!selected){
            ToastUtils.shortToast("Please select answer");
            return;
        }
        ++questionNo;
        if (questionNo<questionsModels.size()){
            initQuestions();
        }else {
            ResultActivity.start(this, correctAnswerCount, questionsModels.size());
        }
    }



    @Override
    public void onSuccessGetQuestions(List<QuestionsModel> response) {
        if(isFinishing()){
            return;
        }
        hideProgress();
        if (response.size() == 0){
            MainActivity.start(this);
            SharedPreferenceHelper.getInstance().saveIsResultCompleted(true);
            return;
        }
        questionsModels.addAll(response);
        initQuestions();
    }

    private void initQuestions() {
        list.clear();
        list.addAll(questionsModels.get(questionNo).getOptions());
        txtQues.setText("");
        txtQues.setText(questionsModels.get(questionNo).getLabel());
        initAdapter();
    }

    @Override
    public void onFailure(String error) {
        if(isFinishing()){
            return;
        }
        hideProgress();
        ToastUtils.longToast(error);
    }

    @Override
    public void onBackPressed() {
        if (fromData!=null)
            super.onBackPressed();
    }

    @OnClick(R.id.iv_left)
    public void onClickLeft(){
        onBackPressed();
    }
}
