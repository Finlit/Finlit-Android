package com.app.finlit.ui.blog.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.BlogModel;
import com.app.finlit.data.models.ViewType;
import com.app.finlit.utils.ToastUtils;
import com.app.finlit.utils.Utility;
import com.app.finlit.utils.image.ImageLoaderUtils;
import com.app.finlit.utils.listeners.OnActionListener;
import com.app.finlit.utils.listeners.OnActionListnerComment;


import com.nostra13.universalimageloader.core.ImageLoader;


import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by Solanki-PC on 1/02/2019.
 */

public class MultiViewPostAdapter extends RecyclerView.Adapter<MultiViewPostAdapter.ViewHolder> {


    private final Context context;
    private final List<BlogModel> items;
    private OnActionListener<BlogModel> onActionListener;
    private OnActionListener<BlogModel> onActionListenerLike;
    private OnActionListener<BlogModel> onActionListenerShare;
    private OnActionListener<BlogModel> onActionListenerComments;
    // private OnActionListener<BlogModel> onActionListnerMore;

    private boolean isLoadingAdded = false;

    public MultiViewPostAdapter(Context context, List<BlogModel> items, OnActionListener<BlogModel> onActionListener,
                                OnActionListener<BlogModel> onActionListenerLike,
                                OnActionListener<BlogModel> onActionListenerShare, OnActionListener<BlogModel> onActionListenerComments, OnActionListener<BlogModel> onActionListenerMore) {
        this.context = context;
        this.items = items;

        this.onActionListener = onActionListener;
        this.onActionListenerLike = onActionListenerLike;
        this.onActionListenerShare = onActionListenerShare;
        this.onActionListenerComments = onActionListenerComments;
        // this.onActionListnerMore = onActionListenerMore;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewType type = ViewType.parseByValue(viewType);
        View view = null;
        switch (type) {
            case ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blog, parent, false);
                break;
            case LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final BlogModel blogModel = items.get(position);

        holder.tvTitle.setText(blogModel.getTitle());
        holder.tvDesc.setText(blogModel.getDescription());


        if(blogModel.getUser().getName()!= null) {
            holder.tv_blog_name.setText("by " + blogModel.getUser().getName());
        }
        if (blogModel.getImgUrl() != null)
            displayImageIcon(blogModel.getImgUrl(), holder);
        else
            holder.postImage.setImageResource(R.mipmap.ic_launcher);


        if (blogModel.getUser().getImgUrl()!= null)
            displayImageIcons(blogModel.getUser().getImgUrl(), holder);
        else
            holder.postImage.setImageResource(R.mipmap.ic_launcher);
        if(blogModel.isLike()==true){
            holder.iv_Like.setImageResource(R.mipmap.heart_unfill_finlit);
        }
        else {
            holder.iv_Like.setImageResource(R.mipmap.heart_fill_finlit);
        }
        holder.likeInt.setText(String.valueOf(blogModel.getLikeCount()));
        holder.CommentInt.setText(String.valueOf(blogModel.getCommentCount()));

    }


    private void displayImageIcon(String attachUrl, final ViewHolder viewHolder) {
        ImageLoader.getInstance().displayImage(attachUrl, viewHolder.postImage,
                ImageLoaderUtils.UIL_ICON_DISPLAY_OPTIONS);
    }

