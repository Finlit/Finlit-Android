package com.app.finlit.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.app.finlit.R;
import com.app.finlit.data.models.ViewType;

import java.util.List;

/**
 * Created by MANISH-PC on 11/1/2018.
 */

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder> {

    private final Context context;
    private final List<DemoModel> items;


    public DemoAdapter(Context context, List<DemoModel> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_demo, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DemoModel model = items.get(position);
        if (model.getValue() == 1){
            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.text_blue));
        }else {
            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.app_color));
        }

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout relativeLayout;

        public ViewHolder(final View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.rel);

        }
    }
}
