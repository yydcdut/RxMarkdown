package com.yydcdut.rxmarkdown.grammar;

import android.support.annotation.NonNull;

/**
 * Created by yuyidong on 16/5/3.
 */
public interface IGrammar {

    boolean isMatch(@NonNull CharSequence charSequence);

    @NonNull
    CharSequence format(@NonNull CharSequence charSequence);
}
