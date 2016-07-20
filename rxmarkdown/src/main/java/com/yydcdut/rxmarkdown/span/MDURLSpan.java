package com.yydcdut.rxmarkdown.span;

import android.text.TextPaint;
import android.text.style.URLSpan;

/**
 * Created by yuyidong on 16/7/20.
 */
public class MDURLSpan extends URLSpan {
    private int mColor;
    private boolean isUnderLine;

    public MDURLSpan(String url, int color, boolean isLinkUnderline) {
        super(url);
        mColor = color;
        isUnderLine = isLinkUnderline;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(mColor);
        ds.setUnderlineText(isUnderLine);
    }
}
