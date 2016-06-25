package com.yydcdut.rxmarkdown.factory;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.Configuration;
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

    public void init(@NonNull Configuration configuration) {
        mTotalChain = new MultiGrammarsChain(
                getCodeGrammar(configuration),
                getUnOrderListGrammar(configuration),
                getOrderListGrammar(configuration));
        mLineChain = new GrammarSingleChain(getHorizontalRulesGrammar(configuration));
        GrammarDoElseChain blockQuitesChain = new GrammarDoElseChain(getBlockQuotesGrammar(configuration));
        GrammarDoElseChain todoChain = new GrammarDoElseChain(getTodoGrammar(configuration));
        GrammarDoElseChain todoDoneChain = new GrammarDoElseChain(getTodoDoneGrammar(configuration));
        GrammarMultiChains centerAlignChain = new GrammarMultiChains(getCenterAlignGrammar(configuration));
        GrammarMultiChains headerChain = new GrammarMultiChains(getHeaderGrammar(configuration));
        MultiGrammarsChain multiChain = new MultiGrammarsChain(
                getImageGrammar(configuration),
                getHyperLinkGrammar(configuration),
                getInlineCodeGrammar(configuration),
                getBoldGrammar(configuration),
                getItalicGrammar(configuration),
                getStrikeThroughGrammar(configuration),
                getFootnoteGrammar(configuration));
        GrammarSingleChain backslashChain = new GrammarSingleChain(getBackslashGrammar(configuration));

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

    protected abstract IGrammar getHorizontalRulesGrammar(@NonNull Configuration configuration);

    protected abstract IGrammar getBlockQuotesGrammar(@NonNull Configuration configuration);

    protected abstract IGrammar getTodoGrammar(@NonNull Configuration configuration);

    protected abstract IGrammar getTodoDoneGrammar(@NonNull Configuration configuration);

    protected abstract IGrammar getOrderListGrammar(@NonNull Configuration configuration);

    protected abstract IGrammar getUnOrderListGrammar(@NonNull Configuration configuration);

    protected abstract IGrammar getCenterAlignGrammar(@NonNull Configuration configuration);

    protected abstract IGrammar getHeaderGrammar(@NonNull Configuration configuration);

    protected abstract IGrammar getBoldGrammar(@NonNull Configuration configuration);

    protected abstract IGrammar getItalicGrammar(@NonNull Configuration configuration);

    protected abstract IGrammar getInlineCodeGrammar(@NonNull Configuration configuration);

    protected abstract IGrammar getStrikeThroughGrammar(@NonNull Configuration configuration);

    protected abstract IGrammar getFootnoteGrammar(@NonNull Configuration configuration);

    protected abstract IGrammar getImageGrammar(@NonNull Configuration configuration);

    protected abstract IGrammar getHyperLinkGrammar(@NonNull Configuration configuration);

    protected abstract IGrammar getCodeGrammar(@NonNull Configuration configuration);

    protected abstract IGrammar getBackslashGrammar(@NonNull Configuration configuration);

    @NonNull
    public abstract CharSequence parse(@NonNull CharSequence charSequence);
}
