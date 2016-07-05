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
import android.os.Parcel;
import android.text.Layout;
import android.text.style.QuoteSpan;

/**
 * code grammar span
 * <p>
 * Created by yuyidong on 16/5/17.
 */
public class MDCodeSpan extends QuoteSpan {
    private static final int GAP_WIDTH_PLUS = 15;

    private final int mColor;

    /**
     * Constructor
     */
    public MDCodeSpan() {
        super();
        mColor = 0xff0000ff;
    }

    /**
     * Constructor
     *
     * @param color {@link QuoteSpan}
     */
    public MDCodeSpan(int color) {
        super(color);
        mColor = color;
    }

    /**
     * Constructor
     *
     * @param src {@link QuoteSpan}
     */
    public MDCodeSpan(Parcel src) {
        super(src);
        mColor = src.readInt();
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return super.getLeadingMargin(first) + GAP_WIDTH_PLUS;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                  int top, int baseline, int bottom,
                                  CharSequence text, int start, int end, boolean first, Layout layout) {
        Paint.Style style = p.getStyle();
        int color = p.getColor();

        p.setStyle(Paint.Style.FILL);
        p.setColor(mColor);

        c.drawRect(x, top, x + layout.getWidth(), bottom, p);

        p.setStyle(style);
        p.setColor(color);
    }
}
