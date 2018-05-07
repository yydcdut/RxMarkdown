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
package com.yydcdut.markdown.syntax.text;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.live.EditToken;
import com.yydcdut.markdown.span.MDUnOrderListSpan;
import com.yydcdut.markdown.syntax.Syntax;
import com.yydcdut.markdown.syntax.SyntaxKey;
import com.yydcdut.markdown.utils.SyntaxUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of syntax for unorder list.
 * syntax:
 * "* "
 * <p>
 * "+ "
 * <p>
 * "- "
 * <p>
 * Created by yuyidong on 16/5/21.
 */
class UnOrderListSyntax implements Syntax {
    private static final int START_POSITION = 2;

    private int mColor;

    public UnOrderListSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        mColor = markdownConfiguration.getUnOrderListColor();
    }

    @Override
    public boolean isMatch(@NonNull CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return false;
        }
        String[] lines = charSequence.toString().split("\n");
        final int length = lines.length;
        for (int i = 0; i < length; i++) {
            if (lines[i].startsWith(SyntaxKey.KEY_UNORDER_LIST_ASTERISK)
                    || lines[i].startsWith(SyntaxKey.KEY_UNORDER_LIST_PLUS)
                    || lines[i].startsWith(SyntaxKey.KEY_UNORDER_LIST_STRIP)) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    @NonNull
    @Override
    public CharSequence format(@NonNull CharSequence charSequence) {
        if (!(charSequence instanceof SpannableStringBuilder)) {
            return charSequence;
        }
        SpannableStringBuilder ssb = (SpannableStringBuilder) charSequence;
        String text = charSequence.toString();
        int currentLineIndex = 0;
        String[] lines = text.split("\n");
        ArrayList<NestedUnOrderListBean> list = new ArrayList<>(lines.length);
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].startsWith(SyntaxKey.IGNORE_UNORDER_LIST_STRIP) || lines[i].startsWith(SyntaxKey.IGNORE_UNORDER_LIST_ASTERISK) || lines[i].startsWith(SyntaxKey.IGNORE_UNORDER_LIST_2) ||
                    lines[i].startsWith(SyntaxKey.IGNORE_UNORDER_LIST_3) || lines[i].startsWith(SyntaxKey.IGNORE_UNORDER_LIST_4) || lines[i].startsWith(SyntaxKey.IGNORE_UNORDER_LIST_5)) {
                list.add(new NestedUnOrderListBean(currentLineIndex, false, lines[i], -1, 0));
                currentLineIndex += (lines[i] + "\n").length();
                continue;
            }
            if (SyntaxUtils.existCodeBlockSpan(ssb, currentLineIndex, currentLineIndex + (lines[i]).length())) {
                list.add(new NestedUnOrderListBean(currentLineIndex, false, lines[i], -1, 0));
                currentLineIndex += (lines[i] + "\n").length();
                continue;
            }
            if (lines[i].startsWith(SyntaxKey.KEY_UNORDER_LIST_ASTERISK)) {
                list.add(new NestedUnOrderListBean(currentLineIndex, true, lines[i], 0, MDUnOrderListSpan.TYPE_KEY_2));
                currentLineIndex += (lines[i] + "\n").length();
                continue;
            }
            if (lines[i].startsWith(SyntaxKey.KEY_UNORDER_LIST_PLUS)) {
                list.add(new NestedUnOrderListBean(currentLineIndex, true, lines[i], 0, MDUnOrderListSpan.TYPE_KEY_0));
                currentLineIndex += (lines[i] + "\n").length();
                continue;
            }
            if (lines[i].startsWith(SyntaxKey.KEY_UNORDER_LIST_STRIP)) {
                list.add(new NestedUnOrderListBean(currentLineIndex, true, lines[i], 0, MDUnOrderListSpan.TYPE_KEY_1));
                currentLineIndex += (lines[i] + "\n").length();
                continue;
            }
            if (!lines[i].startsWith(SyntaxKey.KEY_LIST_HEADER)) {
                list.add(new NestedUnOrderListBean(currentLineIndex, false, lines[i], -1, 0));
                currentLineIndex += (lines[i] + "\n").length();
                continue;
            }
            int nested = calculateNested(lines[i]);
            if (nested > 0) {
                //判断上文
                if (i - 1 < 0 || i - 1 >= list.size()) {
                    currentLineIndex += (lines[i] + "\n").length();
                    continue;
                }
                int type = calculateNestedType(lines[i]);
                NestedUnOrderListBean previousBean = list.get(i - 1);
                if (previousBean != null && previousBean.isRegular &&
                        (nested <= previousBean.nested + 1)) {
                    list.add(new NestedUnOrderListBean(currentLineIndex, true, lines[i], nested, type));
                } else {
                    list.add(new NestedUnOrderListBean(currentLineIndex, false, lines[i], -1, 0));
                }
            }
            currentLineIndex += (lines[i] + "\n").length();
        }

        for (int i = list.size() - 1; i >= 0; i--) {
            NestedUnOrderListBean bean = list.get(i);
            if (bean != null && bean.isRegular) {
                setSSB(bean.nested, bean.start, bean.line, bean.type, ssb);
            }
        }
        return ssb;
    }

    private int calculateNestedType(@NonNull String text) {
        int nested = 0;
        while (true) {
            if ((nested + 1) * SyntaxKey.KEY_LIST_HEADER.length() > text.length()) {
                break;
            }
            String sub = text.substring(nested * SyntaxKey.KEY_LIST_HEADER.length(), (nested + 1) * SyntaxKey.KEY_LIST_HEADER.length());
            if (SyntaxKey.KEY_LIST_HEADER.equals(sub)) {//还是"  "
                nested++;
            } else if (SyntaxKey.KEY_UNORDER_LIST_CHAR_ASTERISK.equals(sub)) {
                return MDUnOrderListSpan.TYPE_KEY_0;
            } else if (SyntaxKey.KEY_UNORDER_LIST_CHAR_PLUS.equals(sub)) {
                return MDUnOrderListSpan.TYPE_KEY_2;
            } else if (SyntaxKey.KEY_UNORDER_LIST_CHAR_STRIP.equals(sub)) {
                return MDUnOrderListSpan.TYPE_KEY_1;
            } else {
                return 0;
            }
        }
        return nested;
    }

    /**
     * calculate nested
     *
     * @param text the content
     * @return nested number of content
     */
    private int calculateNested(@NonNull String text) {//有 "  " 才算嵌套的开始
        int nested = 0;
        while (true) {
            if ((nested + 1) * SyntaxKey.KEY_LIST_HEADER.length() > text.length()) {
                break;
            }
            String sub = text.substring(nested * SyntaxKey.KEY_LIST_HEADER.length(), (nested + 1) * SyntaxKey.KEY_LIST_HEADER.length());
            if (SyntaxKey.KEY_LIST_HEADER.equals(sub)) {//还是"  "
                nested++;
            } else if (SyntaxKey.KEY_UNORDER_LIST_CHAR_ASTERISK.equals(sub) ||
                    SyntaxKey.KEY_UNORDER_LIST_CHAR_PLUS.equals(sub) ||
                    SyntaxKey.KEY_UNORDER_LIST_CHAR_STRIP.equals(sub)) {
                return nested;
            } else {
                return -1;
            }
        }
        return nested;
    }

    /**
     * set bullet
     *
     * @param nested the nested number
     * @param start  start position
     * @param line   the content
     * @param ssb    the content
     */
    private void setSSB(int nested, int start, @NonNull String line, int type, @NonNull SpannableStringBuilder ssb) {
        ssb.delete(start, start + nested * SyntaxKey.KEY_LIST_HEADER.length() + START_POSITION);
        ssb.setSpan(new MDUnOrderListSpan(10, mColor, nested, type),
                start,
                start + line.length() - (nested * SyntaxKey.KEY_LIST_HEADER.length() + START_POSITION),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private static class NestedUnOrderListBean {
        final int start;
        final boolean isRegular;
        final String line;//without "\n"
        final int nested;
        final int type;

        public NestedUnOrderListBean(int start, boolean isRegular, @NonNull String line, int nested, int type) {
            this.start = start;
            this.isRegular = isRegular;
            this.line = line;
            this.nested = nested;
            this.type = type;
        }
    }

    @NonNull
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        return new ArrayList<>();
    }
}
