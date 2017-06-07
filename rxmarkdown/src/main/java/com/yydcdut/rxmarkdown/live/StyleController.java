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
package com.yydcdut.rxmarkdown.live;

import android.text.Editable;
import android.text.style.StyleSpan;

import com.yydcdut.rxmarkdown.syntax.edit.EditFactory;
import com.yydcdut.rxmarkdown.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * RxMDEditText, bold and italic controller.
 * <p>
 * Created by yuyidong on 16/7/21.
 */
public class StyleController extends AbsEditController {

    private static final String KEY = "*";
    private static final String KEY_1 = "_";

    @Override
    public void beforeTextChanged(CharSequence s, int start, int before, int after) {
        super.beforeTextChanged(s, start, before, after);
        if (before == 0 || mRxMDConfiguration == null) {
            return;
        }
        String deleteString = s.subSequence(start, start + before).toString();
        String beforeString = null;
        String afterString = null;
        if (start > 0) {
            beforeString = s.subSequence(start - 1, start).toString();
        }
        if (start + before + 1 <= s.length()) {
            afterString = s.subSequence(start + before, start + before + 1).toString();
        }
        //*11*ss** --> **ss**
        if (deleteString.contains(KEY) || KEY.equals(beforeString) || KEY.equals(afterString) ||
                deleteString.contains(KEY_1) || KEY_1.equals(beforeString) || KEY_1.equals(afterString)) {
            shouldFormat = true;
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int after) {
        if (mRxMDConfiguration == null || !(s instanceof Editable)) {
            return;
        }
        if (shouldFormat) {
            format((Editable) s, start);
            return;
        }
        if (after == 0) {
            return;
        }
        String addString;
        String beforeString = null;
        String afterString = null;
        addString = s.subSequence(start, start + after).toString();
        if (start + 1 <= s.length()) {
            afterString = s.subSequence(start, start + 1).toString();
        }
        if (start > 0) {
            beforeString = s.subSequence(start - 1, start).toString();
        }
        //**ss** --> *11*ss**
        if (addString.contains(KEY) || KEY.equals(beforeString) || KEY.equals(afterString) ||
                addString.contains(KEY_1) || KEY_1.equals(beforeString) || KEY_1.equals(afterString)) {
            format((Editable) s, start);
        }
    }

    private void format(Editable editable, int start) {
        Utils.removeSpans(editable, start, StyleSpan.class);
        if (mGrammar == null) {
            mGrammar = EditFactory.create().getBoldGrammar(mRxMDConfiguration);
        }
        if (mGrammar0 == null) {
            mGrammar0 = EditFactory.create().getItalicGrammar(mRxMDConfiguration);
        }
        List<EditToken> editTokenList = new ArrayList<>();
        editTokenList.addAll(Utils.getMatchedEditTokenList(editable, mGrammar.format(editable), start));
        editTokenList.addAll(Utils.getMatchedEditTokenList(editable, mGrammar0.format(editable), start));
        Utils.setSpans(editable, editTokenList);
    }
}
