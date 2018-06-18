/*
 * Copyright (C) 2016 yydcdut (yuyidong2015@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.yydcdut.markdown.syntax;

import android.support.annotation.NonNull;

import com.yydcdut.markdown.MarkdownConfiguration;

/**
 * To provide syntax and parse content.
 * <p>
 * Created by yuyidong on 16/5/12.
 */
public interface SyntaxFactory {

    /**
     * Get horizontal rules syntax.
     * syntax:
     * "***"
     * <p>
     * "---"
     * <p>
     * "***********"
     * <p>
     * "----------------"
     *
     * @param markdownConfiguration configuration, need HorizontalRulesColor
     * @return the interface {@link Syntax} for horizontal rules syntax
     */
    Syntax getHorizontalRulesSyntax(@NonNull MarkdownConfiguration markdownConfiguration);

    /**
     * Get block quotes syntax.
     * syntax:
     * "&gt; "
     *
     * @param markdownConfiguration configuration, need BlockQuotesColor
     * @return the interface {@link Syntax} for block quotes syntax
     */
    Syntax getBlockQuotesSyntax(@NonNull MarkdownConfiguration markdownConfiguration);

    /**
     * Get to do syntax.
     * syntax:
     * "- [] "
     *
     * @param markdownConfiguration configuration, need TodoColor
     * @return the interface {@link Syntax} for to do syntax
     */
    Syntax getTodoSyntax(@NonNull MarkdownConfiguration markdownConfiguration);

    /**
     * Get to do for "done" syntax.
     * syntax:
     * "- [x] "
     * <p>
     * "- [X] "
     *
     * @param markdownConfiguration configuration, need TodoDoneColor
     * @return the interface {@link Syntax} for to do for "done" syntax
     */
    Syntax getTodoDoneSyntax(@NonNull MarkdownConfiguration markdownConfiguration);

    /**
     * Get order list syntax.
     * syntax:
     * "1. "
     *
     * @param markdownConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for to do for "done" syntax
     */
    Syntax getOrderListSyntax(@NonNull MarkdownConfiguration markdownConfiguration);

    /**
     * Get unorder list syntax.
     * syntax:
     * "* "
     * <p>
     * "+ "
     * <p>
     * "- "
     *
     * @param markdownConfiguration configuration, need UnOrderListColor
     * @return the interface {@link Syntax} for unorder list syntax
     */
    Syntax getUnOrderListSyntax(@NonNull MarkdownConfiguration markdownConfiguration);

    /**
     * Get list syntax(including order and unorder).
     * syntax:
     * "* "
     * <p>
     * "+ "
     * <p>
     * "- "
     * OR
     * "1. "
     *
     * @param markdownConfiguration configuration, need UnOrderListColor
     * @return the interface {@link Syntax} for  list syntax
     */
    Syntax getListSyntax(@NonNull MarkdownConfiguration markdownConfiguration);

    /**
     * Get center align syntax.
     * It's not the real syntax in Markdown.
     * syntax:
     * "[content]"
     *
     * @param markdownConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for center align syntax
     */
    Syntax getCenterAlignSyntax(@NonNull MarkdownConfiguration markdownConfiguration);

    /**
     * Get header syntax.
     * syntax:
     * "# " for h1
     * <p>
     * "## " for h2
     * <p>
     * "### " for h3
     * <p>
     * "#### " for h4
     * <p>
     * "##### " for h5
     * <p>
     * "###### " for h6
     *
     * @param markdownConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for header syntax
     */
    Syntax getHeaderSyntax(@NonNull MarkdownConfiguration markdownConfiguration);

    /**
     * Get bold syntax.
     * syntax:
     * "**content**"
     * "__content__"
     *
     * @param markdownConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for bold syntax
     */
    Syntax getBoldSyntax(@NonNull MarkdownConfiguration markdownConfiguration);

    /**
     * Get italic syntax.
     * syntax:
     * "*content*"
     *
     * @param markdownConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for bold syntax
     */
    Syntax getItalicSyntax(@NonNull MarkdownConfiguration markdownConfiguration);

    /**
     * Get inline code syntax.
     * syntax:
     * "`content`"
     *
     * @param markdownConfiguration configuration, need InlineCodeBgColor
     * @return the interface {@link Syntax} for inline code syntax
     */
    Syntax getCodeSyntax(@NonNull MarkdownConfiguration markdownConfiguration);

    /**
     * Get strike through syntax.
     * syntax:
     * "~~content~~"
     *
     * @param markdownConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for strike through syntax
     */
    Syntax getStrikeThroughSyntax(@NonNull MarkdownConfiguration markdownConfiguration);

    /**
     * Get footnote syntax.
     * syntax:
     * "content[^footnote]"
     *
     * @param markdownConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for footnote syntax
     */
    Syntax getFootnoteSyntax(@NonNull MarkdownConfiguration markdownConfiguration);

    /**
     * Get image syntax.
     * syntax:
     * "![image](http://image.jpg)"
     *
     * @param markdownConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for image syntax
     */
    Syntax getImageSyntax(@NonNull MarkdownConfiguration markdownConfiguration);

    /**
     * Get hyper link syntax.
     * syntax:
     * "[content](http://link.html)"
     *
     * @param markdownConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for hyper link syntax
     */
    Syntax getHyperLinkSyntax(@NonNull MarkdownConfiguration markdownConfiguration);

    /**
     * Get code syntax.
     * syntax:
     * "```
     * content
     * ```"
     *
     * @param markdownConfiguration configuration, need CodeBgColor
     * @return the interface {@link Syntax} for code syntax
     */
    Syntax getCodeBlockSyntax(@NonNull MarkdownConfiguration markdownConfiguration);

    /**
     * Get back slash syntax.
     * syntax:
     * "\"
     *
     * @param markdownConfiguration configuration, need CodeBgColor
     * @return the interface {@link Syntax} for back slash syntax
     */
    Syntax getBackslashSyntax(@NonNull MarkdownConfiguration markdownConfiguration);

    /**
     * parse content
     *
     * @param charSequence          content
     * @param markdownConfiguration configuration
     * @return the content after parsing
     */
    @NonNull
    CharSequence parse(@NonNull CharSequence charSequence, @NonNull MarkdownConfiguration markdownConfiguration);
}
