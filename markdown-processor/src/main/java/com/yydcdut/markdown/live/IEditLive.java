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
package com.yydcdut.markdown.live;

import android.support.annotation.Nullable;

import com.yydcdut.markdown.MarkdownConfiguration;

/**
 * the interface of edit controller.
 * <p>
 * Created by yuyidong on 16/7/21.
 */
public interface IEditLive {

    /**
     * set configuration
     *
     * @param markdownConfiguration RxMDConfiguration
     */
    void setMarkdownConfiguration(@Nullable MarkdownConfiguration markdownConfiguration);

    /**
     * same with {@link android.text.TextWatcher#beforeTextChanged(CharSequence, int, int, int)}
     * invoke when beforeTextChanged
     *
     * @param s      CharSequence
     * @param start  int
     * @param before int
     * @param after  int
     */
    void beforeTextChanged(CharSequence s, int start, int before, int after);

    /**
     * same with {@link android.text.TextWatcher#onTextChanged(CharSequence, int, int, int)}
     * invoke when onTextChanged
     *
     * @param s      CharSequence
     * @param start  int
     * @param before int
     * @param after  int
     */
    void onTextChanged(CharSequence s, int start, int before, int after);

    /**
     * same with {@link android.widget.EditText#onSelectionChanged(int, int)}
     * invoke when onSelectionChanged
     *
     * @param selStart the start of selection
     * @param selEnd   the end of selection
     */
    void onSelectionChanged(int selStart, int selEnd);
}
