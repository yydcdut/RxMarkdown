package com.yydcdut.rxmarkdown.span;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.text.Layout;
import android.text.style.QuoteSpan;

/**
 * Created by yuyidong on 16/5/15.
 */
public class CustomHorizontalRulesSpan extends QuoteSpan {
    private final int mColor;

    public CustomHorizontalRulesSpan() {
        super();
        mColor = 0xff0000ff;
    }

    public CustomHorizontalRulesSpan(int color) {
        super(color);
        mColor = color;
    }

    public CustomHorizontalRulesSpan(Parcel src) {
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

        int height = bottom - top;
        int width = layout.getWidth();
        p.setColor(Color.TRANSPARENT);
        c.drawRect(x, top, x + width, top + height / 4, p);
        p.setColor(mColor);
        c.drawRect(x, top + height / 4, x + width, top + height * 3 / 4, p);
        p.setColor(Color.TRANSPARENT);
        c.drawRect(x, top + height * 3 / 4, x + width, top + height, p);

        p.setStyle(style);
        p.setColor(color);

    }
}
