package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

/**
 * Created by yuyidong on 16/5/3.
 */
class Header3Grammar extends AbsAndroidGrammar {
    private static final String KEY = "### ";

    @Override
    public boolean isMatch(@NonNull String text) {
        return text.startsWith(KEY);
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    @NonNull
    @Override
    public SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        String text = ssb.toString();
        if (!isMatch(text)) {
            return ssb;
        }
        ssb.delete(0, KEY.length());
        ssb.setSpan(new RelativeSizeSpan(1.1f), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        marginSSBLeft(ssb, 10);
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    @Override
    public String toString() {
        return "HeadLine3Grammar{}";
    }
}
