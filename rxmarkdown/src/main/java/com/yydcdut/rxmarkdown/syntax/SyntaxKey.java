package com.yydcdut.rxmarkdown.syntax;

/**
 * Created by yuyidong on 2017/6/8.
 */
public interface SyntaxKey {
    /**
     * place holder
     */
    String PLACE_HOLDER = " ";

    //----------  CodeSyntax  ----------
    /**
     * code key
     */
    String KEY_CODE = "```";
    //----------  CodeSyntax  ----------

    //----------  BlockQuotesSyntax  ----------
    /**
     * block quotes key
     */
    String KEY_BLOCK_QUOTES = "> ";
    //----------  BlockQuotesSyntax  ----------

    //----------  BackslashSyntax  ----------
    /**
     * black slash key
     */
    String KEY_BACKSLASH = "\\";
    String KEY_ENCODE = "@%7DF16dgf%jy@po&";//todo random
    String KEY_ENCODE_1 = "%4usyHDlL&@D%";
    String KEY_ENCODE_2 = "&YDhs@h4sF&%kLsx63sd@";
    String KEY_ENCODE_3 = "%hsyRjh34l%%2@";
    String KEY_ENCODE_4 = "&@da&U56ec%k%QW@";
    //----------  BackslashSyntax  ----------

    //----------  BoldSyntax  ----------
    /**
     * bold key
     */
    String KEY_BOLD = "**";
    /**
     * bold key
     */
    String KEY_BOLD_1 = "__";
    /**
     * black slash for bold key
     */
    String KEY_BOLD_BACKSLASH_VALUE = SyntaxKey.KEY_BACKSLASH + "*";

    /**
     * black slash for bold key
     */
    String KEY_BOLD_BACKSLASH_VALUE_1 = SyntaxKey.KEY_BACKSLASH + "_";
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
    String KEY_CENTER_ALIGN_BACKSLASH_VALUE_RIGHT = SyntaxKey.KEY_BACKSLASH + KEY_CENTER_ALIGN_RIGHT;
    //----------  CenterAlignSyntax  ----------

    //----------  FootnoteSyntax  ----------
    /**
     * footnote key
     */
    String KEY_FOOTNOTE_LEFT = "[^";
    /**
     * footnote key
     */
    String KEY_FOOTNOTE_RIGHT = "]";
    /**
     * black slash for footnote key
     */
    String KEY_FOOTNOTE_BACKSLASH_VALUE_LEFT = SyntaxKey.KEY_BACKSLASH + "[";
    /**
     * black slash for footnote key
     */
    String KEY_FOOTNOTE_BACKSLASH_VALUE_RIGHT = SyntaxKey.KEY_BACKSLASH + "]";
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
    String KEY_HYPER_LINK_RIGHT = ")";
    /**
     * black slash for hyper link key
     */
    String KEY_HYPER_LINK_BACKSLASH_VALUE_LEFT = SyntaxKey.KEY_BACKSLASH + "[";
    /**
     * black slash for hyper link key
     */
    String KEY_HYPER_LINK_BACKSLASH_VALUE_MIDDLE = SyntaxKey.KEY_BACKSLASH + "]";
    /**
     * black slash for hyper link key
     */
    String KEY_HYPER_LINK_BACKSLASH_VALUE_RIGHT = SyntaxKey.KEY_BACKSLASH + ")";
    //----------  HyperLinkSyntax  ----------

    //----------  ImageSyntax  ----------
    /**
     * image key
     */
    String KEY_IMAGE_LEFT = "![";
    /**
     * image key
     */
    String KEY_IMAGE_MIDDLE = "](";
    /**
     * image key
     */
    String KEY_IMAGE_RIGHT = ")";
    /**
     * black slash for image key
     */
    String KEY_IMAGE_BACKSLASH_VALUE_LEFT = SyntaxKey.KEY_BACKSLASH + "!";
    /**
     * black slash for image key
     */
    String KEY_IMAGE_BACKSLASH_VALUE_MIDDLE = SyntaxKey.KEY_BACKSLASH + "]";
    /**
     * black slash for image key
     */
    String KEY_IMAGE_BACKSLASH_VALUE_RIGHT = SyntaxKey.KEY_BACKSLASH + SyntaxKey.KEY_IMAGE_RIGHT;
    //----------  ImageSyntax  ----------

