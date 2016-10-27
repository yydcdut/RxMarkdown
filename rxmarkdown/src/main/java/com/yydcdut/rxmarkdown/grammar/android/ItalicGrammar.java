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

import com.yydcdut.rxmarkdown.RxMDConfiguration;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yydcdut.rxmarkdown.grammar.android.BackslashGrammar.KEY_BACKSLASH;

/**
 * The implementation of grammar for italic.
 * Grammar:
 * "*content*"
 * "_content_"
 * <p>
 * Created by yuyidong on 16/5/3.
 */
class ItalicGrammar extends AbsAndroidGrammar {

    protected static final String KEY_ITALIC = "*";
    protected static final String KEY_ITALIC_1 = "_";
    protected static final String KEY_BACKSLASH_VALUE = KEY_BACKSLASH + KEY_ITALIC;
    protected static final String KEY_BACKSLASH_VALUE_1 = KEY_BACKSLASH + KEY_ITALIC_1;
    private static final Pattern italicsMatchesPattern = Pattern.compile(".*([\\W&&[^\\\\]]|^)(\\*|_)(\\S.*?\\S)(\\2)(\\s|$|[.,!?:\\(\\)]).*");
    private static final Pattern italicsPattern = Pattern.compile("([\\W&&[^\\\\]]|^)(\\*|_)(\\S.*?\\S)(\\2)(\\s|$|[.,!?:\\(\\)])");

    ItalicGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
    }

    @Override
    boolean isMatch(@NonNull String text) {
        return italicsMatchesPattern.matcher(text).matches();
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        Matcher matcher = italicsPattern.matcher(ssb);

        while (matcher.find()) {
            if (!checkInInlineCode(ssb, matcher.start(2), matcher.start(4))) {
                ssb.setSpan(new StyleSpan(Typeface.ITALIC), matcher.start(2), matcher.start(4), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
