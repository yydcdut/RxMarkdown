/*
 * Copyright (C) 2016 yydcdut (yuyidong2015@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.yydcdut.markdown.span;

import android.text.TextPaint;
import android.text.style.URLSpan;
import android.view.View;

import com.yydcdut.markdown.callback.OnLinkClickCallback;

/**
 * hyper link syntax span
 * <p>
 * Created by yuyidong on 16/7/20.
 */
public class MDURLSpan extends URLSpan {
    private int mColor;
    private boolean isUnderLine;
    private OnLinkClickCallback mOnLinkClickCallback;

    public MDURLSpan(String url, int color, boolean isLinkUnderline, OnLinkClickCallback onLinkClickCallback) {
        super(url);
        mColor = color;
        isUnderLine = isLinkUnderline;
        mOnLinkClickCallback = onLinkClickCallback;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(mColor);
        ds.setUnderlineText(isUnderLine);
    }

    @Override
    public void onClick(View widget) {
        if (mOnLinkClickCallback != null) {
            mOnLinkClickCallback.onLinkClicked(widget, getURL());
        } else {
            super.onClick(widget);
        }
    }
}
