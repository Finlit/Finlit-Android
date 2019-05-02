package com.app.finlit.ui.authenticate.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.InterestModel;
import com.app.finlit.data.models.OptionsModel;
import com.app.finlit.data.models.ViewType;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.utils.Utility;
import com.app.finlit.utils.Utils;
import com.app.finlit.utils.listeners.OnActionListener;

import java.util.List;

/**
 * Created by MANISH-PC on 12/6/2018.
 */

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewHolder> {

    private Context context;
    private List<InterestModel> list;
    private View view;
    private Activity activity;
    private boolean isLoadingAdded = false;

    public InterestAdapter(Context context, List<InterestModel> list, View view, Activity activity){
        this.context = context;
        this.list = list;
        this.view = view;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewType type = ViewType.parseByValue(viewType);
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interest, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final InterestModel model= list.get(position);
        holder.tvQuestion.setText(model.getQuestion());

        if (model.isSelected){
            holder.etAnswer.setVisibility(View.VISIBLE);
            holder.checkBox1.setChecked(true);
            if (model.getAnswer()!=null)
                holder.etAnswer.setText(model.getAnswer());
        }else {
            holder.checkBox1.setChecked(false);
            holder.etAnswer.setVisibility(View.GONE);
        }


        holder.checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox1.isChecked()){
                    model.setSelected(true);
                    holder.etAnswer.setVisibility(View.VISIBLE);
                }else {
                    model.setSelected(false);
                    holder.etAnswer.setVisibility(View.GONE);
                }
            }
        });

        holder.etAnswer.setOnEditorActionListener(new EditText.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if (holder.etAnswer.getText().toString().isEmpty()){
                        return false;
                    }
                    model.setAnswer(holder.etAnswer.getText().toString());
                    holder.etAnswer.clearFocus();

                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(holder.etAnswer.getWindowToken(), 0);
                    //Utility.hideSoftKeyboard(activity);
                }
                return false;
            }
        });

        holder.etAnswer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    model.setAnswer(holder.etAnswer.getText().toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private CheckBox checkBox1;
        private TextView tvQuestion;
        private EditText etAnswer;
        ViewHolder(View itemView) {
            super(itemView);

            tvQuestion = itemView.findViewById(R.id.tv_question);
            checkBox1 = itemView.findViewById(R.id.checkbox);
            etAnswer = itemView.findViewById(R.id.et_answer);
//            Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "calibri.ttf");
//            checkBox1.setTypeface(typeface);
        }
    }

    protected void hideKeyboard(View view){
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
