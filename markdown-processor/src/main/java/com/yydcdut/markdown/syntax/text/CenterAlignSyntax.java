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
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AlignmentSpan;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.syntax.SyntaxKey;
import com.yydcdut.markdown.utils.CharacterProtector;

/**
 * The implementation of syntax for center align.
 * It's not the real syntax in Markdown.
 * syntax:
 * "[content]"
 * <p>
 * Created by yuyidong on 16/5/4.
 */
class CenterAlignSyntax extends TextSyntaxAdapter {

    public CenterAlignSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        super(markdownConfiguration);
    }

    @Override
    boolean isMatch(@NonNull String text) {
        return text.startsWith(SyntaxKey.KEY_CENTER_ALIGN_LEFT) && text.endsWith(SyntaxKey.KEY_CENTER_ALIGN_RIGHT);
    }

    @NonNull
    @Override
    boolean encode(@NonNull SpannableStringBuilder ssb) {
        return replace(ssb, SyntaxKey.KEY_CENTER_ALIGN_BACKSLASH_RIGHT, CharacterProtector.getKeyEncode1());
    }

    @NonNull
    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb, int lineNumber) {
        ssb.delete(0, 1).delete(ssb.length() - 1, ssb.length());
        ssb.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    @NonNull
    @Override
    void decode(@NonNull SpannableStringBuilder ssb) {
        replace(ssb, CharacterProtector.getKeyEncode1(), SyntaxKey.KEY_CENTER_ALIGN_BACKSLASH_RIGHT);
    }
}
