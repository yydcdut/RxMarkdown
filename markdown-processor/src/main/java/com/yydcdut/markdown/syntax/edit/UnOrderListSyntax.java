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
package com.yydcdut.markdown.syntax.edit;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Spannable;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.live.EditToken;
import com.yydcdut.markdown.span.MDUnOrderListSpan;
import com.yydcdut.markdown.syntax.SyntaxKey;
import com.yydcdut.markdown.utils.TextHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The implementation of syntax for unorder list.
 * syntax:
 * "* "
 * <p>
 * "+ "
 * <p>
 * "- "
 * <p>
 * Created by yuyidong on 16/7/8.
 */
class UnOrderListSyntax extends EditSyntaxAdapter {

    private int mColor;

    public UnOrderListSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        super(markdownConfiguration);
        mColor = markdownConfiguration.getUnOrderListColor();
    }

    @NonNull
    @Override
    @SuppressLint("WrongConstant")
    public List<EditToken> format(@NonNull Editable editable) {
        List<EditToken> editTokenList = new ArrayList<>();
        List<String> matchList0 = new ArrayList<>();//找到的
        List<String> matchList1 = new ArrayList<>();//找到的
        List<String> matchList2 = new ArrayList<>();//找到的
        StringBuilder content = new StringBuilder(editable);
        replaceTodo(content);
        //+ sss
        Pattern p0 = Pattern.compile("^( *)(\\+ )(.*?)$", Pattern.MULTILINE);
        Matcher m0 = p0.matcher(content);
        while (m0.find()) {
            matchList0.add(m0.group());
        }
        //- sss
        Pattern p1 = Pattern.compile("^( *)(\\- )(.*?)$", Pattern.MULTILINE);
        Matcher m1 = p1.matcher(content);
        while (m1.find()) {
            matchList1.add(m1.group());
        }
        //* sss
        Pattern p2 = Pattern.compile("^( *)(\\* )(.*?)$", Pattern.MULTILINE);
        Matcher m2 = p2.matcher(content);
        while (m2.find()) {
            matchList2.add(m2.group());
        }
        replace(matchList0, MDUnOrderListSpan.TYPE_KEY_2, content, editTokenList);
        replace(matchList1, MDUnOrderListSpan.TYPE_KEY_1, content, editTokenList);
        replace(matchList2, MDUnOrderListSpan.TYPE_KEY_0, content, editTokenList);
        return editTokenList;
    }

    private void replace(List<String> matchList, int type, StringBuilder content, List<EditToken> editTokenList) {
        for (String match : matchList) {
            int index = content.indexOf(match);
            int length = match.length();
            int nested = calculateNested(match);
            editTokenList.add(new EditToken(new MDUnOrderListSpan(10, mColor, nested, type), index, index + length, Spannable.SPAN_INCLUSIVE_INCLUSIVE));
            content.replace(index, index + length, TextHelper.getPlaceHolder(match));
        }
    }

    private int calculateNested(String text) {
        if (text.length() < 2) {
            return -1;
        }
        int nested = 0;
        while (true) {
            if ((nested + 1) * SyntaxKey.KEY_LIST_HEADER.length() > text.length()) {
                break;
            }
            String sub = text.substring(nested * SyntaxKey.KEY_LIST_HEADER.length(), (nested + 1) * SyntaxKey.KEY_LIST_HEADER.length());
            if (SyntaxKey.KEY_LIST_HEADER.equals(sub)) {//还是" "
                nested++;
            } else {
                return nested;
            }
        }
        return nested;
    }

    private void replaceTodo(StringBuilder stringBuilder) {
        while (true) {
            int index0 = stringBuilder.indexOf(SyntaxKey.IGNORE_LIST_HYPHEN_LOW);
            if (index0 == -1) {
                break;
            }
            stringBuilder.replace(index0, index0 + SyntaxKey.IGNORE_LIST_HYPHEN_LOW.length(), SyntaxKey.IGNORE_LIST_PLACE_HOLDER);
        }
        while (true) {
            int index0 = stringBuilder.indexOf(SyntaxKey.IGNORE_LIST_HYPHEN_UP);
            if (index0 == -1) {
                break;
            }
            stringBuilder.replace(index0, index0 + SyntaxKey.IGNORE_LIST_HYPHEN_UP.length(), SyntaxKey.IGNORE_LIST_PLACE_HOLDER);
        }
        while (true) {
            int index0 = stringBuilder.indexOf(SyntaxKey.IGNORE_LIST_HYPHEN);
            if (index0 == -1) {
                break;
            }
            stringBuilder.replace(index0, index0 + SyntaxKey.IGNORE_LIST_HYPHEN.length(), SyntaxKey.IGNORE_LIST_PLACE_HOLDER);
        }
        while (true) {
            int index0 = stringBuilder.indexOf(SyntaxKey.IGNORE_LIST_ASTERISK_LOW);
            if (index0 == -1) {
                break;
            }
            stringBuilder.replace(index0, index0 + SyntaxKey.IGNORE_LIST_ASTERISK_LOW.length(), SyntaxKey.IGNORE_LIST_PLACE_HOLDER);
        }
        while (true) {
            int index0 = stringBuilder.indexOf(SyntaxKey.IGNORE_LIST_ASTERISK_UP);
            if (index0 == -1) {
                break;
            }
            stringBuilder.replace(index0, index0 + SyntaxKey.IGNORE_LIST_ASTERISK_UP.length(), SyntaxKey.IGNORE_LIST_PLACE_HOLDER);
        }
        while (true) {
            int index0 = stringBuilder.indexOf(SyntaxKey.IGNORE_LIST_ASTERISK);
            if (index0 == -1) {
                break;
            }
            stringBuilder.replace(index0, index0 + SyntaxKey.IGNORE_LIST_ASTERISK.length(), SyntaxKey.IGNORE_LIST_PLACE_HOLDER);
        }
    }
}
