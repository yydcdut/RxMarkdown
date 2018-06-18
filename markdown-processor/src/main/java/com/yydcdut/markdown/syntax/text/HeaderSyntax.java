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
import com.yydcdut.markdown.syntax.SyntaxKey;

/**
 * The implementation of syntax for header.
 * syntax:
 * "# " for h1
 * <p>
 * "## " for h2
 * <p>
 * "### " for h3
 * <p>
 * "#### " for h4
 * <p>
 * "##### " for h5
 * <p>
 * "###### " for h6
 * <p>
 * Created by yuyidong on 16/5/20.
 */
class HeaderSyntax extends TextSyntaxAdapter {

    private float mHeader1RelativeSize;
    private float mHeader2RelativeSize;
    private float mHeader3RelativeSize;
    private float mHeader4RelativeSize;
    private float mHeader5RelativeSize;
    private float mHeader6RelativeSize;

    public HeaderSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        super(markdownConfiguration);
        mHeader1RelativeSize = markdownConfiguration.getHeader1RelativeSize();
        mHeader2RelativeSize = markdownConfiguration.getHeader2RelativeSize();
        mHeader3RelativeSize = markdownConfiguration.getHeader3RelativeSize();
        mHeader4RelativeSize = markdownConfiguration.getHeader4RelativeSize();
        mHeader5RelativeSize = markdownConfiguration.getHeader5RelativeSize();
        mHeader6RelativeSize = markdownConfiguration.getHeader6RelativeSize();
    }

    @Override
    boolean isMatch(@NonNull String text) {
        return text.startsWith(SyntaxKey.KEY_0_HEADER)
                || text.startsWith(SyntaxKey.KEY_1_HEADER)
                || text.startsWith(SyntaxKey.KEY_2_HEADER)
                || text.startsWith(SyntaxKey.KEY_3_HEADER)
                || text.startsWith(SyntaxKey.KEY_4_HEADER)
                || text.startsWith(SyntaxKey.KEY_5_HEADER);
    }

    @NonNull
    @Override
    boolean encode(@NonNull SpannableStringBuilder ssb) {
        return false;
    }

    @NonNull
    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb, int lineNumber) {
        String text = ssb.toString();
        if (text.startsWith(SyntaxKey.KEY_5_HEADER)) {
            ssb.delete(0, SyntaxKey.KEY_5_HEADER.length());
            ssb.setSpan(new RelativeSizeSpan(mHeader6RelativeSize), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (text.startsWith(SyntaxKey.KEY_4_HEADER)) {
            ssb.delete(0, SyntaxKey.KEY_4_HEADER.length());
            ssb.setSpan(new RelativeSizeSpan(mHeader5RelativeSize), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (text.startsWith(SyntaxKey.KEY_3_HEADER)) {
            ssb.delete(0, SyntaxKey.KEY_3_HEADER.length());
            ssb.setSpan(new RelativeSizeSpan(mHeader4RelativeSize), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (text.startsWith(SyntaxKey.KEY_2_HEADER)) {
            ssb.delete(0, SyntaxKey.KEY_2_HEADER.length());
            ssb.setSpan(new RelativeSizeSpan(mHeader3RelativeSize), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (text.startsWith(SyntaxKey.KEY_1_HEADER)) {
            ssb.delete(0, SyntaxKey.KEY_1_HEADER.length());
            ssb.setSpan(new RelativeSizeSpan(mHeader2RelativeSize), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (text.startsWith(SyntaxKey.KEY_0_HEADER)) {
            ssb.delete(0, SyntaxKey.KEY_0_HEADER.length());
            ssb.setSpan(new RelativeSizeSpan(mHeader1RelativeSize), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ssb;
    }

    @NonNull
    @Override
    void decode(@NonNull SpannableStringBuilder ssb) {
    }
}
