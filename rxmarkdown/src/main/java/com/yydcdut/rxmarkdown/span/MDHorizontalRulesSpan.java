package com.yydcdut.rxmarkdown.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Parcel;
import android.text.Layout;
import android.text.style.QuoteSpan;

/**
 * Created by yuyidong on 16/5/15.
 */
public class MDHorizontalRulesSpan extends QuoteSpan {
    private final int mColor;

    public MDHorizontalRulesSpan() {
        super();
        mColor = 0xff0000ff;
    }

    public MDHorizontalRulesSpan(int color) {
        super(color);
        mColor = color;
    }

    public MDHorizontalRulesSpan(Parcel src) {
        super(src);
        mColor = src.readInt();
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

        RectF rectF = new RectF(x, top + height * 2 / 5, x + width, bottom - height * 2 / 5);
        c.drawRoundRect(rectF, height / 2, height / 2, p);

        p.setStyle(style);
        p.setColor(color);
    }
}
