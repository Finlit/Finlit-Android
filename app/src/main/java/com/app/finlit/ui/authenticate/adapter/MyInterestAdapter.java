package com.app.finlit.ui.authenticate.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.InterestModel;
import com.app.finlit.data.models.ViewType;

import java.util.List;

/**
 * Created by MANISH-PC on 12/18/2018.
 */

public class MyInterestAdapter extends RecyclerView.Adapter<MyInterestAdapter.ViewHolder>  {
    private Context context;
    private List<InterestModel> list;

    public MyInterestAdapter(Context context, List<InterestModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interest, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

        holder.checkBox1.setEnabled(false);
        holder.etAnswer.setFocusable(false);
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
}
