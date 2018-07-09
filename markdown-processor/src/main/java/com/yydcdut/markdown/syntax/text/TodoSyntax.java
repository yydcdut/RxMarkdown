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
package com.yydcdut.markdown.syntax.text;

import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.callback.OnTodoClickCallback;
import com.yydcdut.markdown.callback.OnTodoClickListener;
import com.yydcdut.markdown.span.MDTodoDoneSpan;
import com.yydcdut.markdown.span.MDTodoSpan;
import com.yydcdut.markdown.syntax.SyntaxKey;
import com.yydcdut.markdown.utils.SyntaxUtils;
import com.yydcdut.markdown.utils.TextHelper;

/**
 * The implementation of syntax for done.
 * syntax:
 * "- [ ] "
 * "* [ ] "
 * <p>
 * Created by yuyidong on 16/5/17.
 */
class TodoSyntax extends TextSyntaxAdapter implements OnTodoClickListener {
    private int mTodoColor;
    private int mDoneColor;
    private OnTodoClickCallback mOnTodoClickCallback;

    public TodoSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        super(markdownConfiguration);
        mTodoColor = markdownConfiguration.getTodoColor();
        mDoneColor = markdownConfiguration.getTodoDoneColor();
        mOnTodoClickCallback = markdownConfiguration.getOnTodoClickCallback();
    }

    @Override
    boolean isMatch(@NonNull String text) {
        return text.startsWith(SyntaxKey.KEY_TODO_HYPHEN) || text.startsWith(SyntaxKey.KEY_TODO_ASTERISK);
    }

    @NonNull
    @Override
    boolean encode(@NonNull SpannableStringBuilder ssb) {
        return false;
    }

    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb, int lineNumber) {
        SyntaxUtils.setTodoOrDoneClick(SyntaxKey.KEY_TODO_HYPHEN.length(), ssb, this);
        MDTodoSpan mdTodoSpan = new MDTodoSpan(mTodoColor, lineNumber);
        ssb.setSpan(mdTodoSpan, 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    @NonNull
    @Override
    void decode(@NonNull SpannableStringBuilder ssb) {
    }

    @Override
    public void onTodoClicked(View view, SpannableStringBuilder ssb) {
        if (mOnTodoClickCallback == null) {
            return;
        }
        MDTodoSpan[] ssbArray = ssb.getSpans(0, ssb.length(), MDTodoSpan.class);
        if (ssbArray == null || ssbArray.length != 1) {
            return;
        }
        int lineNumber = -1;
        String line = null;
        if (ssbArray[0] instanceof MDTodoDoneSpan) {
            lineNumber = ssbArray[0].getLineNumber();
            line = TextHelper.formatTodoLine(ssb, true);
        } else if (ssbArray[0] instanceof MDTodoSpan) {
            lineNumber = ssbArray[0].getLineNumber();
            line = TextHelper.formatTodoLine(ssb, false);
        } else {
            return;
        }
        CharSequence charSequence = mOnTodoClickCallback.onTodoClicked(view, line, lineNumber);
        if (!(charSequence instanceof SpannableString)) {
            return;
        }
        SpannableString sb = (SpannableString) charSequence;
        if (ssbArray[0] instanceof MDTodoDoneSpan) {
            MDTodoDoneSpan mdTodoDoneSpan = (MDTodoDoneSpan) ssbArray[0];
            int start = sb.getSpanStart(mdTodoDoneSpan);
            int end = sb.getSpanEnd(mdTodoDoneSpan);
            if (start < 0 || end < 0) {
                return;
            }
            sb.removeSpan(mdTodoDoneSpan);
            MDTodoSpan mdTodoSpan = new MDTodoSpan(mTodoColor, lineNumber);
            sb.setSpan(mdTodoSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            ssb.removeSpan(mdTodoDoneSpan);
            ssb.setSpan(mdTodoSpan, 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (ssbArray[0] instanceof MDTodoSpan) {
            MDTodoSpan mdTodoSpan = ssbArray[0];
            int start = sb.getSpanStart(mdTodoSpan);
            int end = sb.getSpanEnd(mdTodoSpan);
            if (start < 0 || end < 0) {
                return;
            }
            sb.removeSpan(mdTodoSpan);
            MDTodoDoneSpan mdTodoDoneSpan = new MDTodoDoneSpan(mDoneColor, lineNumber);
            sb.setSpan(mdTodoDoneSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            ssb.removeSpan(mdTodoSpan);
            ssb.setSpan(mdTodoDoneSpan, 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }


}
