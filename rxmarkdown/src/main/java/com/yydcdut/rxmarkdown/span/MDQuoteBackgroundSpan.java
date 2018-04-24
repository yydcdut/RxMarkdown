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
package com.yydcdut.rxmarkdown.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.style.LineBackgroundSpan;

import com.yydcdut.rxmarkdown.callback.BlockquoteBackgroundNestedColorFetcher;

/**
 * nested quote background grammar span
 */
public class MDQuoteBackgroundSpan implements LineBackgroundSpan {
    private final int nestingLevel;
    private final int nestingMargin;
    //    private final BlockquoteBackgroundNestedColorFetcher colorFetcher;
    private Rect rect = new Rect();

    public MDQuoteBackgroundSpan(int nestingLevel, int nestingMargin, BlockquoteBackgroundNestedColorFetcher colorFetcher) {
        this.nestingLevel = nestingLevel;
        this.nestingMargin = nestingMargin;
//        this.colorFetcher = colorFetcher;
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        int paintColor = p.getColor();
        for (int i = 0; i < nestingLevel; i++) {
//            p.setColor(colorFetcher.fetchBackgroundColorForNestingLevel(i));
            rect.set(left + (i * nestingMargin), top, (i == nestingLevel - 1 ? right : left + ((i + 1) * nestingMargin)), bottom);
            c.drawRect(rect, p);
        }
        p.setColor(paintColor);
    }
}
