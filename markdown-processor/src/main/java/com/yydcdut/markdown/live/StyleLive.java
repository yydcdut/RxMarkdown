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

import android.text.Editable;
import android.text.style.StyleSpan;

import com.yydcdut.markdown.syntax.Syntax;
import com.yydcdut.markdown.syntax.SyntaxKey;
import com.yydcdut.markdown.syntax.edit.EditFactory;
import com.yydcdut.markdown.utils.SyntaxUtils;
import com.yydcdut.markdown.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * RxMDEditText, bold and italic controller.
 * <p>
 * Created by yuyidong on 16/7/21.
 */
class StyleLive extends EditLive {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int before, int after) {
        super.beforeTextChanged(s, start, before, after);
        if (before == 0 || mMarkdownConfiguration == null) {
            return;
        }
        String deleteString = s.subSequence(Utils.safePosition(start, s), Utils.safePosition(start + before, s)).toString();
        String beforeString = null;
        String afterString = null;
        if (start > 0) {
            beforeString = s.subSequence(Utils.safePosition(start - 1, s), Utils.safePosition(start, s)).toString();
        }
        if (start + before + 1 <= s.length()) {
            afterString = s.subSequence(Utils.safePosition(start + before, s), Utils.safePosition(start + before + 1, s)).toString();
        }
        //*11*ss** --> **ss**
        if (SyntaxUtils.isNeedFormat(SyntaxKey.KEY_BOLD_ASTERISK_SINGLE, deleteString, beforeString, afterString)
                || SyntaxUtils.isNeedFormat(SyntaxKey.KEY_BOLD_UNDERLINE_SINGLE, deleteString, beforeString, afterString)) {
            shouldFormat = true;
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int after) {
        if (mMarkdownConfiguration == null || !(s instanceof Editable)) {
            return;
        }
        if (shouldFormat) {
            format((Editable) s, start);
            return;
        }
        if (after == 0) {
            return;
        }
        String addString = s.subSequence(Utils.safePosition(start, s), Utils.safePosition(start + after, s)).toString();
        String beforeString = null;
        String afterString = null;
        if (start + 1 <= s.length()) {
            afterString = s.subSequence(Utils.safePosition(start, s), Utils.safePosition(start + 1, s)).toString();
        }
        if (start > 0) {
            beforeString = s.subSequence(Utils.safePosition(start - 1, s), Utils.safePosition(start, s)).toString();
        }
        //**ss** --> *11*ss**
        if (SyntaxUtils.isNeedFormat(SyntaxKey.KEY_BOLD_ASTERISK_SINGLE, addString, beforeString, afterString)
                || SyntaxUtils.isNeedFormat(SyntaxKey.KEY_BOLD_UNDERLINE_SINGLE, addString, beforeString, afterString)) {
            format((Editable) s, start);
        }
    }

    private void format(Editable editable, int start) {
        Utils.removeSpans(editable, start, StyleSpan.class);
        List<EditToken> editTokenList = new ArrayList<>();
        Syntax syntax = EditFactory.create().getBoldSyntax(mMarkdownConfiguration);
        editTokenList.addAll(Utils.getMatchedEditTokenList(editable, syntax.format(editable), start));
        syntax = EditFactory.create().getItalicSyntax(mMarkdownConfiguration);
        editTokenList.addAll(Utils.getMatchedEditTokenList(editable, syntax.format(editable), start));
        Utils.setSpans(editable, editTokenList);
    }
}
