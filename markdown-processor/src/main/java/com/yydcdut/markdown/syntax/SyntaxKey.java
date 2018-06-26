package com.yydcdut.markdown.syntax;

/**
 * Created by yuyidong on 2017/6/8.
 */
public interface SyntaxKey {
    /**
     * place holder
     */
    String PLACE_HOLDER = " ";

    //----------  CodeBlockSyntax  ----------
    /**
     * code key
     */
    String KEY_CODE_BLOCK = "```";
    /**
     * code key
     */
    String KEY_CODE_BLOCK_SINGLE = "`";
    //----------  CodeBlockSyntax  ----------

    //----------  BlockQuotesSyntax  ----------
    /**
     * block quotes key
     */
    String KEY_BLOCK_QUOTES = "> ";
    /**
     * block quotes key(left single)
     */
    String KEY_BLOCK_QUOTES_LEFT_SINGLE = ">";
    //----------  BlockQuotesSyntax  ----------

    //----------  BackslashSyntax  ----------
    /**
     * black slash key
     */
    String KEY_BACKSLASH = "\\";
    /**
     * "."
     */
    String KEY_DOT = ".";
    /**
     * "." for back slash
     */
    String KEY_DOT_BACKSLASH = KEY_BACKSLASH + KEY_DOT;
    /**
     * "-"
     */
    String KEY_HYPHEN = "-";
    /**
     * "-" for back slash
     */
    String KEY_HYPHEN_BACKSLASH = KEY_BACKSLASH + KEY_HYPHEN;

    //----------  BackslashSyntax  ----------

    //----------  BoldSyntax  ----------
    /**
     * bold key
     */
    String KEY_BOLD_ASTERISK = "**";
    /**
     * bold key(single key)
     */
    String KEY_BOLD_ASTERISK_SINGLE = "*";
    /**
     * bold key
     */
    String KEY_BOLD_UNDERLINE = "__";
    /**
     * bold key(single key)
     */
    String KEY_BOLD_UNDERLINE_SINGLE = "_";
    /**
     * black slash for bold key
     */
    String KEY_BOLD_BACKSLASH_ASTERISK = KEY_BACKSLASH + KEY_BOLD_ASTERISK_SINGLE;

    /**
     * black slash for bold key
     */
    String KEY_BOLD_BACKSLASH_UNDERLINE = KEY_BACKSLASH + KEY_BOLD_UNDERLINE_SINGLE;
    //----------  BoldSyntax  ----------

    //----------  CenterAlignSyntax  ----------
    /**
     * center align key
     */
    String KEY_CENTER_ALIGN_LEFT = "[";
    /**
     * center align key
     */
    String KEY_CENTER_ALIGN_RIGHT = "]";
    /**
     * black slash for center align key
     */
    String KEY_CENTER_ALIGN_BACKSLASH_RIGHT = KEY_BACKSLASH + KEY_CENTER_ALIGN_RIGHT;
    //----------  CenterAlignSyntax  ----------

    //----------  FootnoteSyntax  ----------
    /**
     * footnote key
     */
    String KEY_FOOTNOTE_LEFT = "[^";
    /**
     * footnote key
     */
    String KEY_FOOTNOTE_CARET = "^";
    /**
     * footnote key
     */
    String KEY_FOOTNOTE_LEFT_SINGLE = "[";
    /**
     * footnote key
     */
    String KEY_FOOTNOTE_RIGHT = "]";
    /**
     * black slash for footnote key
     */
    String KEY_FOOTNOTE_BACKSLASH_LEFT = KEY_BACKSLASH + KEY_FOOTNOTE_LEFT_SINGLE;
    /**
     * black slash for footnote key
     */
    String KEY_FOOTNOTE_BACKSLASH_RIGHT = KEY_BACKSLASH + KEY_FOOTNOTE_RIGHT;
    /**
     * black slash for footnote key
     */
    String KEY_FOOTNOTE_BACKSLASH_CARET = KEY_BACKSLASH + KEY_FOOTNOTE_CARET;
    //----------  FootnoteSyntax  ----------

