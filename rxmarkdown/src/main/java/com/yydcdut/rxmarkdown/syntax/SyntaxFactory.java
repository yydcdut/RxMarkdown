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
package com.yydcdut.rxmarkdown.syntax;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.RxMDConfiguration;

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
     * @param rxMDConfiguration configuration, need HorizontalRulesColor
     * @return the interface {@link Syntax} for horizontal rules syntax
     */
    Syntax getHorizontalRulesSyntax(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get block quotes syntax.
     * syntax:
     * "&gt; "
     *
     * @param rxMDConfiguration configuration, need BlockQuotesColor
     * @return the interface {@link Syntax} for block quotes syntax
     */
    Syntax getBlockQuotesSyntax(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get to do syntax.
     * syntax:
     * "- [] "
     *
     * @param rxMDConfiguration configuration, need TodoColor
     * @return the interface {@link Syntax} for to do syntax
     */
    Syntax getTodoSyntax(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get to do for "done" syntax.
     * syntax:
     * "- [x] "
     * <p>
     * "- [X] "
     *
     * @param rxMDConfiguration configuration, need TodoDoneColor
     * @return the interface {@link Syntax} for to do for "done" syntax
     */
    Syntax getTodoDoneSyntax(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get order list syntax.
     * syntax:
     * "1. "
     *
     * @param rxMDConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for to do for "done" syntax
     */
    Syntax getOrderListSyntax(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get unorder list syntax.
     * syntax:
     * "* "
     * <p>
     * "+ "
     * <p>
     * "- "
     *
     * @param rxMDConfiguration configuration, need UnOrderListColor
     * @return the interface {@link Syntax} for unorder list syntax
     */
    Syntax getUnOrderListSyntax(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get center align syntax.
     * It's not the real syntax in Markdown.
     * syntax:
     * "[content]"
     *
     * @param rxMDConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for center align syntax
     */
    Syntax getCenterAlignSyntax(@NonNull RxMDConfiguration rxMDConfiguration);

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
     * @param rxMDConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for header syntax
     */
    Syntax getHeaderSyntax(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get bold syntax.
     * syntax:
     * "**content**"
     * "__content__"
     *
     * @param rxMDConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for bold syntax
     */
    Syntax getBoldSyntax(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get italic syntax.
     * syntax:
     * "*content*"
     *
     * @param rxMDConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for bold syntax
     */
    Syntax getItalicSyntax(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get inline code syntax.
     * syntax:
     * "`content`"
     *
     * @param rxMDConfiguration configuration, need InlineCodeBgColor
     * @return the interface {@link Syntax} for inline code syntax
     */
    Syntax getInlineCodeSyntax(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get strike through syntax.
     * syntax:
     * "~~content~~"
     *
     * @param rxMDConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for strike through syntax
     */
    Syntax getStrikeThroughSyntax(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get footnote syntax.
     * syntax:
     * "content[^footnote]"
     *
     * @param rxMDConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for footnote syntax
     */
    Syntax getFootnoteSyntax(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get image syntax.
     * syntax:
     * "![image](http://image.jpg)"
     *
     * @param rxMDConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for image syntax
     */
    Syntax getImageSyntax(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get hyper link syntax.
     * syntax:
     * "[content](http://link.html)"
     *
     * @param rxMDConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for hyper link syntax
     */
    Syntax getHyperLinkSyntax(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get code syntax.
     * syntax:
     * "```
     * content
     * ```"
     *
     * @param rxMDConfiguration configuration, need CodeBgColor
     * @return the interface {@link Syntax} for code syntax
     */
    Syntax getCodeSyntax(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get back slash syntax.
     * syntax:
     * "\"
     *
     * @param rxMDConfiguration configuration, need CodeBgColor
     * @return the interface {@link Syntax} for back slash syntax
     */
    Syntax getBackslashSyntax(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * parse content
     *
     * @param charSequence      content
     * @param rxMDConfiguration configuration
     * @return the content after parsing
     */
    @NonNull
    CharSequence parse(@NonNull CharSequence charSequence, @NonNull RxMDConfiguration rxMDConfiguration);
}
