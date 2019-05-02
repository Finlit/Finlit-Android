package com.app.finlit.ui.nearby.adapter;

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

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.ViewHolder> {


    private final Context context;
    private LayoutInflater inflater;
    private final List<UserModel> items;
//    private OnActionListener<UserModel> onActionListener;
    private OnActionListener<UserModel> onActionListenerViewProfile;

    private boolean isLoadingAdded = false;

    public CarouselAdapter(Context context, List<UserModel> items, OnActionListener<UserModel> onActionListenerViewProfile) {
        this.context = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
        this.onActionListenerViewProfile = onActionListenerViewProfile;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_swipe_effect, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UserModel userModel = items.get(position);
        holder.tvName.setText(userModel.getName());
        holder.tvAbout.setText(userModel.getAboutUs());
        if (userModel.getLocation() != null)
            holder.tvLocation.setText(userModel.getLocation().getAddress());
        holder.tvQuestion.setText(StringUtils.join(userModel.getQuestion(), ", "));
        holder.tvAge.setText(String.valueOf(userModel.getAgeGroup()));
        if (userModel.getProfileType() != null) {
            switch (userModel.getProfileType()) {
                case "novice":
                    holder.ivResult.setBackgroundResource(R.mipmap.result_novice_small);
                    break;
                case "proficent":
                    holder.ivResult.setBackgroundResource(R.mipmap.result_proficent_small);
                    break;
                default:
                    holder.ivResult.setBackgroundResource(R.mipmap.result_expert_small);
                    break;
            }
        }
        if (userModel.getImgUrl() != null) {
            ImageLoader.getInstance().displayImage(userModel.getImgUrl(), holder.ivProfile,
                    ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivProfile, ivResult;
        private TextView tvName, tvLocation, tvAbout, tvQuestion, tvAge;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            ivProfile = itemView.findViewById(R.id.iv_profile);
            ivResult = itemView.findViewById(R.id.iv_result);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAbout = itemView.findViewById(R.id.tv_about);
            tvLocation = itemView.findViewById(R.id.tv_location);
            tvQuestion = itemView.findViewById(R.id.tv_question);
            tvAge = itemView.findViewById(R.id.tv_age);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onActionListenerViewProfile.notify(items.get(getAdapterPosition()), getAdapterPosition());
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

}

