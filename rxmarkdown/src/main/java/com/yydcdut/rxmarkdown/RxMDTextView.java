package com.yydcdut.rxmarkdown;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yydcdut.rxmarkdown.span.MDImageSpan;

/**
 * Created by yuyidong on 16/5/16.
 */
public class RxMDTextView extends TextView {

    private boolean mHasImageInText;

    public RxMDTextView(Context context) {
        super(context);
    }

    public RxMDTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RxMDTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RxMDTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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