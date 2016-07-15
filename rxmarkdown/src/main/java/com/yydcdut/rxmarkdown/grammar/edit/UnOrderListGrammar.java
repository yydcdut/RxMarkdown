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
import com.yydcdut.rxmarkdown.span.MDUnOrderListSpan;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yydcdut.rxmarkdown.grammar.edit.OrderListGrammar.KEY_HEADER;

/**
 * The implementation of grammar for unorder list.
 * Grammar:
 * "* "
 * <p>
 * "+ "
 * <p>
 * "- "
 * <p>
 * Created by yuyidong on 16/7/8.
 */
public class UnOrderListGrammar extends EditGrammarAdapter {
    private static final String IGNORE_0 = "- [x]";
    private static final String IGNORE_1 = "- [X]";
    private static final String IGNORE_2 = "- [ ]";

    private int mColor;

    UnOrderListGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
        mColor = rxMDConfiguration.getUnOrderListColor();
    }

    @NonNull
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        List<EditToken> editTokenList = new ArrayList<>();
        List<String> matchList = new ArrayList<>();//找到的
        StringBuilder content = new StringBuilder(editable);
        //+ sss
        Pattern p0 = Pattern.compile("^( *)(\\+ )(.*?)$", Pattern.MULTILINE);
        Matcher m0 = p0.matcher(content);
        while (m0.find()) {
            matchList.add(m0.group());
        }
        //- sss
        Pattern p1 = Pattern.compile("^( *)(\\- )(.*?)$", Pattern.MULTILINE);
        Matcher m1 = p1.matcher(content);
        while (m1.find()) {
            String match = m1.group();
            if (!(match.startsWith(IGNORE_0) ||
                    match.startsWith(IGNORE_1) ||
                    match.startsWith(IGNORE_2))) {
                matchList.add(match);
            }
        }
        //* sss
        Pattern p2 = Pattern.compile("^( *)(\\* )(.*?)$", Pattern.MULTILINE);
        Matcher m2 = p2.matcher(content);
        while (m2.find()) {
            matchList.add(m2.group());
        }
        for (String match : matchList) {
            int index = content.indexOf(match);
            int length = match.length();
            int nested = calculateNested(match);
            editTokenList.add(new EditToken(new MDUnOrderListSpan(10, mColor, nested), index, index + length));
            content.replace(index, index + length, getPlaceHolder(match));
        }
        return editTokenList;
    }

    private int calculateNested(String text) {
        if (text.length() < 2) {
            return -1;
        }
        int nested = 0;
        while (true) {
            if ((nested + 1) * KEY_HEADER.length() > text.length()) {
                break;
            }
            String sub = text.substring(nested * KEY_HEADER.length(), (nested + 1) * KEY_HEADER.length());
            if (KEY_HEADER.equals(sub)) {//还是" "
                nested++;
            } else {
                return nested;
            }
        }
        return nested;
    }
}
