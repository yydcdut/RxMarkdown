package com.yydcdut.markdown.callback;

import android.view.View;

/**
 * the callback of _todo syntax
 *
 * Created by yuyidong on 2018/5/17.
 */
public interface OnTodoClickCallback {
    /**
     * the click callback
     *
     * @param view the view
     * @param line the line text
     * @return the TextView
     */
    CharSequence onTodoClicked(View view, String line, int lineNumber);
}
