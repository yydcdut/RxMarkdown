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
import android.graphics.RectF;
import android.text.Layout;
import android.text.style.QuoteSpan;

/**
 * horizontal rules grammar span
 * <p>
 * Created by yuyidong on 16/5/15.
 */
public class MDHorizontalRulesSpan extends QuoteSpan {
    private final int mColor;
    private int mHeight;

    /**
     * Constructor
     */
    public MDHorizontalRulesSpan() {
        super();
        mColor = 0xff0000ff;
        mHeight = -1;
    }

    /**
     * Constructor
     *
     * @param color  {@link QuoteSpan}
     * @param height the horizontal rules' height
     */
    public MDHorizontalRulesSpan(int color, int height) {
        super(color);
        mColor = color;
        mHeight = height;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                  int top, int baseline, int bottom,
                                  CharSequence text, int start, int end, boolean first, Layout layout) {
        Paint.Style style = p.getStyle();
        int color = p.getColor();

        p.setStyle(Paint.Style.FILL);
        p.setColor(mColor);

        int height = bottom - top;
        int width = layout.getWidth();

        if (mHeight == -1) {
            RectF rectF = new RectF(x, top + height * 2 / 5, x + width, bottom - height * 2 / 5);
            c.drawRoundRect(rectF, height / 2, height / 2, p);
        } else {
            RectF rectF = new RectF(x, top + (height - mHeight) / 2, x + width, bottom - (height - mHeight) / 2);
            c.drawRoundRect(rectF, mHeight / 2, mHeight / 2, p);
        }

        p.setStyle(style);
        p.setColor(color);
    }
}
