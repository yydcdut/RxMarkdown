/*
 * Copyright (C) 2017 yydcdut (yuyidong2015@gmail.com)
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

import android.support.annotation.NonNull;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.MarkdownEditText;

import java.util.ArrayList;

/**
 * Created by yuyidong on 2017/6/7.
 */
public class LivePrepare {
    private boolean isConfig;
    private ArrayList<IEditLive> mEditControllerList;

    public LivePrepare(MarkdownEditText MarkdownEditText, MarkdownEditText.EditTextWatcher editTextWatcher) {
        prepare(MarkdownEditText, editTextWatcher);
    }

    private void prepare(MarkdownEditText MarkdownEditText, MarkdownEditText.EditTextWatcher editTextWatcher) {
        mEditControllerList = new ArrayList<>();
        mEditControllerList.add(new BlockQuotesLive());
        mEditControllerList.add(new StyleLive());
        mEditControllerList.add(new CenterAlignLive());
        mEditControllerList.add(new HeaderLive());
        mEditControllerList.add(new HorizontalRulesLive(MarkdownEditText));
        mEditControllerList.add(new CodeLive());
        mEditControllerList.add(new StrikeThroughLive());
        mEditControllerList.add(new ListLive(MarkdownEditText, editTextWatcher));
        mEditControllerList.add(new CodeBlockLive());
    }

    public void config(@NonNull MarkdownConfiguration markdownConfiguration) {
        if (markdownConfiguration != null) {
            isConfig = true;
        }
        for (IEditLive controller : mEditControllerList) {
            controller.setMarkdownConfiguration(markdownConfiguration);
        }
    }

    public void onSelectionChanged(int selStart, int selEnd) {
        if (!isConfig) {
            return;
        }
        for (IEditLive iEditLive : mEditControllerList) {
            iEditLive.onSelectionChanged(selStart, selEnd);
        }
    }

    public void beforeTextChanged(CharSequence s, int start, int before, int after) {
        if (!isConfig) {
            return;
        }
        for (IEditLive iEditLive : mEditControllerList) {
            iEditLive.beforeTextChanged(s, start, before, after);
        }
    }

    public void onTextChanged(CharSequence s, int start, int before, int after) {
        if (!isConfig) {
            return;
        }
        for (IEditLive iEditLive : mEditControllerList) {
            iEditLive.onTextChanged(s, start, before, after);
        }
    }

}
