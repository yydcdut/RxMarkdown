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
package com.yydcdut.rxmarkdown.syntax.text;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.span.MDOrderListSpan;
import com.yydcdut.rxmarkdown.syntax.SyntaxKey;

import java.util.ArrayList;

/**
 * The implementation of syntax for order list.
 * syntax:
 * "1. "
 * <p>
 * Created by yuyidong on 16/5/22.
 */
class OrderListSyntax extends ListAndCodeSyntaxAdapter {

    /**
     * Constructor
     *
     * @param rxMDConfiguration RxMDConfiguration
     */
    public OrderListSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
    }

    @Override
    public boolean isMatch(@NonNull CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return false;
        }
        String text = charSequence.toString();
        String[] lines = text.split("\n");
        for (int i = 0; i < lines.length; i++) {
            boolean b = check(lines[i]);
            if (b) {
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
        ArrayList<NestedOrderListBean> list = new ArrayList<>(lines.length);
        for (int i = 0; i < lines.length; i++) {
            int nested = calculateNested(lines[i]);
            if (nested < 0) {
                list.add(new NestedOrderListBean(currentLineIndex, false, lines[i], -1, -1, -1));
                currentLineIndex += (lines[i] + "\n").length();
                continue;
            }
            if (existCodeSpan(ssb, currentLineIndex, currentLineIndex + (lines[i]).length())) {
                list.add(new NestedOrderListBean(currentLineIndex, false, lines[i], -1, -1, -1));
                currentLineIndex += (lines[i] + "\n").length();
                continue;
            }
            int number = calculateNumber(lines[i], nested);
            //判断上文
            if (i - 1 < 0 || i - 1 >= list.size()) {
                if (nested == 0) {
                    list.add(new NestedOrderListBean(currentLineIndex, true, lines[i], 0, 1, number));
                } else {
                    list.add(new NestedOrderListBean(currentLineIndex, false, lines[i], -1, -1, -1));
                }
                currentLineIndex += (lines[i] + "\n").length();
                continue;
            }
            NestedOrderListBean previousBean = list.get(i - 1);
            if (previousBean != null && previousBean.isRegular && (nested <= previousBean.nested + 1)) {
                if (nested == previousBean.nested) {
                    list.add(new NestedOrderListBean(currentLineIndex, true, lines[i], nested, previousBean.number + 1, number));
                } else if (nested == previousBean.nested + 1) {
                    list.add(new NestedOrderListBean(currentLineIndex, true, lines[i], nested, 1, number));
                } else {
                    for (int j = i - 2; j >= 0; j--) {
                        NestedOrderListBean previousSameNestedBean = list.get(j);
                        if (previousSameNestedBean != null && previousSameNestedBean.isRegular && previousSameNestedBean.nested == nested) {
                            list.add(new NestedOrderListBean(currentLineIndex, true, lines[i], nested, previousSameNestedBean.number + 1, number));
                            break;
                        } else if (previousSameNestedBean == null || !previousSameNestedBean.isRegular) {
                            list.add(new NestedOrderListBean(currentLineIndex, false, lines[i], -1, -1, -1));
                            break;
                        }
                    }
                    //check
                    NestedOrderListBean bean = list.get(i);
                    //如果为null说明上面某一部肯定有问题
                    if (bean == null) {
                        list.add(new NestedOrderListBean(currentLineIndex, false, lines[i], -1, -1, -1));
                    }
                }
            } else if (previousBean != null && !previousBean.isRegular && nested == 0) {
                list.add(new NestedOrderListBean(currentLineIndex, true, lines[i], nested, 1, number));
            } else {
                list.add(new NestedOrderListBean(currentLineIndex, false, lines[i], -1, -1, -1));
            }
            currentLineIndex += (lines[i] + "\n").length();
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            NestedOrderListBean bean = list.get(i);
            if (bean != null && bean.isRegular) {
                setSSB(bean.nested, bean.start, bean.line, ssb, bean.number, bean.originalNumber);
            }
        }
        return ssb;
    }

    private boolean check(@NonNull String text) {
        if (text.length() < 3) {
            return false;
        }
        if (TextUtils.isDigitsOnly(String.valueOf(text.charAt(0)))) {
            int dotPosition = 1;
            for (int i = 1; i < text.length(); i++) {
                char c = text.charAt(i);
                if (TextUtils.isDigitsOnly(String.valueOf(c))) {
                    continue;
                } else {
                    dotPosition = i;
                    break;
                }
            }
            char dot = text.charAt(dotPosition);
            if (dot == SyntaxKey.DOT) {
                if (text.charAt(dotPosition + 1) == ' ') {
                    return true;
                }
                return false;
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * calculate nested
     *
     * @param text the content
     * @return nested number of content
     */
    private int calculateNested(@NonNull String text) {
        if (text.length() < 2) {
            return -1;
        }
        int nested = 0;
        while (true) {
            if ((nested + 1) * SyntaxKey.KEY_LIST_HEADER.length() > text.length()) {
                break;
            }
            String sub = text.substring(nested * SyntaxKey.KEY_LIST_HEADER.length(), (nested + 1) * SyntaxKey.KEY_LIST_HEADER.length());
            if (SyntaxKey.KEY_LIST_HEADER.equals(sub)) {//还是" "
                nested++;
            } else if (check(text.substring(nested * SyntaxKey.KEY_LIST_HEADER.length(), text.length()))) {
                return nested;
            } else {
                return -1;
            }
        }
        return nested;
    }

    /**
     * calculate the key number
     *
     * @param text   the content
     * @param nested the nested number
     * @return the key number
     */
    private int calculateNumber(@NonNull String text, int nested) {
        if (text.length() < 3) {
            return -1;
        }
        int number;
        String s = text.substring(nested * SyntaxKey.KEY_LIST_HEADER.length(), text.length());
        if (TextUtils.isDigitsOnly(s.substring(0, 1))) {
            number = Integer.parseInt(s.substring(0, 1));
            for (int i = 1; i < s.length(); i++) {
                if (TextUtils.isDigitsOnly(s.substring(i, i + 1))) {
                    number = number * 10 + Integer.parseInt(s.substring(i, i + 1));
                    continue;
                } else {
                    return number;
                }
            }
        } else {
            return -1;
        }
        return number;
    }

    /**
     * set key number
     *
     * @param nested         the nested number
     * @param start          start position
     * @param line           the content
     * @param ssb            the content
     * @param number         the key number
     * @param originalNumber the original number
     */
    private void setSSB(int nested, int start, @NonNull String line, @NonNull SpannableStringBuilder ssb, int number, int originalNumber) {
        ssb.delete(start, start + nested * SyntaxKey.KEY_LIST_HEADER.length() + String.valueOf(originalNumber).length());
        ssb.insert(start, String.valueOf(number));
        ssb.setSpan(new MDOrderListSpan(30, nested, number),
                start,
                start + line.length() - (nested * SyntaxKey.KEY_LIST_HEADER.length() + String.valueOf(originalNumber).length()),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private static class NestedOrderListBean {
        final int start;
        final boolean isRegular;
        final String line;//without "\n"
        final int nested;
        final int number;
        final int originalNumber;

        public NestedOrderListBean(int start, boolean isRegular, @NonNull String line, int nested, int number, int originalNumber) {
            this.start = start;
            this.isRegular = isRegular;
            this.line = line;
            this.nested = nested;
            this.number = number;
            this.originalNumber = originalNumber;
        }
    }
}