    private void displayImageIcons(String attachUrl, final ViewHolder viewHolder) {
        ImageLoader.getInstance().displayImage(attachUrl, viewHolder.iv_blog_profile,
                ImageLoaderUtils.UIL_ICON_DISPLAY_OPTIONS);
    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle, tvDesc, likeInt, CommentInt,tv_blog_name;
        private ImageView postImage, iv_Like,iv_blog_profile;
        private LinearLayout ll_like, llShare, llComments;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            postImage = itemView.findViewById(R.id.postimage);
            iv_blog_profile=itemView.findViewById(R.id.iv_blog_profile);
            tv_blog_name=itemView.findViewById(R.id.tv_blog_name);
            tvTitle = itemView.findViewById(R.id.tv_titleblog);
            ll_like = itemView.findViewById(R.id.ll_like);
            likeInt = itemView.findViewById(R.id.likeint);
            tvDesc = itemView.findViewById(R.id.tv_description);
            llShare = itemView.findViewById(R.id.ll_share);
            llComments = itemView.findViewById(R.id.ll_comments);
            CommentInt = itemView.findViewById(R.id.tv_comment);
            iv_Like = itemView.findViewById(R.id.iv_likee);

            ll_like.setOnClickListener(this);
            llComments.setOnClickListener(this);

            llShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onActionListenerShare.notify(items.get(getAdapterPosition()), getAdapterPosition());

                }
            });


        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_like:
                    BlogModel model = items.get(getAdapterPosition());

                    if (items.get(getAdapterPosition()).isLike() == false) {
                        model.setLike(true);
                        model.setLikeCount(items.get(getAdapterPosition()).getLikeCount() + 1);
                        items.set(getAdapterPosition(), model);
                        likeInt.setText(String.valueOf(items.get(getAdapterPosition()).getLikeCount()));
                        iv_Like.setImageResource(R.mipmap.heart_unfill_finlit);
                    } else if (items.get(getAdapterPosition()).isLike() == true) {
                        model.setLike(false);
                        items.set(getAdapterPosition(), model);
                        model.setLikeCount(items.get(getAdapterPosition()).getLikeCount() - 1);
                        likeInt.setText(String.valueOf(items.get(getAdapterPosition()).getLikeCount()));
                        iv_Like.setImageResource(R.mipmap.heart_fill_finlit);
                    }
//                    items.set(getAdapterPosition(),model);
//                    notifyItemChanged(getAdapterPosition());
                    onActionListenerLike.notify(model, getAdapterPosition());

                    break;

                case R.id.ll_comments:
                    if(getAdapterPosition()<0){
                        return;
                    }
                    onActionListenerComments.notify(items.get(getAdapterPosition()), getAdapterPosition());
                    break;


            }

        }

        @OnClick(R.id.postimage)
        public void onClickPostImage() {
            onActionListener.notify(items.get(getAdapterPosition()), getAdapterPosition());

        }


    }


    @Override
    public int getItemViewType(int position) {
        return (position == items.size() - 1 && isLoadingAdded) ? ViewType.LOADING.getValue() : ViewType.ITEM.getValue();
    }

    public void add(BlogModel model) {
        items.add(model);
        notifyItemInserted(items.size() - 1);
    }

    public void addAll(List<BlogModel> mcList) {
        for (BlogModel mc : mcList) {
            add(mc);
        }
    }

    public void remove(BlogModel city) {
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
        add(new BlogModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = items.size() - 1;
        BlogModel item = getItem(position);

        if (item != null) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    private BlogModel getItem(int position) {
        return items.get(position);
    }

//
//    private void setLastReaction(final ViewHolder viewHolder, PostModel postModel){
//        if (postModel.getLike().getType().equalsIgnoreCase("heart"))
//            viewHolder.ivLike.setImageResource(R.mipmap.heart_icon);
//        else if (postModel.getLike().getType().equalsIgnoreCase("smile light"))
//            viewHolder.ivLike.setImageResource(R.mipmap.smile_light);
//        else if (postModel.getLike().getType().equalsIgnoreCase("teeth hanger"))
//            viewHolder.ivLike.setImageResource(R.mipmap.teethanger);
//        else if (postModel.getLike().getType().equalsIgnoreCase("smile full"))
//            viewHolder.ivLike.setImageResource(R.mipmap.smile_full);
//        else if (postModel.getLike().getType().equalsIgnoreCase("tounge"))
//            viewHolder.ivLike.setImageResource(R.mipmap.tounge);
//        else if (postModel.getLike().getType().equalsIgnoreCase("angry"))
//            viewHolder.ivLike.setImageResource(R.mipmap.angry);
//        else if (postModel.getLike().getType().equalsIgnoreCase("down"))
//            viewHolder.ivLike.setImageResource(R.mipmap.down);
//        else if (postModel.getLike().getType().equalsIgnoreCase("front thumb"))
//            viewHolder.ivLike.setImageResource(R.mipmap.fronthumb);
//        else if (postModel.getLike().getType().equalsIgnoreCase("thumb up"))
//            viewHolder.ivLike.setImageResource(R.mipmap.thumbup);
//    }
}
