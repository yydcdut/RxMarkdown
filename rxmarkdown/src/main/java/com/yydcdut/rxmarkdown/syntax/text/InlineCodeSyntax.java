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
import android.text.style.BackgroundColorSpan;
import android.text.style.TypefaceSpan;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.syntax.SyntaxKey;

import java.util.regex.Pattern;

/**
 * The implementation of syntax for inline code.
 * syntax:
 * "`content`"
 * <p>
 * Created by yuyidong on 16/5/13.
 */
class InlineCodeSyntax extends TextSyntaxAdapter {

    private int mColor;

    public InlineCodeSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
        mColor = rxMDConfiguration.getInlineCodeBgColor();
    }

    @Override
    boolean isMatch(@NonNull String text) {
        if (!text.contains(SyntaxKey.KEY_INLINE_CODE)) {
            return false;
        }
        Pattern pattern = Pattern.compile(".*[`]{1}.*[`]{1}.*");
        return pattern.matcher(text).matches();
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        int index;
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(SyntaxKey.KEY_INLINE_BACKSLASH_VALUE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + SyntaxKey.KEY_INLINE_BACKSLASH_VALUE.length(), SyntaxKey.KEY_ENCODE);
        }
        return ssb;
    }

    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        String text = ssb.toString();
        return parse(text, ssb);
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        int index;
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(SyntaxKey.KEY_ENCODE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + SyntaxKey.KEY_ENCODE.length(), SyntaxKey.KEY_INLINE_BACKSLASH_VALUE);
        }
        return ssb;
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
            tmpTotal = tmpTotal.substring(positionHeader + SyntaxKey.KEY_INLINE_CODE.length(), tmpTotal.length());
            int positionFooter = findPosition(tmpTotal, ssb, tmp);
            if (positionFooter != -1) {
                ssb.delete(tmp.length(), tmp.length() + SyntaxKey.KEY_INLINE_CODE.length());
                tmp.append(tmpTotal.substring(0, positionFooter));
                ssb.setSpan(new BackgroundColorSpan(mColor), index, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new TypefaceSpan("monospace"), index, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//todo TypefaceSpan
                ssb.delete(tmp.length(), tmp.length() + SyntaxKey.KEY_INLINE_CODE.length());
            } else {
                tmp.append(SyntaxKey.KEY_INLINE_CODE);
                tmp.append(tmpTotal.substring(0, tmpTotal.length()));
                break;
            }
            tmpTotal = tmpTotal.substring(positionFooter + SyntaxKey.KEY_INLINE_CODE.length(), tmpTotal.length());
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
        int position = tmpTmpTotal.indexOf(SyntaxKey.KEY_INLINE_CODE);
        if (position == -1) {
            return -1;
        } else {
            if (checkInHyperLink(ssb, tmp.length() + position, SyntaxKey.KEY_INLINE_CODE.length()) ||
                    checkInImage(ssb, tmp.length() + position, SyntaxKey.KEY_INLINE_CODE.length())) {//key是否在HyperLink或者CustomImage中
                StringBuilder sb = new StringBuilder(tmpTmpTotal.substring(0, position))
                        .append("$").append(tmpTmpTotal.substring(position + SyntaxKey.KEY_INLINE_CODE.length(), tmpTmpTotal.length()));
                return findPosition(sb.toString(), ssb, tmp);
            } else {
                return position;
            }
        }
    }

}
