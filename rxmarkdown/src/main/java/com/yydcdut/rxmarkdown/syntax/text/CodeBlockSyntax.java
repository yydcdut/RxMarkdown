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
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Pair;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.live.EditToken;
import com.yydcdut.rxmarkdown.prettify.PrettifyHighLighter;
import com.yydcdut.rxmarkdown.span.MDCodeBlockSpan;
import com.yydcdut.rxmarkdown.syntax.Syntax;
import com.yydcdut.rxmarkdown.syntax.SyntaxKey;
import com.yydcdut.rxmarkdown.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of syntax for code block.
 * syntax:
 * "```
 * content
 * ```"
 * <p>
 * Created by yuyidong on 16/5/17.
 */
class CodeBlockSyntax implements Syntax {

    private int mBackgroundColor;
    private int mTextColor;
    private PrettifyHighLighter mPrettifyHighLighter;//todo 耗时

    public CodeBlockSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        mBackgroundColor = rxMDConfiguration.getTheme().getBackgroundColor();
        mPrettifyHighLighter = new PrettifyHighLighter(rxMDConfiguration);
        mTextColor = rxMDConfiguration.getTheme().getPlainTextColor();
    }

    @Override
    public boolean isMatch(@NonNull CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return false;
        }
        return Utils.find(charSequence.toString(), SyntaxKey.KEY_CODE_BLOCK).size() > 0;
    }

    @NonNull
    @Override
    public CharSequence format(@NonNull CharSequence charSequence) {
        if (!(charSequence instanceof SpannableStringBuilder)) {
            return charSequence;
        }
        SpannableStringBuilder ssb = (SpannableStringBuilder) charSequence;
        String text = charSequence.toString();
        List<Pair<Integer, Integer>> list = Utils.find(text, SyntaxKey.KEY_CODE_BLOCK);
        for (int i = list.size() - 1; i >= 0; i--) {
            Pair<Integer, Integer> pair = list.get(i);
            int start = pair.first;
            int end = pair.second;
            List<Integer> middleList = Utils.getNewLineCharPosition(ssb, start, end);
            String language = "";
            if (middleList.size() > 0) {
                language = ssb.subSequence(start, middleList.get(0)).toString().replace(SyntaxKey.KEY_CODE_BLOCK, "").replace("\n", "");
            }
            int current = middleList.get(0) + 1;
            for (int j = 1; j < middleList.size(); j++) {//放弃0，因为0是```java这样的
                int position = middleList.get(j);
                if (position == current) {//处理只有换行符
                    ssb.replace(position - 1, position, " ");
                }
                ssb.setSpan(new MDCodeBlockSpan(mBackgroundColor,
                                language, (j == 1 ? true : false), (j == middleList.size() - 1 ? true : false),
                                ssb.subSequence(current, position).toString()),
                        current, position, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                current = position + 1;
            }
            if (!TextUtils.equals("", language)) {
                mPrettifyHighLighter.highLight(language, ssb, start, end);
            } else {
                current = middleList.get(0) + 1;
                for (int j = 1; j < middleList.size(); j++) {//放弃0，因为0是```java这样的
                    int position = middleList.get(j);
                    ssb.setSpan(new ForegroundColorSpan(mTextColor), current, position, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    current = position + 1;
                }
            }
            ssb.delete(end, end + SyntaxKey.KEY_CODE_BLOCK.length() + (end + SyntaxKey.KEY_CODE_BLOCK.length() >= ssb.length() ? 0 : 1));
            ssb.delete(start, Utils.findNextNewLineChar(ssb, start) + 1);
        }
        return ssb;
    }

    @NonNull
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        return new ArrayList<>();
    }
}
