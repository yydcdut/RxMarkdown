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
package com.yydcdut.rxmarkdown.grammar.edit;

import android.support.annotation.NonNull;
import android.text.Editable;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.edit.EditToken;
import com.yydcdut.rxmarkdown.span.MDCodeSpan;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The implementation of grammar for code.
 * Grammar:
 * "```
 * content
 * ```"
 * <p>
 * Created by yuyidong on 16/6/30.
 */
class CodeGrammar extends EditGrammarAdapter {

    private int mColor;

    CodeGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
        mColor = rxMDConfiguration.getCodeBgColor();
    }

    @NonNull
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        List<EditToken> editTokenList = new ArrayList<>();
        StringBuilder content = new StringBuilder(editable);
        Pattern p = Pattern.compile("^```$", Pattern.MULTILINE);
        Matcher m = p.matcher(content);
        List<String> matchList = new ArrayList<>();//找到的
        while (m.find()) {
            matchList.add(m.group());
        }
        int size = matchList.size() % 2 == 0 ? matchList.size() : matchList.size() - 1;
        int index = 0;
        for (int i = 0; i < size; i++) {
            String match = matchList.get(i);
            if (i % 2 == 0) {
                index = content.indexOf(match);
                char c4 = content.charAt(index + 3);
                int length = match.length();
                content.replace(index, index + length, getPlaceHolder(match));
                if (c4 != '\n') {
                    index = 0;
                    i--;
                    continue;
                }
                if (index > 0) {
                    char c0 = content.charAt(index - 1);
                    if (c0 != '\n') {
                        index = 0;
                        i--;
                        continue;
                    }
                }
            } else {
                int currentIndex = content.indexOf(match);
                int length = match.length();
                content.replace(currentIndex, currentIndex + length, getPlaceHolder(match));
                char c4 = content.charAt(currentIndex + 3);
                char c0 = content.charAt(index == 0 ? index : index - 1);
                if ((index != 0 && c0 != '\n') || c4 != '\n') {
                    i--;
                    continue;
                } else {
                    editTokenList.add(new EditToken(new MDCodeSpan(mColor), index, currentIndex + length));
                }
            }
        }
        return editTokenList;
    }
}
