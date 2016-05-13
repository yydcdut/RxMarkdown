package com.yydcdut.rxmarkdown.chain;

import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;

import com.yydcdut.rxmarkdown.grammar.IGrammar;

import java.util.Arrays;

/**
 * Created by yuyidong on 16/5/4.
 */
public class MultiGrammarsChain implements IResponsibilityChain {
    private IGrammar[] mGrammars;

    private IResponsibilityChain mNextHandleGrammar = null;

    public MultiGrammarsChain(@Nullable IGrammar... grammars) {
        mGrammars = grammars;
    }

    @Nullable
    @Override
    public boolean handleGrammar(@Nullable SpannableStringBuilder ssb) {
        boolean handled = false;
        for (IGrammar iGrammar : mGrammars) {
            if (iGrammar.isMatch(ssb.toString())) {
                ssb = iGrammar.format(ssb);
                handled |= true;
            }
        }
        if (handled) {
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
        return "MultiGrammarsChain{" +
                "mGrammars=" + Arrays.toString(mGrammars) +
                ", mNextHandleGrammar=" + mNextHandleGrammar +
                '}';
    }
}
