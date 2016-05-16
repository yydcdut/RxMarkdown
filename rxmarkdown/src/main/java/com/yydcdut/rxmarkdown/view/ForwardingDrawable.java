package com.yydcdut.rxmarkdown.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by yuyidong on 16/5/16.
 */
public class ForwardingDrawable extends Drawable implements Drawable.Callback {
    private Drawable mCurrentDelegate;

    public ForwardingDrawable(Drawable drawable) {
        mCurrentDelegate = drawable;
    }

    @Override
    public void draw(Canvas canvas) {
        mCurrentDelegate.draw(canvas);
    }

    @Override
    public void setAlpha(int alpha) {
        mCurrentDelegate.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mCurrentDelegate.setColorFilter(colorFilter);
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        super.setVisible(visible, restart);
        return mCurrentDelegate.setVisible(visible, restart);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        mCurrentDelegate.setBounds(bounds);
    }

    @Override
    public boolean isStateful() {
        return mCurrentDelegate.isStateful();
    }

    @Override
    protected boolean onStateChange(int[] state) {
        return mCurrentDelegate.setState(state);
    }

    @Override
    protected boolean onLevelChange(int level) {
        return mCurrentDelegate.setLevel(level);
    }

    @Override
    public int getOpacity() {
        return mCurrentDelegate.getOpacity();
    }

    public Drawable setCurrent(Drawable newDelegate) {
        Drawable previousDelegate = setCurrentWithoutInvalidate(newDelegate);
        invalidateSelf();
        return previousDelegate;
    }

    protected Drawable setCurrentWithoutInvalidate(Drawable newDelegate) {
        Drawable previousDelegate = mCurrentDelegate;
        copyProperties(newDelegate, previousDelegate);
        mCurrentDelegate = newDelegate;
        return previousDelegate;
    }

    public static void copyProperties(Drawable to, Drawable from) {
        if (from == null || to == null || to == from) {
            return;
        }
        to.setBounds(from.getBounds());
        to.setChangingConfigurations(from.getChangingConfigurations());
        to.setLevel(from.getLevel());
        to.setVisible(from.isVisible(), false);
        to.setState(from.getState());
    }

    @Override
    public int getIntrinsicWidth() {
        return mCurrentDelegate.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mCurrentDelegate.getIntrinsicHeight();
    }

    @Override
    public boolean getPadding(Rect padding) {
        return mCurrentDelegate.getPadding(padding);
    }

    @Override
    public Drawable mutate() {
        mCurrentDelegate.mutate();
        return this;
    }

    @Override
    public Drawable getCurrent() {
        return mCurrentDelegate;
    }

    @Override
    public void invalidateDrawable(Drawable who) {
        invalidateSelf();
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        scheduleSelf(what, when);
    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {
        unscheduleSelf(what);
    }
}
