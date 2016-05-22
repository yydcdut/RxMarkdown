package com.yydcdut.rxmarkdown.grammar.android;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;

import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.span.CustomBulletSpan;

import java.util.ArrayList;

/**
 * Created by yuyidong on 16/5/22.
 */
class OrderListGrammar implements IGrammar {
    private static final String KEY_HEADER = "  ";

    private static final char DOT = '.';

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
            Log.wtf("NestedOrderListGrammar", "charSequence 类型 " + charSequence.getClass().getName());
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
                        Log.wtf("NestedOrderListGrammar", "bean == null");
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


    private boolean check(String text) {
        if (text.length() < 3) {
            return false;
        }
        if (TextUtils.isDigitsOnly(text.charAt(0) + "")) {
            int dotPosition = 1;
            for (int i = 1; i < text.length(); i++) {
                char c = text.charAt(i);
                if (TextUtils.isDigitsOnly(c + "")) {
                    continue;
                } else {
                    dotPosition = i;
                    break;
                }
            }
            char dot = text.charAt(dotPosition);
            if (dot == DOT) {
                if (text.charAt(dotPosition + 1) == ' ') {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private int calculateNested(String text) {
        if (text.length() < 3) {
            return -1;
        }
        int nested = 0;
        while (true) {
            if ((nested + 1) * KEY_HEADER.length() > text.length()) {
                break;
            }
            String sub = text.substring(nested * KEY_HEADER.length(), (nested + 1) * KEY_HEADER.length());
            if (KEY_HEADER.equals(sub)) {//还是"  "
                nested++;
            } else if (check(text.substring(nested * KEY_HEADER.length(), text.length()))) {
                return nested;
            } else {
                return -1;
            }
        }
        return nested;
    }

    private int calculateNumber(String text, int nested) {
        if (text.length() < 3) {
            return -1;
        }
        int number = -1;
        String s = text.substring(nested * KEY_HEADER.length(), text.length());
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

    private void setSSB(int nested, int start, String line, SpannableStringBuilder ssb, int number, int originalNumber) {
        ssb.delete(start, start + nested * KEY_HEADER.length() + (originalNumber + "").length());
        ssb.insert(start, number + "");
        ssb.setSpan(new CustomBulletSpan(10, Color.TRANSPARENT, nested),
                start,
                start + line.length() - (nested * KEY_HEADER.length() + (originalNumber + "").length()),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }


    private static class NestedOrderListBean {
        final int start;
        final boolean isRegular;
        final String line;//without "\n"
        final int nested;
        final int number;
        final int originalNumber;

        public NestedOrderListBean(int start, boolean isRegular, String line, int nested, int number, int originalNumber) {
            this.start = start;
            this.isRegular = isRegular;
            this.line = line;
            this.nested = nested;
            this.number = number;
            this.originalNumber = originalNumber;
        }
    }
}
