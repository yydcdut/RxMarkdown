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
package com.yydcdut.rxmarkdown.live;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.yydcdut.rxmarkdown.RxMDEditText;
import com.yydcdut.rxmarkdown.span.MDHorizontalRulesSpan;
import com.yydcdut.rxmarkdown.syntax.SyntaxKey;
import com.yydcdut.rxmarkdown.syntax.edit.EditFactory;
import com.yydcdut.rxmarkdown.utils.Utils;

import java.util.List;

/**
 * RxMDEditText, horizontal rules controller.
 * When editText's selection is on horizontal rules, The transparency of words is normal.
 * When editText's selection is not on horizontal rules, The transparency of words is 20%.
 * <p>
 * Created by yuyidong on 16/7/8.
 */
class HorizontalRulesLive extends EditLive {

    private RxMDEditText mRxMDEditText;

    /**
     * Constructor
     *
     * @param rxMDEditText RxMDEditText
     */
    public HorizontalRulesLive(@NonNull RxMDEditText rxMDEditText) {
        mRxMDEditText = rxMDEditText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int before, int after) {
        super.beforeTextChanged(s, start, before, after);
        if (before == 0 || mRxMDConfiguration == null) {
            return;
        }
        String deleteString = s.subSequence(start, start + before).toString();
        String beforeString = null;
        String afterString = null;
        if (start > 0) {
            beforeString = s.subSequence(start - 1, start).toString();
        }
        if (start + before + 1 <= s.length()) {
            afterString = s.subSequence(start + before, start + before + 1).toString();
        }
        //1---(-1--)(--1-)(---1) --> ---
        if (deleteString.contains(SyntaxKey.KEY_BOLD_UNDERLINE_SINGLE) || deleteString.contains(SyntaxKey.KEY_BOLD_ASTERISK_SINGLE)
                || (SyntaxKey.KEY_BOLD_UNDERLINE_SINGLE.equals(beforeString) || SyntaxKey.KEY_BOLD_UNDERLINE_SINGLE.equals(afterString))
                || (SyntaxKey.KEY_BOLD_ASTERISK_SINGLE.equals(beforeString) || SyntaxKey.KEY_BOLD_ASTERISK_SINGLE.equals(afterString))) {
            shouldFormat = true;
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int after) {
        if (mRxMDConfiguration == null || !(s instanceof Editable)) {
            return;
        }
        if (shouldFormat) {
            format((Editable) s, start);
            return;
        }
        if (after == 0) {
            return;
        }
        String addString;
        String beforeString = null;
        String afterString = null;
        addString = s.subSequence(start, start + after).toString();
        if (start + 1 <= s.length()) {
            afterString = s.subSequence(start, start + 1).toString();
        }
        if (start > 0) {
            beforeString = s.subSequence(start - 1, start).toString();
        }
        //--- --> 1---(-1--)(--1-)(---1)
        if ((addString.contains(SyntaxKey.KEY_BOLD_UNDERLINE_SINGLE) || addString.contains(SyntaxKey.KEY_BOLD_ASTERISK_SINGLE))
                || (SyntaxKey.KEY_BOLD_UNDERLINE_SINGLE.equals(beforeString) || SyntaxKey.KEY_BOLD_UNDERLINE_SINGLE.equals(afterString))
                || (SyntaxKey.KEY_BOLD_ASTERISK_SINGLE.equals(beforeString) || SyntaxKey.KEY_BOLD_ASTERISK_SINGLE.equals(afterString))) {
            format((Editable) s, start);
        }
    }

    private void format(Editable editable, int start) {
        Utils.removeSpans(editable, start, MDHorizontalRulesSpan.class);
        if (mSyntax == null) {
            mSyntax = EditFactory.create().getHorizontalRulesSyntax(mRxMDConfiguration);
        }
        List<EditToken> editTokenList = Utils.getMatchedEditTokenList(editable, mSyntax.format(editable), start);
        Utils.setSpans(editable, editTokenList);
    }

    @Override
    public void onSelectionChanged(int selStart, int selEnd) {
        setAllHorizontalRulesTextColor();
        removeCurrentHorizontalRulesTextColor(selStart, selEnd);
    }

    private void setAllHorizontalRulesTextColor() {
        Editable editable = mRxMDEditText.getText();
        MDHorizontalRulesSpan[] spans = editable.getSpans(0, editable.length(), MDHorizontalRulesSpan.class);
        if (spans.length > 0) {
            for (MDHorizontalRulesSpan span : spans) {
                int start = editable.getSpanStart(span);
                int end = editable.getSpanEnd(span);
                if (!existForegroundColorSpan(start, end)) {
                    int textColor = mRxMDEditText.getCurrentTextColor();
                    editable.setSpan(new ForegroundColorSpan(
                                    Color.argb(51,
                                            Color.red(textColor),
                                            Color.green(textColor),
                                            Color.blue(textColor))),
                            start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
    }

    private void removeCurrentHorizontalRulesTextColor(int selStart, int selEnd) {
        Editable editable = mRxMDEditText.getText();
        MDHorizontalRulesSpan[] spans = editable.getSpans(selStart, selEnd, MDHorizontalRulesSpan.class);
        if (spans.length > 0) {
            for (MDHorizontalRulesSpan span : spans) {
                int start = editable.getSpanStart(span);
                int end = editable.getSpanEnd(span);
                ForegroundColorSpan[] foregroundColorSpans = editable.getSpans(start, end, ForegroundColorSpan.class);
                for (ForegroundColorSpan foregroundColorSpan : foregroundColorSpans) {
                    editable.removeSpan(foregroundColorSpan);
                }
            }
        }
    }

    private boolean existForegroundColorSpan(int start, int end) {
        ForegroundColorSpan[] foregroundColorSpans = mRxMDEditText.getText().getSpans(start, end, ForegroundColorSpan.class);
        return foregroundColorSpans != null ? foregroundColorSpans.length == 0 ? false : true : false;
    }
}
