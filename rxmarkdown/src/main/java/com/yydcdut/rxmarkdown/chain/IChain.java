package com.yydcdut.rxmarkdown.chain;

import android.support.annotation.NonNull;

/**
 * Created by yuyidong on 16/5/4.
 */
public interface IChain {
    @NonNull
    boolean handleGrammar(@NonNull CharSequence ssb);

    boolean addNextHandleGrammar(@NonNull IChain nextHandleGrammar);

    boolean setNextHandleGrammar(@NonNull IChain nextHandleGrammar);

}
