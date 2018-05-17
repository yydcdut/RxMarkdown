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
import com.yydcdut.markdown.span.MDHorizontalRulesSpan;
import com.yydcdut.markdown.utils.SyntaxUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of syntax for horizontal rules.
 * syntax:
 * "***"
 * <p>
 * "---"
 * <p>
 * "***********"
 * <p>
 * "----------------"
 * <p>
 * Created by yuyidong on 16/7/7.
 */
class HorizontalRulesSyntax extends EditSyntaxAdapter implements SyntaxUtils.OnWhatSpanCallback {
    private static final String PATTERN_ASTERISK = "^\\*{3,}$";
    private static final String PATTERN_HYPHEN = "^\\-{3,}$";

    private int mColor;
    private int mHeight;

    public HorizontalRulesSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        super(markdownConfiguration);
        mColor = markdownConfiguration.getHorizontalRulesColor();
        mHeight = markdownConfiguration.getHorizontalRulesHeight();
    }

    @NonNull
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        List<EditToken> editTokenList = new ArrayList<>();
        editTokenList.addAll(SyntaxUtils.parse(editable, PATTERN_ASTERISK, this));
        editTokenList.addAll(SyntaxUtils.parse(editable, PATTERN_HYPHEN, this));
        return editTokenList;
    }

    @Override
    public Object whatSpan() {
        return new MDHorizontalRulesSpan(mColor, mHeight);
    }
}
