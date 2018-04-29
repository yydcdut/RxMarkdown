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

import com.yydcdut.rxmarkdown.syntax.Syntax;

/**
 * It is real chain of responsibility pattern.
 * <p>
 * Created by yuyidong on 16/5/4.
 */
public class SyntaxChain implements ISpecialChain {
    private Syntax mSyntax;

    private ISpecialChain mNextHandleSyntax = null;

    /**
     * Constructor
     *
     * @param syntax the syntax
     */
    public SyntaxChain(@NonNull Syntax syntax) {
        mSyntax = syntax;
    }

    @NonNull
    @Override
    public boolean handleSyntax(@NonNull CharSequence charSequence) {
        if (mSyntax.isMatch(charSequence)) {
            mSyntax.format(charSequence);
            return true;
        } else {
            if (mNextHandleSyntax != null) {
                return mNextHandleSyntax.handleSyntax(charSequence);
            } else {
                return false;
            }
        }
    }

    /**
     * @param nextHandleSyntax the next chain
     * @return boolean
     * @deprecated
     */
    @Override
    @Deprecated
    public boolean addNextHandleSyntax(@NonNull ISpecialChain nextHandleSyntax) {
        return false;
    }

    @Override
    public boolean setNextHandleSyntax(@NonNull ISpecialChain nextHandleSyntax) {
        mNextHandleSyntax = nextHandleSyntax;
        return true;
    }
}
