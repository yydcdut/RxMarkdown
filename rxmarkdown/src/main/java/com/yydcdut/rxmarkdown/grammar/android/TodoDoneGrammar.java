package com.yydcdut.rxmarkdown.grammar.android;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;

import com.yydcdut.rxmarkdown.span.CustomTodoDoneSpan;

/**
 * Created by yuyidong on 16/5/17.
 */
public class TodoDoneGrammar extends AbsAndroidGrammar {
    private static final String KEY_0 = "- [x] ";
    private static final String KEY_1 = "- [X] ";

    private static final int START_POSITION = 6;

    @Override
    boolean isMatch(@NonNull String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        return text.startsWith(KEY_0) ||
                text.startsWith(KEY_1);
    }

    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        if (ssb == null) {
            return new SpannableStringBuilder("");
        }
        String text = ssb.toString();
        if (TextUtils.isEmpty(text)) {
            return ssb;
        }
        if (!isMatch(text)) {
            return ssb;
        }
        ssb.delete(0, START_POSITION);
        ssb.setSpan(new CustomTodoDoneSpan(Color.DKGRAY), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }
}
