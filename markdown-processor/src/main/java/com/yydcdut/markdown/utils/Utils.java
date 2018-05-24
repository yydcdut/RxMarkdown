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
package com.yydcdut.markdown.utils;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Pair;

import com.yydcdut.markdown.live.EditToken;
import com.yydcdut.markdown.span.MDCodeBlockSpan;
import com.yydcdut.markdown.theme.Theme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for edit controller in RxMarkdown
 * <p>
 * Created by yuyidong on 2017/6/6.
 */
public class Utils {

    /**
     * find '\n' from "start" position
     * if not find, return -1
     *
     * @param s     text
     * @param start start position
     * @return the '\n' position
     */
    public static int findNextNewLineChar(CharSequence s, int start) {
        for (int i = start; i < s.length(); i++) {
            if (s.charAt(i) == '\n') {
                return i;
            }
        }
        return -1;
    }

    /**
     * find '\n' from "start" position
     * if not find, return s.length()
     *
     * @param s     text
     * @param start start position
     * @return the '\n' position
     */
    public static int findNextNewLineCharCompat(CharSequence s, int start) {
        int position = findNextNewLineChar(s, start);
        if (position == -1) {
            return s.length();
        }
        return position;
    }

    /**
     * find '\n' before "start" position
     *
     * @param s     text
     * @param start start position
     * @return the '\n' position
     */
    public static int findBeforeNewLineChar(CharSequence s, int start) {
        for (int i = start - 1; i > 0; i--) {
            if (s.charAt(i) == '\n') {
                return i;
            }
        }
        return -1;
    }

    /**
     * remove spans
     *
     * @param editable Editable, the text
     * @param start    int, the selection position
     * @param clazz    class
     * @param <T>      span
     */
    public static <T> void removeSpans(Editable editable, int start, Class<T> clazz) {
        int startPosition = findBeforeNewLineChar(editable, start) + 1;
        int endPosition = findNextNewLineCharCompat(editable, start);
        T[] ts = editable.getSpans(startPosition, endPosition, clazz);
        if (clazz.isAssignableFrom(MDCodeBlockSpan.class)) {
            for (T t : ts) {
                MDCodeBlockSpan mdCodeBlockSpan = ((MDCodeBlockSpan) t);
                while (mdCodeBlockSpan != null) {
                    editable.removeSpan(mdCodeBlockSpan);
                    mdCodeBlockSpan = mdCodeBlockSpan.getNext();
                }
            }
        } else {
            for (T t : ts) {
                editable.removeSpan(t);
            }
        }
    }

    /**
     * set spans
     *
     * @param editable      Editable, the text
     * @param editTokenList List, the edit token collection
     */
    public static void setSpans(Editable editable, List<EditToken> editTokenList) {
        for (EditToken editToken : editTokenList) {
            editable.setSpan(editToken.getSpan(), editToken.getStart(), editToken.getEnd(), editToken.getFlag());
        }
    }

    /**
     * set spans for  code span
     *
     * @param editable      Editable, the text
     * @param editTokenList List, the edit token collection
     */
    public static void setCodeSpan(Editable editable, List<EditToken> editTokenList) {
        for (EditToken editToken : editTokenList) {
            Object[] spans = editable.getSpans(editToken.getStart(), editToken.getEnd(), Object.class);
            for (Object o : spans) {
                if (editToken.getStart() <= editable.getSpanStart(o) && editToken.getEnd() >= editable.getSpanEnd(o)) {
                    editable.removeSpan(o);
                }
            }
        }
        setSpans(editable, editTokenList);
    }

    /**
     * get matched edit token list
     *
     * @param editable Editable, the text
     * @param allList  List, the edit token collection
     * @param start    the selection position
     * @return the matched edit token list
     */
    public static List<EditToken> getMatchedEditTokenList(Editable editable, List<EditToken> allList, int start) {
        List<EditToken> matchEditTokenList = new ArrayList<>();
        int startPosition = findBeforeNewLineChar(editable, start) + 1;
        int endPosition = findNextNewLineCharCompat(editable, start);
        for (EditToken editToken : allList) {
            if (editToken.getStart() >= startPosition && editToken.getEnd() <= endPosition) {
                matchEditTokenList.add(editToken);
            }
        }
        return matchEditTokenList;
    }

    /**
     * find the key code start positions and end positions
     * especially for code syntax
     *
     * @param text     the content
     * @param KEY_CODE the key code
     * @return the list contains start positions and end positions for key code
     */
    public static List<Pair<Integer, Integer>> find(@NonNull String text, @NonNull String KEY_CODE) {
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        String[] lines = text.split("\n");
        int start = -1;
        int end = -1;
        int currentLength = 0;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].startsWith(KEY_CODE)) {
                if (start == -1) {
                    start = currentLength;
                } else if (end == -1 && TextUtils.equals(lines[i], KEY_CODE)) {
                    end = currentLength;
                }
                if (start != -1 && end != -1) {
                    list.add(new Pair<>(start, end));
                    start = -1;
                    end = -1;
                }
            }
            currentLength += lines[i].length() + "\n".length();
        }
        return list;
    }

    /**
     * get the positions of '\n' from SpannableStringBuilder from start to end
     *
     * @param ssb   the content
     * @param start the start position
     * @param end   the end position
     * @return the '\n' positions
     */
    public static List<Integer> getNewLineCharPosition(SpannableStringBuilder ssb, int start, int end) {
        List<Integer> list = new ArrayList<>();
        for (int i = start; i < end; i++) {
            if (ssb.charAt(i) == '\n') {
                list.add(i);
            }
        }
        return list;
    }

    /**
     * get place holder for match string
     *
     * @param matchString String, the match string
     * @return the place holder string
     */
    public static String getPlaceHolder(String matchString) {
        int length = matchString.length();
        StringBuilder placeHolder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            placeHolder.append(" ");
        }
        return placeHolder.toString();
    }

    /**
     * get the color map by Theme
     *
     * @param theme theme
     * @return map
     */
    public static Map<String, Integer> buildColorsMap(Theme theme) {
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

    /**
     * replace line change, DOS to Unix and Mac to Unix
     *
     * @param text        the original text content
     * @param regex       the regex to match
     * @param replacement the replacement text
     * @return the text after replace
     */
    public static StringBuilder standardizeLineEndings(StringBuilder text, String regex, String replacement) {
        Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher m = p.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, replacement);
        }
        m.appendTail(sb);
        return new StringBuilder(sb);
    }

    /**
     * replace line change, DOS to Unix and Mac to Unix
     *
     * @param ssb         the original text content
     * @param target      the target to match
     * @param replacement the replacement text
     */
    public static void standardizeLineEndings(SpannableStringBuilder ssb, String target, String replacement) {
        String string = ssb.toString();
        while (string.indexOf(target) != -1) {
            int start = string.indexOf(target);
            ssb.replace(start, start + target.length(), replacement);
            string = ssb.toString();
        }
    }

    /**
     * format SpannableStringBuilder for _todo syntax or done syntax
     *
     * @param ssb    the original text
     * @param isTodo is _todo or done?
     * @return the formatted text
     */
    public static String formatTodoLine(SpannableStringBuilder ssb, boolean isTodo) {
        if (isTodo) {
            return new StringBuilder().append("- [ ] ").append(ssb.subSequence("- [ ] ".length(), ssb.length() - 1)).toString();
        } else {
            return new StringBuilder().append("- [x] ").append(ssb.subSequence("- [x] ".length(), ssb.length() - 1)).toString();
        }
    }

}
