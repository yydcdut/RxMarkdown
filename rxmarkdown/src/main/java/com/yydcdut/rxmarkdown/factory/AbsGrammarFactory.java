package com.yydcdut.rxmarkdown.factory;

import com.yydcdut.rxmarkdown.chain.GrammarMultiChains;
import com.yydcdut.rxmarkdown.chain.GrammarSingleChain;
import com.yydcdut.rxmarkdown.chain.IChain;
import com.yydcdut.rxmarkdown.chain.MultiGrammarsChain;
import com.yydcdut.rxmarkdown.grammar.IGrammar;

/**
 * Created by yuyidong on 16/5/12.
 */
public abstract class AbsGrammarFactory {
    protected IChain mChain;

    public AbsGrammarFactory() {
        mChain = new GrammarSingleChain(getHorizontalRulesGrammar());
        GrammarSingleChain blockQuitesChain = new GrammarSingleChain(getBlockQuotesGrammar());
        GrammarSingleChain orderListChain = new GrammarSingleChain(getOrderListGrammar());
        GrammarSingleChain unOrderListChain = new GrammarSingleChain(getUnOrderListGrammar());
        GrammarMultiChains centerAlignChain = new GrammarMultiChains(getCenterAlignGrammar());
        GrammarMultiChains headerLine3Chain = new GrammarMultiChains(getHeader3Grammar());
        GrammarMultiChains headerLine2Chain = new GrammarMultiChains(getHeader2Grammar());
        GrammarMultiChains headerLine1Chain = new GrammarMultiChains(getHeader1Grammar());
        MultiGrammarsChain multiChain = new MultiGrammarsChain(
                getBoldGrammar(),
                getItalicGrammar(),
                getInlineCodeGrammar(),
                getStrikeThroughGrammar(),
                getSuperscriptGrammar(),
                getImageGrammar(),
                getHyperLinkGrammar());
        mChain.setNextHandleGrammar(blockQuitesChain);
        blockQuitesChain.setNextHandleGrammar(orderListChain);
        orderListChain.setNextHandleGrammar(unOrderListChain);
        unOrderListChain.setNextHandleGrammar(centerAlignChain);
        centerAlignChain.addNextHandleGrammar(headerLine3Chain);
        centerAlignChain.addNextHandleGrammar(multiChain);
        headerLine3Chain.addNextHandleGrammar(headerLine2Chain);
        headerLine3Chain.addNextHandleGrammar(multiChain);
        headerLine2Chain.addNextHandleGrammar(headerLine1Chain);
        headerLine2Chain.addNextHandleGrammar(multiChain);
        headerLine1Chain.addNextHandleGrammar(multiChain);
    }

    protected abstract IGrammar getImageGrammar();

    protected abstract IGrammar getHorizontalRulesGrammar();

    protected abstract IGrammar getBlockQuotesGrammar();

    protected abstract IGrammar getOrderListGrammar();

    protected abstract IGrammar getUnOrderListGrammar();

    protected abstract IGrammar getCenterAlignGrammar();

    protected abstract IGrammar getHeader3Grammar();

    protected abstract IGrammar getHeader2Grammar();

    protected abstract IGrammar getHeader1Grammar();

    protected abstract IGrammar getBoldGrammar();

    protected abstract IGrammar getItalicGrammar();

    protected abstract IGrammar getInlineCodeGrammar();

    protected abstract IGrammar getStrikeThroughGrammar();

    protected abstract IGrammar getSuperscriptGrammar();

    protected abstract IGrammar getHyperLinkGrammar();

    public IChain getChain() {
        return mChain;
    }
}
