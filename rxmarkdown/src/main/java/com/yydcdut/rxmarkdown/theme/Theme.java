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
     * color for typ style
     *
     * @return color
     */
    @ColorInt
    int getTypColor();

    /**
     * color for kwd style
     *
     * @return color
     */
    @ColorInt
    int getKwdColor();

    /**
     * color for lit style
     *
     * @return color
     */
    @ColorInt
    int getLitColor();

    /**
     * color for com style
     *
     * @return color
     */
    @ColorInt
    int getComColor();

    /**
     * color for str style
     *
     * @return color
     */
    @ColorInt
    int getStrColor();

    /**
     * color for pun style
     *
     * @return color
     */
    @ColorInt
    int getPunColor();

    /**
     * color for tag style
     *
     * @return color
     */
    @ColorInt
    int getTagColor();

    /**
     * color for pln style
     *
     * @return color
     */
    @ColorInt
    int getPlnColor();

    /**
     * color for dec style
     *
     * @return color
     */
    @ColorInt
    int getDecColor();

    /**
     * color for atn style
     *
     * @return color
     */
    @ColorInt
    int getAtnColor();

    /**
     * color for atv style
     *
     * @return color
     */
    @ColorInt
    int getAtvColor();

    /**
     * color for opn style
     *
     * @return color
     */
    @ColorInt
    int getOpnColor();

    /**
     * color for clo style
     *
     * @return color
     */
    @ColorInt
    int getCloColor();

    /**
     * color for var style
     *
     * @return color
     */
    @ColorInt
    int getVarColor();

    /**
     * color for fun style
     *
     * @return color
     */
    @ColorInt
    int getFunColor();

    /**
     * color for nocode style
     *
     * @return color
     */
    @ColorInt
    int getNocodeColor();
}
