/*
 * Copyright (C) 2018 yydcdut (yuyidong2015@gmail.com)
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

import android.text.style.BulletSpan;

/**
 * Created by yuyidong on 2018/5/28.
 */
public abstract class MDBaseListSpan extends BulletSpan {

    protected int mNested;
    protected MDBaseListSpan mParent;

    public MDBaseListSpan(int gapWidth, int color) {
        super(gapWidth, color);
    }

    /**
     * get list nested
     *
     * @return the nested number
     */
    public int getNested() {
        return mNested;
    }

    /**
     * get parent base list span
     *
     * @return {@link MDBaseListSpan}
     */
    public MDBaseListSpan getParent() {
        return mParent;
    }

    /**
     * set parent base list span
     *
     * @param parent {@link MDBaseListSpan}
     */
    public void setParent(MDBaseListSpan parent) {
        mParent = parent;
    }
}
