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
 * to do grammar span
 * <p>
 * Created by yuyidong on 16/5/17.
 */
public class MDTodoSpan extends QuoteSpan {
    private static final int GAP_WIDTH_PLUS = 10;

    private int mMarginLength = 50;

    protected final int mColor;
    protected final int mLineNumber;

    /**
     * Constructor
     *
     * @param color      {@link QuoteSpan}
     * @param lineNumber line number
     */
    public MDTodoSpan(int color, int lineNumber) {
        super(color);
        mColor = color;
        mLineNumber = lineNumber;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return super.getLeadingMargin(first) + GAP_WIDTH_PLUS + mMarginLength;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom,
                                  CharSequence text, int start, int end, boolean first, Layout layout) {
        if (!first) {
            return;
        }
        Paint.Style style = p.getStyle();
        float strokeWidth = p.getStrokeWidth();
        int color = p.getColor();

        float height = bottom - top;
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(height * 1 / 10 > 2 ? height * 1 / 9 : 2f);
        p.setColor(mColor);
        p.setStrokeJoin(Paint.Join.ROUND);
        p.setStrokeCap(Paint.Cap.ROUND);

        RectF rectF = new RectF(x + height * 1 / 9, top + height * 1 / 9, x + height * 8 / 9, bottom - height * 1 / 9);
        c.drawRoundRect(rectF, height * 2 / 9, height * 2 / 9, p);

        p.setStyle(style);
        p.setColor(color);
        p.setStrokeWidth(strokeWidth);

        mMarginLength = (int) height;
    }

    public int getLineNumber() {
        return mLineNumber;
    }
}
