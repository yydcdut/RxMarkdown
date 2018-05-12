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
package com.yydcdut.markdown.theme;

import android.graphics.Color;

/**
 * The default theme for code syntax, high light programming language
 * <p>
 * Created by yuyidong on 2017/5/27.
 */
public class ThemeDefault implements Theme {

    @Override
    public int getBackgroundColor() {
        return 0xffcccccc;
    }

    @Override
    public int getTypeColor() {
        return 0xff660066;
    }

    @Override
    public int getKeyWordColor() {
        return 0xff000088;
    }

    @Override
    public int getLiteralColor() {
        return 0xff006666;
    }

    @Override
    public int getCommentColor() {
        return 0xff880000;
    }

    @Override
    public int getStringColor() {
        return 0xff008800;
    }

    @Override
    public int getPunctuationColor() {
        return 0xff666600;
    }

    @Override
    public int getTagColor() {
        return 0xff000088;
    }

    @Override
    public int getPlainTextColor() {
        return 0xff000000;
    }

    @Override
    public int getDecimalColor() {
        return 0xff000000;
    }

    @Override
    public int getAttributeNameColor() {
        return 0xff660066;
    }

    @Override
    public int getAttributeValueColor() {
        return 0xff008800;
    }

    @Override
    public int getOpnColor() {
        return 0xff666600;
    }

    @Override
    public int getCloColor() {
        return 0xff666600;
    }

    @Override
    public int getVarColor() {
        return 0xff660066;
    }

    @Override
    public int getFunColor() {
        return Color.RED;
    }

    @Override
    public int getNocodeColor() {
        return 0xff000000;
    }

    @Override
    public int getIndentedSize() {
        return 30;
    }
}
