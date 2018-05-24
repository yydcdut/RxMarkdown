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
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.TypefaceSpan;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.span.MDCodeSpan;
import com.yydcdut.markdown.syntax.SyntaxKey;
import com.yydcdut.markdown.utils.CharacterProtector;
import com.yydcdut.markdown.utils.SyntaxUtils;

import java.util.regex.Pattern;

/**
 * The implementation of syntax for inline code.
 * syntax:
 * "`content`"
 * <p>
 * Created by yuyidong on 16/5/13.
 */
class CodeSyntax extends TextSyntaxAdapter {
    private static final String PATTERN = ".*[`]{1}.*[`]{1}.*";

    private int mColor;

    public CodeSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        super(markdownConfiguration);
        mColor = markdownConfiguration.getCodeBgColor();
    }

    @Override
    boolean isMatch(@NonNull String text) {
        return text.contains(SyntaxKey.KEY_CODE) ? Pattern.compile(PATTERN).matcher(text).matches() : false;
    }

    @NonNull
    @Override
    boolean encode(@NonNull SpannableStringBuilder ssb) {
        return replace(ssb, SyntaxKey.KEY_CODE_BACKSLASH, CharacterProtector.getKeyEncode());
    }

    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb, int lineNumber) {
        String text = ssb.toString();
        return parse(text, ssb);
    }

    @NonNull
    @Override
    void decode(@NonNull SpannableStringBuilder ssb) {
        replace(ssb, CharacterProtector.getKeyEncode(), SyntaxKey.KEY_CODE_BACKSLASH);
    }

    /**
     * parse
     *
     * @param text the original content,the class type is {@link String}
     * @param ssb  the original content,the class type is {@link SpannableStringBuilder}
     * @return the content after parsing
     */
    @NonNull
    private SpannableStringBuilder parse(@NonNull String text, @NonNull SpannableStringBuilder ssb) {
        SpannableStringBuilder tmp = new SpannableStringBuilder();
        String tmpTotal = text;
        while (true) {
            int positionHeader = findPosition(tmpTotal, ssb, tmp);
            if (positionHeader == -1) {
                tmp.append(tmpTotal.substring(0, tmpTotal.length()));
                break;
            }
            tmp.append(tmpTotal.substring(0, positionHeader));
            int index = tmp.length();
            tmpTotal = tmpTotal.substring(positionHeader + SyntaxKey.KEY_CODE.length(), tmpTotal.length());
            int positionFooter = findPosition(tmpTotal, ssb, tmp);
            if (positionFooter != -1) {
                ssb.delete(tmp.length(), tmp.length() + SyntaxKey.KEY_CODE.length());
                tmp.append(tmpTotal.substring(0, positionFooter));
//                ssb.setSpan(new BackgroundColorSpan(mColor), index, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new MDCodeSpan(mColor), index, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new TypefaceSpan("monospace"), index, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//todo TypefaceSpan
                ssb.delete(tmp.length(), tmp.length() + SyntaxKey.KEY_CODE.length());
            } else {
                tmp.append(SyntaxKey.KEY_CODE);
                tmp.append(tmpTotal.substring(0, tmpTotal.length()));
                break;
            }
            tmpTotal = tmpTotal.substring(positionFooter + SyntaxKey.KEY_CODE.length(), tmpTotal.length());
        }
        return ssb;
    }

    /**
     * find the position of next "`"
     * ignore the "`" in inline code syntax or image syntax
     *
     * @param tmpTotal the original content, the class type is {@link String}
     * @param ssb      the original content, the class type is {@link SpannableStringBuilder}
     * @param tmp      the content that has parsed
     * @return the next position of "`"
     */
    private int findPosition(@NonNull String tmpTotal, @NonNull SpannableStringBuilder ssb, @NonNull SpannableStringBuilder tmp) {
        String tmpTmpTotal = tmpTotal;
        int position = tmpTmpTotal.indexOf(SyntaxKey.KEY_CODE);
        if (position == -1) {
            return -1;
        } else {
            if (SyntaxUtils.existHyperLinkSyntax(ssb, tmp.length() + position, SyntaxKey.KEY_CODE.length()) ||
                    SyntaxUtils.existImageSyntax(ssb, tmp.length() + position, SyntaxKey.KEY_CODE.length())) {//key是否在HyperLink或者CustomImage中
                StringBuilder sb = new StringBuilder(tmpTmpTotal.substring(0, position))
                        .append("$").append(tmpTmpTotal.substring(position + SyntaxKey.KEY_CODE.length(), tmpTmpTotal.length()));
                return findPosition(sb.toString(), ssb, tmp);
            } else {
                return position;
            }
        }
    }

}
