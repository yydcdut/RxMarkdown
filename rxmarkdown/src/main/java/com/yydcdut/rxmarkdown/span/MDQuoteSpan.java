package com.yydcdut.rxmarkdown.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.QuoteSpan;

import com.yydcdut.rxmarkdown.grammar.IGrammar;

/**
 * Created by yuyidong on 16/5/15.
 */
public class MDQuoteSpan extends QuoteSpan {
    private static final int GAP_WIDTH_PLUS = 5;
    private static final int DRAWING_X_PLUS = 10;
    private static final int QUOTE_WIDTH_PLUS = 2;

    private int mNested = 1;

    private boolean isEdit;

    public MDQuoteSpan(int color, int nested, boolean isEdit) {
        super(color);
        mNested = nested;
        this.isEdit = isEdit;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        if (isEdit) {
            return super.getLeadingMargin(first);
        } else {
            return mNested * (super.getLeadingMargin(first) + GAP_WIDTH_PLUS);
        }
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom, CharSequence text, int start, int end, boolean first, Layout layout) {
        if (isEdit) {
            float length = p.measureText(IGrammar.KEY_BLOCK_QUOTES);
            for (int i = 1; i <= mNested; i++) {
                super.drawLeadingMargin(c, p, (int) (x + (i - 1) * length), dir + QUOTE_WIDTH_PLUS, top, baseline, bottom, text, start, end, first, layout);
            }
        } else {
            for (int i = 1; i <= mNested; i++) {
                super.drawLeadingMargin(c, p, x + i * DRAWING_X_PLUS, dir + QUOTE_WIDTH_PLUS, top, baseline, bottom, text, start, end, first, layout);
            }
        }
    }
}
