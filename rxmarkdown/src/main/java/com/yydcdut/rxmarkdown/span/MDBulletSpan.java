package com.yydcdut.rxmarkdown.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.BulletSpan;

/**
 * Created by yuyidong on 16/5/21.
 */
public class MDBulletSpan extends BulletSpan {
    private int mNested = 0;
    private static final int NESTED_MARGIN_LENGTH = 9;
    private static final int GAP_WIDTH_PLUS = 10;

    public MDBulletSpan(int gapWidth, int color, int nested) {
        super(gapWidth, color);
        mNested = nested;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return super.getLeadingMargin(first) + GAP_WIDTH_PLUS * (mNested + 1);
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom, CharSequence text, int start, int end, boolean first, Layout l) {
        super.drawLeadingMargin(c, p, x + (mNested + 1) * NESTED_MARGIN_LENGTH, dir, top, baseline, bottom, text, start, end, first, l);
    }
}
