package com.yydcdut.rxmarkdown.chain;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.grammar.IGrammar;


/**
 * Created by yuyidong on 16/5/4.
 */
public class GrammarSingleChain implements IChain {
    private IGrammar mGrammar;

    private IChain mNextHandleGrammar = null;

    public GrammarSingleChain(@NonNull IGrammar grammar) {
        mGrammar = grammar;
    }

    @NonNull
    @Override
    public boolean handleGrammar(@NonNull CharSequence charSequence) {
        if (mGrammar.isMatch(charSequence)) {
            mGrammar.format(charSequence);
            return true;
        } else {
            if (mNextHandleGrammar != null) {
                return mNextHandleGrammar.handleGrammar(charSequence);
            } else {
                return false;
            }
        }
    }

    @Override
    @Deprecated
    public boolean addNextHandleGrammar(@NonNull IChain nextHandleGrammar) {
        return false;
    }

    @Override
    public boolean setNextHandleGrammar(@NonNull IChain nextHandleGrammar) {
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
