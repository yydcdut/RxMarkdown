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
package com.yydcdut.markdown;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yydcdut.markdown.span.MDImageSpan;

/**
 * RxMDTextView cooperate with com.yydcdut.rxmarkdown.grammar.android.ImageGrammar.
 * <p>
 * Created by yuyidong on 16/5/16.
 */
public class MarkdownTextView extends TextView {

    private boolean mHasImageInText;

    /**
     * Constructor
     *
     * @param context {@link TextView}
     */
    public MarkdownTextView(Context context) {
        super(context);
    }

    /**
     * Constructor
     *
     * @param context {@link TextView}
     * @param attrs   {@link TextView}
     */
    public MarkdownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor
     *
     * @param context      {@link TextView}
     * @param attrs        {@link TextView}
     * @param defStyleAttr {@link TextView}
     */
    public MarkdownTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (mHasImageInText) {
            onDetach();
            mHasImageInText = false;
        }
        if (text instanceof Spanned) {
            MDImageSpan[] spans = ((Spanned) text).getSpans(0, text.length(), MDImageSpan.class);
            mHasImageInText = spans.length > 0;
            for (MDImageSpan image : spans) {
                image.onAttach(this);
            }
        }
        super.setText(text, type);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onDetach();
    }


    @Override
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        onDetach();
    }


    @Override
    public void invalidateDrawable(Drawable dr) {
        if (mHasImageInText) {
            invalidate();
        } else {
            super.invalidateDrawable(dr);
        }
    }

    final void onDetach() {
        MDImageSpan[] images = getImages();
        for (MDImageSpan image : images) {
            Drawable drawable = image.getDrawable();
            if (drawable != null) {
                unscheduleDrawable(drawable);
            }
            image.onDetach();
        }
    }

    private MDImageSpan[] getImages() {
        if (mHasImageInText && length() > 0) {
            return ((Spanned) getText()).getSpans(0, length(), MDImageSpan.class);
        }
        return new MDImageSpan[0];
    }
}