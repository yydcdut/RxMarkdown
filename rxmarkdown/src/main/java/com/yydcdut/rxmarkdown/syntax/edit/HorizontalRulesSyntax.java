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

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.live.EditToken;
import com.yydcdut.rxmarkdown.span.MDHorizontalRulesSpan;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The implementation of grammar for horizontal rules.
 * Grammar:
 * "***"
 * <p>
 * "---"
 * <p>
 * "***********"
 * <p>
 * "----------------"
 * <p>
 * Created by yuyidong on 16/7/7.
 */
class HorizontalRulesSyntax extends EditSyntaxAdapter {

    private int mColor;
    private int mHeight;

    public HorizontalRulesSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
        mColor = rxMDConfiguration.getHorizontalRulesColor();
        mHeight = rxMDConfiguration.getHorizontalRulesHeight();
    }

    @NonNull
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        List<EditToken> editTokenList = new ArrayList<>();
        StringBuilder content = new StringBuilder(editable);
        List<String> matchList = new ArrayList<>();//找到的
        matchList.addAll(getMatchList(editable, "\\*"));
        matchList.addAll(getMatchList(editable, "\\-"));
        int size = matchList.size();
        for (int i = 0; i < size; i++) {
            String match = matchList.get(i);
            int index = content.indexOf(match);
            int length = match.length();
            content.replace(index, index + length, getPlaceHolder(match));
            editTokenList.add(new EditToken(new MDHorizontalRulesSpan(mColor, mHeight), index, index + length));
        }
        return editTokenList;
    }

    private List<String> getMatchList(@NonNull Editable editable, @NonNull String key) {
        Pattern p = Pattern.compile("^" + key + "{3,}$", Pattern.MULTILINE);
        Matcher m0 = p.matcher(editable);
        List<String> matchList = new ArrayList<>();//找到的
        while (m0.find()) {
            matchList.add(m0.group());
        }
        return matchList;
    }
}
