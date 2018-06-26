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

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.syntax.SyntaxKey;
import com.yydcdut.markdown.utils.CharacterProtector;
import com.yydcdut.markdown.utils.SyntaxUtils;

import java.util.regex.Pattern;

/**
 * The implementation of syntax for bold.
 * syntax:
 * "**content**"
 * "__content__"
 * <p>
 * Created by yuyidong on 16/5/3.
 */
class BoldSyntax extends TextSyntaxAdapter {
    private static final String PATTERN_ASTERISK = ".*[\\*]{2}.*[\\*]{2}.*";
    private static final String PATTERN_UNDERLINE = ".*[_]{2}.*[_]{2}.*";

    private boolean isContainsAsterisk;
    private boolean isContainsUnderline;

    public BoldSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        super(markdownConfiguration);
    }

    @Override
    boolean isMatch(@NonNull String text) {
        if (!text.contains(SyntaxKey.KEY_BOLD_ASTERISK) && !text.contains(SyntaxKey.KEY_BOLD_UNDERLINE)) {
            return false;
        }
        Pattern pattern = Pattern.compile(PATTERN_ASTERISK);
        isContainsAsterisk = pattern.matcher(text).matches();
        pattern = Pattern.compile(PATTERN_UNDERLINE);
        isContainsUnderline = pattern.matcher(text).matches();
        return isContainsAsterisk | isContainsUnderline;
    }

    @NonNull
    @Override
    boolean encode(@NonNull SpannableStringBuilder ssb) {
        boolean isHandledBackSlash = false;
        if (isContainsAsterisk) {
            isHandledBackSlash |= replace(ssb, SyntaxKey.KEY_BOLD_BACKSLASH_ASTERISK, CharacterProtector.getKeyEncode());
        }
        if (isContainsUnderline) {
            isHandledBackSlash |= replace(ssb, SyntaxKey.KEY_BOLD_BACKSLASH_UNDERLINE, CharacterProtector.getKeyEncode1());
        }
        return isHandledBackSlash;
    }

    @NonNull
    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb, int lineNumber) {
        if (isContainsAsterisk) {
            ssb = SyntaxUtils.parseBoldAndItalic(SyntaxKey.KEY_BOLD_ASTERISK, ssb, mCallback);
        }
        if (isContainsUnderline) {
            ssb = SyntaxUtils.parseBoldAndItalic(SyntaxKey.KEY_BOLD_UNDERLINE, ssb, mCallback);
        }
        return ssb;
    }

    @NonNull
    @Override
    void decode(@NonNull SpannableStringBuilder ssb) {
        if (isContainsAsterisk) {
            replace(ssb, CharacterProtector.getKeyEncode(), SyntaxKey.KEY_BOLD_BACKSLASH_ASTERISK);
        }
        if (isContainsUnderline) {
            replace(ssb, CharacterProtector.getKeyEncode1(), SyntaxKey.KEY_BOLD_BACKSLASH_UNDERLINE);
        }
    }

    private SyntaxUtils.OnWhatSpanCallback mCallback = new SyntaxUtils.OnWhatSpanCallback() {
        @Override
        public Object whatSpan() {
            return new StyleSpan(Typeface.BOLD);
        }
    };
}
