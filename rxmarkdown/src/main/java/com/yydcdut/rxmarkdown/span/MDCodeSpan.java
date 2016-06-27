package com.yydcdut.rxmarkdown.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.text.Layout;
import android.text.style.QuoteSpan;

/**
 * Created by yuyidong on 16/5/17.
 */
public class MDCodeSpan extends QuoteSpan {
    private static final int GAP_WIDTH_PLUS = 15;

    private final int mColor;

    public MDCodeSpan() {
        super();
        mColor = 0xff0000ff;
    }

    public MDCodeSpan(int color) {
        super(color);
        mColor = color;
    }

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
        Paint.Style style = p.getStyle();
        int color = p.getColor();

        p.setStyle(Paint.Style.FILL);
        p.setColor(mColor);

        c.drawRect(x, top, x + layout.getWidth(), bottom, p);

        p.setStyle(style);
        p.setColor(color);
    }
}
