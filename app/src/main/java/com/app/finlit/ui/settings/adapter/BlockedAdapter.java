package com.app.finlit.ui.settings.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.models.ViewType;
import com.app.finlit.ui.notification.adapter.NotificationAdapter;
import com.app.finlit.utils.image.ImageLoaderUtils;
import com.app.finlit.utils.listeners.OnActionListener;

import com.nostra13.universalimageloader.core.ImageLoader;


import java.util.List;

/**
 * Created by SOLANKI-PC on 01/03/2019.
 */

public class BlockedAdapter extends RecyclerView.Adapter<BlockedAdapter.ViewHolder>{
    private final Context context;
    private final List<UserModel> items;
    private OnActionListener<UserModel> onActionListener;
  // private OnActionListener<UserModel> onActionListenerUnblock;

    private boolean isLoadingAdded = false;

    public BlockedAdapter(Context context, List<UserModel> items, OnActionListener<UserModel> onActionListener) {
        this.context = context;
        this.items = items;
        this.onActionListener = onActionListener;
       // this.onActionListener = onActionListenerUnblock;
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewType type = ViewType.parseByValue(viewType);
        View view = null;
        switch (type){
            case ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_block_user, parent, false);
                break;
            case LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final UserModel model = items.get(position);

        if (holder.tvName != null) {
            holder.tvName.setText(model.getName());
        }
        if(holder.tv_age_block!=null){
            holder.tv_age_block.setText(String.valueOf(model.getAgeGroup()));
        }
         if (model.getImgUrl() != null) {
                displayImageIcon(model.getImgUrl(), holder);
            }

        holder.tvUnblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionListener.notify(model, holder.getAdapterPosition());
                items.remove(model);
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });

    }

    private void displayImageIcon(String imgUrl, ViewHolder holder) {
        ImageLoader.getInstance().displayImage(imgUrl, holder.ivProfile,
                ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivProfile;
        private TextView tvName, tvUnblock, tv_age_block;
        public ViewHolder(View itemView) {
            super(itemView);

            ivProfile = itemView.findViewById(R.id.iv_profile);
            tvName = itemView.findViewById(R.id.tv_name);
            tvUnblock = itemView.findViewById(R.id.tv_unblock);
            tv_age_block=itemView.findViewById(R.id.tv_age_block);
         //   tvLocation = itemView.findViewById(R.id.tv_location);

            tvUnblock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onActionListener.notify(items.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == items.size() - 1 && isLoadingAdded) ? ViewType.LOADING.getValue() : ViewType.ITEM.getValue();
    }


    public void add(UserModel model) {
        items.add(model);
        notifyItemInserted(items.size() - 1);
    }

    public void addAll(List<UserModel> mcList) {
        for (UserModel mc : mcList) {
            add(mc);
        }
    }

    public void remove(UserModel city) {
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
        add(new UserModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = items.size() - 1;
        UserModel item = getItem(position);

        if (item != null) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public UserModel getItem(int position) {
        return items.get(position);
    }
}
