package com.app.finlit.ui.chat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.finlit.R;
import com.app.finlit.data.models.ChatModel;
import com.app.finlit.data.models.ViewType;
import com.app.finlit.utils.listeners.OnActionListener;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by MANISH-PC on 10/8/2018.
 */

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ViewHolder> {

    private final Context context;
    private final List<ChatModel> items;
    private OnActionListener<ChatModel> onActionListener;

    private boolean isLoadingAdded = false;

    public ChatRoomAdapter(Context context, List<ChatModel> items, OnActionListener<ChatModel> onActionListener) {

        this.context = context;
        this.items = items;
        this.onActionListener = onActionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewType type = ViewType.parseByValue(viewType);
        View view = null;
        switch (type) {
            case ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_room, parent, false);
                break;
            case LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position % 2 == 0){
            holder.linearLayoutFirst.setVisibility(View.VISIBLE);
            holder.linearLayoutSecond.setVisibility(View.GONE);
        }else {
            holder.linearLayoutFirst.setVisibility(View.GONE);
            holder.linearLayoutSecond.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout linearLayoutSecond, linearLayoutFirst;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);

            linearLayoutSecond = itemView.findViewById(R.id.linear_second);
            linearLayoutFirst = itemView.findViewById(R.id.linear_first);


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onActionListener.notify(items.get(getAdapterPosition()), getAdapterPosition());
//                }
//            });

        }
    }

//    private void displayImageIcon(String attachUrl, final ViewHolder viewHolder) {
//        ImageLoader.getInstance().displayImage(attachUrl, viewHolder.ivProfile,
//                ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS);
//    }

    @Override
    public int getItemViewType(int position) {
        return (position == items.size() - 1 && isLoadingAdded) ? ViewType.LOADING.getValue() : ViewType.ITEM.getValue();
    }

    public void add(ChatModel model) {
        items.add(model);
        notifyItemInserted(items.size() - 1);
    }

    public void addAll(List<ChatModel> mcList) {
        for (ChatModel mc : mcList) {
            add(mc);
        }
    }

    public void remove(ChatModel city) {
        int position = items.indexOf(city);
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
        add(new ChatModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = items.size() - 1;
        ChatModel item = getItem(position);

        if (item != null) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    private ChatModel getItem(int position) {
        return items.get(position);
    }
}
