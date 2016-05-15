package com.yydcdut.rxmarkdown.grammar.android;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;

import com.yydcdut.rxmarkdown.span.CustomHorizontalRulesSpan;

/**
 * Created by yuyidong on 16/5/15.
 */
public class HorizontalRulesGrammar extends AbsAndroidGrammar {
    private static final String KEY_0 = "***";
    private static final String KEY_1 = "---";

    private static final char KEY_SINGLE_0 = '*';
    private static final char KEY_SINGLE_1 = '-';

    @Override
    boolean isMatch(@NonNull String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        if (!(text.contains(KEY_0) || text.contains(KEY_1))) {
            return false;
        }
        if (!(text.startsWith(KEY_0) || text.startsWith(KEY_1))) {
            return false;
        }
        return check(text, KEY_SINGLE_0) || check(text, KEY_SINGLE_1);
    }

    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        if (ssb == null) {
            return new SpannableStringBuilder("");
        }
        String text = ssb.toString();
        if (TextUtils.isEmpty(text)) {
            return new SpannableStringBuilder("");
        }
        if (!isMatch(text)) {
            return ssb;
        }
        ssb.replace(0, ssb.length(), " ");
        ssb.setSpan(new CustomHorizontalRulesSpan(Color.LTGRAY), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    private boolean check(String text, char key) {
        char[] chars = text.toCharArray();
        boolean bool = true;
        for (int i = 0; i < chars.length; i++) {
            bool &= (chars[i] == key);
            if (!bool) {
                break;
            }
        }
        return bool;
    }
}
