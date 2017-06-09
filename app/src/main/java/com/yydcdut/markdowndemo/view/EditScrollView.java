package com.yydcdut.markdowndemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by yuyidong on 2017/6/9.
 */
public class EditScrollView extends ScrollView {
    public EditScrollView(Context context) {
        super(context);
    }

    public EditScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    private OnScrollChangedListener mOnScrollChangedListener;

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        mOnScrollChangedListener = onScrollChangedListener;
    }

    public static interface OnScrollChangedListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}
