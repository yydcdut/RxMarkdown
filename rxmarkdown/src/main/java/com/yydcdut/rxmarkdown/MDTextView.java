package com.yydcdut.rxmarkdown;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yydcdut.rxmarkdown.span.CustomImageSpan;

/**
 * Created by yuyidong on 16/5/16.
 */
public class MDTextView extends TextView {

    private boolean mHasImageInText;

    public MDTextView(Context context) {
        super(context);
    }

    public MDTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MDTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MDTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (mHasImageInText) {
            onDetach();
            mHasImageInText = false;
        }
        if (text instanceof Spanned) {
            CustomImageSpan[] spans = ((Spanned) text).getSpans(0, text.length(), CustomImageSpan.class);
            mHasImageInText = spans.length > 0;
            for (CustomImageSpan image : spans) {
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
        CustomImageSpan[] images = getImages();
        for (CustomImageSpan image : images) {
            Drawable drawable = image.getDrawable();
            if (drawable != null) {
                unscheduleDrawable(drawable);
            }
            image.onDetach();
        }
    }

    private CustomImageSpan[] getImages() {
        if (mHasImageInText && length() > 0) {
            return ((Spanned) getText()).getSpans(0, length(), CustomImageSpan.class);
        }
        return new CustomImageSpan[0];
    }
}