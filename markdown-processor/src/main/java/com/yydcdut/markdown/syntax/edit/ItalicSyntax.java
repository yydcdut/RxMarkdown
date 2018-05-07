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

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.style.StyleSpan;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.live.EditToken;
import com.yydcdut.markdown.utils.SyntaxUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of syntax for italic.
 * syntax:
 * "*content*"
 * "_content_"
 * <p>
 * Created by yuyidong on 16/6/29.
 */
class ItalicSyntax extends EditSyntaxAdapter implements SyntaxUtils.OnWhatSpanCallback {
    private static final String PATTERN_ASTERISK = "(\\*)(.*?)(\\*)";
    private static final String PATTERN_UNDERLINE = "(_)(.*?)(_)";

    public ItalicSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        super(markdownConfiguration);
    }

    @Nullable
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        List<EditToken> editTokenList = new ArrayList<>();
        editTokenList.addAll(SyntaxUtils.parse(editable, PATTERN_ASTERISK, this));
        editTokenList.addAll(SyntaxUtils.parse(editable, PATTERN_UNDERLINE, this));
        return editTokenList;
    }

    @Override
    public Object whatSpan() {
        return new StyleSpan(Typeface.ITALIC);
    }
}
