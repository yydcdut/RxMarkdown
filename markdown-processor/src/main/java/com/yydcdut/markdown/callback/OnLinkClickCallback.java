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
package com.yydcdut.markdown.callback;

import android.view.View;

/**
 * the callback of link syntax.
 * <p>
 * Created by yuyidong on 16/7/28.
 */
public interface OnLinkClickCallback {
    /**
     * the click listener callback
     *
     * @param view the view
     * @param link the url link
     */
    void onLinkClicked(View view, String link);
}
