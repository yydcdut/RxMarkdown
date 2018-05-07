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
package com.yydcdut.markdown.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.QuoteSpan;

/**
 * block quote syntax span
 * <p>
 * Created by yuyidong on 16/5/15.
 */
public class MDQuoteSpan extends QuoteSpan {

    private static final int QUOTE_WIDTH_PLUS = 2;

    private int mNested = 1;

    /**
     * Constructor
     *
     * @param color  {@link QuoteSpan}
     * @param nested the nested number
     */
    public MDQuoteSpan(int color, int nested) {
        super(color);
        mNested = nested;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom, CharSequence text, int start, int end, boolean first, Layout layout) {
        float length = p.measureText("  ");
        for (int i = 1; i <= mNested; i++) {
            super.drawLeadingMargin(c, p, (int) (x + i * length), dir + QUOTE_WIDTH_PLUS, top, baseline, bottom, text, start, end, first, layout);
        }
    }
}
