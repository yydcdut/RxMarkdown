package com.yydcdut.rxmarkdown.grammar;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.Grammar;

/**
 * Created by yuyidong on 16/5/3.
 */
public interface IGrammar extends Grammar {

    boolean isMatch(@NonNull CharSequence charSequence);

    @NonNull
    CharSequence format(@NonNull CharSequence charSequence);
}
