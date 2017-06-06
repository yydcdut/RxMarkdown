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
package com.yydcdut.rxmarkdown.syntax.text;

import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AlignmentSpan;

import com.yydcdut.rxmarkdown.RxMDConfiguration;

import static com.yydcdut.rxmarkdown.syntax.text.BackslashSyntax.KEY_BACKSLASH;

/**
 * The implementation of grammar for center align.
 * It's not the real grammar in Markdown.
 * Grammar:
 * "[content]"
 * <p>
 * Created by yuyidong on 16/5/4.
 */
class CenterAlignSyntax extends TextSyntaxAdapter {

    protected static final String KEY_0_CENTER_ALIGN = "[";
    protected static final String KEY_1_CENTER_ALIGN = "]";

    protected static final String KEY_BACKSLASH_VALUE_1 = KEY_BACKSLASH + KEY_1_CENTER_ALIGN;

    public CenterAlignSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
    }

    @Override
    boolean isMatch(@NonNull String text) {
        return text.startsWith(KEY_0_CENTER_ALIGN) && text.endsWith(KEY_1_CENTER_ALIGN);
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        int index1;
        while (true) {
            String text = ssb.toString();
            index1 = text.indexOf(KEY_BACKSLASH_VALUE_1);
            if (index1 == -1) {
                break;
            }
            ssb.replace(index1, index1 + KEY_BACKSLASH_VALUE_1.length(), BackslashSyntax.KEY_ENCODE_1);
        }
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        ssb.delete(0, 1).delete(ssb.length() - 1, ssb.length());
        ssb.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        int index1;
        while (true) {
            String text = ssb.toString();
            index1 = text.indexOf(BackslashSyntax.KEY_ENCODE_1);
            if (index1 == -1) {
                break;
            }
            ssb.replace(index1, index1 + BackslashSyntax.KEY_ENCODE_1.length(), KEY_BACKSLASH_VALUE_1);
        }
        return ssb;
    }
}
