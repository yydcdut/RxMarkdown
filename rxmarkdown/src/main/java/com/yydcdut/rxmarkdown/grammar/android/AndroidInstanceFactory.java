package com.yydcdut.rxmarkdown.grammar.android;

import com.yydcdut.rxmarkdown.grammar.IGrammar;

/**
 * Created by yuyidong on 16/5/12.
 */
public class AndroidInstanceFactory {

    public static final int GRAMMAR_BLOCK_QUOTES = 0;
    public static final int GRAMMAR_ORDER_LIST = 1;
    public static final int GRAMMAR_UNORDER_LIST = 2;
    public static final int GRAMMAR_CENTER_ALIGN = 10;
    public static final int GRAMMAR_HEADER_LINE_1 = 11;
    public static final int GRAMMAR_HEADER_LINE_2 = 12;
    public static final int GRAMMAR_HEADER_LINE_3 = 13;
    public static final int GRAMMAR_BOLD = 14;
    public static final int GRAMMAR_ITALIC = 15;
    public static final int GRAMMAR_INLINE_CODE = 16;
    public static final int GRAMMAR_STRIKE_THROUGH = 17;

    public static IGrammar getAndroidGrammar(int grammar) {
        switch (grammar) {
            case GRAMMAR_BLOCK_QUOTES:
                return new BlockQuotesGrammar();
            case GRAMMAR_ORDER_LIST:
                return new OrderListGrammar();
            case GRAMMAR_UNORDER_LIST:
                return new UnOrderListGrammar();
            case GRAMMAR_CENTER_ALIGN:
                return new CenterAlignGrammar();
            case GRAMMAR_HEADER_LINE_1:
                return new Header1Grammar();
            case GRAMMAR_HEADER_LINE_2:
                return new Header2Grammar();
            case GRAMMAR_HEADER_LINE_3:
                return new Header3Grammar();
            case GRAMMAR_BOLD:
                return new BoldGrammar();
            case GRAMMAR_ITALIC:
                return new ItalicGrammar();
            case GRAMMAR_INLINE_CODE:
                return new InlineCodeGrammar();
            case GRAMMAR_STRIKE_THROUGH:
                return new StrikeThroughGrammar();
            default:
                return new NormalGrammar();
        }
    }

}
