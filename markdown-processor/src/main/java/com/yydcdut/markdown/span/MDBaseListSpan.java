package com.yydcdut.markdown.span;

import android.text.style.BulletSpan;

/**
 * Created by yuyidong on 2018/5/28.
 */
public abstract class MDBaseListSpan extends BulletSpan {

    protected int mNested;
    protected MDBaseListSpan mParent;

    public MDBaseListSpan(int gapWidth, int color) {
        super(gapWidth, color);
    }

    /**
     * get list nested
     *
     * @return the nested number
     */
    public int getNested() {
        return mNested;
    }

    /**
     * get parent base list span
     *
     * @return {@link MDBaseListSpan}
     */
    public MDBaseListSpan getParent() {
        return mParent;
    }

    /**
     * set parent base list span
     *
     * @param parent {@link MDBaseListSpan}
     */
    public void setParent(MDBaseListSpan parent) {
        mParent = parent;
    }
}
