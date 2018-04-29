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
package com.yydcdut.rxmarkdown.syntax.text;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.span.MDTodoSpan;
import com.yydcdut.rxmarkdown.syntax.SyntaxKey;

/**
 * The implementation of syntax for done.
 * syntax:
 * "- [ ] "
 * "* [ ] "
 * <p>
 * Created by yuyidong on 16/5/17.
 */
class TodoSyntax extends TextSyntaxAdapter {
    private int mColor;

    public TodoSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
        mColor = rxMDConfiguration.getTodoColor();
    }

    @Override
    boolean isMatch(@NonNull String text) {
        return text.startsWith(SyntaxKey.KEY_TODO_0) || text.startsWith(SyntaxKey.KEY_TODO_1);
    }

    @NonNull
    @Override
    boolean encode(@NonNull SpannableStringBuilder ssb) {
        return false;
    }

    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        ssb.delete(0, SyntaxKey.KEY_TODO_0.length());
        ssb.setSpan(new MDTodoSpan(mColor), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    @NonNull
    @Override
    void decode(@NonNull SpannableStringBuilder ssb) {
    }
}
