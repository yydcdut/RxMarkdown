package com.yydcdut.rxmarkdown.factory;

import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.grammar.android.AndroidInstanceFactory;

/**
 * Created by yuyidong on 16/5/12.
 */
public class AndroidFactory extends AbsGrammarFactory {
    public AndroidFactory() {
        super();
    }

    public static AndroidFactory create() {
        return new AndroidFactory();
    }

    @Override
    protected IGrammar getHorizontalRules() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_HORIZONTAL_RULES);
    }

    @Override
    protected IGrammar getBlockQuotesGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_BLOCK_QUOTES);
    }

    @Override
    protected IGrammar getOrderListGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_ORDER_LIST);
    }

    @Override
    protected IGrammar getUnOrderListGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_UNORDER_LIST);
    }

    @Override
    protected IGrammar getCenterAlignGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_CENTER_ALIGN);
    }

    @Override
    protected IGrammar getHeader3Grammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_HEADER_LINE_3);
    }

    @Override
    protected IGrammar getHeader2Grammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_HEADER_LINE_2);
    }

    @Override
    protected IGrammar getHeader1Grammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_HEADER_LINE_1);
    }

    @Override
    protected IGrammar getBoldGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_BOLD);
    }

    @Override
    protected IGrammar getItalicGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_ITALIC);
    }

    @Override
    protected IGrammar getInlineCodeGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_INLINE_CODE);
    }

    @Override
    protected IGrammar getStrikeThroughGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_STRIKE_THROUGH);
    }

    @Override
    protected IGrammar getSuperscriptGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar((AndroidInstanceFactory.GRAMMAR_SUPERSCRIPT));
    }

    @Override
    protected IGrammar getHyperLinkGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_HYPERLINK);
    }
}
