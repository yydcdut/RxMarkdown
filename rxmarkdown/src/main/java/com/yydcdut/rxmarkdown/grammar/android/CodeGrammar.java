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
import android.util.Pair;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.prettify.PrettifyHighLighter;
import com.yydcdut.rxmarkdown.span.MDCodeSpan;

import java.util.ArrayList;
import java.util.List;

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
    private PrettifyHighLighter mPrettifyHighLighter;//todo 耗时

    public CodeGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        mColor = rxMDConfiguration.getTheme().getBackgroundColor();
        mPrettifyHighLighter = new PrettifyHighLighter(rxMDConfiguration);
    }

    @Override
    public boolean isMatch(@NonNull CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return false;
        }
        return find(charSequence.toString()).size() > 0;
    }

    @NonNull
    @Override
    public CharSequence format(@NonNull CharSequence charSequence) {
        if (!(charSequence instanceof SpannableStringBuilder)) {
            return charSequence;
        }
        SpannableStringBuilder ssb = (SpannableStringBuilder) charSequence;
        String text = charSequence.toString();
        List<Pair<Integer, Integer>> list = find(text);
        for (int i = list.size() - 1; i >= 0; i--) {
            Pair<Integer, Integer> pair = list.get(i);
            int start = pair.first;
            int end = pair.second;
            List<Integer> middleList = getMiddleNewLineCharPosition(ssb, start, end);
            String language = "";
            if (middleList.size() > 0) {
                language = ssb.subSequence(start, middleList.get(0)).toString().replace(KEY_CODE, "").replace("\n", "");
            }
            int current = start;
            for (int j = 1; j < middleList.size(); j++) {//放弃0，因为0是```java这样的
                int position = middleList.get(j);
                if (position == current) {//处理只有换行符
                    ssb.replace(position - 1, position, " ");
                }
                ssb.setSpan(new MDCodeSpan(mColor, language, (j == 1 ? true : false), (j == middleList.size() - 1 ? true : false)), current, position, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                current = position + 1;
            }
            mPrettifyHighLighter.highLight(language, ssb, start, end);
            ssb.delete(end, end + KEY_CODE.length() + (end + KEY_CODE.length() >= ssb.length() ? 0 : 1));
            ssb.delete(start, findNextNewLineChar(ssb, start) + 1);
        }
        return ssb;
    }

    public static List<Pair<Integer, Integer>> find(@NonNull String text) {
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        String[] lines = text.split("\n");
        int start = -1;
        int end = -1;
        int currentLength = 0;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].startsWith(KEY_CODE)) {
                if (start == -1) {
                    start = currentLength;
                } else if (end == -1 && TextUtils.equals(lines[i], KEY_CODE)) {
                    end = currentLength;
                }
                if (start != -1 && end != -1) {
                    list.add(new Pair<>(start, end));
                    start = -1;
                    end = -1;
                }
            }
            currentLength += lines[i].length() + "\n".length();
        }
        return list;
    }

    private static int findNextNewLineChar(SpannableStringBuilder ssb, int start) {//todo utils
        for (int i = start; i < ssb.length(); i++) {
            if (ssb.charAt(i) == '\n') {
                return i;
            }
        }
        return -1;
    }

    private static List<Integer> getMiddleNewLineCharPosition(SpannableStringBuilder ssb, int start, int end) {//todo utils
        List<Integer> list = new ArrayList<>();
        for (int i = start; i < end; i++) {
            if (ssb.charAt(i) == '\n') {
                list.add(i);
            }
        }
        return list;
    }

}
