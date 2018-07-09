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
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for edit controller in RxMarkdown
 * <p>
 * Created by yuyidong on 2017/6/6.
 */
public class TextHelper {

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
     * whether the content contains the key
     *
     * @param key          the key string
     * @param string       the content
     * @param beforeString the text text before key position
     * @param afterString  the text text after key position
     * @return boolean
     */
    public static boolean isNeedFormat(String key, String string, String beforeString, String afterString) {
        return string.contains(key) || key.equals(beforeString) || key.equals(afterString);
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
            return new StringBuilder().append("- [ ] ").append(ssb.subSequence(TextHelper.safePosition("- [ ] ".length(), ssb), TextHelper.safePosition(ssb.length() - 1, ssb))).toString();
        } else {
            return new StringBuilder().append("- [x] ").append(ssb.subSequence(TextHelper.safePosition("- [x] ".length(), ssb), TextHelper.safePosition(ssb.length() - 1, ssb))).toString();
        }
    }

    /**
     * get char from char array
     *
     * @param array char array
     * @param index the index in the char array
     * @return the char
     */
    public static char getChar(char[] array, int index) {
        if (array == null || index < 0 || index >= array.length) {
            return 0;
        }
        return array[index];
    }

    /**
     * get position safely
     *
     * @param position the position
     * @param s        the CharSequence
     * @return the safe position
     */
    public static int safePosition(int position, CharSequence s) {
        return position > s.length() ? s.length() : (position < 0 ? 0 : position);
    }
}
