package com.yydcdut.rxmarkdown.chain;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.grammar.IGrammar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyidong on 16/5/20.
 */
public class GrammarDoElseChain implements IChain {
    private IGrammar mGrammar;

    private IChain mNextHandleGrammar = null;
    private List<IChain> mNextHandleGrammarList = null;

    public GrammarDoElseChain(@NonNull IGrammar grammar) {
        mGrammar = grammar;
    }

    @NonNull
    @Override
    public boolean handleGrammar(@NonNull CharSequence charSequence) {
        boolean success = false;
        if (mGrammar.isMatch(charSequence)) {
            mGrammar.format(charSequence);
            boolean handled = false;
            for (IChain responsibilityChain : mNextHandleGrammarList) {
                handled |= responsibilityChain.handleGrammar(charSequence);
            }
            return handled;
        } else {
            if (mNextHandleGrammar != null) {
                return mNextHandleGrammar.handleGrammar(charSequence);
            } else {
                return false;
            }
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
    public boolean setNextHandleGrammar(@NonNull IChain nextHandleGrammar) {
        mNextHandleGrammar = nextHandleGrammar;
        return true;
    }
}
