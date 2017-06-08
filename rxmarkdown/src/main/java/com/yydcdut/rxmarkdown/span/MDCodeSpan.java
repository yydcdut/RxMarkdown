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
package com.yydcdut.rxmarkdown.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcel;
import android.text.Layout;
import android.text.style.QuoteSpan;

/**
 * code syntax span
 * <p>
 * Created by yuyidong on 16/5/17.
 */
public class MDCodeSpan extends QuoteSpan {
    private static final int GAP_WIDTH_PLUS = 15;

    private final int mColor;
    private Drawable mDrawable;

    private String mLanguage;
    private MDCodeSpan mNext;

    /**
     * Constructor
     */
    public MDCodeSpan() {
        super();
        mColor = 0xff0000ff;
    }

    /**
     * Constructor
     *
     * @param color {@link QuoteSpan}
     */
    public MDCodeSpan(int color) {
        super(color);
        mColor = color;
    }

    /**
     * Constructor
     *
     * @param color       color {@link QuoteSpan}
     * @param isBeginning whether it's the beginning line of the code
     * @param isEnding    whether it's the ending line of the code
     */
    public MDCodeSpan(int color, String language, boolean isBeginning, boolean isEnding) {
        super(color);
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
     * @param next {@link MDCodeSpan}
     */
    public void setNext(MDCodeSpan next) {
        mNext = next;
    }

    /**
     * for edit code, get next span
     *
     * @return {@link MDCodeSpan}
     */
    public MDCodeSpan getNext() {
        return mNext;
    }

    /**
     * Constructor
     *
     * @param src {@link QuoteSpan}
     */
    public MDCodeSpan(Parcel src) {
        super(src);
        mColor = src.readInt();
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return super.getLeadingMargin(first) + GAP_WIDTH_PLUS;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                  int top, int baseline, int bottom,
                                  CharSequence text, int start, int end, boolean first, Layout layout) {
        if (mDrawable != null) {
            mDrawable.setBounds(x, top, x + layout.getWidth(), bottom);
            mDrawable.draw(c);
        } else {
            Paint.Style style = p.getStyle();
            int color = p.getColor();
            p.setStyle(Paint.Style.FILL);
            p.setColor(mColor);
            c.drawRect(x, top, x + layout.getWidth(), bottom, p);
            p.setStyle(style);
            p.setColor(color);
        }
    }
}
