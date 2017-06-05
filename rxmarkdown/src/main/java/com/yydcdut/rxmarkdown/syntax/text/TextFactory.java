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
import android.text.SpannableStringBuilder;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.chain.GrammarDoElseChain;
import com.yydcdut.rxmarkdown.chain.GrammarMultiChains;
import com.yydcdut.rxmarkdown.chain.GrammarSingleChain;
import com.yydcdut.rxmarkdown.chain.IChain;
import com.yydcdut.rxmarkdown.chain.MultiGrammarsChain;
import com.yydcdut.rxmarkdown.factory.AbsGrammarFactory;
import com.yydcdut.rxmarkdown.syntax.IGrammar;

/**
 * This factory's purpose is parsing content <b>correctly</b>, as the same time, it destroys the integrity of the content.
 * This factory will delete the key words of markdown grammar in content.
 * So, hope that it will be used in TextView, not in EditText.
 * <p>
 * Created by yuyidong on 16/5/12.
 */
public class TextFactory extends AbsGrammarFactory {
    private static final String NEWLINE = "\n";
    private RxMDConfiguration mRxMDConfiguration;
    private IChain mLineChain;
    private IChain mTotalChain;

    private TextFactory() {
        super();
    }

    /**
     * get AndroidFactory object
     *
     * @return {@link AbsGrammarFactory}
     */
    public static AbsGrammarFactory create() {
        return new TextFactory();
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
        return new TodoGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getTodoDoneGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new TodoDoneGrammar(rxMDConfiguration);
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
        return new FootnoteGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getImageGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new ImageGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getHyperLinkGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new HyperLinkGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getCodeGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new CodeGrammar(rxMDConfiguration);
    }

    @Override
    public IGrammar getBackslashGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new BackslashGrammar(rxMDConfiguration);
    }

    private void init(@NonNull RxMDConfiguration rxMDConfiguration) {
        mRxMDConfiguration = rxMDConfiguration;
        mTotalChain = new MultiGrammarsChain(
                getCodeGrammar(rxMDConfiguration),
                getUnOrderListGrammar(rxMDConfiguration),
                getOrderListGrammar(rxMDConfiguration));
        mLineChain = new GrammarSingleChain(getHorizontalRulesGrammar(rxMDConfiguration));
        GrammarDoElseChain blockQuitesChain = new GrammarDoElseChain(getBlockQuotesGrammar(rxMDConfiguration));
        GrammarDoElseChain todoChain = new GrammarDoElseChain(getTodoGrammar(rxMDConfiguration));
        GrammarDoElseChain todoDoneChain = new GrammarDoElseChain(getTodoDoneGrammar(rxMDConfiguration));
        GrammarMultiChains centerAlignChain = new GrammarMultiChains(getCenterAlignGrammar(rxMDConfiguration));
        GrammarMultiChains headerChain = new GrammarMultiChains(getHeaderGrammar(rxMDConfiguration));
        MultiGrammarsChain multiChain = new MultiGrammarsChain(
                getImageGrammar(rxMDConfiguration),
                getHyperLinkGrammar(rxMDConfiguration),
                getInlineCodeGrammar(rxMDConfiguration),
                getBoldGrammar(rxMDConfiguration),
                getItalicGrammar(rxMDConfiguration),
                getStrikeThroughGrammar(rxMDConfiguration),
                getFootnoteGrammar(rxMDConfiguration));
        GrammarSingleChain backslashChain = new GrammarSingleChain(getBackslashGrammar(rxMDConfiguration));

        mLineChain.setNextHandleGrammar(blockQuitesChain);

        blockQuitesChain.setNextHandleGrammar(todoChain);
        blockQuitesChain.addNextHandleGrammar(multiChain);

        todoChain.setNextHandleGrammar(todoDoneChain);
        todoChain.addNextHandleGrammar(multiChain);

        todoDoneChain.setNextHandleGrammar(centerAlignChain);
        todoDoneChain.addNextHandleGrammar(multiChain);

        centerAlignChain.addNextHandleGrammar(headerChain);
        centerAlignChain.addNextHandleGrammar(multiChain);

        multiChain.setNextHandleGrammar(backslashChain);
    }

    @NonNull
    @Override
    public CharSequence parse(@NonNull CharSequence charSequence, @NonNull RxMDConfiguration rxMDConfiguration) {
        if (rxMDConfiguration == null) {
            return charSequence;
        }
        if (mTotalChain == null || mLineChain == null || mRxMDConfiguration == null || mRxMDConfiguration != rxMDConfiguration) {
            init(rxMDConfiguration);
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder(charSequence);
        ssb = parseTotal(mTotalChain, ssb);
        ssb = parseByLine(mLineChain, ssb);
        return ssb;
    }

    private SpannableStringBuilder parseTotal(IChain totalChain, SpannableStringBuilder ssb) {
        totalChain.handleGrammar(ssb);
        return ssb;
    }

    private SpannableStringBuilder parseByLine(IChain lineChain, SpannableStringBuilder content) {
        String text = content.toString();
        String[] lines = text.split("\n");
        SpannableStringBuilder[] ssbLines = new SpannableStringBuilder[lines.length];
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        int index = 0;
        for (int i = 0; i < lines.length; i++) {
            ssbLines[i] = (SpannableStringBuilder) content.subSequence(index, index + lines[i].length());
            lineChain.handleGrammar(ssbLines[i]);
            index += (lines[i]).length();
            if (i < lines.length - 1 || mRxMDConfiguration.isAppendNewlineAfterLastLine()) {
                ssbLines[i].append(NEWLINE);
                index += NEWLINE.length();
            }
            ssb.append(ssbLines[i]);
        }
        return ssb;
    }

}
