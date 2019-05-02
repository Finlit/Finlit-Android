package com.app.finlit.utils.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by jaspreet on 29/12/17.
 */

public class ResponsiveScrollView extends ScrollView {

    public interface OnEndScrollListener {
        public void onEndScroll();
    }

    private boolean mIsFling;
    private OnEndScrollListener mOnEndScrollListener;

    public ResponsiveScrollView(Context context) {
        this(context, null, 0);
    }

    public ResponsiveScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ResponsiveScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        mIsFling = true;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        View view = (View) getChildAt(getChildCount()-1);
        int diff = (view.getBottom()-(getHeight()+getScrollY()));
        if (diff <= 0 && mOnEndScrollListener != null) {
//            if (mIsFling) {
//                mIsFling = false;
//                return;
//            }
            mOnEndScrollListener.onEndScroll();
        }
//        if (mIsFling) {
//            if (Math.abs(y - oldY) < 2 || y >= getMeasuredHeight() || y == 0) {
//                if (mOnEndScrollListener != null) {
//                    mOnEndScrollListener.onEndScroll();
//                }
//                mIsFling = false;
//            }
//        }
    }

    public OnEndScrollListener getOnEndScrollListener() {
        return mOnEndScrollListener;
    }

    public void setOnEndScrollListener(OnEndScrollListener mOnEndScrollListener) {
        this.mOnEndScrollListener = mOnEndScrollListener;
    }

}