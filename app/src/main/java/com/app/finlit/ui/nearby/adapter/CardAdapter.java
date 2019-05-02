package com.app.finlit.ui.nearby.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.utils.image.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class CardAdapter extends ArrayAdapter<UserModel> {
    private final ArrayList<UserModel> cards;
    private final LayoutInflater layoutInflater;

    public CardAdapter(Context context, ArrayList<UserModel> cards) {
        super(context, -1);
        this.cards = cards;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserModel card = cards.get(position);
        View view = layoutInflater.inflate(R.layout.item_swipe_effect, parent, false);
         ImageView ivProfile = view.findViewById(R.id.iv_profile);
//        ((TextView) view.findViewById(R.id.helloText)).setText(card.name);
        if (card.getImgUrl() != null) {
            ImageLoader.getInstance().displayImage(card.getImgUrl(),ivProfile,
                    ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS);
        }
        return view;
    }

    @Override public UserModel getItem(int position) {
        return cards.get(position);
    }

    @Override public int getCount() {
        return cards.size();
    }
}