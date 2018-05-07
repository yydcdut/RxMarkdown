/*
 * Copyright (C) 2017 yydcdut (yuyidong2015@gmail.com)
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
package com.yydcdut.markdown.prettify;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.theme.Theme;
import com.yydcdut.markdown.utils.Utils;

import java.util.List;
import java.util.Map;

import prettify.PrettifyParser;
import syntaxhighlight.ParseResult;
import syntaxhighlight.Parser;

/**
 * high light
 * <p>
 * Created by yuyidong on 2017/5/26.
 */
public class PrettifyHighLighter {
    private Map<String, Integer> mColorMap;
    private Parser mParser;

    /**
     * Constructor
     *
     * @param markdownConfiguration the config
     */
    public PrettifyHighLighter(@NonNull MarkdownConfiguration markdownConfiguration) {
        mColorMap = Utils.buildColorsMap(markdownConfiguration.getTheme());
        mParser = new PrettifyParser();
    }

    /**
     * high light
     *
     * @param language   programing language
     * @param sourceCode the content
     * @param start      start position
     * @param end        end position
     * @return SpannableStringBuilder
     */
    public SpannableStringBuilder highLight(String language, SpannableStringBuilder sourceCode, int start, int end) {
        List<ParseResult> results = mParser.parse(language, sourceCode.toString().substring(start, end));
        for (ParseResult result : results) {
            String type = result.getStyleKeys().get(0);
            sourceCode.setSpan(new ForegroundColorSpan(getColor(type)), start + result.getOffset(), start + result.getOffset() + result.getLength(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return sourceCode;
    }

    private int getColor(String type) {
        return mColorMap.containsKey(type) ? mColorMap.get(type) : mColorMap.get(Theme.CODE_PLN);
    }


}
