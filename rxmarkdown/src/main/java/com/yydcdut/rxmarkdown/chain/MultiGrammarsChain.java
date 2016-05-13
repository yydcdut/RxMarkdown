package com.yydcdut.rxmarkdown.chain;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.grammar.IGrammar;

import java.util.Arrays;

/**
 * Created by yuyidong on 16/5/4.
 */
public class MultiGrammarsChain implements IResponsibilityChain {
    private IGrammar[] mGrammars;

    private IResponsibilityChain mNextHandleGrammar = null;

    public MultiGrammarsChain(@NonNull IGrammar... grammars) {
        mGrammars = grammars;
    }

    @NonNull
    @Override
    public boolean handleGrammar(@NonNull CharSequence charSequence) {
        boolean handled = false;
        for (IGrammar iGrammar : mGrammars) {
            if (iGrammar.isMatch(charSequence)) {
                charSequence = iGrammar.format(charSequence);
                handled |= true;
            }
        }
        if (handled) {
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
    public boolean addNextHandleGrammar(@NonNull IResponsibilityChain nextHandleGrammar) {
        return false;
    }

    @Override
    public boolean setNextHandleGrammar(@NonNull IResponsibilityChain nextHandleGrammar) {
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
