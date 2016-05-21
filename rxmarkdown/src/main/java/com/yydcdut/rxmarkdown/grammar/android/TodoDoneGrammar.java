package com.yydcdut.rxmarkdown.grammar.android;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.yydcdut.rxmarkdown.span.CustomTodoDoneSpan;

/**
 * Created by yuyidong on 16/5/17.
 */
class TodoDoneGrammar extends AbsAndroidGrammar {
    private static final String KEY_0 = "- [x] ";
    private static final String KEY_1 = "- [X] ";

    private static final String KEY_BACKSLASH_VALUE_0 = KEY_BACKSLASH + "-";
    private static final String KEY_BACKSLASH_VALUE_1 = KEY_BACKSLASH + "[";
    private static final String KEY_BACKSLASH_VALUE_2 = KEY_BACKSLASH + "]";

    private static final int START_POSITION = 6;

    @Override
    boolean isMatch(@NonNull String text) {
        return text.startsWith(KEY_0) ||
                text.startsWith(KEY_1);
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        String text = ssb.toString();
        if (!isMatch(text)) {
            return ssb;
        }
        ssb.delete(0, START_POSITION);
        ssb.setSpan(new CustomTodoDoneSpan(Color.DKGRAY), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }
}
