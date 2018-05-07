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
package com.yydcdut.markdown.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Copy some useful methods from project -- fresco (https://github.com/facebook/fresco) [com.facebook.drawee.drawable.ForwardingDrawable]
 * As the same time, thanks to BiliBili for drawee-text-view(https://github.com/Bilibili/drawee-text-view).
 * <p>
 * Created by yuyidong on 16/5/16.
 */
public class ForwardingDrawable extends Drawable implements Drawable.Callback {
    private Drawable mCurrentDelegate;

    /**
     * Constructor
     *
     * @param drawable first drawable, hold place drawable
     */
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

    private Drawable setCurrentWithoutInvalidate(Drawable newDelegate) {
        Drawable previousDelegate = mCurrentDelegate;
        copyProperties(newDelegate, previousDelegate);
        mCurrentDelegate = newDelegate;
        return previousDelegate;
    }

    public void refreshCurrent(Drawable newDelegate) {
        if (newDelegate == null || mCurrentDelegate == null || newDelegate == mCurrentDelegate) {
            return;
        }
        Drawable previousDelegate = mCurrentDelegate;
        copyProperties(previousDelegate, newDelegate);
        mCurrentDelegate = newDelegate;
        invalidateSelf();
    }

    private static void copyProperties(Drawable to, Drawable from) {
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