    //----------  InlineCodeSyntax  ----------
    /**
     * inline code key
     */
    String KEY_INLINE_CODE = "`";
    /**
     * black slash for inline code key
     */
    String KEY_INLINE_BACKSLASH_VALUE = SyntaxKey.KEY_BACKSLASH + SyntaxKey.KEY_INLINE_CODE;
    //----------  InlineCodeSyntax  ----------

    //----------  ItalicSyntax  ----------
    /**
     * italic key
     */
    String KEY_ITALIC = "*";
    /**
     * italic key
     */
    String KEY_ITALIC_1 = "_";
    /**
     * black slash for italic key
     */
    String KEY_ITALIC_BACKSLASH_VALUE = SyntaxKey.KEY_BACKSLASH + SyntaxKey.KEY_ITALIC;
    /**
     * black slash for italic key
     */
    String KEY_ITALIC_BACKSLASH_VALUE_1 = SyntaxKey.KEY_BACKSLASH + SyntaxKey.KEY_ITALIC_1;
    //----------  ItalicSyntax  ----------

    //----------  StrikeThroughSyntax  ----------
    /**
     * strike key
     */
    String KEY_STRIKE_THROUGH = "~~";
    /**
     * black slash for strike key
     */
    String KEY_STRIKE_BACKSLASH_VALUE = SyntaxKey.KEY_BACKSLASH + "~";
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
    //----------  HeaderSyntax  ----------

    //----------  OrderListSyntax  ----------
    /**
     * list header key
     */
    String KEY_LIST_HEADER = " ";
    /**
     * order list dot char
     */
    char DOT = '.';
    /**
     * list ignore key
     */
    String IGNORE_LIST_0 = "- [x]";
    /**
     * list ignore key
     */
    String IGNORE_LIST_1 = "- [X]";
    /**
     * list ignore key
     */
    String IGNORE_LIST_2 = "- [ ]";
    /**
     * list ignore key
     */
    String IGNORE_LIST_3 = "* [x]";
    /**
     * list ignore key
     */
    String IGNORE_LIST_4 = "* [x]";
    /**
     * list ignore key
     */
    String IGNORE_LIST_5 = "* [ ]";
    /**
     * list ignore place holder
     */
    String IGNORE_LIST_PLACE_HOLDER = "     ";
    //----------  ListSyntax  ----------

    //----------  HorizontalRulesSyntax  ----------
    /**
     * horizontal rules key
     */
    String KEY_0_HORIZONTAL_RULES = "***";
    /**
     * horizontal rules key
     */
    String KEY_1_HORIZONTAL_RULES = "---";
    /**
     * horizontal rules single char
     */
    char KEY_SINGLE_0 = '*';
    /**
     * horizontal rules single char
     */
    char KEY_SINGLE_1 = '-';
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
    String KEY_TODO_0 = "- [ ] ";
    /**
     * to do key
     */
    String KEY_TODO_1 = "* [ ] ";
    //----------  TodoSyntax  ----------

    //----------  UnOrderListSyntax  ----------
    /**
     * unorder list key
     */
    String KEY_UNORDER_LIST_0 = "* ";
    /**
     * unorder list key
     */
    String KEY_UNORDER_LIST_CHAR_0 = "*";
    /**
     * unorder list key
     */
    String KEY_UNORDER_LIST_1 = "+ ";
    /**
     * unorder list key
     */
    String KEY_UNORDER_LIST_CHAR_1 = "+";
    /**
     * unorder list key
     */
    String KEY_UNORDER_LIST_2 = "- ";
    /**
     * unorder list key
     */
    String KEY_UNORDER_LIST_CHAR_2 = "-";
    /**
     * unorder list ignore key
     */
    String IGNORE_UNORDER_LIST_0 = SyntaxKey.KEY_TODO_0;
    /**
     * unorder list ignore key
     */
    String IGNORE_UNORDER_LIST_1 = SyntaxKey.KEY_TODO_1;
    /**
     * unorder list ignore key
     */
    String IGNORE_UNORDER_LIST_2 = SyntaxKey.KEY_TODO_DONE_0;
    /**
     * unorder list ignore key
     */
    String IGNORE_UNORDER_LIST_3 = SyntaxKey.KEY_TODO_DONE_1;
    /**
     * unorder list ignore key
     */
    String IGNORE_UNORDER_LIST_4 = SyntaxKey.KEY_TODO_DONE_2;
    /**
     * unorder list ignore key
     */
    String IGNORE_UNORDER_LIST_5 = SyntaxKey.KEY_TODO_DONE_3;
    //----------  UnOrderListSyntax  ----------

}
