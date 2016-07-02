package com.yydcdut.rxmarkdown.edit;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.Grammar;

/**
 * Created by yuyidong on 16/6/28.
 */
public class EditToken implements Grammar {
    private final Object span;
    private final int start;
    private final int end;

    public EditToken(@NonNull Object span, int start, int end) {
        this.span = span;
        this.start = start;
        this.end = end;
    }

    public Object getSpan() {
        return span;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
