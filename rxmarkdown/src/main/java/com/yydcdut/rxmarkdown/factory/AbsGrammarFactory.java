package com.yydcdut.rxmarkdown.factory;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.chain.GrammarDoElseChain;
import com.yydcdut.rxmarkdown.chain.GrammarMultiChains;
import com.yydcdut.rxmarkdown.chain.GrammarSingleChain;
import com.yydcdut.rxmarkdown.chain.IChain;
import com.yydcdut.rxmarkdown.chain.MultiGrammarsChain;
import com.yydcdut.rxmarkdown.grammar.IGrammar;

/**
 * Created by yuyidong on 16/5/12.
 */
public abstract class AbsGrammarFactory {
    protected IChain mLineChain;
    protected IChain mTotalChain;

    protected AbsGrammarFactory() {
    }

    public void init(@NonNull RxMDConfiguration rxMDConfiguration) {
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

    protected abstract IGrammar getHorizontalRulesGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    protected abstract IGrammar getBlockQuotesGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    protected abstract IGrammar getTodoGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    protected abstract IGrammar getTodoDoneGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    protected abstract IGrammar getOrderListGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    protected abstract IGrammar getUnOrderListGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    protected abstract IGrammar getCenterAlignGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    protected abstract IGrammar getHeaderGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    protected abstract IGrammar getBoldGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    protected abstract IGrammar getItalicGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    protected abstract IGrammar getInlineCodeGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    protected abstract IGrammar getStrikeThroughGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    protected abstract IGrammar getFootnoteGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    protected abstract IGrammar getImageGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    protected abstract IGrammar getHyperLinkGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    protected abstract IGrammar getCodeGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    protected abstract IGrammar getBackslashGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    @NonNull
    public abstract CharSequence parse(@NonNull CharSequence charSequence);
}
