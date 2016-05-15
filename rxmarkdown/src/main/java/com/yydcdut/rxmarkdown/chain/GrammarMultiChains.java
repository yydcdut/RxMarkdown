package com.yydcdut.rxmarkdown.chain;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.grammar.IGrammar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyidong on 16/5/4.
 */
public class GrammarMultiChains implements IChain {
    private IGrammar mGrammar;

    private List<IChain> mNextHandleGrammarList = null;

    public GrammarMultiChains(@NonNull IGrammar grammar) {
        mGrammar = grammar;
    }

    @NonNull
    @Override
    public boolean handleGrammar(@NonNull CharSequence charSequence) {
        if (mGrammar.isMatch(charSequence)) {
            mGrammar.format(charSequence);
        }
        if (mNextHandleGrammarList != null) {
            boolean handled = false;
            for (IChain responsibilityChain : mNextHandleGrammarList) {
                handled |= responsibilityChain.handleGrammar(charSequence);
            }
            return handled;
        } else {
            return false;
        }
    }

    @Override
    public boolean addNextHandleGrammar(@NonNull IChain nextHandleGrammar) {
        if (mNextHandleGrammarList == null) {
            mNextHandleGrammarList = new ArrayList<>();
        }
        mNextHandleGrammarList.add(nextHandleGrammar);
        return true;
    }

    @Override
    @Deprecated
    public boolean setNextHandleGrammar(@NonNull IChain nextHandleGrammar) {
        return false;
    }

    @Override
    public String toString() {
        return "GrammarMultiChains{" +
                "mGrammar=" + mGrammar +
                ", mNextHandleGrammarList=" + mNextHandleGrammarList +
                '}';
    }
}
