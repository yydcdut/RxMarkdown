package com.yydcdut.rxmarkdown.callback;

import android.support.annotation.ColorInt;

public interface BlockquoteBackgroundNestedColorFetcher {
    @ColorInt
    int fetchBackgroundColorForNestingLevel(int nestingLevel);
}
