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
package com.yydcdut.rxmarkdown.grammar;

import android.support.annotation.NonNull;
import android.text.Editable;

import com.yydcdut.rxmarkdown.edit.EditToken;

import java.util.List;

/**
 * Grammar for markdown.
 * It's an interface, the implements of it are the real parser.
 * <p>
 * Created by yuyidong on 16/5/3.
 */
public interface IGrammar {

    /**
     * Whether this grammar can match(parse) this content
     *
     * @param charSequence the content
     * @return TRUE:can match(parse)
     */
    boolean isMatch(@NonNull CharSequence charSequence);

    /**
     * Parse content, and return the content after parsing
     *
     * @param charSequence the original and matched content
     * @return the content after parsing
     */
    @NonNull
    CharSequence format(@NonNull CharSequence charSequence);

    /**
     * Parse content, and return the {@link EditToken}s after parsing
     *
     * @param editable the original content
     * @return the result
     */
    @NonNull
    List<EditToken> format(@NonNull Editable editable);
}
