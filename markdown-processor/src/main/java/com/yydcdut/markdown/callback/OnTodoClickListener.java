package com.yydcdut.markdown.callback;

import android.text.SpannableStringBuilder;
import android.view.View;

/**
 * the listener of _todo syntax
 *
 * Created by yuyidong on 2018/5/17.
 */
public interface OnTodoClickListener {

    /**
     * the click callback
     *
     * @param view the view
     * @param ssb  the line text
     */
    void onTodoClicked(View view, SpannableStringBuilder ssb);
}
