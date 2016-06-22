package com.yydcdut.rxmarkdown.grammar.android;

import com.yydcdut.rxmarkdown.grammar.IGrammar;

/**
 * Created by yuyidong on 16/5/12.
 */
public class AndroidInstanceFactory {

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

    public static IGrammar getAndroidGrammar(int grammar) {
        switch (grammar) {
            case GRAMMAR_HORIZONTAL_RULES:
                return new HorizontalRulesGrammar();
            case GRAMMAR_BLOCK_QUOTES:
                return new BlockQuotesGrammar();
            case GRAMMAR_CENTER_ALIGN:
                return new CenterAlignGrammar();
            case GRAMMAR_HEADER_LINE:
                return new HeaderGrammar();
            case GRAMMAR_BOLD:
                return new BoldGrammar();
            case GRAMMAR_ITALIC:
                return new ItalicGrammar();
            case GRAMMAR_INLINE_CODE:
                return new InlineCodeGrammar();
            case GRAMMAR_STRIKE_THROUGH:
                return new StrikeThroughGrammar();
            case GRAMMAR_FOOTNOTE:
                return new FootnoteGrammar();
            case GRAMMAR_IMAGE:
                return new ImageGrammar();
            case GRAMMAR_HYPERLINK:
                return new HyperLinkGrammar();
            case GRAMMAR_TODO:
                return new TodoGrammar();
            case GRAMMAR_TODO_DONE:
                return new TodoDoneGrammar();
            case GRAMMAR_CODE:
                return new CodeGrammar();
            case GRAMMAR_UNORDER_LIST:
                return new UnOrderListGrammar();
            case GRAMMAR_ORDER_LIST:
                return new OrderListGrammar();
            case GRAMMAR_BACKSLASH:
                return new BackslashGrammar();
            default:
                return new NormalGrammar();
        }
    }

}
