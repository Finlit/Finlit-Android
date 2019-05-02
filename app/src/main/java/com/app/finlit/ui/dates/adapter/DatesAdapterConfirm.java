package com.app.finlit.ui.dates.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.DateStatusModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.models.ViewType;
import com.app.finlit.utils.DateHelper;
import com.app.finlit.utils.image.ImageLoaderUtils;
import com.app.finlit.utils.listeners.OnActionListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by MANISH-PC on 1/28/2019.
 */

public class DatesAdapterConfirm extends RecyclerView.Adapter<DatesAdapterConfirm.ViewHolder> {


    private final Context context;
    private final List<DateStatusModel> items;
    private OnActionListener<DateStatusModel> onActionListener;
    private OnActionListener<DateStatusModel> onActionListenerCancelDate;
    private OnActionListener<DateStatusModel> onActionListenerProfile;


    private boolean isLoadingAdded = false;

    public DatesAdapterConfirm(Context context, List<DateStatusModel> items, OnActionListener<DateStatusModel> onActionListener,
                               OnActionListener<DateStatusModel> onActionListenerCancelDate,OnActionListener<DateStatusModel> onActionListenerProfile) {
        this.context = context;
        this.items = items;
        this.onActionListener = onActionListener;
        this.onActionListenerCancelDate = onActionListenerCancelDate;
        this.onActionListenerProfile = onActionListenerProfile;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewType type = ViewType.parseByValue(viewType);
        View view = null;
        switch (type) {
            case ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dates_confirmed, parent, false);
                break;
            case LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final DateStatusModel model = items.get(position);

        holder.tv_name.setText(model.getName());
        if (model.getImgUrl() != null) {
            displayImageIcon(model.getImgUrl(), holder);
            displayImageIcons(model.getImgUrl(), holder);
        }

        holder.tv_age.setText(", " + model.getAgeGroup());
        holder.tv_profession.setText(String.valueOf(model.getQuestion()));
        holder.tv_date.setText(DateHelper.getTimeAgo(model.getCreatedAt()));
        holder.tv_time.setText((model.getDate()));
        holder.tv_location.setSelected(true);
        if(holder.tv_location!=null) {
            holder.tv_location.setText(model.getUserlocation().getAddress());
        }
        holder.tv_Cancel_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionListenerCancelDate.notify(model,holder.getAdapterPosition());
                items.remove(model);
                notifyItemRemoved(holder.getAdapterPosition());

            }

        });



    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView tv_name, tv_profession, tv_edit, tv_Cancel_date, tv_age,tv_date,tv_time,tv_location;
        private ImageView iv_Profile, iv_profileUp;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_profession = itemView.findViewById(R.id.tv_proffesion_confirmed);
            tv_edit = itemView.findViewById(R.id.tv_edit);

            tv_Cancel_date = itemView.findViewById(R.id.tv_cancel_date);

            iv_Profile = itemView.findViewById(R.id.iv_profile_confirm);
            iv_profileUp = itemView.findViewById(R.id.iv_profile_up);
            tv_age = itemView.findViewById(R.id.tv_age);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_time=itemView.findViewById(R.id.tv_time_confirm);
            tv_location=itemView.findViewById(R.id.tv_location_confirm);
            tv_edit.setOnClickListener(this);
            tv_Cancel_date.setOnClickListener(this);
            iv_Profile.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.tv_edit:
                    if (getAdapterPosition()<0){
                        return;
                    }
                    onActionListener.notify(items.get(getAdapterPosition()), getAdapterPosition());
                    break;

                case R.id.tv_cancel_date:
                    if (getAdapterPosition()<0){
                        return;
                    }
                    onActionListenerCancelDate.notify(items.get(getAdapterPosition()), getAdapterPosition());
                    break;

                case R.id.iv_profile_confirm:
                    if (getAdapterPosition()<0){
                        return;
                    }
                    onActionListenerProfile.notify(items.get(getAdapterPosition()), getAdapterPosition());
                    break;
            }

        }
    }

    private void displayImageIcon(String attachUrl, final ViewHolder viewHolder) {
        ImageLoader.getInstance().displayImage(attachUrl, viewHolder.iv_Profile,
                ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS);
    }
    private void displayImageIcons(String attachUrl, final ViewHolder viewHolder) {
        ImageLoader.getInstance().displayImage(attachUrl, viewHolder.iv_profileUp,
                ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == items.size() - 1 && isLoadingAdded) ? ViewType.LOADING.getValue() : ViewType.ITEM.getValue();
    }

    public void add(DateStatusModel model) {
        items.add(model);
        notifyItemInserted(items.size() - 1);
    }

    public void addAll(List<DateStatusModel> mcList) {
        for (DateStatusModel mc : mcList) {
            add(mc);
        }
    }

    public void remove(DateStatusModel city) {
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
        add(new DateStatusModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = items.size() - 1;
        DateStatusModel item = getItem(position);

        if (item != null) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    private DateStatusModel getItem(int position) {
        return items.get(position);
    }}
