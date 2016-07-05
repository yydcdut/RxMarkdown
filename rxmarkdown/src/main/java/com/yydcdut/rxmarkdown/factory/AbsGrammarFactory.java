package com.yydcdut.rxmarkdown.factory;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.grammar.IGrammar;

/**
 * Created by yuyidong on 16/5/12.
 */
public abstract class AbsGrammarFactory {
    public static final int GRAMMAR_NORMAL = -1;

    public static final int GRAMMAR_HORIZONTAL_RULES = 0;
    public static final int GRAMMAR_BLOCK_QUOTES = 1;
    public static final int GRAMMAR_CENTER_ALIGN = 10;
    public static final int GRAMMAR_HEADER_LINE = 11;
    public static final int GRAMMAR_BOLD = 14;
    public static final int GRAMMAR_ITALIC = 15;
    public static final int GRAMMAR_INLINE_CODE = 16;
    public static final int GRAMMAR_STRIKE_THROUGH = 17;
    public static final int GRAMMAR_FOOTNOTE = 18;
    public static final int GRAMMAR_IMAGE = 19;
    public static final int GRAMMAR_HYPERLINK = 20;
    public static final int GRAMMAR_TODO = 21;
    public static final int GRAMMAR_TODO_DONE = 22;
    public static final int GRAMMAR_BACKSLASH = 23;

    public static final int GRAMMAR_CODE = 30;
    public static final int GRAMMAR_UNORDER_LIST = 31;
    public static final int GRAMMAR_ORDER_LIST = 32;

    protected AbsGrammarFactory() {
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
    public abstract CharSequence parse(@NonNull CharSequence charSequence, @NonNull RxMDConfiguration rxMDConfiguration);
}
