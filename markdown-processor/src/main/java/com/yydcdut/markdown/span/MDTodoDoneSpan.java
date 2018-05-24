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
import android.text.Layout;

/**
 * done syntax span
 * <p>
 * Created by yuyidong on 16/5/17.
 */
public class MDTodoDoneSpan extends MDTodoSpan {

    /**
     * Constructor
     *
     * @param color      {@link MDTodoSpan}
     * @param lineNumber line number
     */
    public MDTodoDoneSpan(int color, int lineNumber) {
        super(color, lineNumber);
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom, CharSequence text, int start, int end, boolean first, Layout layout) {
        if (!first) {
            return;
        }
        super.drawLeadingMargin(c, p, x, dir, top, baseline, bottom, text, start, end, first, layout);
        Paint.Style style = p.getStyle();
        float strokeWidth = p.getStrokeWidth();
        int color = p.getColor();

        float height = bottom - top;

        p.setStyle(Paint.Style.FILL);
        p.setStrokeWidth((int) (height * 1 / 10 > 2 ? height * 1 / 9 : 2f));
        p.setColor(mColor);
        p.setStrokeJoin(Paint.Join.ROUND);
        p.setStrokeCap(Paint.Cap.ROUND);

        float boxInsideLength = height * 7 / 9 - (height * 1 / 10 > 2 ? height * 1 / 9 : 2f) * 2;
        float boxBoard = height * 1 / 10 > 2 ? height * 1 / 9 : 2f;

        c.drawLine(
                (x + height * 1 / 9) + boxBoard + boxInsideLength * 1 / 10, // margin + board + inside / 10
                (top + height * 1 / 9) + boxBoard + boxInsideLength * 5 / 10, // margin + board + inside * 5 / 10
                (x + height * 1 / 9) + boxBoard + boxInsideLength * 4 / 10, // margin + board + inside * 4 / 10
                (top + height * 1 / 9) + boxBoard + boxInsideLength * 9 / 10, // margin + board + inside * 9 / 10
                p
        );

        c.drawLine((x + height * 1 / 9) + boxBoard + boxInsideLength * 4 / 10, // margin + board + inside * 4 / 10
                (top + height * 1 / 9) + boxBoard + boxInsideLength * 9 / 10, // margin + board + inside * 9 / 10)
                (x + height * 1 / 9) + boxBoard + boxInsideLength * 9 / 10, // margin + board + inside * 9 / 10
                (top + height * 1 / 9) + boxBoard + boxInsideLength * 1 / 10, // margin + board + inside * 1 / 10
                p);

        p.setStyle(style);
        p.setColor(color);
        p.setStrokeWidth(strokeWidth);
    }
}
