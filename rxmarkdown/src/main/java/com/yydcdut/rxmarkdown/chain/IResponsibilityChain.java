package com.yydcdut.rxmarkdown.chain;

import android.support.annotation.NonNull;

/**
 * Created by yuyidong on 16/5/4.
 */
public interface IResponsibilityChain {
    @NonNull
    boolean handleGrammar(@NonNull CharSequence ssb);

    boolean addNextHandleGrammar(@NonNull IResponsibilityChain nextHandleGrammar);

    boolean setNextHandleGrammar(@NonNull IResponsibilityChain nextHandleGrammar);

}