    //----------  HyperLinkSyntax  ----------
    /**
     * hyper link key
     */
    String KEY_HYPER_LINK_LEFT = "[";
    /**
     * hyper link key
     */
    String KEY_HYPER_LINK_MIDDLE = "](";
    /**
     * hyper link key
     */
    String KEY_HYPER_LINK_MIDDLE_RIGHT = "(";
    /**
     * hyper link key (single)
     */
    String KEY_HYPER_LINK_MIDDLE_SINGLE = "]";
    /**
     * hyper link key
     */
    String KEY_HYPER_LINK_RIGHT = ")";
    /**
     * black slash for hyper link key
     */
    String KEY_HYPER_LINK_BACKSLASH_LEFT = KEY_BACKSLASH + KEY_HYPER_LINK_LEFT;
    /**
     * black slash for hyper link key
     */
    String KEY_HYPER_LINK_BACKSLASH_MIDDLE = KEY_BACKSLASH + KEY_HYPER_LINK_MIDDLE_SINGLE;
    /**
     * black slash for hyper link key
     */
    String KEY_HYPER_LINK_BACKSLASH_MIDDLE_RIGHT = KEY_BACKSLASH + KEY_HYPER_LINK_MIDDLE_RIGHT;
    /**
     * black slash for hyper link key
     */
    String KEY_HYPER_LINK_BACKSLASH_RIGHT = KEY_BACKSLASH + KEY_HYPER_LINK_RIGHT;
    /**
     * hyper link key
     */
    char KEY_HYPER_LINK_LEFT_CHAR = '[';
    /**
     * hyper link key
     */
    char KEY_HYPER_LINK_MIDDLE_LEFT_CHAR = ']';
    /**
     * hyper link key
     */
    char KEY_HYPER_LINK_MIDDLE_RIGHT_CHAR = '(';
    /**
     * hyper link key
     */
    char KEY_HYPER_LINK_RIGHT_CHAR = ')';
    /**
     * hyper link key
     */
    String KEY_HYPER_LINK_EMPTY = "[]()";
    //----------  HyperLinkSyntax  ----------

    //----------  ImageSyntax  ----------
    /**
     * image key
     */
    String KEY_IMAGE_LEFT = "![";
    /**
     * image key (single)
     */
    String KEY_IMAGE_LEFT_SINGLE = "!";
    /**
     * image key
     */
    String KEY_IMAGE_MIDDLE = "](";
    /**
     * image key (single)
     */
    String KEY_IMAGE_MIDDLE_SINGLE = "]";
    /**
     * image key
     */
    String KEY_IMAGE_RIGHT = ")";
    /**
     * black slash for image key
     */
    String KEY_IMAGE_BACKSLASH_LEFT = KEY_BACKSLASH + KEY_IMAGE_LEFT_SINGLE;
    /**
     * black slash for image key
     */
    String KEY_IMAGE_BACKSLASH_MIDDLE = KEY_BACKSLASH + KEY_IMAGE_MIDDLE_SINGLE;
    /**
     * black slash for image key
     */
    String KEY_IMAGE_BACKSLASH_RIGHT = KEY_BACKSLASH + KEY_IMAGE_RIGHT;
    //----------  ImageSyntax  ----------

    //----------  CodeSyntax  ----------
    /**
     * inline code key
     */
    String KEY_CODE = "`";
    /**
     * black slash for (inline) code key
     */
    String KEY_CODE_BACKSLASH = KEY_BACKSLASH + KEY_CODE;
    //----------  CodeSyntax  ----------

    //----------  ItalicSyntax  ----------
    /**
     * italic key
     */
    String KEY_ITALIC_ASTERISK = "*";
    /**
     * italic key
     */
    String KEY_ITALIC_UNDERLINE = "_";
    /**
     * black slash for italic key
     */
    String KEY_ITALIC_BACKSLASH_ASTERISK = KEY_BACKSLASH + KEY_ITALIC_ASTERISK;
    /**
     * black slash for italic key
     */
    String KEY_ITALIC_BACKSLASH_UNDERLINE = KEY_BACKSLASH + KEY_ITALIC_UNDERLINE;
    //----------  ItalicSyntax  ----------

    //----------  StrikeThroughSyntax  ----------
    /**
     * strike key
     */
    String KEY_STRIKE_THROUGH = "~~";
    /**
     * strike key (single)
     */
    String KEY_STRIKE_THROUGH_SINGLE = "~";

    /**
     * black slash for strike key
     */
    String KEY_STRIKE_BACKSLASH = KEY_BACKSLASH + KEY_STRIKE_THROUGH_SINGLE;
    //----------  StrikeThroughSyntax  ----------

