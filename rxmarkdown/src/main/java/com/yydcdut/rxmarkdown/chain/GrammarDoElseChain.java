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

import com.yydcdut.rxmarkdown.grammar.IGrammar;

import java.util.ArrayList;
import java.util.List;

/**
 * First, this chain can set one or more next chains.
 * Second, if chain can handle it, then, let other chains to handle it too.
 * <p>
 * Created by yuyidong on 16/5/20.
 */
public class GrammarDoElseChain implements IChain {
    private IGrammar mGrammar;

    private IChain mNextHandleGrammar = null;
    private List<IChain> mNextHandleGrammarList = null;

    /**
     * Constructor
     *
     * @param grammar the grammar
     */
    public GrammarDoElseChain(@NonNull IGrammar grammar) {
        mGrammar = grammar;
    }

    @NonNull
    @Override
    public boolean handleGrammar(@NonNull CharSequence charSequence) {
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
