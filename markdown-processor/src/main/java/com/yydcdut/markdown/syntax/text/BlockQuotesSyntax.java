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
import android.text.style.RelativeSizeSpan;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.span.MDQuoteBackgroundSpan;
import com.yydcdut.markdown.span.MDQuoteSpan;
import com.yydcdut.markdown.syntax.SyntaxKey;
import com.yydcdut.markdown.utils.SyntaxUtils;

import java.util.List;

/**
 * The implementation of syntax for block quotes.
 * syntax:
 * "> "
 * <p>
 * Created by yuyidong on 16/5/4.
 */
class BlockQuotesSyntax extends TextSyntaxAdapter {

    private final float mRelativeSize;
    private final List<Integer> bgColorList;

    private int mColor;

    public BlockQuotesSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        super(markdownConfiguration);
        mColor = markdownConfiguration.getBlockQuotesLineColor();
        mRelativeSize = markdownConfiguration.getBlockQuoteRelativeSize();
        bgColorList = markdownConfiguration.getBlockQuoteBgColor();
    }

    @Override
    boolean isMatch(@NonNull String text) {
        return text.startsWith(SyntaxKey.KEY_BLOCK_QUOTES);
    }

    @NonNull
    @Override
    boolean encode(@NonNull SpannableStringBuilder ssb) {
        return false;
    }

    @NonNull
    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb, int lineNumber) {
        int nested = calculateNested(ssb.toString());
        if (nested == 0) {
            return ssb;
        }

        // calculate the first non-blockquote and non-whitespace character
        int i = 0;
        while (i < ssb.length()) {
            if (ssb.charAt(i) == '>' || ssb.charAt(i) == ' ') {
                i++;
            } else {
                break;
            }
        }

        final int number = i / 2;
        for (int n = 0; n < number; n++) {
            ssb.replace(2 * n, 2 * (n + 1), "  ");
        }

        ssb.setSpan(new MDQuoteSpan(mColor, nested), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE | (2 << Spanned.SPAN_PRIORITY_SHIFT));
        ssb.setSpan(new MDQuoteBackgroundSpan(nested, bgColorList), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE | (1 << Spanned.SPAN_PRIORITY_SHIFT));
        if (mRelativeSize != 1f) {
            ssb.setSpan(new RelativeSizeSpan(mRelativeSize), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        SyntaxUtils.marginSSBLeft(ssb, 32);
        return ssb;
    }

    /**
     * calculate nested, one "> ", nest++
     *
     * @param text the content
     * @return nested number of content
     */
    private static int calculateNested(@NonNull String text) {//有一个 "> " 就算嵌套一层
        int nested = 0;
        int i = 0;
        while (i < text.length()) {
            if (text.charAt(i) == '>') {
                ++nested;
            } else {
                break;
            }
            i++;
            while (i < text.length() && text.charAt(i) == ' ') {
                i++;
            }
        }
        return nested;
    }

    @NonNull
    @Override
    void decode(@NonNull SpannableStringBuilder ssb) {
    }
}
