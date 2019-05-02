package com.app.finlit.ui.home.adapter;

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
import com.app.finlit.utils.image.ImageLoaderUtils;
import com.app.finlit.utils.listeners.OnActionListener;
import com.cloudinary.utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by MANISH-PC on 10/4/2018.
 */

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.ViewHolder> {


    private final Context context;
    private final List<UserModel> items;
    private OnActionListener<UserModel> onActionListener;
    private OnActionListener<UserModel> onActionListenerMessage;
    private OnActionListener<UserModel> onActionListenerViewProfile;

    private boolean isLoadingAdded = false;

    public CircleAdapter(Context context, List<UserModel> items, OnActionListener<UserModel> onActionListener,
                         OnActionListener<UserModel> onActionListenerMessage,
                         OnActionListener<UserModel> onActionListenerViewProfile) {

        this.context = context;
        this.items = items;
        this.onActionListener = onActionListener;
        this.onActionListenerMessage = onActionListenerMessage;
        this.onActionListenerViewProfile = onActionListenerViewProfile;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewType type = ViewType.parseByValue(viewType);
        View view = null;
        switch (type) {
            case ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circle, parent, false);
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
                holder.tvQuestion.setText(StringUtils.join(model.getQuestion(), ","));
                holder.tvAge.setText(String.valueOf(model.getAgeGroup()));
                if (model.getLocation() != null)
                    holder.tvLocation.setText(model.getLocation().getAddress());

                if (model.getImgUrl() != null)
                    displayImageIcon(model.getImgUrl(), holder);
                else
                    holder.ivProfile.setImageResource(R.mipmap.default_user_square);

                if (model.isFavourite)
                    holder.ivLike.setImageResource(R.mipmap.icon_heart_filled);
                else
                    holder.ivLike.setImageResource(R.mipmap.icon_heart_unfilled);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivProfile, ivLike;
        private TextView tvName, tvQuestion, tvLocation, tvMessage, tvProfile, tvAge;

        @Optional
        @OnClick(R.id.tv_profile)
        void onClickProfile(){
            onActionListenerViewProfile.notify(items.get(getAdapterPosition()), getAdapterPosition());
        }

        @Optional
        @OnClick(R.id.tv_message)
        void onClickMessage(){
            onActionListenerMessage.notify(items.get(getAdapterPosition()), getAdapterPosition());
        }

        @Optional
        @OnClick(R.id.iv_like)
        void onClickLikeUnLike(){
            onActionListener.notify(items.get(getAdapterPosition()), getAdapterPosition());
            items.get(getAdapterPosition()).isFavourite = !items.get(getAdapterPosition()).isFavourite;
            notifyItemChanged(getAdapterPosition());
        }


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            ivProfile = itemView.findViewById(R.id.iv_profile);
            ivLike = itemView.findViewById(R.id.iv_like);
            tvName = itemView.findViewById(R.id.tv_name);
            tvQuestion = itemView.findViewById(R.id.tv_question);
            tvLocation = itemView.findViewById(R.id.tv_location);
            tvMessage = itemView.findViewById(R.id.tv_message);
            tvProfile = itemView.findViewById(R.id.tv_profile);
            tvAge = itemView.findViewById(R.id.tv_age);

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

    private UserModel getItem(int position) {
        return items.get(position);
    }
}
