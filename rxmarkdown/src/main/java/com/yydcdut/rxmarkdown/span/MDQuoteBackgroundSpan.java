package com.yydcdut.rxmarkdown.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.style.LineBackgroundSpan;

import com.yydcdut.rxmarkdown.callback.BlockQuoteBackgroundNestedColorFetcher;

public class MDQuoteBackgroundSpan implements LineBackgroundSpan {
    private final int nestingLevel;
    private final int nestingMargin;
    private final BlockQuoteBackgroundNestedColorFetcher colorFetcher;
    private Rect rect = new Rect();

    public MDQuoteBackgroundSpan(int nestingLevel, int nestingMargin, BlockQuoteBackgroundNestedColorFetcher colorFetcher) {
        this.nestingLevel = nestingLevel;
        this.nestingMargin = nestingMargin;
        this.colorFetcher = colorFetcher;
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        int paintColor = p.getColor();
        for (int i = 0; i < nestingLevel; i++) {
            p.setColor(colorFetcher.fetchBackgroundColorForNestingLevel(i));
            rect.set(left + (i * nestingMargin), top, (i == nestingLevel - 1 ? right : left + ((i + 1) * nestingMargin)), bottom);
            c.drawRect(rect, p);
        }
        p.setColor(paintColor);
    }
}
