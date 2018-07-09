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

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.text.style.LineBackgroundSpan;

import com.yydcdut.markdown.utils.TextHelper;

/**
 * code syntax span
 * <p>
 * Created by yuyidong on 16/5/17.
 */
public class MDCodeBlockSpan implements LineBackgroundSpan {
    private final int mColor;
    private Drawable mDrawable;
    private String mText;

    private String mLanguage;
    private MDCodeBlockSpan mNext;

    /**
     * Constructor
     *
     * @param color the color
     */
    public MDCodeBlockSpan(int color) {
        mColor = color;
    }

    /**
     * Constructor
     *
     * @param color       color
     * @param isBeginning whether it's the beginning line of the code
     * @param isEnding    whether it's the ending line of the code
     * @param language    language
     * @param text        the begin or end line content
     */
    public MDCodeBlockSpan(int color, String language, boolean isBeginning, boolean isEnding, String text) {
        mColor = color;
        mLanguage = language;
        if (isBeginning || isEnding) {
            GradientDrawable d = new GradientDrawable();
            d.setColor(mColor);
            if (isBeginning && !isEnding) {
                d.setCornerRadii(new float[]{10, 10, 10, 10, 0, 0, 0, 0});
            } else if (!isBeginning && isEnding) {
                d.setCornerRadii(new float[]{0, 0, 0, 0, 10, 10, 10, 10});
            } else {
                d.setCornerRadius(10);
            }
            mDrawable = d;
            mText = text;
        }
    }

    /**
     * Code language
     *
     * @return code language
     */
    public String getLanguage() {
        return mLanguage;
    }

    /**
     * for edit code, set next span
     *
     * @param next {@link MDCodeBlockSpan}
     */
    public void setNext(MDCodeBlockSpan next) {
        mNext = next;
    }

    /**
     * for edit code, get next span
     *
     * @return {@link MDCodeBlockSpan}
     */
    public MDCodeBlockSpan getNext() {
        return mNext;
    }

    @Override
    public void drawBackground(Canvas c, Paint p,
                               int left, int right, int top, int baseline, int bottom,
                               CharSequence text, int start, int end, int lnum) {
        if (mDrawable != null && !TextUtils.isEmpty(mText) && (mText.startsWith(text.subSequence(TextHelper.safePosition(start, text), TextHelper.safePosition(end, text)).toString())
                || text.subSequence(TextHelper.safePosition(start, text), TextHelper.safePosition(end, text)).toString().startsWith(mText))) {
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        } else {
            Paint.Style style = p.getStyle();
            int color = p.getColor();
            p.setStyle(Paint.Style.FILL);
            p.setColor(mColor);
            c.drawRect(left, top, right, bottom, p);
            p.setStyle(style);
            p.setColor(color);
        }
    }
}
