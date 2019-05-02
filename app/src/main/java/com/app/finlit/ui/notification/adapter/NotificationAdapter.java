package com.app.finlit.ui.notification.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.NotificationModel;
import com.app.finlit.data.models.ViewType;
import com.app.finlit.utils.DateHelper;
import com.app.finlit.utils.image.ImageLoaderUtils;
import com.app.finlit.utils.listeners.OnActionListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private final Context context;
    private final List<NotificationModel> items;
    private OnActionListener<NotificationModel> onActionListener;

    private boolean isLoadingAdded = false;

    public NotificationAdapter(Context context, List<NotificationModel> items, OnActionListener<NotificationModel> onActionListener) {
        this.context = context;
        this.items = items;
        this.onActionListener = onActionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewType type = ViewType.parseByValue(viewType);
        View view = null;
        switch (type){
            case ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
                break;
            case LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NotificationModel model = items.get(position);
        if(model!=null){
            if(model.getDescription()!=null){
                holder.tvName.setText(model.getDescription());
            }
            if(model.getCreatedAt()!=null){
                if(holder.tvTime!=null)
                holder.tvTime.setText(DateHelper.getTimeAgo(model.getCreatedAt()));
            }
            if (model.getImgUrl() != null) {
                displayImageIcon(model.getImgUrl(), holder);
            }else {
                if (holder.ivProfile != null) {
                    holder.ivProfile.setImageResource(R.mipmap.default_user_square);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivProfile;
        private TextView tvName, tvDesc, tvTime;
        public ViewHolder(final View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.iv_profile);
            tvName = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            tvTime = itemView.findViewById(R.id.tv_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onActionListener.notify(items.get(getAdapterPosition()),getAdapterPosition());
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


    public void add(NotificationModel model) {
        items.add(model);
        notifyItemInserted(items.size() - 1);
    }

    public void addAll(List<NotificationModel> mcList) {
        for (NotificationModel mc : mcList) {
            add(mc);
        }
    }

    public void remove(NotificationModel city) {
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
        add(new NotificationModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = items.size() - 1;
        NotificationModel item = getItem(position);

        if (item != null) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public NotificationModel getItem(int position) {
        return items.get(position);
    }
}
