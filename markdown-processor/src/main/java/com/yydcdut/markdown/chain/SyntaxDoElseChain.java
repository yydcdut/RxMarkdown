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
package com.yydcdut.markdown.chain;

import android.support.annotation.NonNull;

import com.yydcdut.markdown.syntax.Syntax;

import java.util.ArrayList;
import java.util.List;

/**
 * First, this chain can set one or more next chains.
 * Second, if chain can handle it, then, let other chains to handle it too.
 * <p>
 * Created by yuyidong on 16/5/20.
 */
public class SyntaxDoElseChain implements ISpecialChain {
    private Syntax mSyntax;

    private ISpecialChain mNextHandleSyntax = null;
    private List<ISpecialChain> mNextHandleSyntaxList = null;

    /**
     * Constructor
     *
     * @param syntax the syntax
     */
    public SyntaxDoElseChain(@NonNull Syntax syntax) {
        mSyntax = syntax;
    }

    @NonNull
    @Override
    public boolean handleSyntax(@NonNull CharSequence charSequence, int lineNumber) {
        if (mSyntax.isMatch(charSequence)) {
            mSyntax.format(charSequence, lineNumber);
            boolean handled = false;
            for (ISpecialChain responsibilityChain : mNextHandleSyntaxList) {
                handled |= responsibilityChain.handleSyntax(charSequence, lineNumber);
            }
            return handled;
        } else {
            if (mNextHandleSyntax != null) {
                return mNextHandleSyntax.handleSyntax(charSequence, lineNumber);
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean addNextHandleSyntax(@NonNull ISpecialChain nextHandleSyntax) {
        if (mNextHandleSyntaxList == null) {
            mNextHandleSyntaxList = new ArrayList<>();
        }
        mNextHandleSyntaxList.add(nextHandleSyntax);
        return true;
    }

    @Override
    public boolean setNextHandleSyntax(@NonNull ISpecialChain nextHandleSyntax) {
        mNextHandleSyntax = nextHandleSyntax;
        return true;
    }
}
