/*
 * Copyright (C) 2016 yydcdut (yuyidong2015@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.yydcdut.rxmarkdown.chain;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.syntax.IGrammar;

/**
 * It is real chain of responsibility pattern.
 * <p>
 * Created by yuyidong on 16/5/4.
 */
public class GrammarSingleChain implements IChain {
    private IGrammar mGrammar;

    private IChain mNextHandleGrammar = null;

    /**
     * Constructor
     *
     * @param grammar the grammar
     */
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

    /**
     * @param nextHandleGrammar the next chain
     * @return boolean
     * @deprecated
     */
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
