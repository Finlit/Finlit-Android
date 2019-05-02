package com.app.finlit.ui.dates.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.DateStatusModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.models.ViewType;
import com.app.finlit.ui.dates.CreateDateActivity;
import com.app.finlit.utils.ToastUtils;
import com.app.finlit.utils.image.ImageLoaderUtils;
import com.app.finlit.utils.listeners.OnActionListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by MANISH-PC on 1/28/2019.
 */

public class DatesAdapterAvailable extends RecyclerView.Adapter<DatesAdapterAvailable.ViewHolder> {


    private final Context context;
    private final List<DateStatusModel> items;
    private OnActionListener<DateStatusModel> onActionListener;
    private OnActionListener<DateStatusModel> onActionListenerNothanks;
    private OnActionListener<DateStatusModel> onActionListenerProfile;

    private boolean isLoadingAdded = false;

    public DatesAdapterAvailable(Context context, List<DateStatusModel> items, OnActionListener<DateStatusModel> onActionListener,
                                 OnActionListener<DateStatusModel> onActionListenerNothanks,OnActionListener<DateStatusModel> onActionListenerProfile) {
        this.context = context;
        this.items = items;
        this.onActionListener = onActionListener;
        this.onActionListenerNothanks = onActionListenerNothanks;
        this.onActionListenerProfile = onActionListenerProfile;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewType type = ViewType.parseByValue(viewType);
        View view = null;
        switch (type) {
            case ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dates_available, parent, false);
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
        holder.tv_available_location.setSelected(true);
        holder.tv_available_location.setText(model.getLocation().getAddress());
//        if (model.getImgUrl() != null) {
//            displayImageIcon(model.getImgUrl(), holder);
//            displayImageIcons(model.getImgUrl(), holder);
//        }

  Picasso.get().load( model.getImgUrl() ).placeholder( R.drawable.default_user ).error( R.drawable.default_user).into( holder.iv_profileUp,new Callback() {
      @Override
      public void onSuccess() {
          holder.show_progress2.setVisibility( View.VISIBLE );
      }

      @Override
      public void onError(Exception e) {

      }
  } );
       Picasso.get().load( model.getImgUrl() ).placeholder( R.drawable.default_user ).error( R.drawable.default_user).into( holder.iv_profileUp);
        Picasso.get().load( model.getImgUrl() ).placeholder( R.drawable.default_user ).error( R.drawable.default_user).into( holder.iv_Profile);

        holder.tv_age.setText(", " + model.getAgeGroup());
        //holder.tv_profession.setText(String.valueOf(model.getQuestion().get()));
        String value="";
        for (int i=0;i < model.getQuestion().size();i++)
        {
            if(i==0)
                value+=model.getQuestion().get(i);
            else
            value+=", "+model.getQuestion().get(i);

        }
        holder.tv_profession.setText(value);

        holder.tv_noThanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onActionListenerNothanks.notify(model, holder.getAdapterPosition());
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

        private TextView tv_name, tv_profession, tv_Intrested, tv_noThanks, tv_age,tv_available_location;
        private ImageView iv_Profile, iv_profileUp;
        private ProgressBar show_progress2;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_profession = itemView.findViewById(R.id.tv_proffesion);
            tv_Intrested = itemView.findViewById(R.id.tv_intrested);
            tv_noThanks = itemView.findViewById(R.id.tv_nothanks);
            iv_Profile = itemView.findViewById(R.id.iv_profileavailable);
            iv_profileUp = itemView.findViewById(R.id.iv_ivprofile);
            tv_age = itemView.findViewById(R.id.tv_age);
            tv_available_location=itemView.findViewById(R.id.available_location);


            show_progress2 = itemView.findViewById(R.id.show_progress2);

            tv_Intrested.setOnClickListener(this);
            tv_noThanks.setOnClickListener(this);
            iv_Profile.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.tv_intrested:
                    if (getAdapterPosition()<0){
                        return;
                    }
                    onActionListener.notify(items.get(getAdapterPosition()), getAdapterPosition());
                    break;

                case  R.id.tv_nothanks:
                    if (getAdapterPosition()<0){
                        return;
                    }
                    onActionListenerNothanks.notify(items.get(getAdapterPosition()),getAdapterPosition());
                    break;

                case R.id.iv_profileavailable:
                    if (getAdapterPosition()<0){
                        return;
                    }
                            onActionListenerProfile.notify(items.get(getAdapterPosition()),getAdapterPosition());
                break;

            }
        }
    }


//    private void displayImageIcon(String attachUrl, final ViewHolder viewHolder) {
//        ImageLoader.getInstance().displayImage(attachUrl, viewHolder.iv_Profile,
//                ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS);
//    }
//
//    private void displayImageIcons(String attachUrl, final ViewHolder viewHolder) {
//        ImageLoader.getInstance().displayImage(attachUrl, viewHolder.iv_profileUp,
//                ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS);
//    }


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
    }
}
