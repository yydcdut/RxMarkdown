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
import android.text.Spanned;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.span.MDHorizontalRulesSpan;
import com.yydcdut.markdown.syntax.SyntaxKey;

/**
 * The implementation of syntax for horizontal rules.
 * syntax:
 * "***"
 * <p>
 * "---"
 * <p>
 * "***********"
 * <p>
 * "----------------"
 * <p>
 * Created by yuyidong on 16/5/15.
 */
class HorizontalRulesSyntax extends TextSyntaxAdapter {

    private int mColor;
    private int mHeight;

    public HorizontalRulesSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        super(markdownConfiguration);
        mColor = markdownConfiguration.getHorizontalRulesColor();
        mHeight = markdownConfiguration.getHorizontalRulesHeight();
    }

    @Override
    boolean isMatch(@NonNull String text) {
        if (text.startsWith(SyntaxKey.KEY_HORIZONTAL_RULES_ASTERISK)) {
            if (check(text, SyntaxKey.KEY_HORIZONTAL_RULES_ASTERISK_SINGLE)) {
                return true;
            }
        }
        if (text.startsWith(SyntaxKey.KEY_HORIZONTAL_RULES_HYPHEN)) {
            if (check(text, SyntaxKey.KEY_HORIZONTAL_RULES_HYPHEN_SINGLE)) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    @Override
    boolean encode(@NonNull SpannableStringBuilder ssb) {
        return false;
    }

    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb, int lineNumber) {
        ssb.replace(0, ssb.length(), " ");
        ssb.setSpan(new MDHorizontalRulesSpan(mColor, mHeight), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    @NonNull
    @Override
    void decode(@NonNull SpannableStringBuilder ssb) {
    }

    /**
     * check whether it's the same character
     *
     * @param text the content
     * @param key  the character
     * @return TRUE: the same
     */
    private static boolean check(@NonNull String text, char key) {
        char[] chars = text.toCharArray();
        boolean bool = true;
        for (int i = 0; i < chars.length; i++) {
            bool &= (chars[i] == key);
            if (!bool) {
                break;
            }
        }
        return bool;
    }
}
