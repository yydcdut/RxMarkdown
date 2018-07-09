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
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.live.EditToken;
import com.yydcdut.markdown.syntax.SyntaxKey;
import com.yydcdut.markdown.utils.TextHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The implementation of syntax for header.
 * syntax:
 * "# " for h1
 * <p>
 * "## " for h2
 * <p>
 * "### " for h3
 * <p>
 * "#### " for h4
 * <p>
 * "##### " for h5
 * <p>
 * "###### " for h6
 * <p>
 * Created by yuyidong on 16/6/30.
 */
class HeaderSyntax extends EditSyntaxAdapter {
    private static final String PATTERN = "^(#{1,6})( )(.*?)$";
    private static final String PATTERN_WITH_CENTER_ALIGN = "^\\[(#{1,6}( )(.*?)\\]$)";

    private float mHeader1RelativeSize;
    private float mHeader2RelativeSize;
    private float mHeader3RelativeSize;
    private float mHeader4RelativeSize;
    private float mHeader5RelativeSize;
    private float mHeader6RelativeSize;

    public HeaderSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        super(markdownConfiguration);
        mHeader1RelativeSize = markdownConfiguration.getHeader1RelativeSize();
        mHeader2RelativeSize = markdownConfiguration.getHeader2RelativeSize();
        mHeader3RelativeSize = markdownConfiguration.getHeader3RelativeSize();
        mHeader4RelativeSize = markdownConfiguration.getHeader4RelativeSize();
        mHeader5RelativeSize = markdownConfiguration.getHeader5RelativeSize();
        mHeader6RelativeSize = markdownConfiguration.getHeader6RelativeSize();
    }

    @NonNull
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        List<EditToken> editTokenList = new ArrayList<>();
        StringBuilder content = new StringBuilder(editable);
        Matcher m = Pattern.compile(PATTERN, Pattern.MULTILINE).matcher(content);
        List<String> matchList = new ArrayList<>();//找到的
        Map<String, Integer> specialMap = new HashMap<>();//处理特殊的
        while (m.find()) {
            String matchString = m.group();
            if (matchSpecial(matchString)) {
                addSpecial(matchString, specialMap);
            } else {
                matchList.add(matchString);
            }
        }
        Matcher m2 = Pattern.compile(PATTERN_WITH_CENTER_ALIGN, Pattern.MULTILINE).matcher(content);
        while (m2.find()) {
            String matchString = m2.group();
            if (matchSpecial(matchString)) {
                addSpecial(matchString, specialMap);
            } else {
                matchList.add(matchString);
            }
        }
        for (String match : matchList) {
            replace(content, match, editTokenList);
        }
        replaceSpecial(specialMap, content, editTokenList);
        return editTokenList;
    }

    /**
     * get span
     *
     * @param match the matched string
     * @return the span
     */
    private Object getSpan(String match) {
        if (match.startsWith(SyntaxKey.KEY_CENTER_ALIGN_LEFT)) {
            return getRealSpan(match);
        } else {
            return getRealSpan(match);
        }
    }

    /**
     * get span
     *
     * @param match the matched string
     * @return the span
     */
    private Object getRealSpan(String match) {
        if (match.contains(SyntaxKey.KEY_5_HEADER)) {
            return new RelativeSizeSpan(mHeader6RelativeSize);
        } else if (match.contains(SyntaxKey.KEY_4_HEADER)) {
            return new RelativeSizeSpan(mHeader5RelativeSize);
        } else if (match.contains(SyntaxKey.KEY_3_HEADER)) {
            return new RelativeSizeSpan(mHeader4RelativeSize);
        } else if (match.contains(SyntaxKey.KEY_2_HEADER)) {
            return new RelativeSizeSpan(mHeader3RelativeSize);
        } else if (match.contains(SyntaxKey.KEY_1_HEADER)) {
            return new RelativeSizeSpan(mHeader2RelativeSize);
        } else if (match.contains(SyntaxKey.KEY_0_HEADER)) {
            return new RelativeSizeSpan(mHeader1RelativeSize);
        }
        return new RelativeSizeSpan(1.0f);
    }

    /**
     * match special string
     *
     * @param match the matched string
     * @return if matched
     */
    private static boolean matchSpecial(String match) {
        if (TextUtils.equals(match, SyntaxKey.KEY_0_HEADER)
                || TextUtils.equals(match, SyntaxKey.KEY_1_HEADER)
                || TextUtils.equals(match, SyntaxKey.KEY_2_HEADER)
                || TextUtils.equals(match, SyntaxKey.KEY_3_HEADER)
                || TextUtils.equals(match, SyntaxKey.KEY_4_HEADER)
                || TextUtils.equals(match, SyntaxKey.KEY_5_HEADER)) {
            return true;
        }
        return false;
    }

    /**
     * add header to special map
     *
     * @param match the match string
     * @param map   the special map
     */
    private static void addSpecial(String match, Map<String, Integer> map) {
        Integer integer = map.get(match);
        if (integer == null) {
            map.put(match, 1);
        } else {
            map.remove(match);
            map.put(match, ++integer);
        }
    }

    /**
     * replace header
     *
     * @param content       the content
     * @param match         the match string
     * @param editTokenList the edit syntax list
     */
    private void replace(StringBuilder content, String match, List<EditToken> editTokenList) {
        int index = content.indexOf(match);
        int length = match.length();
        editTokenList.add(new EditToken(getSpan(match), index, index + length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE));
        content.replace(index, index + length, TextHelper.getPlaceHolder(match));
    }

    /**
     * replace header
     *
     * @param specialMap    the header map
     * @param content       the content
     * @param editTokenList the edit syntax list
     */
    private void replaceSpecial(Map<String, Integer> specialMap, StringBuilder content, List<EditToken> editTokenList) {
        Integer num5Int = specialMap.get(SyntaxKey.KEY_5_HEADER);
        if (num5Int != null && num5Int > 0) {
            replace(content, SyntaxKey.KEY_5_HEADER, editTokenList);
        }
        Integer num4Int = specialMap.get(SyntaxKey.KEY_4_HEADER);
        if (num4Int != null && num4Int > 0) {
            replace(content, SyntaxKey.KEY_4_HEADER, editTokenList);
        }
        Integer num3Int = specialMap.get(SyntaxKey.KEY_3_HEADER);
        if (num3Int != null && num3Int > 0) {
            replace(content, SyntaxKey.KEY_3_HEADER, editTokenList);
        }
        Integer num2Int = specialMap.get(SyntaxKey.KEY_2_HEADER);
        if (num2Int != null && num2Int > 0) {
            replace(content, SyntaxKey.KEY_2_HEADER, editTokenList);
        }
        Integer num1Int = specialMap.get(SyntaxKey.KEY_1_HEADER);
        if (num1Int != null && num1Int > 0) {
            replace(content, SyntaxKey.KEY_1_HEADER, editTokenList);
        }
        Integer num0Int = specialMap.get(SyntaxKey.KEY_0_HEADER);
        if (num0Int != null && num0Int > 0) {
            replace(content, SyntaxKey.KEY_0_HEADER, editTokenList);
        }
    }
}
