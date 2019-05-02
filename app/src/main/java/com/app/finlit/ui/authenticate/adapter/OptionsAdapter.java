package com.app.finlit.ui.authenticate.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.OptionsModel;
import com.app.finlit.data.models.ViewType;
import com.app.finlit.utils.listeners.OnActionListener;

import java.util.List;

/**
 * Created by MANISH-PC on 10/29/2018.
 */

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.ViewHolder> {

    private Context context;
    private List<OptionsModel> list;
    private OnActionListener<OptionsModel> onActionListenerSelected;
    private OnActionListener<OptionsModel> onActionListenerUnSelected;
    private boolean isLoadingAdded = false;

    public OptionsAdapter(Context context, List<OptionsModel> list,OnActionListener<OptionsModel> onActionListenerSelected,OnActionListener<OptionsModel> onActionListenerUnSelected){
        this.context = context;
        this.list = list;
        this.onActionListenerSelected = onActionListenerSelected;
        this.onActionListenerUnSelected = onActionListenerUnSelected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewType type = ViewType.parseByValue(viewType);
        View view = null;
        switch (type){
            case ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_options, parent, false);
                break;
            case LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final OptionsModel model= list.get(position);
        holder.tvName.setText(model.getName());

        if (model.isSelected){
            holder.checkBox1.setChecked(true);
            onActionListenerSelected.notify(list.get(position),position);
        }else {
            holder.checkBox1.setChecked(false);
            onActionListenerUnSelected.notify(list.get(position),position);
        }

        holder.checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            model.isSelected = !model.isSelected;
            notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private CheckBox checkBox1;
        private TextView tvName;
        ViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            checkBox1 = itemView.findViewById(R.id.checkbox);
//            Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "calibri.ttf");
//            checkBox1.setTypeface(typeface);
        }
    }

//    private void interestSelected(CheckBox checkBox){
//        checkBox.setBackgroundResource(R.drawable.background_button_category_selected);
//    }
//
//    private void interestUnSelected(CheckBox checkBox){
//        checkBox.setBackgroundResource(R.drawable.background_button_category);
//    }

    public void add(OptionsModel model) {
        list.add(model);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll(List<OptionsModel> mcList) {
        for (OptionsModel mc : mcList) {
            add(mc);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == list.size() - 1 && isLoadingAdded) ? ViewType.LOADING.getValue() : ViewType.ITEM.getValue();
    }

    public void remove(OptionsModel optionsModel) {
        int position = list.indexOf(optionsModel);
        if (position > -1) {
            list.remove(position);
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
        add(new OptionsModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = list.size() - 1;
        OptionsModel item = getItem(position);

        if (item != null) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public OptionsModel getItem(int position) {
        return list.get(position);
    }
}
