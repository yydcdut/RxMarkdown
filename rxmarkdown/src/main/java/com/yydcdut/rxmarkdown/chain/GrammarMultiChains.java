package com.yydcdut.rxmarkdown.chain;

import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;

import com.yydcdut.rxmarkdown.grammar.IGrammar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyidong on 16/5/4.
 */
public class GrammarMultiChains implements IResponsibilityChain {
    private IGrammar mGrammar;

    private List<IResponsibilityChain> mNextHandleGrammarList = null;

    public GrammarMultiChains(@Nullable IGrammar grammar) {
        mGrammar = grammar;
    }

    @Nullable
    @Override
    public boolean handleGrammar(@Nullable SpannableStringBuilder ssb) {
        if (mGrammar.isMatch(ssb.toString())) {
            mGrammar.format(ssb);
        }
        if (mNextHandleGrammarList != null) {
            boolean handled = false;
            for (IResponsibilityChain responsibilityChain : mNextHandleGrammarList) {
                handled |= responsibilityChain.handleGrammar(ssb);
            }
            return handled;
        } else {
            return false;
        }
    }

    @Override
    public boolean addNextHandleGrammar(@Nullable IResponsibilityChain nextHandleGrammar) {
        if (mNextHandleGrammarList == null) {
            mNextHandleGrammarList = new ArrayList<>();
        }
        mNextHandleGrammarList.add(nextHandleGrammar);
        return true;
    }

    @Override
    @Deprecated
    public boolean setNextHandleGrammar(@Nullable IResponsibilityChain nextHandleGrammar) {
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