    //----------  HeaderSyntax  ----------
    /**
     * header key
     */
    String KEY_0_HEADER = "# ";
    /**
     * header key
     */
    String KEY_1_HEADER = "## ";
    /**
     * header key
     */
    String KEY_2_HEADER = "### ";
    /**
     * header key
     */
    String KEY_3_HEADER = "#### ";
    /**
     * header key
     */
    String KEY_4_HEADER = "##### ";
    /**
     * header key
     */
    String KEY_5_HEADER = "###### ";
    /**
     * header key(single)
     */
    String KEY_HEADER_SINGLE = "#";
    //----------  HeaderSyntax  ----------

    //----------  OrderListSyntax  ----------
    /**
     * list header key
     */
    String KEY_LIST_HEADER = "  ";

    /**
     * list header key
     */
    String KEY_LIST_HEADER_HALF = "  ";
    /**
     * order list dot char
     */
    char DOT = '.';
    /**
     * list ignore key
     */
    String IGNORE_LIST_HYPHEN_LOW = "- [x]";
    /**
     * list ignore key
     */
    String IGNORE_LIST_HYPHEN_UP = "- [X]";
    /**
     * list ignore key
     */
    String IGNORE_LIST_HYPHEN = "- [ ]";
    /**
     * list ignore key
     */
    String IGNORE_LIST_ASTERISK_LOW = "* [x]";
    /**
     * list ignore key
     */
    String IGNORE_LIST_ASTERISK_UP = "* [x]";
    /**
     * list ignore key
     */
    String IGNORE_LIST_ASTERISK = "* [ ]";
    /**
     * list ignore place holder
     */
    String IGNORE_LIST_PLACE_HOLDER = "     ";
    //----------  ListSyntax  ----------

    //----------  HorizontalRulesSyntax  ----------
    /**
     * horizontal rules key
     */
    String KEY_HORIZONTAL_RULES_ASTERISK = "***";
    /**
     * horizontal rules key
     */
    String KEY_HORIZONTAL_RULES_HYPHEN = "---";
    /**
     * horizontal rules single char
     */
    char KEY_HORIZONTAL_RULES_ASTERISK_SINGLE = '*';
    /**
     * horizontal rules single char
     */
    char KEY_HORIZONTAL_RULES_HYPHEN_SINGLE = '-';
    //----------  HorizontalRulesSyntax  ----------

    //----------  TodoDoneSyntax  ----------
    /**
     * to do done key
     */
    String KEY_TODO_DONE_0 = "- [x] ";
    /**
     * to do done key
     */
    String KEY_TODO_DONE_1 = "- [X] ";
    /**
     * to do done key
     */
    String KEY_TODO_DONE_2 = "* [x] ";
    /**
     * to do done key
     */
    String KEY_TODO_DONE_3 = "* [X] ";
    //----------  TodoDoneSyntax  ----------

    //----------  TodoSyntax  ----------
    /**
     * to do key
     */
    String KEY_TODO_HYPHEN = "- [ ] ";
    /**
     * to do key
     */
    String KEY_TODO_ASTERISK = "* [ ] ";
    //----------  TodoSyntax  ----------

    //----------  UnOrderListSyntax  ----------
    /**
     * unorder list key
     */
    String KEY_UNORDER_LIST_ASTERISK = "* ";
    /**
     * unorder list key
     */
    String KEY_UNORDER_LIST_CHAR_ASTERISK = "*";
    /**
     * unorder list key
     */
    String KEY_UNORDER_LIST_PLUS = "+ ";
    /**
     * unorder list key
     */
    String KEY_UNORDER_LIST_CHAR_PLUS = "+";
    /**
     * unorder list key
     */
    String KEY_UNORDER_LIST_HYPHEN = "- ";
    /**
     * unorder list key
     */
    String KEY_UNORDER_LIST_CHAR_HYPHEN = "-";
    /**
     * unorder list ignore key
     */
    String IGNORE_UNORDER_LIST_HYPHEN = KEY_TODO_HYPHEN;
    /**
     * unorder list ignore key
     */
    String IGNORE_UNORDER_LIST_ASTERISK = KEY_TODO_ASTERISK;
    /**
     * unorder list ignore key
     */
    String IGNORE_UNORDER_LIST_2 = KEY_TODO_DONE_0;
    /**
     * unorder list ignore key
     */
    String IGNORE_UNORDER_LIST_3 = KEY_TODO_DONE_1;
    /**
     * unorder list ignore key
     */
    String IGNORE_UNORDER_LIST_4 = KEY_TODO_DONE_2;
    /**
     * unorder list ignore key
     */
    String IGNORE_UNORDER_LIST_5 = KEY_TODO_DONE_3;
    //----------  UnOrderListSyntax  ----------

}
