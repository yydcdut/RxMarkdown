/*
 * Copyright (C) 2017 yydcdut (yuyidong2015@gmail.com)
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
package com.yydcdut.rxmarkdown.theme;

import android.support.annotation.ColorInt;

/**
 * The  theme for code syntax, high light programming language
 * <p>
 * Created by yuyidong on 2017/5/27.
 */
public interface Theme {
    String CODE_TYP = "typ";
    String CODE_KWD = "kwd";
    String CODE_LIT = "lit";
    String CODE_COM = "com";
    String CODE_STR = "str";
    String CODE_PUN = "pun";
    String CODE_TAG = "tag";
    String CODE_PLN = "pln";
    String CODE_DEC = "dec";
    String CODE_ATN = "atn";
    String CODE_ATV = "atv";
    String CODE_OPN = "opn";
    String CODE_CLO = "clo";
    String CODE_VAR = "var";
    String CODE_FUN = "fun";
    String CODE_NOCODE = "nocode";

    /**
     * background color
     *
     * @return color
     */
    @ColorInt
    int getBackgroundColor();

    /**
     * color for type
     *
     * @return color
     */
    @ColorInt
    int getTypeColor();

    /**
     * color for keyword
     *
     * @return color
     */
    @ColorInt
    int getKeyWordColor();

    /**
     * color for literal
     *
     * @return color
     */
    @ColorInt
    int getLiteralColor();

    /**
     * color for comment
     *
     * @return color
     */
    @ColorInt
    int getCommentColor();

    /**
     * color for string
     *
     * @return color
     */
    @ColorInt
    int getStringColor();

    /**
     * color for punctuation
     *
     * @return color
     */
    @ColorInt
    int getPunctuationColor();

    /**
     * color for html/xml tag
     *
     * @return color
     */
    @ColorInt
    int getTagColor();

    /**
     * color for a plain text
     *
     * @return color
     */
    @ColorInt
    int getPlainTextColor();

    /**
     * color for a markup declaration such as a DOCTYPE
     *
     * @return color
     */
    @ColorInt
    int getDecimalColor();

    /**
     * color for html/xml attribute name
     *
     * @return color
     */
    @ColorInt
    int getAttributeNameColor();

    /**
     * color for html/xml attribute value
     *
     * @return color
     */
    @ColorInt
    int getAttributeValueColor();

    /**
     * color for opn
     *
     * @return color
     */
    @ColorInt
    int getOpnColor();

    /**
     * color for clo
     *
     * @return color
     */
    @ColorInt
    int getCloColor();

    /**
     * color for var
     *
     * @return color
     */
    @ColorInt
    int getVarColor();

    /**
     * color for fun
     *
     * @return color
     */
    @ColorInt
    int getFunColor();

    /**
     * A class that indicates a section of markup that is not code, e.g. to allow
     * embedding of line numbers within code listings.
     *
     * @return color
     */
    @ColorInt
    int getNocodeColor();
}
