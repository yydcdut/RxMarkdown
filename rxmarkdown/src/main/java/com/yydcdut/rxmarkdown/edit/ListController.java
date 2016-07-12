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
package com.yydcdut.rxmarkdown.edit;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.RxMDEditText;
import com.yydcdut.rxmarkdown.span.MDOrderListSpan;
import com.yydcdut.rxmarkdown.span.MDUnOrderListSpan;

/**
 * RxMDEditText, order and unorder list controller.
 * When input "enter(\n)", RxMDEditText will check the line up the new line whether is order nor unorder list.
 * If it is, RxMDEditText will add order or unorder list grammar key automatically.
 * <p>
 * Created by yuyidong on 16/7/8.
 */
public class ListController {

    private RxMDEditText mRxMDEditText;
    private TextWatcher mTextWatcher;
    private RxMDConfiguration mRxMDConfiguration;

    /**
     * Constructor
     *
     * @param rxMDEditText RxMDEditText
     * @param textWatcher  TextWatcher
     */
    public ListController(RxMDEditText rxMDEditText, TextWatcher textWatcher) {
        mRxMDEditText = rxMDEditText;
        mTextWatcher = textWatcher;
    }

    /**
     * set configuration
     *
     * @param rxMDConfiguration RxMDConfiguration
     */
    public void setRxMDConfiguration(@Nullable RxMDConfiguration rxMDConfiguration) {
        mRxMDConfiguration = rxMDConfiguration;
    }

