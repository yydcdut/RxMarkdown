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
package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.span.MDQuoteSpan;

/**
 * The implementation of grammar for block quotes.
 * Grammar:
 * "> "
 * <p>
 * Created by yuyidong on 16/5/4.
 */
class BlockQuotesGrammar extends AbsAndroidGrammar {

    /**
     * {@link com.yydcdut.rxmarkdown.grammar.edit.BlockQuotesGrammar#KEY_BLOCK_QUOTES}
     * {@link com.yydcdut.rxmarkdown.span.MDQuoteSpan#KEY_BLOCK_QUOTES}
     */
    protected static final String KEY_BLOCK_QUOTES = "> ";

    private int mColor;

    BlockQuotesGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
        mColor = rxMDConfiguration.getBlockQuotesColor();
    }

    @Override
    boolean isMatch(@NonNull String text) {
        if (!text.startsWith(KEY_BLOCK_QUOTES)) {
            return false;
        }
        return true;
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        int nested = calculateNested(ssb.toString());
        if (nested == 0) {
            return ssb;
        }
        ssb.replace(0, nested * KEY_BLOCK_QUOTES.length() - 1, getHolder(nested));
        ssb.setSpan(new MDQuoteSpan(mColor, nested), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        marginSSBLeft(ssb, 20);
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
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
        while (true) {
            if ((nested + 1) * KEY_BLOCK_QUOTES.length() > text.length()) {
                break;
            }
            String sub = text.substring(nested * KEY_BLOCK_QUOTES.length(), (nested + 1) * KEY_BLOCK_QUOTES.length());
            if (!KEY_BLOCK_QUOTES.equals(sub)) {
                break;
            }
            ++nested;
        }
        return nested;
    }

    /**
     * get place holder
     *
     * @param nested the nested number
     * @return the place holder based on nested number
     */
    private String getHolder(int nested) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < nested; i++) {
            stringBuilder.append("   ");
        }
        return stringBuilder.toString();
    }
}
