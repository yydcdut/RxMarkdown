/*
 * Copyright (C) 2018 yydcdut (yuyidong2015@gmail.com)
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
package com.yydcdut.markdown;

import android.content.Context;
import android.support.annotation.NonNull;

import com.yydcdut.markdown.syntax.SyntaxFactory;

/**
 * Created by yuyidong on 2018/5/6.
 */
public class MarkdownProcessor {
    private Context context;
    private MarkdownConfiguration markdownConfiguration;
    private SyntaxFactory syntaxFactory;

    public MarkdownProcessor(Context context) {
        checkNULL(context);
        this.context = context;
    }

    public void config(MarkdownConfiguration markdownConfiguration) {
        this.markdownConfiguration = markdownConfiguration;
    }

    public void factory(SyntaxFactory syntaxFactory) {
        checkNULL(syntaxFactory);
        this.syntaxFactory = syntaxFactory;
    }

    public CharSequence parse(CharSequence charSequence) {
        MarkdownConfiguration config = getMarkdownConfiguration();
        return syntaxFactory.parse(charSequence, config);
    }

    public void live(MarkdownEditText editText) {
        editText.setFactoryAndConfig(syntaxFactory, getMarkdownConfiguration());
    }

    @NonNull
    private MarkdownConfiguration getMarkdownConfiguration() {
        if (markdownConfiguration == null) {
            markdownConfiguration = new MarkdownConfiguration.Builder(context).build();
        }
        return markdownConfiguration;
    }

    private void checkNULL(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("" + o.getClass().getName() + " is NULL");
        }
    }
}
