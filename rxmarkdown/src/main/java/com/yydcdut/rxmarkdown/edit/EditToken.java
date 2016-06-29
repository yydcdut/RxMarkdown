package com.yydcdut.rxmarkdown.edit;

import com.yydcdut.rxmarkdown.Grammar;

/**
 * Created by yuyidong on 16/6/28.
 */

public class EditToken implements Grammar {
    private Object span;
    private int start;
    private int end;

    public EditToken(Object span, int start, int end) {
        this.span = span;
        this.start = start;
        this.end = end;
    }

    public void changeStart(int start) {
        this.start = start;
    }

    public void changeEnd(int end) {
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
