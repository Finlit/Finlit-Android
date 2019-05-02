package com.app.finlit.ui.authenticate.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.finlit.R;

import java.util.List;

/**
 * Created by MANISH-PC on 8/13/2018.
 */

public class SpinnerAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> mList;

    public SpinnerAdapter(Context mContext, List<String> mList) {
        super();
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.spinner_background, parent,false);
            holder.txtValue = (TextView) convertView.findViewById(R.id.txt_dropdown);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(position == 0){
            holder.txtValue.setTextColor(ContextCompat.getColor(mContext, R.color.hint_color));
        } else {
            holder.txtValue.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        }
        holder.txtValue.setText(mList.get(position));
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.spinner_dropdown, parent, false);
            holder.txtValue = (TextView) convertView.findViewById(R.id.txt_spinner);
            holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relative_main);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == 0)
            holder.txtValue.setVisibility(View.GONE);
        else
            holder.txtValue.setVisibility(View.VISIBLE);

        holder.txtValue.setText(mList.get(position));

        return convertView;
    }

    private class ViewHolder {
        TextView txtValue;
        RelativeLayout relativeLayout;
    }

}
