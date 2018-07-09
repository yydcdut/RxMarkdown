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

import android.support.annotation.NonNull;
import android.text.Editable;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.live.EditToken;
import com.yydcdut.markdown.span.MDQuoteSpan;
import com.yydcdut.markdown.syntax.SyntaxKey;
import com.yydcdut.markdown.utils.TextHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The implementation of syntax for block quotes.
 * syntax:
 * "> "
 * <p>
 * Created by yuyidong on 16/6/30.
 */
class BlockQuotesSyntax extends EditSyntaxAdapter {
    private static final String PATTERN = "^(> )(.*?)$";

    private int mColor;

    public BlockQuotesSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        super(markdownConfiguration);
        mColor = markdownConfiguration.getBlockQuotesLineColor();
    }

    @NonNull
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        List<EditToken> editTokenList = new ArrayList<>();
        StringBuilder content = new StringBuilder(editable);
        Matcher m = Pattern.compile(PATTERN, Pattern.MULTILINE).matcher(content);
        List<String> matchList = new ArrayList<>();//找到的
        while (m.find()) {
            matchList.add(m.group());
        }
        for (String match : matchList) {
            int nested = calculateNested(match);
            if (nested == 0) {
                return editTokenList;
            }
            int index = content.indexOf(match);
            int length = match.length();
            editTokenList.add(new EditToken(new MDQuoteSpan(mColor, nested), index, index + length));
            content.replace(index, index + length, TextHelper.getPlaceHolder(match));
        }
        return editTokenList;
    }

    /**
     * calculate nested, one "> ", nest++
     *
     * @param text the content
     * @return nested number of content
     */
    private static int calculateNested(@NonNull String text) {
        int nested = 0;
        while (true) {
            if ((nested + 1) * SyntaxKey.KEY_BLOCK_QUOTES.length() > text.length()) {
                break;
            }
            String sub = text.substring(nested * SyntaxKey.KEY_BLOCK_QUOTES.length(), (nested + 1) * SyntaxKey.KEY_BLOCK_QUOTES.length());
            if (!SyntaxKey.KEY_BLOCK_QUOTES.equals(sub)) {
                break;
            }
            ++nested;
        }
        return nested;
    }
}
