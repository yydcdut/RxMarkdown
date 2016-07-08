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

    private ListState mListState;

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
     * invoke when onTextChanged
     *
     * @param s      CharSequence
     * @param start  start position
     * @param before before changed number
     * @param after  after changed number
     */
    public void onTextChanged(CharSequence s, int start, int before, int after) {
        if (mRxMDConfiguration == null) {
            return;
        }
        mListState = null;
        if (checkNewLineInput(s, start, before, after)) {
            if (isOrderListSpanBeforeNewLine(s, start)) {
                mListState = new ListState(true,
                        getOrderListSpanNumber(s, start),
                        getOrderListSpanNested(s, start),
                        start);
            } else if (isUnOrderListSpanBeforeNewLine(s, start)) {
                mListState = new ListState(false,
                        -1,
                        getUnOrderListSpanNested(s, start),
                        start);
            }
        } else if (isAfterListSpan(s, start)) {
            updateListSpanRange(s, start, before, after);
        }
    }

    /**
     * invoke when afterTextChanged
     */
    public void afterTextChanged(Editable editable) {
        if (mListState == null || mRxMDConfiguration == null) {
            return;
        }
        mRxMDEditText.removeTextChangedListener(mTextWatcher);
        String appendString = getNestedString(mListState);
        editable.insert(mListState.start + 1, appendString);
        if (mListState.isOrderList) {
            editable.setSpan(new MDOrderListSpan(10, mListState.nested, mListState.number + 1),
                    mListState.start + 1,
                    mListState.start + 1 + appendString.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            editable.setSpan(new MDUnOrderListSpan(10, mRxMDConfiguration.getUnOrderListColor(), mListState.nested),
                    mListState.start + 1,
                    mListState.start + 1 + appendString.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        mRxMDEditText.addTextChangedListener(mTextWatcher);
    }

    private String getNestedString(ListState listState) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < listState.nested; i++) {
            sb.append("  ");
        }
        if (listState.isOrderList) {
            sb.append(String.valueOf(listState.number + 1));
            sb.append(". ");
        } else {
            sb.append("+ ");
        }
        return sb.toString();
    }

    private boolean checkNewLineInput(CharSequence s, int start, int before, int after) {
        if (after != 1 || before != 0) {
            return false;
        }
        CharSequence charSequence = s.subSequence(start, start + 1);
        if (charSequence.charAt(0) == '\n') {
            return true;
        }
        return false;
    }

    private boolean isOrderListSpanBeforeNewLine(CharSequence s, int start) {
        if (!(s instanceof Editable)) {
            return false;
        }
        Editable editable = (Editable) s;
        MDOrderListSpan[] mdOrderListSpans = editable.getSpans(start - 1, start, MDOrderListSpan.class);
        if (mdOrderListSpans != null && mdOrderListSpans.length > 0) {
            return true;
        }
        return false;
    }

    private int getOrderListSpanNumber(CharSequence s, int start) {
        Editable editable = (Editable) s;
        MDOrderListSpan[] mdOrderListSpans = editable.getSpans(start - 1, start, MDOrderListSpan.class);
        if (mdOrderListSpans != null && mdOrderListSpans.length > 0) {
            return mdOrderListSpans[0].getNumber();
        }
        throw new RuntimeException("Excuse me??");
    }

    private int getOrderListSpanNested(CharSequence s, int start) {
        Editable editable = (Editable) s;
        MDOrderListSpan[] mdOrderListSpans = editable.getSpans(start - 1, start, MDOrderListSpan.class);
        if (mdOrderListSpans != null && mdOrderListSpans.length > 0) {
            return mdOrderListSpans[0].getNested();
        }
        throw new RuntimeException("Excuse me??");
    }

    private boolean isUnOrderListSpanBeforeNewLine(CharSequence s, int start) {
        if (!(s instanceof Editable)) {
            return false;
        }
        Editable editable = (Editable) s;
        MDUnOrderListSpan[] mdUnOrderListSpans = editable.getSpans(start - 1, start, MDUnOrderListSpan.class);
        if (mdUnOrderListSpans != null && mdUnOrderListSpans.length > 0) {
            return true;
        }
        return false;
    }

    private int getUnOrderListSpanNested(CharSequence s, int start) {
        Editable editable = (Editable) s;
        MDUnOrderListSpan[] mdUnOrderListSpans = editable.getSpans(start - 1, start, MDUnOrderListSpan.class);
        if (mdUnOrderListSpans != null && mdUnOrderListSpans.length > 0) {
            return mdUnOrderListSpans[0].getNested();
        }
        throw new RuntimeException("Excuse me??");
    }

    private boolean isAfterListSpan(CharSequence s, int start) {
        if (start - 1 < 0 || !(s instanceof Editable)) {
            return false;
        }
        Editable editable = (Editable) s;
        MDOrderListSpan[] mdOrderListSpans = editable.getSpans(start - 1, start, MDOrderListSpan.class);
        if (mdOrderListSpans != null & mdOrderListSpans.length > 0) {
            MDOrderListSpan mdOrderListSpan = mdOrderListSpans[0];
            int endSpan = editable.getSpanEnd(mdOrderListSpan);
            if (endSpan == start) {
                return true;
            }
            return false;
        }
        MDUnOrderListSpan[] mdUnOrderListSpans = editable.getSpans(start - 1, start, MDUnOrderListSpan.class);
        if (mdUnOrderListSpans != null & mdUnOrderListSpans.length > 0) {
            MDUnOrderListSpan mdUnOrderListSpan = mdUnOrderListSpans[0];
            int endSpan = editable.getSpanEnd(mdUnOrderListSpan);
            if (endSpan == start) {
                return true;
            }
            return false;
        }
        return false;
    }

    private void updateListSpanRange(CharSequence s, int start, int before, int after) {
        if (start - 1 < 0 || !(s instanceof Editable) ||
                (before - after) > 0) {//减去的多
            return;
        }
        Editable editable = (Editable) s;
        MDOrderListSpan[] mdOrderListSpans = editable.getSpans(start - 1, start, MDOrderListSpan.class);
        if (mdOrderListSpans != null & mdOrderListSpans.length > 0) {
            MDOrderListSpan mdOrderListSpan = mdOrderListSpans[0];
            int startSpan = editable.getSpanStart(mdOrderListSpan);
            int endSpan = editable.getSpanEnd(mdOrderListSpan);
            editable.removeSpan(mdOrderListSpan);
            editable.setSpan(new MDOrderListSpan(10, mdOrderListSpan.getNested(), mdOrderListSpan.getNumber()),
                    startSpan, endSpan + (after - before), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        MDUnOrderListSpan[] mdUnOrderListSpans = editable.getSpans(start - 1, start, MDUnOrderListSpan.class);
        if (mdUnOrderListSpans != null & mdUnOrderListSpans.length > 0) {
            MDUnOrderListSpan mdUnOrderListSpan = mdUnOrderListSpans[0];
            int startSpan = editable.getSpanStart(mdUnOrderListSpan);
            int endSpan = editable.getSpanEnd(mdUnOrderListSpan);
            editable.removeSpan(mdUnOrderListSpan);
            editable.setSpan(new MDUnOrderListSpan(10, mdUnOrderListSpan.getColor(), mdUnOrderListSpan.getNested()),
                    startSpan, endSpan + (after - before), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    /**
     * the bean of order and unorder list
     */
    private class ListState {
        final boolean isOrderList;
        final int number;
        final int nested;
        final int start;

        public ListState(boolean isOrderList, int number, int nested, int start) {
            this.isOrderList = isOrderList;
            this.number = number;
            this.nested = nested;
            this.start = start;
        }
    }
}
