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
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.live.EditToken;
import com.yydcdut.markdown.span.MDCodeBlockSpan;
import com.yydcdut.markdown.syntax.Syntax;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide some common methods.
 * Provide some template methods.
 * Adapter some methods.
 * <p>
 * Created by yuyidong on 16/5/13.
 */
abstract class TextSyntaxAdapter implements Syntax {

    public TextSyntaxAdapter(@NonNull MarkdownConfiguration markdownConfiguration) {
    }

    @Override
    public boolean isMatch(@NonNull CharSequence charSequence) {
        SpannableStringBuilder ssb;
        if (charSequence instanceof SpannableStringBuilder) {
            ssb = (SpannableStringBuilder) charSequence;
        } else {
            return false;
        }
        if (ssb.getSpans(0, ssb.length(), MDCodeBlockSpan.class).length > 0) {
            return false;
        }
        if (TextUtils.isEmpty(charSequence)) {
            return false;
        }
        return isMatch(charSequence.toString());
    }

    @NonNull
    @Override
    public CharSequence format(@NonNull CharSequence charSequence, int lineNumber) {
        SpannableStringBuilder ssb;
        if (charSequence instanceof SpannableStringBuilder) {
            ssb = (SpannableStringBuilder) charSequence;
        } else {
            return charSequence;
        }
        boolean isHandledBackSlash = encode(ssb);
        ssb = format(ssb, lineNumber);
        if (isHandledBackSlash) {
            decode(ssb);
        }
        return ssb;
    }

    protected static boolean replace(SpannableStringBuilder ssb, String key, String replace) {
        boolean isHandledBackSlash = false;
        int index;
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(key);
            if (index == -1) {
                break;
            }
            isHandledBackSlash = true;
            ssb.replace(index, index + key.length(), replace);
        }
        return isHandledBackSlash;
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
     * @return is handled back slash
     */
    @NonNull
    abstract boolean encode(@NonNull SpannableStringBuilder ssb);

    /**
     * parse the content which is encoded
     *
     * @param ssb        the content which is encoded
     * @param lineNumber the number of text in the content
     */
    @NonNull
    abstract SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb, int lineNumber);

    /**
     * decode the back slash in content
     *
     * @param ssb the content which is parsed
     * @return the result content
     */
    @NonNull
    abstract void decode(@NonNull SpannableStringBuilder ssb);

    @NonNull
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        return new ArrayList<>();
    }

}
