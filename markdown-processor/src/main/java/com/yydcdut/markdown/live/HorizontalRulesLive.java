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
package com.yydcdut.markdown.live;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.yydcdut.markdown.MarkdownEditText;
import com.yydcdut.markdown.span.MDHorizontalRulesSpan;
import com.yydcdut.markdown.syntax.Syntax;
import com.yydcdut.markdown.syntax.SyntaxKey;
import com.yydcdut.markdown.syntax.edit.EditFactory;
import com.yydcdut.markdown.utils.SyntaxUtils;
import com.yydcdut.markdown.utils.TextHelper;

import java.util.List;

/**
 * RxMDEditText, horizontal rules controller.
 * When editText's selection is on horizontal rules, The transparency of words is normal.
 * When editText's selection is not on horizontal rules, The transparency of words is 20%.
 * <p>
 * Created by yuyidong on 16/7/8.
 */
class HorizontalRulesLive extends EditLive {

    private MarkdownEditText mMarkdownEditText;

    /**
     * Constructor
     *
     * @param MarkdownEditText RxMDEditText
     */
    public HorizontalRulesLive(@NonNull MarkdownEditText MarkdownEditText) {
        mMarkdownEditText = MarkdownEditText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int before, int after) {
        super.beforeTextChanged(s, start, before, after);
        if (before == 0 || mMarkdownConfiguration == null) {
            return;
        }
        String deleteString = s.subSequence(TextHelper.safePosition(start, s), TextHelper.safePosition(start + before, s)).toString();
        String beforeString = null;
        String afterString = null;
        if (start > 0) {
            beforeString = s.subSequence(TextHelper.safePosition(start - 1, s), TextHelper.safePosition(start, s)).toString();
        }
        if (start + before + 1 <= s.length()) {
            afterString = s.subSequence(TextHelper.safePosition(start + before, s), TextHelper.safePosition(start + before + 1, s)).toString();
        }
        //1---(-1--)(--1-)(---1) --> ---
        if (TextHelper.isNeedFormat(SyntaxKey.KEY_BOLD_UNDERLINE_SINGLE, deleteString, beforeString, afterString)
                || TextHelper.isNeedFormat(SyntaxKey.KEY_BOLD_ASTERISK_SINGLE, deleteString, beforeString, afterString)) {
            shouldFormat = true;
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int after) {
        if (mMarkdownConfiguration == null || !(s instanceof Editable)) {
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
        addString = s.subSequence(TextHelper.safePosition(start, s), TextHelper.safePosition(start + after, s)).toString();
        if (start + 1 <= s.length()) {
            afterString = s.subSequence(TextHelper.safePosition(start, s), TextHelper.safePosition(start + 1, s)).toString();
        }
        if (start > 0) {
            beforeString = s.subSequence(TextHelper.safePosition(start - 1, s), TextHelper.safePosition(start, s)).toString();
        }
        //--- --> 1---(-1--)(--1-)(---1)
        if (TextHelper.isNeedFormat(SyntaxKey.KEY_BOLD_UNDERLINE_SINGLE, addString, beforeString, afterString)
                || TextHelper.isNeedFormat(SyntaxKey.KEY_BOLD_ASTERISK_SINGLE, addString, beforeString, afterString)) {
            format((Editable) s, start);
        }
    }

    private void format(Editable editable, int start) {
        SyntaxUtils.removeSpans(editable, start, MDHorizontalRulesSpan.class);
        Syntax syntax = EditFactory.create().getHorizontalRulesSyntax(mMarkdownConfiguration);
        List<EditToken> editTokenList = SyntaxUtils.getMatchedEditTokenList(editable, syntax.format(editable), start);
        SyntaxUtils.setSpans(editable, editTokenList);
    }

    @Override
    public void onSelectionChanged(int selStart, int selEnd) {
        setAllHorizontalRulesTextColor();
        removeCurrentHorizontalRulesTextColor(selStart, selEnd);
    }

    private void setAllHorizontalRulesTextColor() {
        Editable editable = mMarkdownEditText.getText();
        MDHorizontalRulesSpan[] spans = editable.getSpans(0, editable.length(), MDHorizontalRulesSpan.class);
        if (spans.length > 0) {
            for (MDHorizontalRulesSpan span : spans) {
                int start = editable.getSpanStart(span);
                int end = editable.getSpanEnd(span);
                if (!existForegroundColorSpan(start, end)) {
                    int textColor = mMarkdownEditText.getCurrentTextColor();
                    editable.setSpan(new ForegroundColorSpan(
                                    Color.argb(51, Color.red(textColor), Color.green(textColor), Color.blue(textColor))),
                            start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
    }

    private void removeCurrentHorizontalRulesTextColor(int selStart, int selEnd) {
        Editable editable = mMarkdownEditText.getText();
        MDHorizontalRulesSpan[] spans = editable.getSpans(selStart, selEnd, MDHorizontalRulesSpan.class);
        if (spans == null || spans.length == 0) {
            return;
        }
        for (MDHorizontalRulesSpan span : spans) {
            int start = editable.getSpanStart(span);
            int end = editable.getSpanEnd(span);
            ForegroundColorSpan[] foregroundColorSpans = editable.getSpans(start, end, ForegroundColorSpan.class);
            for (ForegroundColorSpan foregroundColorSpan : foregroundColorSpans) {
                editable.removeSpan(foregroundColorSpan);
            }
        }
    }

    private boolean existForegroundColorSpan(int start, int end) {
        ForegroundColorSpan[] foregroundColorSpans = mMarkdownEditText.getText().getSpans(start, end, ForegroundColorSpan.class);
        return foregroundColorSpans != null ? foregroundColorSpans.length == 0 ? false : true : false;
    }
}
