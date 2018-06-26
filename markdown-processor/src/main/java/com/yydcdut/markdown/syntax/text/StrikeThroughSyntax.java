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
import android.text.SpannableStringBuilder;
import android.text.style.StrikethroughSpan;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.syntax.SyntaxKey;
import com.yydcdut.markdown.utils.CharacterProtector;
import com.yydcdut.markdown.utils.SyntaxUtils;

import java.util.regex.Pattern;

/**
 * The implementation of syntax for strike through.
 * syntax:
 * "~~content~~"
 * <p>
 * Created by yuyidong on 16/5/13.
 */
class StrikeThroughSyntax extends TextSyntaxAdapter {
    private static final String PATTERN = ".*[~]{2}.*[~]{2}.*";

    public StrikeThroughSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        super(markdownConfiguration);
    }

    @Override
    boolean isMatch(@NonNull String text) {
        return text.contains(SyntaxKey.KEY_STRIKE_THROUGH) ? Pattern.compile(PATTERN).matcher(text).matches() : false;
    }

    @NonNull
    @Override
    boolean encode(@NonNull SpannableStringBuilder ssb) {
        return replace(ssb, SyntaxKey.KEY_STRIKE_BACKSLASH, CharacterProtector.getKeyEncode());
    }

    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb, int lineNumber) {
        return SyntaxUtils.parseBoldAndItalic(SyntaxKey.KEY_STRIKE_THROUGH, ssb, mCallback);
    }

    @NonNull
    @Override
    void decode(@NonNull SpannableStringBuilder ssb) {
        replace(ssb, CharacterProtector.getKeyEncode(), SyntaxKey.KEY_STRIKE_BACKSLASH);
    }

    private SyntaxUtils.OnWhatSpanCallback mCallback = new SyntaxUtils.OnWhatSpanCallback() {
        @Override
        public Object whatSpan() {
            return new StrikethroughSpan();
        }
    };
}
