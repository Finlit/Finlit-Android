package com.app.finlit.ui.authenticate.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.TourModel;
import com.app.finlit.utils.listeners.OnActionListener;

import java.util.List;

import butterknife.OnClick;

public class RecyclerIndicatorAdapter extends RecyclerView.Adapter<RecyclerIndicatorAdapter.ViewHolder> {

    private List<TourModel> mList;
    private OnActionListener<TourModel> onActionListener;

    public RecyclerIndicatorAdapter(List<TourModel> mList, OnActionListener<TourModel> onActionListener) {

        this.mList = mList;
        this.onActionListener = onActionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tour_dot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TourModel model = mList.get(position);
        if (model.isSelected())
            holder.dot.setBackgroundResource(R.drawable.background_dot_primary);
        else
            holder.dot.setBackgroundResource(R.drawable.background_dot_header);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView dot;

        @OnClick(R.id.dot)
        public void onClickDot(){
            onActionListener.notify(mList.get(getAdapterPosition()), getAdapterPosition());
        }

        public ViewHolder(View itemView) {
            super(itemView);

            dot = itemView.findViewById(R.id.dot);
        }
    }
}