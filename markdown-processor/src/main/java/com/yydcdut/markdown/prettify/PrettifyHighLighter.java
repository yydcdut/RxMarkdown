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

import java.util.HashMap;
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
        mColorMap = buildColorsMap(markdownConfiguration.getTheme());
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

    //get the color by type
    private int getColor(String type) {
        return mColorMap.containsKey(type) ? mColorMap.get(type) : mColorMap.get(Theme.CODE_PLN);
    }

    //get the color map by Theme
    private static Map<String, Integer> buildColorsMap(Theme theme) {
        Map<String, Integer> map = new HashMap<>();
        map.put(Theme.CODE_TYP, theme.getTypeColor());
        map.put(Theme.CODE_KWD, theme.getKeyWordColor());
        map.put(Theme.CODE_LIT, theme.getLiteralColor());
        map.put(Theme.CODE_COM, theme.getCommentColor());
        map.put(Theme.CODE_STR, theme.getStringColor());
        map.put(Theme.CODE_PUN, theme.getPunctuationColor());
        map.put(Theme.CODE_TAG, theme.getTagColor());
        map.put(Theme.CODE_PLN, theme.getPlainTextColor());
        map.put(Theme.CODE_DEC, theme.getDecimalColor());
        map.put(Theme.CODE_ATN, theme.getAttributeNameColor());
        map.put(Theme.CODE_ATV, theme.getAttributeValueColor());
        map.put(Theme.CODE_OPN, theme.getOpnColor());
        map.put(Theme.CODE_CLO, theme.getCloColor());
        map.put(Theme.CODE_VAR, theme.getVarColor());
        map.put(Theme.CODE_FUN, theme.getFunColor());
        map.put(Theme.CODE_NOCODE, theme.getNocodeColor());
        return map;
    }
}
