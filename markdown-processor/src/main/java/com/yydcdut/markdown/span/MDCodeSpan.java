/*
 * Copyright (C) 2018 yydcdut (yuyidong2015@gmail.com)
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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ReplacementSpan;

/**
 * Created by yuyidong on 2017/6/22.
 */
public class MDCodeSpan extends ReplacementSpan {
    private float round = 10f;
    private int bgColor;

    public MDCodeSpan(int bgColor) {
        this.bgColor = bgColor;
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        return Math.round(MeasureText(paint, text, start, end)) + 10;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        int textColor = paint.getColor();
        Paint.FontMetrics fm = paint.getFontMetrics();
        top = (int) (y + fm.ascent);
        bottom = (int) (y + fm.bottom);
        RectF rect = new RectF(x, top, x + MeasureText(paint, text, start, end) + 10, bottom);
        paint.setColor(bgColor);
        canvas.drawRoundRect(rect, round, round, paint);
        paint.setColor(textColor);
        canvas.drawText(text, start, end, x + 5, y, paint);
    }

    private float MeasureText(Paint paint, CharSequence text, int start, int end) {
        return paint.measureText(text, start, end);
    }
}
