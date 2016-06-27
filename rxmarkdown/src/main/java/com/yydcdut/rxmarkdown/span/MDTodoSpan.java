package com.yydcdut.rxmarkdown.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Parcel;
import android.text.Layout;
import android.text.style.QuoteSpan;

/**
 * Created by yuyidong on 16/5/17.
 */
public class MDTodoSpan extends QuoteSpan {
    private static final int GAP_WIDTH_PLUS = 10;

    private int mMarginLength = 50;

    protected final int mColor;

    public MDTodoSpan() {
        super();
        mColor = 0xff0000ff;
    }

    public MDTodoSpan(int color) {
        super(color);
        mColor = color;
    }

    public MDTodoSpan(Parcel src) {
        super(src);
        mColor = src.readInt();
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return super.getLeadingMargin(first) + GAP_WIDTH_PLUS + mMarginLength;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                  int top, int baseline, int bottom,
                                  CharSequence text, int start, int end, boolean first, Layout layout) {
        Paint.Style style = p.getStyle();
        float strokeWidth = p.getStrokeWidth();
        int color = p.getColor();

        float height = bottom - top;
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(height * 1 / 10 > 2 ? height * 1 / 9 : 2f);
        p.setColor(mColor);

        RectF rectF = new RectF(x + height * 1 / 9, top + height * 1 / 9, x + height * 8 / 9, bottom - height * 1 / 9);
        c.drawRoundRect(rectF, height * 2 / 9, height * 2 / 9, p);

        p.setStyle(style);
        p.setColor(color);
        p.setStrokeWidth(strokeWidth);

        mMarginLength = (int) height;
    }

}
