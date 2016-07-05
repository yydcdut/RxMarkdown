package com.yydcdut.rxmarkdown.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.QuoteSpan;

/**
 * Created by yuyidong on 16/5/15.
 */
public class MDQuoteSpan extends QuoteSpan {
    /**
     * {@link com.yydcdut.rxmarkdown.grammar.edit.BlockQuotesGrammar#KEY_BLOCK_QUOTES}
     * {@link com.yydcdut.rxmarkdown.grammar.android.BlockQuotesGrammar#KEY_BLOCK_QUOTES}
     */
    protected static final String KEY_BLOCK_QUOTES = "> ";

    private static final int QUOTE_WIDTH_PLUS = 2;

    private int mNested = 1;

    public MDQuoteSpan(int color, int nested) {
        super(color);
        mNested = nested;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom, CharSequence text, int start, int end, boolean first, Layout layout) {
        float length = p.measureText(KEY_BLOCK_QUOTES);
        for (int i = 1; i <= mNested; i++) {
            super.drawLeadingMargin(c, p, (int) (x + (i - 1) * length), dir + QUOTE_WIDTH_PLUS, top, baseline, bottom, text, start, end, first, layout);
        }
    }
}
