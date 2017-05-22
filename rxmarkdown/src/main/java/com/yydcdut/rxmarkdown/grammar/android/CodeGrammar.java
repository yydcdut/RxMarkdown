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
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.span.MDCodeSpan;

import java.util.regex.Pattern;

/**
 * The implementation of grammar for code.
 * Grammar:
 * "```
 * content
 * ```"
 * <p>
 * Created by yuyidong on 16/5/17.
 */
public class CodeGrammar extends GrammarAdapter {
    protected static final String KEY_CODE = "```";

    private int mColor;

    public CodeGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        mColor = rxMDConfiguration.getCodeBgColor();
    }

    @Override
    public boolean isMatch(@NonNull CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return false;
        }
        String text = charSequence.toString();
        String newText = text.replace("\n", "*!!*@@*##*$$*%%*^^*&&***((*))*--*^^*++*$$*");
        Pattern pattern = Pattern.compile(".*[```]{3}.*[```]{3}.*");
        if (!pattern.matcher(newText).matches()) {
            return false;
        }
        return calculateTotalKey(text) >= 2; //大于2就OK了
    }

    @NonNull
    @Override
    public CharSequence format(@NonNull CharSequence charSequence) {
        if (!(charSequence instanceof SpannableStringBuilder)) {
            return charSequence;
        }
        SpannableStringBuilder ssb = (SpannableStringBuilder) charSequence;
        int index = 0;
        String text = charSequence.toString();
        int totalKey = calculateTotalKey(text);
        boolean needCareful = (totalKey % 2 == 1);
        String[] lines = text.split("\n");
        int currentKeyIndex = 0;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].equals(KEY_CODE)) {
                currentKeyIndex++;
            }
            if (currentKeyIndex % 2 == 0) {//非代码部分
                if (KEY_CODE.equals(lines[i])) {
                    ssb.delete(index, index + KEY_CODE.length() + ((i == lines.length - 1) ? 0 : "\n".length()));
                    continue;
                }
            } else {//代码部分
                if (needCareful && currentKeyIndex == totalKey) {
                    break;
                }
                if (KEY_CODE.equals(lines[i])) {
                    ssb.delete(index, index + KEY_CODE.length() + "\n".length());
                    continue;
                }
                //中间如果有直接换行的，就删除掉
                if ("".equals(lines[i])) {
                    ssb.delete(index, index + "".length() + ((i == lines.length - 1) ? 0 : "\n".length()));
                    continue;
                }
                int start = index;
                int end = index + lines[i].length() + ((i == lines.length - 1) ? 0 : "\n".length());
                ssb.setSpan(new MDCodeSpan(mColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                ssb.setSpan(new TypefaceSpan("monospace"), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            index += lines[i].length() + "\n".length();
        }
        return ssb;
    }

    /**
     * calculate the content has how many "```"
     *
     * @param text the content
     * @return the number of "```" in content
     */
    private int calculateTotalKey(@NonNull String text) {
        String[] lines = text.split("\n");
        int number = 0;
        for (int i = 0; i < lines.length; i++) {
            number += KEY_CODE.equals(lines[i]) ? 1 : 0;
        }
        return number;
    }
}
