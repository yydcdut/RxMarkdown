package com.yydcdut.rxmarkdown;

/**
 * Created by yuyidong on 16/6/28.
 */

public interface Grammar {
    String KEY_BOLD = "**";

    String KEY_BACKSLASH = "\\";

    String KEY_BLOCK_QUOTES = "> ";

    String KEY_0_CENTER_ALIGN = "[";
    String KEY_1_CENTER_ALIGN = "]";

    String KEY_CODE = "```";

    String KEY_0_FOOTNOTE = "[^";
    String KEY_1_FOOTNOTE = "]";

    String KEY_0_HEADER = "# ";
    String KEY_1_HEADER = "## ";
    String KEY_2_HEADER = "### ";
    String KEY_3_HEADER = "#### ";
    String KEY_4_HEADER = "##### ";
    String KEY_5_HEADER = "###### ";

    String KEY_0_HORIZONTAL_RULES = "***";
    String KEY_1_HORIZONTAL_RULES = "---";

    String KEY_0_HYPER_LINK = "[";
    String KEY_1_HYPER_LINK = "](";
    String KEY_2_HYPER_LINK = ")";

    String KEY_0_IMAGE = "![";
    String KEY_1_IMAGE = "](";
    String KEY_2_IMAGE = ")";

    String KEY_INLINE_CODE = "`";

    String KEY_ITALIC = "*";

    String KEY_STRIKE_THROUGH = "~~";

    String KEY_0_TODO_DONE = "- [x] ";
    String KEY_1_TODO_DONE = "- [X] ";

    String KEY_TODO = "- [ ] ";

    String KEY_0_UNORDER_LIST = "* ";
    String KEY_1_UNORDER_LIST = "+ ";
    String KEY_2_UNORDER_LIST = "- ";

}
