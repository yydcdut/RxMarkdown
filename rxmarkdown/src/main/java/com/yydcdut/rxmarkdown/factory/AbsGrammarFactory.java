package com.yydcdut.rxmarkdown.factory;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.grammar.IGrammar;

/**
 * Created by yuyidong on 16/5/12.
 */
public abstract class AbsGrammarFactory {
    protected RxMDConfiguration mRxMDConfiguration;

    protected AbsGrammarFactory() {
    }

    public void init(@NonNull RxMDConfiguration rxMDConfiguration) {
        mRxMDConfiguration = rxMDConfiguration;
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
