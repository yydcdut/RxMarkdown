package com.yydcdut.rxmarkdown.chain;

import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;

import com.yydcdut.rxmarkdown.grammar.IGrammar;


/**
 * Created by yuyidong on 16/5/4.
 */
public class GrammarSingleChain implements IResponsibilityChain {
    private IGrammar mGrammar;

    private IResponsibilityChain mNextHandleGrammar = null;

    public GrammarSingleChain(@Nullable IGrammar grammar) {
        mGrammar = grammar;
    }

    @Nullable
    @Override
    public boolean handleGrammar(@Nullable SpannableStringBuilder ssb) {
        if (mGrammar.isMatch(ssb.toString())) {
            mGrammar.format(ssb);
            return true;
        } else {
            if (mNextHandleGrammar != null) {
                return mNextHandleGrammar.handleGrammar(ssb);
            } else {
                return false;
            }
        }
    }

    @Override
    @Deprecated
    public boolean addNextHandleGrammar(@Nullable IResponsibilityChain nextHandleGrammar) {
        return false;
    }

    @Override
    public boolean setNextHandleGrammar(@Nullable IResponsibilityChain nextHandleGrammar) {
        mNextHandleGrammar = nextHandleGrammar;
        return true;
    }

    @Override
    public String toString() {
        return "GrammarSingleChain{" +
                "mGrammar=" + mGrammar +
                ", mNextHandleGrammar=" + mNextHandleGrammar +
                '}';
    }
}
