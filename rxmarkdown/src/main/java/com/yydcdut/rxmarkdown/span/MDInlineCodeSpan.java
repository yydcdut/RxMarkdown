package com.yydcdut.rxmarkdown.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ReplacementSpan;

/**
 * Created by yuyidong on 2017/6/22.
 */
public class MDInlineCodeSpan extends ReplacementSpan {
    private float round = 10f;
    private int bgColor;

    public MDInlineCodeSpan(int bgColor) {
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
