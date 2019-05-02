package com.app.finlit.ui.blog.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.BlogModel;
import com.app.finlit.data.models.GetCommentsModel;
import com.app.finlit.data.models.ViewType;
import com.app.finlit.utils.DateHelper;
import com.app.finlit.utils.image.ImageLoaderUtils;
import com.app.finlit.utils.listeners.OnActionListener;
import com.nostra13.universalimageloader.core.ImageLoader;


import java.util.List;

/**
 * Created by SOLANKI-PC on 16/02/2019.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {


    private final Context context;
    private final List<GetCommentsModel> items;
    private OnActionListener<GetCommentsModel> onActionListener;
    private OnActionListener<GetCommentsModel> onActionListenercomments;


    private boolean isLoadingAdded = false;

    public CommentAdapter(Context context, List<GetCommentsModel> items, OnActionListener<GetCommentsModel> onActionListener, OnActionListener<GetCommentsModel> onActionListenercomments) {
        this.context = context;
        this.items = items;
        this.onActionListener = onActionListener;
        this.onActionListenercomments=onActionListenercomments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewType type = ViewType.parseByValue(viewType);
        View view = null;
        switch (type) {
            case ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comments, parent, false);
                break;
            case LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final GetCommentsModel model=items.get(position);

        holder.tv_User_name.setText(model.getUser().getName());
        holder.tv_User_message.setText(model.getText());

        holder.tv_Message_Time.setText(DateHelper.getTimeAgo(model.getCreatedAt()));


        if(model.getUser()!=null){
            if(model.getUser().getImgUrl()!=null) {
                displayImageIcon(model.getUser().getImgUrl(), holder);
            }
        else
                holder.iv_User_Profile.setImageResource(R.mipmap.ic_launcher);
            }
        }

    private void displayImageIcon(String attachUrl, final ViewHolder viewHolder) {
        ImageLoader.getInstance().displayImage(attachUrl, viewHolder.iv_User_Profile, ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_User_Profile;
        private TextView tv_User_name, tv_User_message, tv_Message_Time;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_User_Profile = itemView.findViewById(R.id.iv_user_profile);
            tv_User_name = itemView.findViewById(R.id.tv_user_name);
            tv_User_message = itemView.findViewById(R.id.tv_user_message);
            tv_Message_Time = itemView.findViewById(R.id.tv_message_time);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == items.size() - 1 && isLoadingAdded) ? ViewType.LOADING.getValue() : ViewType.ITEM.getValue();
    }

    public void add(GetCommentsModel model) {
        items.add(model);
        notifyItemInserted(items.size() - 1);
    }

    public void addAll(List<GetCommentsModel> mcList) {
        for (GetCommentsModel mc : mcList) {
            add(mc);
        }
    }

    public void remove(GetCommentsModel city) {
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
        add(new GetCommentsModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = items.size() - 1;
        GetCommentsModel item = getItem(position);

        if (item != null) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    private GetCommentsModel getItem(int position) {
        return items.get(position);
    }

}
