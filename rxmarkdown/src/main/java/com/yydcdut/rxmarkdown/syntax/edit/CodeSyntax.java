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
package com.yydcdut.rxmarkdown.syntax.edit;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Pair;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.live.EditToken;
import com.yydcdut.rxmarkdown.span.MDCodeBlockSpan;
import com.yydcdut.rxmarkdown.syntax.SyntaxKey;
import com.yydcdut.rxmarkdown.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of syntax for code.
 * syntax:
 * "```
 * content
 * ```"
 * <p>
 * Created by yuyidong on 16/6/30.
 */
class CodeSyntax extends EditSyntaxAdapter {

    private int mColor;

    public CodeSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
        mColor = rxMDConfiguration.getTheme().getBackgroundColor();
    }

    @NonNull
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        List<EditToken> editTokenList = new ArrayList<>();
        List<Pair<Integer, Integer>> list = Utils.find(editable.toString(), SyntaxKey.KEY_CODE_BLOCK);
        for (int i = list.size() - 1; i >= 0; i--) {
            Pair<Integer, Integer> pair = list.get(i);
            int start = pair.first;
            int end = pair.second;
            List<Integer> middleList = Utils.getMiddleNewLineCharPosition((SpannableStringBuilder) editable, start, end);
            int current = start;
            MDCodeBlockSpan parentSpan = null;
            for (int j = 0; j < middleList.size(); j++) {
                int position = middleList.get(j);
                MDCodeBlockSpan mdCodeBlockSpan = new MDCodeBlockSpan(mColor);
                if (position == current) {//处理只有换行符
                    editTokenList.add(new EditToken(mdCodeBlockSpan, position - 1, position + 1, j == 0 ? Spannable.SPAN_EXCLUSIVE_INCLUSIVE : Spannable.SPAN_INCLUSIVE_INCLUSIVE));
                } else {
                    editTokenList.add(new EditToken(mdCodeBlockSpan, current, position, j == 0 ? Spannable.SPAN_EXCLUSIVE_INCLUSIVE : Spannable.SPAN_INCLUSIVE_INCLUSIVE));
                }
                if (parentSpan != null) {
                    parentSpan.setNext(mdCodeBlockSpan);
                }
                parentSpan = mdCodeBlockSpan;
                current = position + 1;
            }
            MDCodeBlockSpan mdCodeBlockSpan = new MDCodeBlockSpan(mColor);
            editTokenList.add(new EditToken(mdCodeBlockSpan, end,
                    end + SyntaxKey.KEY_CODE_BLOCK.length() + (end + SyntaxKey.KEY_CODE_BLOCK.length() >= editable.length() ? 0 : 1),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE));
            if (parentSpan != null) {
                parentSpan.setNext(mdCodeBlockSpan);
            }
        }
        return editTokenList;
    }
}
