package com.yydcdut.rxmarkdown.grammar.android;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;

import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.span.CustomBulletSpan;

import java.util.ArrayList;

/**
 * Created by yuyidong on 16/5/21.
 * Key 与 TodoDoneGrammar 和 TodoGrammar 有关联
 */
class UnOrderListGrammar implements IGrammar {
    private static final String KEY_HEADER = "  ";
    private static final String KEY_0 = "* ";
    private static final String KEY_1 = "+ ";
    private static final String KEY_2 = "- ";

    private static final String KEY_IGNORE_0 = "- [ ] ";
    private static final String KEY_IGNORE_1 = "- [x] ";
    private static final String KEY_IGNORE_2 = "- [X] ";

    private static final int START_POSITION = 2;

    @Override
    public boolean isMatch(@NonNull CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return false;
        }
        String text = charSequence.toString();
        String[] lines = text.split("\n");
        for (int i = 0; i < lines.length; i++) {
            boolean b = lines[i].startsWith(KEY_0) ||
                    lines[i].startsWith(KEY_1) ||
                    (lines[i].startsWith(KEY_2));
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
        ArrayList<NestedUnOrderListBean> list = new ArrayList<>(lines.length);
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].startsWith(KEY_IGNORE_0) || lines[i].startsWith(KEY_IGNORE_1) || lines[i].startsWith(KEY_IGNORE_2)) {
                list.add(new NestedUnOrderListBean(currentLineIndex, false, lines[i], -1));
                currentLineIndex += (lines[i] + "\n").length();
                continue;
            }
            if (lines[i].startsWith(KEY_0) || lines[i].startsWith(KEY_1) || lines[i].startsWith(KEY_2)) {
                list.add(new NestedUnOrderListBean(currentLineIndex, true, lines[i], 0));
                currentLineIndex += (lines[i] + "\n").length();
                continue;
            }
            if (!lines[i].startsWith(KEY_HEADER)) {
                list.add(new NestedUnOrderListBean(currentLineIndex, false, lines[i], -1));
                currentLineIndex += (lines[i] + "\n").length();
                continue;
            }
            int nested = calculateNested(lines[i]);
            if (nested > 0) {
                //判断上文
                if (i - 1 < 0) {
                    currentLineIndex += (lines[i] + "\n").length();
                    continue;
                }
                NestedUnOrderListBean previousBean = list.get(i - 1);
                if (previousBean != null && previousBean.isRegular &&
                        (nested <= previousBean.nested + 1)) {
                    list.add(new NestedUnOrderListBean(currentLineIndex, true, lines[i], nested));
                } else {
                    list.add(new NestedUnOrderListBean(currentLineIndex, false, lines[i], -1));
                }
            }
            currentLineIndex += (lines[i] + "\n").length();
        }

        for (int i = list.size() - 1; i >= 0; i--) {
            NestedUnOrderListBean bean = list.get(i);
            if (bean != null && bean.isRegular) {
                setSSB(bean.nested, bean.start, bean.line, ssb);
            }
        }
        return ssb;
    }

    /**
     * 有 "  " 才算嵌套的开始
     *
     * @param text
     * @return
     */
    private int calculateNested(String text) {
        int nested = 0;
        while (true) {
            if ((nested + 1) * KEY_HEADER.length() > text.length()) {
                break;
            }
            String sub = text.substring(nested * KEY_HEADER.length(), (nested + 1) * KEY_HEADER.length());
            if (KEY_HEADER.equals(sub)) {//还是"  "
                nested++;
            } else if (KEY_0.equals(sub) || KEY_1.equals(sub) || KEY_2.equals(sub)) {
                return nested;
            } else {
                return -1;
            }
        }
        return nested;
    }

    private void setSSB(int nested, int start, String line, SpannableStringBuilder ssb) {
        ssb.delete(start, start + nested * KEY_HEADER.length() + START_POSITION);
        ssb.setSpan(new CustomBulletSpan(10, Color.BLACK, nested),
                start,
                start + line.length() - (nested * KEY_HEADER.length() + START_POSITION),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private static class NestedUnOrderListBean {
        final int start;
        final boolean isRegular;
        final String line;//without "\n"
        final int nested;

        public NestedUnOrderListBean(int start, boolean isRegular, String line, int nested) {
            this.start = start;
            this.isRegular = isRegular;
            this.line = line;
            this.nested = nested;
        }
    }
}
