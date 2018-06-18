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
import com.yydcdut.markdown.syntax.Syntax;
import com.yydcdut.markdown.syntax.SyntaxFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This factory's purpose is parsing content <b>quickly</b>, as the same time, it doesn't support all syntax.
 * So, hope that it will be used in EditText.
 * <p>
 * Created by yuyidong on 16/7/2.
 */
public class EditFactory implements SyntaxFactory {

    private List<Syntax> mSyntaxList;
    private MarkdownConfiguration mMarkdownConfiguration;

    private EditFactory() {
    }

    /**
     * get EditFactory object
     *
     * @return {@link SyntaxFactory}
     */
    public static SyntaxFactory create() {
        return new EditFactory();
    }

    @Override
    public Syntax getHorizontalRulesSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new HorizontalRulesSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getBlockQuotesSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new BlockQuotesSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getTodoSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new NormalSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getTodoDoneSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new NormalSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getOrderListSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new OrderListSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getUnOrderListSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new UnOrderListSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getListSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new NormalSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getCenterAlignSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new CenterAlignSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getHeaderSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new HeaderSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getBoldSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new BoldSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getItalicSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new ItalicSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getCodeSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new CodeSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getStrikeThroughSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new StrikeThroughSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getFootnoteSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new NormalSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getImageSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new NormalSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getHyperLinkSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new NormalSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getCodeBlockSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new CodeBlockSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getBackslashSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new NormalSyntax(markdownConfiguration);
    }

    private void init(MarkdownConfiguration markdownConfiguration) {
        mMarkdownConfiguration = markdownConfiguration;
        mSyntaxList = new ArrayList<>();
        mSyntaxList.add(getBoldSyntax(mMarkdownConfiguration));
        mSyntaxList.add(getItalicSyntax(mMarkdownConfiguration));
        mSyntaxList.add(getStrikeThroughSyntax(mMarkdownConfiguration));
        mSyntaxList.add(getCodeSyntax(mMarkdownConfiguration));
        mSyntaxList.add(getCenterAlignSyntax(mMarkdownConfiguration));
        mSyntaxList.add(getHeaderSyntax(mMarkdownConfiguration));
        mSyntaxList.add(getBlockQuotesSyntax(mMarkdownConfiguration));
        mSyntaxList.add(getCodeBlockSyntax(mMarkdownConfiguration));
        mSyntaxList.add(getHorizontalRulesSyntax(mMarkdownConfiguration));
        mSyntaxList.add(getOrderListSyntax(mMarkdownConfiguration));
        mSyntaxList.add(getUnOrderListSyntax(mMarkdownConfiguration));
    }

    @NonNull
    @Override
    public CharSequence parse(@NonNull CharSequence charSequence, @NonNull MarkdownConfiguration markdownConfiguration) {
        if (!(charSequence instanceof Editable)) {
            return charSequence;
        }
        if (markdownConfiguration == null) {
            return charSequence;
        }
        if (mSyntaxList == null || mMarkdownConfiguration == null || mMarkdownConfiguration != markdownConfiguration) {
            init(markdownConfiguration);
        }
        Editable editable = (Editable) charSequence;
        List<EditToken> list = new ArrayList<>();
        for (Syntax syntax : mSyntaxList) {
            list.addAll(syntax.format(editable));
        }
        Editable newEditable = Editable.Factory.getInstance().newEditable(editable.toString());
        for (EditToken editToken : list) {
            newEditable.setSpan(editToken.getSpan(), editToken.getStart(), editToken.getEnd(), editToken.getFlag());
        }
        return newEditable;
    }
}
