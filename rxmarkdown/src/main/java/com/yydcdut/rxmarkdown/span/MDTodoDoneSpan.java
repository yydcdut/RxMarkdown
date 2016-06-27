package com.yydcdut.rxmarkdown.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.text.Layout;

/**
 * Created by yuyidong on 16/5/17.
 */
public class MDTodoDoneSpan extends MDTodoSpan {
    public MDTodoDoneSpan() {
        super();
    }

    public MDTodoDoneSpan(int color) {
        super(color);
    }

    public MDTodoDoneSpan(Parcel src) {
        super(src);
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom, CharSequence text, int start, int end, boolean first, Layout layout) {
        super.drawLeadingMargin(c, p, x, dir, top, baseline, bottom, text, start, end, first, layout);
        Paint.Style style = p.getStyle();
        float strokeWidth = p.getStrokeWidth();
        int color = p.getColor();

        float height = bottom - top;

        p.setStyle(Paint.Style.FILL);
        p.setStrokeWidth((int) (height * 1 / 10 > 2 ? height * 1 / 9 : 2f));
        p.setColor(mColor);

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
