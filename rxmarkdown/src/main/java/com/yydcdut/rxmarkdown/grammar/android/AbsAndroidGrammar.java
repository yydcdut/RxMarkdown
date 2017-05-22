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
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.LeadingMarginSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.edit.EditToken;
import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.span.MDCodeSpan;
import com.yydcdut.rxmarkdown.span.MDImageSpan;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide some common methods.
 * Provide some template methods.
 * Adapter some methods.
 * <p>
 * Created by yuyidong on 16/5/13.
 */
public abstract class AbsAndroidGrammar implements IGrammar {

    public AbsAndroidGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
    }

    /**
     * set content margin left.
     *
     * @param ssb   the content
     * @param every the distance that margin left
     */
    protected static void marginSSBLeft(SpannableStringBuilder ssb, int every) {
        ssb.setSpan(new LeadingMarginSpan.Standard(every), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    @Override
    public boolean isMatch(@NonNull CharSequence charSequence) {
        SpannableStringBuilder ssb;
        if (charSequence instanceof SpannableStringBuilder) {
            ssb = (SpannableStringBuilder) charSequence;
        } else {
            return false;
        }
        if (ssb.getSpans(0, ssb.length(), MDCodeSpan.class).length > 0) {
            return false;
        }
        if (TextUtils.isEmpty(charSequence)) {
            return false;
        }
        return isMatch(charSequence.toString());
    }

    @NonNull
    @Override
    public CharSequence format(@NonNull CharSequence charSequence) {
        SpannableStringBuilder ssb;
        if (charSequence instanceof SpannableStringBuilder) {
            ssb = (SpannableStringBuilder) charSequence;
        } else {
            return charSequence;
        }
        ssb = encode(ssb);
        ssb = format(ssb);
        ssb = decode(ssb);
        return ssb;
    }

    /**
     * is match
     *
     * @param text the content
     * @return TRUE: match
     */
    abstract boolean isMatch(@NonNull String text);

    /**
     * encode the back slash in content
     *
     * @param ssb the original content
     * @return the content after encoding
     */
    @NonNull
    abstract SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb);

    /**
     * parse the content which is encoded
     *
     * @param ssb the content which is encoded
     * @return the content after parsing
     */
    @NonNull
    abstract SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb);

    /**
     * decode the back slash in content
     *
     * @param ssb the content which is parsed
     * @return the result content
     */
    @NonNull
    abstract SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb);

    /**
     * check whether contains inline code grammar
     *
     * @param ssb       the content
     * @param position  start position
     * @param keyLength the checking words' length
     * @return TRUE: contains
     */
    protected boolean checkInInlineCode(SpannableStringBuilder ssb, int position, int keyLength) {
        TypefaceSpan[] spans = ssb.getSpans(position, position + keyLength, TypefaceSpan.class);
        if (spans.length == 0) {
            return false;
        }
        return true;
    }

    /**
     * check whether contains hyper link grammar
     *
     * @param ssb       the content
     * @param position  start position
     * @param keyLength the checking words' length
     * @return TRUE: contains
     */
    protected boolean checkInHyperLink(SpannableStringBuilder ssb, int position, int keyLength) {
        URLSpan[] spans = ssb.getSpans(position, position + keyLength, URLSpan.class);
        if (spans.length == 0) {
            return false;
        }
        return true;
    }

    /**
     * check whether contains image grammar
     *
     * @param ssb       the content
     * @param position  start position
     * @param keyLength the checking words' length
     * @return TRUE: contains
     */
    protected boolean checkInImage(SpannableStringBuilder ssb, int position, int keyLength) {
        MDImageSpan[] spans = ssb.getSpans(position, position + keyLength, MDImageSpan.class);
        if (spans.length == 0) {
            return false;
        }
        return true;
    }

    @NonNull
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        return new ArrayList<>();
    }

}
