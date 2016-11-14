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

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;

import com.yydcdut.rxmarkdown.RxMDConfiguration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The implementation of grammar for bold.
 * Grammar:
 * "**content**"
 * "__content__"
 * <p>
 * Created by yuyidong on 16/5/3.
 */
class BoldGrammar extends AbsAndroidGrammar {

    protected static final String KEY_BACKSLASH_VALUE = BackslashGrammar.KEY_BACKSLASH + "*";
    protected static final String KEY_BACKSLASH_VALUE_1 = BackslashGrammar.KEY_BACKSLASH + "_";
    private static final Pattern boldMatchesPattern = Pattern.compile(".*([\\W&&[^\\\\]]|^)(\\*\\*|__)(\\S.*?\\S)(\\2)(\\s|$|[.,!?:\\(\\)]).*");
    private static final Pattern boldPattern = Pattern.compile("([\\W&&[^\\\\]]|^)(\\*\\*|__)(\\S.*?\\S)(\\2)(\\s|$|[.,!?:\\(\\)])");

    BoldGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
    }

    @Override
    boolean isMatch(@NonNull String text) {
        return boldMatchesPattern.matcher(text).matches();
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        Matcher matcher = boldPattern.matcher(ssb);
        while (matcher.find()) {
            if (!checkInInlineCode(ssb, matcher.start(2), matcher.start(4))) {
                ssb.setSpan(new StyleSpan(Typeface.BOLD), matcher.start(2), matcher.start(4), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.delete(matcher.start(4), matcher.end(4));
                ssb.delete(matcher.start(2), matcher.end(2));
                matcher.reset(ssb);
            }
        }

        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

}
