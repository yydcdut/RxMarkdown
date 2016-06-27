package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import com.yydcdut.rxmarkdown.Configuration;
import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.span.MDCodeSpan;

import java.util.regex.Pattern;

/**
 * Created by yuyidong on 16/5/17.
 */
class CodeGrammar implements IGrammar {
    private static final String KEY = "```";

    private int mColor;

    public CodeGrammar(@NonNull Configuration configuration) {
        mColor = configuration.getCodeBgColor();
    }

    @Override
    public boolean isMatch(@NonNull CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return false;
        }
        String text = charSequence.toString();
        String newText = text.replace("\n", "*!!*@@*##*$$*%%*^^*&&***((*))*--*^^*++*$$*");
        Pattern pattern = Pattern.compile(".*[```]{3}.*[```]{3}.*");
        if (!pattern.matcher(newText).matches()) {
            return false;
        }
        return calculateTotalKey(text) >= 2; //大于2就OK了
    }

    @NonNull
    @Override
    public CharSequence format(@NonNull CharSequence charSequence) {
        if (!(charSequence instanceof SpannableStringBuilder)) {
            return charSequence;
        }
        SpannableStringBuilder ssb = (SpannableStringBuilder) charSequence;
        int index = 0;
        String text = charSequence.toString();
        int totalKey = calculateTotalKey(text);
        boolean needCareful = (totalKey % 2 == 1);
        String[] lines = text.split("\n");
        int currentKeyIndex = 0;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].equals(KEY)) {
                currentKeyIndex++;
            }
            if (currentKeyIndex % 2 == 0) {//非代码部分
                if (KEY.equals(lines[i])) {
                    ssb.delete(index, index + KEY.length() + ((i == lines.length - 1) ? 0 : "\n".length()));
                    continue;
                }
            } else {//代码部分
                if (needCareful && currentKeyIndex == totalKey) {
                    break;
                }
                if (KEY.equals(lines[i])) {
                    ssb.delete(index, index + KEY.length() + "\n".length());
                    continue;
                }
                //中间如果有直接换行的，就删除掉
                if ("".equals(lines[i])) {
                    ssb.delete(index, index + "".length() + ((i == lines.length - 1) ? 0 : "\n".length()));
                    continue;
                }
                int start = index;
                int end = index + lines[i].length() + ((i == lines.length - 1) ? 0 : "\n".length());
                ssb.setSpan(new MDCodeSpan(mColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            index += lines[i].length() + "\n".length();
        }
        return ssb;
    }

    private int calculateTotalKey(@NonNull String text) {
        String[] lines = text.split("\n");
        int number = 0;
        for (int i = 0; i < lines.length; i++) {
            number += KEY.equals(lines[i]) ? 1 : 0;
        }
        return number;
    }
}
