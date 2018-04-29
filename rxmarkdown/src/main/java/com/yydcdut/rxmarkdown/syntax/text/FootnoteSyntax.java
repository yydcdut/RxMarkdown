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
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.SuperscriptSpan;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.syntax.SyntaxKey;
import com.yydcdut.rxmarkdown.utils.CharacterProtector;

import java.util.regex.Pattern;

/**
 * The implementation of syntax for footnote.
 * syntax:
 * "content[^footnote]"
 * <p>
 * Created by yuyidong on 16/5/13.
 */
class FootnoteSyntax extends TextSyntaxAdapter {
    public FootnoteSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
    }

    @Override
    boolean isMatch(@NonNull String text) {
        if (!(text.contains(SyntaxKey.KEY_FOOTNOTE_LEFT) && text.contains(SyntaxKey.KEY_FOOTNOTE_RIGHT))) {
            return false;
        }
        Pattern pattern = Pattern.compile(".*[\\[\\^].*[]].*");
        return pattern.matcher(text).matches();
    }

    @NonNull
    @Override
    boolean encode(@NonNull SpannableStringBuilder ssb) {
        boolean isHandledBackSlash = false;
        isHandledBackSlash |= replace(ssb, SyntaxKey.KEY_FOOTNOTE_BACKSLASH_LEFT, CharacterProtector.getKeyEncode());
        isHandledBackSlash |= replace(ssb, SyntaxKey.KEY_FOOTNOTE_BACKSLASH_RIGHT, CharacterProtector.getKeyEncode2());
        return isHandledBackSlash;
    }

    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        String text = ssb.toString();
        return parse(text, ssb);
    }

    @NonNull
    @Override
    void decode(@NonNull SpannableStringBuilder ssb) {
        replace(ssb, CharacterProtector.getKeyEncode(), SyntaxKey.KEY_FOOTNOTE_BACKSLASH_LEFT);
        replace(ssb, CharacterProtector.getKeyEncode2(), SyntaxKey.KEY_FOOTNOTE_BACKSLASH_RIGHT);
    }

    /**
     * parse
     *
     * @param text the original content,the class type is {@link String}
     * @param ssb  the original content,the class type is {@link SpannableStringBuilder}
     * @return the content after parsing
     */
    private SpannableStringBuilder parse(@NonNull String text, @NonNull SpannableStringBuilder ssb) {
        SpannableStringBuilder tmp = new SpannableStringBuilder();
        String tmpTotal = text;
        while (true) {
            int positionHeader = findBeginPosition(tmpTotal, ssb, tmp);
            if (positionHeader == -1) {
                tmp.append(tmpTotal.substring(0, tmpTotal.length()));
                break;
            }
            tmp.append(tmpTotal.substring(0, positionHeader));
            int index = tmp.length();
            tmpTotal = tmpTotal.substring(positionHeader + SyntaxKey.KEY_FOOTNOTE_LEFT.length(), tmpTotal.length());
            int positionFooter = findEndPosition(tmpTotal, ssb, tmp);
            if (positionFooter != -1) {
                ssb.delete(tmp.length(), tmp.length() + SyntaxKey.KEY_FOOTNOTE_LEFT.length());
                tmp.append(tmpTotal.substring(0, positionFooter));
                ssb.setSpan(new SuperscriptSpan(), index, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.delete(tmp.length(), tmp.length() + SyntaxKey.KEY_FOOTNOTE_RIGHT.length());
            } else {
                tmp.append(SyntaxKey.KEY_FOOTNOTE_LEFT);
                tmp.append(tmpTotal.substring(0, tmpTotal.length()));
                break;
            }
            tmpTotal = tmpTotal.substring(positionFooter + SyntaxKey.KEY_FOOTNOTE_LEFT.length(), tmpTotal.length());
        }
        return ssb;
    }

    /**
     * find the "[" position
     * ignore the "[" in inline code syntax,
     *
     * @param tmpTotal the original content, the class type is {@link String}
     * @param ssb      the original content, the class type is {@link SpannableStringBuilder}
     * @param tmp      the content that has parsed
     * @return the  position of "["
     */
    private int findBeginPosition(@NonNull String tmpTotal, @NonNull SpannableStringBuilder ssb, @NonNull SpannableStringBuilder tmp) {
        String tmpTmpTotal = tmpTotal;
        int position = tmpTmpTotal.indexOf(SyntaxKey.KEY_FOOTNOTE_LEFT);
        if (position == -1) {
            return -1;
        } else {
            if (checkInInlineCode(ssb, tmp.length() + position, SyntaxKey.KEY_FOOTNOTE_LEFT.length())) {//key是否在inlineCode中
                StringBuilder sb = new StringBuilder(tmpTmpTotal.substring(0, position))
                        .append("$$").append(tmpTmpTotal.substring(position + SyntaxKey.KEY_FOOTNOTE_LEFT.length(), tmpTmpTotal.length()));
                return findBeginPosition(sb.toString(), ssb, tmp);
            } else {
                return position;
            }
        }
    }

    /**
     * find the "]" position
     * ignore the "]" in inline code syntax,
     *
     * @param tmpTotal the original content, the class type is {@link String}
     * @param ssb      the original content, the class type is {@link SpannableStringBuilder}
     * @param tmp      the content that has parsed
     * @return the  position of "]"
     */
    private int findEndPosition(@NonNull String tmpTotal, @NonNull SpannableStringBuilder ssb, @NonNull SpannableStringBuilder tmp) {
        String tmpTmpTotal = tmpTotal;
        int position = tmpTmpTotal.indexOf(SyntaxKey.KEY_FOOTNOTE_RIGHT);
        if (position == -1) {
            return -1;
        } else {
            if (checkInInlineCode(ssb, tmp.length() + position, SyntaxKey.KEY_FOOTNOTE_RIGHT.length())) {//key是否在inlineCode中
                StringBuilder sb = new StringBuilder(tmpTmpTotal.substring(0, position))
                        .append("$").append(tmpTmpTotal.substring(position + SyntaxKey.KEY_FOOTNOTE_RIGHT.length(), tmpTmpTotal.length()));
                return findBeginPosition(sb.toString(), ssb, tmp);
            } else {
                return position;
            }
        }
    }
}
