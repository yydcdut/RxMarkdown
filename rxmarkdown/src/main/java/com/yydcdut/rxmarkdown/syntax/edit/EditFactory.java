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
package com.yydcdut.rxmarkdown.syntax.edit;

import android.support.annotation.NonNull;
import android.text.Editable;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.live.EditToken;
import com.yydcdut.rxmarkdown.syntax.Syntax;
import com.yydcdut.rxmarkdown.syntax.SyntaxFactory;

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
    private RxMDConfiguration mRxMDConfiguration;

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
    public Syntax getHorizontalRulesSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new HorizontalRulesSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getBlockQuotesSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new BlockQuotesSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getTodoSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new NormalSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getTodoDoneSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new NormalSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getOrderListSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new OrderListSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getUnOrderListSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new UnOrderListSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getCenterAlignSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new CenterAlignSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getHeaderSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new HeaderSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getBoldSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new BoldSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getItalicSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new ItalicSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getCodeSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new CodeSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getStrikeThroughSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new StrikeThroughSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getFootnoteSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new NormalSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getImageSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new NormalSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getHyperLinkSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new NormalSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getCodeBlockSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new CodeBlockSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getBackslashSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new NormalSyntax(rxMDConfiguration);
    }

    private void init(RxMDConfiguration rxMDConfiguration) {
        mRxMDConfiguration = rxMDConfiguration;
        mSyntaxList = new ArrayList<>();
        mSyntaxList.add(getBoldSyntax(mRxMDConfiguration));
        mSyntaxList.add(getItalicSyntax(mRxMDConfiguration));
        mSyntaxList.add(getStrikeThroughSyntax(mRxMDConfiguration));
        mSyntaxList.add(getCodeSyntax(mRxMDConfiguration));
        mSyntaxList.add(getCenterAlignSyntax(mRxMDConfiguration));
        mSyntaxList.add(getHeaderSyntax(mRxMDConfiguration));
        mSyntaxList.add(getBlockQuotesSyntax(mRxMDConfiguration));
        mSyntaxList.add(getCodeBlockSyntax(mRxMDConfiguration));
        mSyntaxList.add(getHorizontalRulesSyntax(mRxMDConfiguration));
        mSyntaxList.add(getOrderListSyntax(mRxMDConfiguration));
        mSyntaxList.add(getUnOrderListSyntax(mRxMDConfiguration));
    }

    @NonNull
    @Override
    public CharSequence parse(@NonNull CharSequence charSequence, @NonNull RxMDConfiguration rxMDConfiguration) {
        if (!(charSequence instanceof Editable)) {
            return charSequence;
        }
        if (rxMDConfiguration == null) {
            return charSequence;
        }
        if (mSyntaxList == null || mRxMDConfiguration == null || mRxMDConfiguration != rxMDConfiguration) {
            init(rxMDConfiguration);
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
