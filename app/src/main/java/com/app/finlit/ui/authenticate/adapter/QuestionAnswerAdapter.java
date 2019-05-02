package com.app.finlit.ui.authenticate.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.OptionModel;
import com.app.finlit.data.models.QuestionsModel;
import com.app.finlit.data.models.ViewType;
import com.app.finlit.utils.listeners.OnActionListener;

import java.util.List;

public class QuestionAnswerAdapter extends RecyclerView.Adapter<QuestionAnswerAdapter.ViewHolder>  {

    private Context context;
    private List<OptionModel> items;
    private OnActionListener<OptionModel> onActionListener;
    private boolean isLoadingAdded = false;

    public QuestionAnswerAdapter(Context context, List<OptionModel> items,OnActionListener<OptionModel> onActionListener){
        this.context = context;
        this.items = items;
        this.onActionListener= onActionListener;
    }

    @Override
    public QuestionAnswerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewType type = ViewType.parseByValue(viewType);
        View view = null;
        switch (type){
            case ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questions_ans, parent, false);
                break;
            case LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAnswerAdapter.ViewHolder holder, int position) {
        OptionModel model= items.get(position);
            if(model.getText()!=null){
                holder.txtName.setText(model.getText());
            }
            if (model.isSelected)
                holder.ivImage.setImageResource(R.mipmap.icon_tick_pink);
            else
                holder.ivImage.setImageResource(R.mipmap.icon_tick);

            if (position == items.size()-1){
                holder.view.setVisibility(View.GONE);
            }else {
                holder.view.setVisibility(View.VISIBLE);
            }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivImage;
        private TextView txtName;
        private View view;

        public ViewHolder(final View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_item_questions_ans);
            txtName = itemView.findViewById(R.id.txt_item_questions_ans);
            view = itemView.findViewById(R.id.view);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (items.get(getAdapterPosition()).isSelected){
                        return;
                    }
                    onActionListener.notify(items.get(getAdapterPosition()),getAdapterPosition());
                }
            });
        }
    }

    public void add(OptionModel model) {
        items.add(model);
        notifyItemInserted(items.size() - 1);
    }

    public void addAll(List<OptionModel> mcList) {
        for (OptionModel mc : mcList) {
            add(mc);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == items.size() - 1 && isLoadingAdded) ? ViewType.LOADING.getValue() : ViewType.ITEM.getValue();
    }

    public void remove(OptionModel questionsModel) {
        int position = items.indexOf(questionsModel);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new OptionModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        if(items.size()>0) {
            int position = items.size() - 1;
            OptionModel item = getItem(position);

            if (item != null) {
                items.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public OptionModel getItem(int position) {
        return items.get(position);
    }
}
