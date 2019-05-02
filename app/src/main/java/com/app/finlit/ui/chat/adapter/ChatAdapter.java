package com.app.finlit.ui.chat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.ChatModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.models.ViewType;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.image.ImageLoaderUtils;
import com.app.finlit.utils.listeners.OnActionListener;
import com.app.finlit.utils.listeners.OnActionListnerComment;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by MANISH-PC on 10/8/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    private final Context context;
    private final List<ChatModel> items;
    private OnActionListener<ChatModel> onActionListener;
    private OnActionListnerComment<ChatModel> onActionListnerDelete;
    private SharedPreferenceHelper preferenceHelper;

    private boolean isLoadingAdded = false;

    public ChatAdapter(Context context, List<ChatModel> items, OnActionListener<ChatModel> onActionListener,
                       OnActionListnerComment<ChatModel> onActionListnerDelete) {

        this.context = context;
        this.items = items;
        this.onActionListener = onActionListener;
        this.onActionListnerDelete = onActionListnerDelete;

        preferenceHelper = SharedPreferenceHelper.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewType type = ViewType.parseByValue(viewType);
        View view = null;
        switch (type) {
            case ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
                break;
            case LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatModel model = items.get(position);

        if (holder.tvName!=null){
            if (model.getLastMessage()!=null)
                holder.tvMessage.setText(model.getLastMessage());


            UserModel userModel = null;
            if (model.getParticipants().get(0).getUser().getId().equals(preferenceHelper.getUserId())) {
                userModel = model.getParticipants().get(1).getUser();
                if (model.getParticipants().get(0).getUnreadCount() != 0) {
                    holder.tvUnreadCount.setVisibility(View.VISIBLE);
                    holder.tvUnreadCount.setText(String.valueOf(model.getParticipants().get(0).getUnreadCount()));
                }else {
                    holder.tvUnreadCount.setVisibility(View.GONE);
                }
            }
            else {
                userModel = model.getParticipants().get(0).getUser();
                if (model.getParticipants().get(1).getUnreadCount() != 0) {
                    holder.tvUnreadCount.setVisibility(View.VISIBLE);
                    holder.tvUnreadCount.setText(String.valueOf(model.getParticipants().get(1).getUnreadCount()));
                }else {
                    holder.tvUnreadCount.setVisibility(View.GONE);
                }
            }


                holder.tvName.setText(userModel.getName());
                if (userModel.getImgUrl()!=null)
                    displayImageIcon(userModel.getImgUrl(), holder);
                else
                    holder.ivProfile.setImageResource(R.mipmap.default_user);

        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivProfile;
        private TextView tvName, tvMessage, tvTime, tvUnreadCount;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            ivProfile = itemView.findViewById(R.id.iv_profile);
            tvName = itemView.findViewById(R.id.tv_name);
            tvMessage = itemView.findViewById(R.id.tv_message);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvUnreadCount = itemView.findViewById(R.id.tv_unread_count);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onActionListener.notify(items.get(getAdapterPosition()), getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onActionListnerDelete.notify(getAdapterPosition(), v, items.get(getAdapterPosition()));
                    return false;
                }
            });

        }
    }

    private void displayImageIcon(String attachUrl, final ViewHolder viewHolder) {
        ImageLoader.getInstance().displayImage(attachUrl, viewHolder.ivProfile,
                ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS);
    }

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
