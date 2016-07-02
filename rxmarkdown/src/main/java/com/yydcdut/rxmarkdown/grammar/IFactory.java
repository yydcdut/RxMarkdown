package com.yydcdut.rxmarkdown.grammar;

/**
 * Created by yuyidong on 16/7/2.
 */

public interface IFactory {
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

}
