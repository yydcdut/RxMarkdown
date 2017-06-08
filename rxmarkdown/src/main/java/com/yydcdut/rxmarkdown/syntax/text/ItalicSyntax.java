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

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

import com.yydcdut.rxmarkdown.RxMDConfiguration;

import java.util.regex.Pattern;

import static com.yydcdut.rxmarkdown.syntax.text.BackslashSyntax.KEY_BACKSLASH;

/**
 * The implementation of syntax for italic.
 * syntax:
 * "*content*"
 * "_content_"
 * <p>
 * Created by yuyidong on 16/5/3.
 */
class ItalicSyntax extends TextSyntaxAdapter {

    protected static final String KEY_ITALIC = "*";
    protected static final String KEY_ITALIC_1 = "_";

    protected static final String KEY_BACKSLASH_VALUE = KEY_BACKSLASH + KEY_ITALIC;
    protected static final String KEY_BACKSLASH_VALUE_1 = KEY_BACKSLASH + KEY_ITALIC_1;

    public ItalicSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
    }

    @Override
    boolean isMatch(@NonNull String text) {
        if (!text.contains(KEY_ITALIC) && !text.contains(KEY_ITALIC_1)) {
            return false;
        }
        boolean match = false;
        Pattern pattern = Pattern.compile(".*[\\*]{1}.*[\\*]{1}.*");
        Pattern pattern1 = Pattern.compile(".*[_]{1}.*[_]{1}.*");
        match |= pattern.matcher(text).matches();
        if (match) {
            return true;
        }
        match |= pattern1.matcher(text).matches();
        return match;
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        int index;
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(KEY_BACKSLASH_VALUE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + KEY_BACKSLASH_VALUE.length(), BackslashSyntax.KEY_ENCODE);
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(KEY_BACKSLASH_VALUE_1);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + KEY_BACKSLASH_VALUE_1.length(), BackslashSyntax.KEY_ENCODE_1);
        }
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        ssb = parse(KEY_ITALIC, ssb.toString(), ssb);
        return parse(KEY_ITALIC_1, ssb.toString(), ssb);
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        int index;
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(BackslashSyntax.KEY_ENCODE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + BackslashSyntax.KEY_ENCODE.length(), KEY_BACKSLASH_VALUE);
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(BackslashSyntax.KEY_ENCODE_1);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + BackslashSyntax.KEY_ENCODE_1.length(), KEY_BACKSLASH_VALUE_1);
        }
        return ssb;
    }

    /**
     * parse
     *
     * @param key  {@link ItalicSyntax#KEY_ITALIC} or {@link ItalicSyntax#KEY_ITALIC_1}
     * @param text the original content,the class type is {@link String}
     * @param ssb  the original content,the class type is {@link SpannableStringBuilder}
     * @return the content after parsing
     */
    @NonNull
    private SpannableStringBuilder parse(@NonNull String key, @NonNull String text, @NonNull SpannableStringBuilder ssb) {
        int keyLength = key.length();
        SpannableStringBuilder tmp = new SpannableStringBuilder();
        String tmpTotal = text;
        while (true) {
            int positionHeader = findPosition(key, tmpTotal, ssb, tmp);
            if (positionHeader == -1) {
                tmp.append(tmpTotal.substring(0, tmpTotal.length()));
                break;
            }
            tmp.append(tmpTotal.substring(0, positionHeader));
            int index = tmp.length();
            tmpTotal = tmpTotal.substring(positionHeader + keyLength, tmpTotal.length());
            int positionFooter = findPosition(key, tmpTotal, ssb, tmp);
            if (positionFooter != -1) {
                ssb.delete(tmp.length(), tmp.length() + keyLength);
                tmp.append(tmpTotal.substring(0, positionFooter));
                ssb.setSpan(new StyleSpan(Typeface.ITALIC), index, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.delete(tmp.length(), tmp.length() + keyLength);
            } else {
                tmp.append(key);
                tmp.append(tmpTotal.substring(0, tmpTotal.length()));
                break;
            }
            tmpTotal = tmpTotal.substring(positionFooter + keyLength, tmpTotal.length());
        }
        return ssb;
    }

    /**
     * find the position of next "*"
     * ignore the "*" in inline code syntax
     *
     * @param tmpTotal the original content, the class type is {@link String}
     * @param ssb      the original content, the class type is {@link SpannableStringBuilder}
     * @param tmp      the content that has parsed
     * @return the next position of "*"
     */
    private int findPosition(@NonNull String key, @NonNull String tmpTotal, @NonNull SpannableStringBuilder ssb, @NonNull SpannableStringBuilder tmp) {
        String tmpTmpTotal = tmpTotal;
        int position = tmpTmpTotal.indexOf(key);
        if (position == -1) {
            return -1;
        } else {
            if (checkInInlineCode(ssb, tmp.length() + position, key.length())) {//key是否在inlineCode中
                StringBuilder sb = new StringBuilder(tmpTmpTotal.substring(0, position))
                        .append("$").append(tmpTmpTotal.substring(position + key.length(), tmpTmpTotal.length()));
                return findPosition(key, sb.toString(), ssb, tmp);
            } else {
                return position;
            }
        }
    }
}
