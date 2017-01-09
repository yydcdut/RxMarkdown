package com.yydcdut.rxmarkdown.callback;

import android.support.annotation.ColorInt;

public interface BlockQuoteBackgroundNestedColorFetcher {
    @ColorInt
    int fetchBackgroundColorForNestingLevel(int nestingLevel);
}