    /**
     * invoke when beforeTextChanged
     *
     * @param s      CharSequence
     * @param start  start position
     * @param before before changed number
     * @param after  after changed number
     */
    public void beforeTextChanged(CharSequence s, int start, int before, int after) {
        if (mRxMDConfiguration == null && (s instanceof Editable)) {
            return;
        }
        Editable editable = (Editable) s;
        if (checkLineDelete(editable, start, before, after)) {
            int beforeLinePosition = findBeforeNewLineChar(editable, start - 1) + 1;
            if (beforeLinePosition == -1) {
                beforeLinePosition = 0;
            }
            MDOrderListSpan mdOrderListSpan = getOrderListSpan(mRxMDEditText.getText(), beforeLinePosition, true);
            MDUnOrderListSpan mdUnOrderListSpan = getUnOrderListSpan(mRxMDEditText.getText(), beforeLinePosition, true);
            if (mdOrderListSpan != null) {
                int spanStart = editable.getSpanStart(mdOrderListSpan);
                int position = findNextNewLineChar(editable, start);
                if (position == -1) {
                    position = editable.length();
                }
                mRxMDEditText.getText().removeSpan(mdOrderListSpan);
                mRxMDEditText.getText().setSpan(new MDOrderListSpan(10, mdOrderListSpan.getNested(), mdOrderListSpan.getNumber()),
                        spanStart, position, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (mdUnOrderListSpan != null) {
                int spanStart = editable.getSpanStart(mdUnOrderListSpan);
                int position = findNextNewLineChar(editable, start);
                if (position == -1) {
                    position = editable.length();
                }
                mRxMDEditText.getText().removeSpan(mdUnOrderListSpan);
                mRxMDEditText.getText().setSpan(new MDUnOrderListSpan(10, mdUnOrderListSpan.getColor(), mdUnOrderListSpan.getNested()),
                        spanStart, position, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    /**
     * invoke when onTextChanged
     *
     * @param s      CharSequence
     * @param start  start position
     * @param before before changed number
     * @param after  after changed number
     */
    public void onTextChanged(CharSequence s, int start, int before, int after) {
        if (mRxMDConfiguration == null && (s instanceof Editable)) {
            return;
        }
        Editable editable = (Editable) s;
        if (checkNewLineInput(editable, start, before, after)) {
            MDOrderListSpan mdOrderListSpan = getOrderListSpan(editable, start, false);
            MDUnOrderListSpan mdUnOrderListSpan = getUnOrderListSpan(editable, start, false);
            if (mdOrderListSpan != null) {
                updateOrderListSpanBeforeNewLine(editable, start, mdOrderListSpan);
                insertOrderList(editable, mdOrderListSpan, start);
            } else if (mdUnOrderListSpan != null) {
                updateUnOrderListSpanBeforeNewLine(editable, start, mdUnOrderListSpan);
                insertUnOrderList(editable, mdUnOrderListSpan, start);
            }
        } else if (isEndOfListSpan(editable, start)) {
            updateListSpanEnd(editable, start, before, after);
        } else if (isBeginningOfListSpan(editable, start, before, after)) {
            updateListSpanBeginning(editable, start, before, after);
        }
    }

    /**
     * the text that automatic filling after enter "\n"
     *
     * @param nested      the nested number
     * @param isOrderList is order list or unorder list
     * @param number      if order list, the list number
     * @return the text
     */
    private static String getNestedString(int nested, boolean isOrderList, int number) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nested; i++) {
            sb.append(" ");
        }
        if (isOrderList) {
            sb.append(String.valueOf(number + 1));
            sb.append(". ");
        } else {
            sb.append("+ ");
        }
        return sb.toString();
    }

    /**
     * 1. aaa --> 1. aaa\n2.
     *
     * @param editable        the editable
     * @param mdOrderListSpan the order list span
     * @param start           start position
     */
    private void insertOrderList(Editable editable, MDOrderListSpan mdOrderListSpan, int start) {
        mRxMDEditText.removeTextChangedListener(mTextWatcher);
        String appendString = getNestedString(mdOrderListSpan.getNested(), true, mdOrderListSpan.getNumber());
        editable.insert(start + 1, appendString);
        int position = findNextNewLineChar(editable, start + appendString.length());
        if (position == -1) {
            position = editable.length();
        }
        editable.setSpan(new MDOrderListSpan(10, mdOrderListSpan.getNested(), mdOrderListSpan.getNumber() + 1),
                start + 1,
                position == -1 ? start + 1 + appendString.length() : position,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mRxMDEditText.addTextChangedListener(mTextWatcher);
    }

    /**
     * - aaa --> - aaa\n-
     *
     * @param editable          the editable
     * @param mdUnOrderListSpan the unorder list span
     * @param start             start position
     */
    private void insertUnOrderList(Editable editable, MDUnOrderListSpan mdUnOrderListSpan, int start) {
        mRxMDEditText.removeTextChangedListener(mTextWatcher);
        String appendString = getNestedString(mdUnOrderListSpan.getNested(), false, -1);
        editable.insert(start + 1, appendString);
        int position = findNextNewLineChar(editable, start + appendString.length());
        if (position == -1) {
            position = editable.length();
        }
        editable.setSpan(new MDUnOrderListSpan(10, mdUnOrderListSpan.getColor(), mdUnOrderListSpan.getNested()),
                start + 1,
                position == -1 ? start + 1 + appendString.length() : position,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mRxMDEditText.addTextChangedListener(mTextWatcher);
    }

    /**
     * checking the input character is '\n'
     *
     * @param editable the editable after finishing inputting
     * @param start    the start position
     * @param before   delete text's number
     * @param after    add text's number
     * @return whether input character is '\n'
     */
    private static boolean checkNewLineInput(Editable editable, int start, int before, int after) {
        if (after != 1 || before != 0 || start >= editable.length()) {
            return false;
        }
        if (editable.charAt(start) == '\n') {
            return true;
        }
        return false;
    }

    /**
     * checking the delete character is '\n'
     *
     * @param editable the editable after finishing deleting
     * @param start    the start position
     * @param before   delete text's number
     * @param after    add text's number
     * @return whether delete character is '\n'
     */
    private static boolean checkLineDelete(Editable editable, int start, int before, int after) {
        if (before != 1 || after != 0) {
            return false;
        }
        if (editable.charAt(start - 1) == '\n') {
            return true;
        }
        return false;
    }

    /**
     * get the order list span, if there isn't contains order list span, return null
     *
     * @param editable  the text
     * @param start     the start position
     * @param beginning whether calculate from beginning
     * @return
     */
    @Nullable
    private static MDOrderListSpan getOrderListSpan(Editable editable, int start, boolean beginning) {
        MDOrderListSpan[] mdOrderListSpans;
        if (beginning) {
            mdOrderListSpans = editable.getSpans(start, start + 1, MDOrderListSpan.class);
        } else {
            mdOrderListSpans = editable.getSpans(start - 1, start, MDOrderListSpan.class);
        }
        if (mdOrderListSpans != null && mdOrderListSpans.length > 0) {
            return mdOrderListSpans[0];
        }
        return null;
    }

    /**
     * 1. aaa --> 1. a\n2. aa
     *
     * @param editable        the text
     * @param start           the start position
     * @param mdOrderListSpan the order list span
     */
    private static void updateOrderListSpanBeforeNewLine(Editable editable, int start, MDOrderListSpan mdOrderListSpan) {
        int position = findNextNewLineChar(editable, start);
        int startSpan = editable.getSpanStart(mdOrderListSpan);
        int endSpan = editable.getSpanEnd(mdOrderListSpan);
        if (endSpan <= position) {
            return;
        }
        editable.removeSpan(mdOrderListSpan);
        editable.setSpan(new MDOrderListSpan(10, mdOrderListSpan.getNested(), mdOrderListSpan.getNumber()),
                startSpan, position, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * - aaa --> - a\n- aa
     *
     * @param editable          the text
     * @param start             the start position
     * @param mdUnOrderListSpan the order list span
     */
    private static void updateUnOrderListSpanBeforeNewLine(Editable editable, int start, MDUnOrderListSpan mdUnOrderListSpan) {
        int position = findNextNewLineChar(editable, start);
        int startSpan = editable.getSpanStart(mdUnOrderListSpan);
        int endSpan = editable.getSpanEnd(mdUnOrderListSpan);
        if (endSpan <= position) {
            return;
        }
        editable.removeSpan(mdUnOrderListSpan);
        editable.setSpan(new MDUnOrderListSpan(10, mdUnOrderListSpan.getColor(), mdUnOrderListSpan.getNested()),
                startSpan, position, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * get the unorder list span, if there isn't contains unorder list span, return null
     *
     * @param editable  the text
     * @param start     the start position
     * @param beginning whether calculate from beginning
     * @return
     */
    @Nullable
    private static MDUnOrderListSpan getUnOrderListSpan(Editable editable, int start, boolean beginning) {
        MDUnOrderListSpan[] mdUnOrderListSpans;
        if (beginning) {
            mdUnOrderListSpans = editable.getSpans(start, start + 1, MDUnOrderListSpan.class);
        } else {
            mdUnOrderListSpans = editable.getSpans(start - 1, start, MDUnOrderListSpan.class);
        }
        if (mdUnOrderListSpans != null && mdUnOrderListSpans.length > 0) {
            return mdUnOrderListSpans[0];
        }
        return null;
    }

    /**
     * check the position is end of list(order and unorder) span
     * 1. aaa\n --> 1. aaas\n
     * + aaa\n --> + aaas\n
     *
     * @param editable the text
     * @param start    the position
     * @return TRUE --> end of list span
     */
    private static boolean isEndOfListSpan(Editable editable, int start) {
        if (start - 1 < 0) {
            return false;
        }
        boolean bool = isEndOfOrderListSpan(editable, start);
        if (bool) {
            return bool;
        }
        bool = isEndOfUnOrderListSpan(editable, start);
        return bool;
    }

    /**
     * check the position is end of order list span
     * 1. aaa\n --> 1. aaas\n
     *
     * @param editable the text
     * @param start    the position
     * @return TRUE --> end of list span
     */
    private static boolean isEndOfOrderListSpan(Editable editable, int start) {
        MDOrderListSpan[] mdOrderListSpans = editable.getSpans(start - 1, start, MDOrderListSpan.class);
        if (mdOrderListSpans != null && mdOrderListSpans.length > 0) {
            MDOrderListSpan mdOrderListSpan = mdOrderListSpans[0];
            int endSpan = editable.getSpanEnd(mdOrderListSpan);
            if (endSpan == start) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * check the position is end of unorder list span
     * + aaa\n --> + aaas\n
     *
     * @param editable the text
     * @param start    the position
     * @return TRUE --> end of list span
     */
    private static boolean isEndOfUnOrderListSpan(Editable editable, int start) {
        MDUnOrderListSpan[] mdUnOrderListSpans = editable.getSpans(start - 1, start, MDUnOrderListSpan.class);
        if (mdUnOrderListSpans != null && mdUnOrderListSpans.length > 0) {
            MDUnOrderListSpan mdUnOrderListSpan = mdUnOrderListSpans[0];
            int endSpan = editable.getSpanEnd(mdUnOrderListSpan);
            if (endSpan == start) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * if it's the end of list span, update the span range
     *
     * @param editable the text
     * @param start    the start position
     * @param before   the delete number
     * @param after    the add number
     */
    private static void updateListSpanEnd(Editable editable, int start, int before, int after) {
        updateOrderListSpanEnd(editable, start, before, after);
        updateUnOrderListSpanEnd(editable, start, before, after);
    }

    /**
     * if it's the end of order list span, update the span range
     *
     * @param editable the text
     * @param start    the start position
     * @param before   the delete number
     * @param after    the add number
     */
    private static void updateOrderListSpanEnd(Editable editable, int start, int before, int after) {
        //加载末尾的情况
        MDOrderListSpan[] mdOrderListSpans = editable.getSpans(start - 1, start, MDOrderListSpan.class);
        if (mdOrderListSpans != null && mdOrderListSpans.length > 0) {
            MDOrderListSpan mdOrderListSpan = mdOrderListSpans[0];
            int startSpan = editable.getSpanStart(mdOrderListSpan);
            int endSpan = editable.getSpanEnd(mdOrderListSpan);
            editable.removeSpan(mdOrderListSpan);
            editable.setSpan(new MDOrderListSpan(10, mdOrderListSpan.getNested(), mdOrderListSpan.getNumber()),
                    startSpan, endSpan + (after - before), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    /**
     * if it's the end of unorder list span, update the span range
     *
     * @param editable the text
     * @param start    the start position
     * @param before   the delete number
     * @param after    the add number
     */
    private static void updateUnOrderListSpanEnd(Editable editable, int start, int before, int after) {
        //加载末尾的情况
        MDUnOrderListSpan[] mdUnOrderListSpans = editable.getSpans(start - 1, start, MDUnOrderListSpan.class);
        if (mdUnOrderListSpans != null && mdUnOrderListSpans.length > 0) {
            MDUnOrderListSpan mdUnOrderListSpan = mdUnOrderListSpans[0];
            int startSpan = editable.getSpanStart(mdUnOrderListSpan);
            int endSpan = editable.getSpanEnd(mdUnOrderListSpan);
            editable.removeSpan(mdUnOrderListSpan);
            editable.setSpan(new MDUnOrderListSpan(10, mdUnOrderListSpan.getColor(), mdUnOrderListSpan.getNested()),
                    startSpan, endSpan + (after - before), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    /**
     * check the position is beginning of list span
     *
     * @param editable the text
     * @param start    the start position
     * @param before   the delete number
     * @param after    the add number
     * @return TRUE --> at the beginning
     */
    private static boolean isBeginningOfListSpan(Editable editable, int start, int before, int after) {
        MDOrderListSpan mdOrderListSpan = getOrderListBeginning(editable, start, before, after);
        MDUnOrderListSpan mdUnOrderListSpan = getUnOrderListBeginning(editable, start, before, after);
        if (mdOrderListSpan != null) {
            int spanStart = editable.getSpanStart(mdOrderListSpan);
            if (start <= spanStart || (start >= spanStart && start <= (spanStart + mdOrderListSpan.getNested()))) {
                return true;
            }
        } else if (mdUnOrderListSpan != null) {
            int spanStart = editable.getSpanStart(mdUnOrderListSpan);
            if (start <= spanStart || (start >= spanStart && start <= (spanStart + mdUnOrderListSpan.getNested()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * get the order list span, if there doesn't contain, return null
     *
     * @param editable the text
     * @param start    the start position
     * @param before   the delete number
     * @param after    the add number
     * @return the order list span or null
     */
    @Nullable
    private static MDOrderListSpan getOrderListBeginning(Editable editable, int start, int before, int after) {
        if (start + 1 > editable.length()) {
            return null;
        }
        int addNumber = Math.abs(after - before);//增加了多少
        MDOrderListSpan[] mdOrderListSpans = editable.getSpans(start + addNumber, start + addNumber + 1, MDOrderListSpan.class);
        if (mdOrderListSpans != null && mdOrderListSpans.length > 0) {
            return mdOrderListSpans[0];
        }
        return null;
    }

    /**
     * get the unorder list span, if there doesn't contain, return null
     *
     * @param editable the text
     * @param start    the start position
     * @param before   the delete number
     * @param after    the add number
     * @return the unorder list span or null
     */
    @Nullable
    private static MDUnOrderListSpan getUnOrderListBeginning(Editable editable, int start, int before, int after) {
        if (start + 1 > editable.length()) {
            return null;
        }
        MDUnOrderListSpan[] mdUnOrderListSpans = editable.getSpans(start, start + after + 1, MDUnOrderListSpan.class);
        if (mdUnOrderListSpans != null && mdUnOrderListSpans.length > 0) {
            return mdUnOrderListSpans[0];
        }
        return null;
    }

    /**
     * "1. aaa" --> " 1. aaa"
     *
     * @param editable the text
     * @param start    the start position
     * @param before   the delete number
     * @param after    the add number
     */
    private static void updateListSpanBeginning(Editable editable, int start, int before, int after) {
        MDOrderListSpan mdOrderListSpan = getOrderListBeginning(editable, start, before, after);
        MDUnOrderListSpan mdUnOrderListSpan = getUnOrderListBeginning(editable, start, before, after);
        if (mdOrderListSpan != null) {
            int spanEnd = editable.getSpanEnd(mdOrderListSpan);
            int position = findBeforeNewLineChar(editable, start) + 1;
            if (!isOrderList(editable, position, false)) {
                return;
            }
            if (position == -1) {
                return;
            }
            int nested = calculateNested(editable, position, 0);
            if (nested == -1) {
                return;
            }
            editable.removeSpan(mdOrderListSpan);
            editable.setSpan(new MDOrderListSpan(10, nested, mdOrderListSpan.getNumber()),
                    position, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (mdUnOrderListSpan != null) {
            int spanEnd = editable.getSpanEnd(mdUnOrderListSpan);
            int position = findBeforeNewLineChar(editable, start) + 1;
            if (position == -1) {
                return;
            }
            if (!isUnOrderList(editable, position, false)) {
                return;
            }
            int nested = calculateNested(editable, position, 0);
            if (nested == -1) {
                return;
            }
            editable.removeSpan(mdUnOrderListSpan);
            editable.setSpan(new MDUnOrderListSpan(10, mdUnOrderListSpan.getColor(), nested),
                    position, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    /**
     * find '\n' from "start" position
     *
     * @param s     text
     * @param start start position
     * @return the '\n' position
     */
    private static int findNextNewLineChar(CharSequence s, int start) {
        for (int i = start; i < s.length(); i++) {
            if (s.charAt(i) == '\n') {
                return i;
            }
        }
        return -1;
    }

    /**
     * find '\n' before "start" position
     *
     * @param s     text
     * @param start start position
     * @return the '\n' position
     */
    private static int findBeforeNewLineChar(CharSequence s, int start) {
        for (int i = start; i > 0; i--) {
            if (s.charAt(i) == '\n') {
                return i;
            }
        }
        return -2;
    }

    /**
     * calculate nested number
     *
     * @param s             the text
     * @param next          the next position
     * @param currentNested current nested number
     * @return the nested number
     */
    private static int calculateNested(CharSequence s, int next, int currentNested) {
        if (next + 1 > s.length()) {
            return -1;
        }
        if (s.charAt(next) == ' ') {
            return calculateNested(s, next + 1, currentNested + 1);
        } else {
            return currentNested;
        }
    }

    /**
     * check the text is order list format
     *
     * @param s        the text
     * @param next     the next position
     * @param isNumber is already judge number, so next is "." or number
     * @return TRUE --> is order list format
     */
    private static boolean isOrderList(CharSequence s, int next, boolean isNumber) {
        if (next + 1 > s.length()) {
            return false;
        }
        char c = s.charAt(next);
        if (isNumber) {
            if (Character.isDigit(c)) {
                return isOrderList(s, next + 1, true);
            } else if (c == '.') {
                return true;
            } else {
                return false;
            }
        } else if (c == ' ') {
            return isOrderList(s, next + 1, false);
        } else if (Character.isDigit(c)) {
            return isOrderList(s, next + 1, true);
        } else {
            return false;
        }
    }

    /**
     * check the text is unorder list format
     *
     * @param s      the text
     * @param next   the next position
     * @param hasKey is already judge the key(*\+\-)
     * @return TRUE --> is unorder list format
     */
    private static boolean isUnOrderList(CharSequence s, int next, boolean hasKey) {
        if (next + 1 > s.length()) {
            return false;
        }
        if (hasKey) {
            char c = s.charAt(next);
            if (c == ' ') {
                return true;
            } else {
                return false;
            }
        } else {
            char c = s.charAt(next);
            if (c == '+' || c == '-' || c == '*') {
                return isUnOrderList(s, next + 1, true);
            } else if (c == ' ') {
                return isUnOrderList(s, next + 1, false);
            } else {
                return false;
            }
        }
    }
}
