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

/**
 * Son of Obsidian theme.
 * <p>
 * Created by yuyidong on 2017/6/15.
 */
public class ThemeSonsOfObsidian implements Theme {
    @Override
    public int getBackgroundColor() {
        return 0xff000000;
    }

    @Override
    public int getTypeColor() {
        return 0xff678CB1;
    }

    @Override
    public int getKeyWordColor() {
        return 0xff93C763;
    }

    @Override
    public int getLiteralColor() {
        return 0xffFACD22;
    }

    @Override
    public int getCommentColor() {
        return 0xff66747B;
    }

    @Override
    public int getStringColor() {
        return 0xffEC7600;
    }

    @Override
    public int getPunctuationColor() {
        return 0xffF1F2F3;
    }

    @Override
    public int getTagColor() {
        return 0xff8AC763;
    }

    @Override
    public int getPlainTextColor() {
        return 0xffF1F2F3;
    }

    @Override
    public int getDecimalColor() {
        return 0xff800080;
    }

    @Override
    public int getAttributeNameColor() {
        return 0xffE0E2E4;
    }

    @Override
    public int getAttributeValueColor() {
        return 0xffEC7600;
    }

    @Override
    public int getOpnColor() {
        return getPlainTextColor();
    }

    @Override
    public int getCloColor() {
        return getPlainTextColor();
    }

    @Override
    public int getVarColor() {
        return getPlainTextColor();
    }

    @Override
    public int getFunColor() {
        return getPlainTextColor();
    }

    @Override
    public int getNocodeColor() {
        return getPlainTextColor();
    }
}
