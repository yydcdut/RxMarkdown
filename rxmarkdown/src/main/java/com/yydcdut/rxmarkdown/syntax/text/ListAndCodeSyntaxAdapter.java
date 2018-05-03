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
package com.yydcdut.rxmarkdown.syntax.text;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.SpannableStringBuilder;

import com.yydcdut.rxmarkdown.live.EditToken;
import com.yydcdut.rxmarkdown.span.MDCodeBlockSpan;
import com.yydcdut.rxmarkdown.syntax.Syntax;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter some methods.
 * <p>
 * Created by yuyidong on 16/6/29.
 */
abstract class ListAndCodeSyntaxAdapter implements Syntax {

    @NonNull
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        return new ArrayList<>();
    }

    /**
     * check whether exists code block span
     *
     * @param ssb   the text
     * @param start the start position
     * @param end   the end position
     * @return if exists, return true
     */
    protected static boolean existCodeBlockSpan(@NonNull SpannableStringBuilder ssb, int start, int end) {
        MDCodeBlockSpan[] mdCodeBlockSpans = ssb.getSpans(start, end, MDCodeBlockSpan.class);
        return mdCodeBlockSpans != null && mdCodeBlockSpans.length > 0;
    }
}
