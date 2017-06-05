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
import com.yydcdut.rxmarkdown.factory.AbsGrammarFactory;
import com.yydcdut.rxmarkdown.live.EditToken;
import com.yydcdut.rxmarkdown.syntax.IGrammar;

import java.util.ArrayList;
import java.util.List;

/**
 * This factory's purpose is parsing content <b>quickly</b>, as the same time, it doesn't support all grammars.
 * So, hope that it will be used in EditText.
 * <p>
 * Created by yuyidong on 16/7/2.
 */
public class EditFactory extends AbsGrammarFactory {

    private List<IGrammar> mGrammarList;
    private RxMDConfiguration mRxMDConfiguration;

    private EditFactory() {
    }

    /**
     * get EditFactory object
     *
     * @return {@link AbsGrammarFactory}
     */
    public static AbsGrammarFactory create() {
        return new EditFactory();
    }

    @Override
    public IGrammar getHorizontalRulesGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new HorizontalRulesGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getBlockQuotesGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new BlockQuotesGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getTodoGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new NormalGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getTodoDoneGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new NormalGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getOrderListGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new OrderListGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getUnOrderListGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new UnOrderListGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getCenterAlignGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new CenterAlignGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getHeaderGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new HeaderGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getBoldGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new BoldGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getItalicGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new ItalicGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getInlineCodeGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new InlineCodeGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getStrikeThroughGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new StrikeThroughGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getFootnoteGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new NormalGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getImageGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new NormalGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getHyperLinkGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new NormalGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getCodeGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new CodeGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getBackslashGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new NormalGrammar(rxMDConfiguration);
    }

    private void init(RxMDConfiguration rxMDConfiguration) {
        mRxMDConfiguration = rxMDConfiguration;
        mGrammarList = new ArrayList<>();
        mGrammarList.add(getBoldGrammar(mRxMDConfiguration));
        mGrammarList.add(getItalicGrammar(mRxMDConfiguration));
        mGrammarList.add(getStrikeThroughGrammar(mRxMDConfiguration));
        mGrammarList.add(getInlineCodeGrammar(mRxMDConfiguration));
        mGrammarList.add(getCenterAlignGrammar(mRxMDConfiguration));
        mGrammarList.add(getHeaderGrammar(mRxMDConfiguration));
        mGrammarList.add(getBlockQuotesGrammar(mRxMDConfiguration));
        mGrammarList.add(getCodeGrammar(mRxMDConfiguration));
        mGrammarList.add(getHorizontalRulesGrammar(mRxMDConfiguration));
        mGrammarList.add(getOrderListGrammar(mRxMDConfiguration));
        mGrammarList.add(getUnOrderListGrammar(mRxMDConfiguration));
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
        if (mGrammarList == null || mRxMDConfiguration == null || mRxMDConfiguration != rxMDConfiguration) {
            init(rxMDConfiguration);
        }
        Editable editable = (Editable) charSequence;
        List<EditToken> list = new ArrayList<>();
        for (IGrammar iGrammar : mGrammarList) {
            list.addAll(iGrammar.format(editable));
        }
        Editable newEditable = Editable.Factory.getInstance().newEditable(editable.toString());
        for (EditToken editToken : list) {
            newEditable.setSpan(editToken.getSpan(), editToken.getStart(), editToken.getEnd(), editToken.getFlag());
        }
        return newEditable;
    }
}
