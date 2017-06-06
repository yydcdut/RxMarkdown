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
 * Abstract factory pattern.
 * To provide grammars and parse content.
 * <p>
 * Created by yuyidong on 16/5/12.
 */
public interface SyntaxFactory {

    /**
     * Get horizontal rules grammar.
     * Grammar:
     * "***"
     * <p>
     * "---"
     * <p>
     * "***********"
     * <p>
     * "----------------"
     *
     * @param rxMDConfiguration configuration, need HorizontalRulesColor
     * @return the interface {@link Syntax} for horizontal rules grammar
     */
    Syntax getHorizontalRulesGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get block quotes grammar.
     * Grammar:
     * "&gt; "
     *
     * @param rxMDConfiguration configuration, need BlockQuotesColor
     * @return the interface {@link Syntax} for block quotes grammar
     */
    Syntax getBlockQuotesGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get to do grammar.
     * Grammar:
     * "- [] "
     *
     * @param rxMDConfiguration configuration, need TodoColor
     * @return the interface {@link Syntax} for to do grammar
     */
    Syntax getTodoGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get to do for "done" grammar.
     * Grammar:
     * "- [x] "
     * <p>
     * "- [X] "
     *
     * @param rxMDConfiguration configuration, need TodoDoneColor
     * @return the interface {@link Syntax} for to do for "done" grammar
     */
    Syntax getTodoDoneGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get order list grammar.
     * Grammar:
     * "1. "
     *
     * @param rxMDConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for to do for "done" grammar
     */
    Syntax getOrderListGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get unorder list grammar.
     * Grammar:
     * "* "
     * <p>
     * "+ "
     * <p>
     * "- "
     *
     * @param rxMDConfiguration configuration, need UnOrderListColor
     * @return the interface {@link Syntax} for unorder list grammar
     */
    Syntax getUnOrderListGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get center align grammar.
     * It's not the real grammar in Markdown.
     * Grammar:
     * "[content]"
     *
     * @param rxMDConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for center align grammar
     */
    Syntax getCenterAlignGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get header grammar.
     * Grammar:
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
     * @return the interface {@link Syntax} for header grammar
     */
    Syntax getHeaderGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get bold grammar.
     * Grammar:
     * "**content**"
     * "__content__"
     *
     * @param rxMDConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for bold grammar
     */
    Syntax getBoldGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get italic grammar.
     * Grammar:
     * "*content*"
     *
     * @param rxMDConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for bold grammar
     */
    Syntax getItalicGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get inline code grammar.
     * Grammar:
     * "`content`"
     *
     * @param rxMDConfiguration configuration, need InlineCodeBgColor
     * @return the interface {@link Syntax} for inline code grammar
     */
    Syntax getInlineCodeGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get strike through grammar.
     * Grammar:
     * "~~content~~"
     *
     * @param rxMDConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for strike through grammar
     */
    Syntax getStrikeThroughGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get footnote grammar.
     * Grammar:
     * "content[^footnote]"
     *
     * @param rxMDConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for footnote grammar
     */
    Syntax getFootnoteGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get image grammar.
     * Grammar:
     * "![image](http://image.jpg)"
     *
     * @param rxMDConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for image grammar
     */
    Syntax getImageGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get hyper link grammar.
     * Grammar:
     * "[content](http://link.html)"
     *
     * @param rxMDConfiguration configuration, need nothing
     * @return the interface {@link Syntax} for hyper link grammar
     */
    Syntax getHyperLinkGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get code grammar.
     * Grammar:
     * "```
     * content
     * ```"
     *
     * @param rxMDConfiguration configuration, need CodeBgColor
     * @return the interface {@link Syntax} for code grammar
     */
    Syntax getCodeGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

    /**
     * Get back slash grammar.
     * Grammar:
     * "\"
     *
     * @param rxMDConfiguration configuration, need CodeBgColor
     * @return the interface {@link Syntax} for back slash grammar
     */
    Syntax getBackslashGrammar(@NonNull RxMDConfiguration rxMDConfiguration);

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
