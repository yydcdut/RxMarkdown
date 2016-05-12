package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;

import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.utils.MDUtils;

/**
 * Created by yuyidong on 16/5/3.
 */
public class Header2Grammar implements IGrammar {
    private static final String KEY = "## ";

    @Override
    public boolean isMatch(@Nullable String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        return text.startsWith(KEY);
    }

    @Nullable
    @Override
    public SpannableStringBuilder format(@Nullable SpannableStringBuilder ssb) {
        if (ssb == null) {
            return new SpannableStringBuilder("");
        }
        String text = ssb.toString();
        if (TextUtils.isEmpty(text)) {
            return ssb;
        }
        if (!text.startsWith(KEY)) {
            return ssb;
        }
        if (!isMatch(text)) {
            return ssb;
        }
        ssb.delete(0, KEY.length());
        ssb.setSpan(new RelativeSizeSpan(1.3f), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        MDUtils.marginSSBLeft(ssb, 10);
        return ssb;
    }

    @Override
    public String toString() {
        return "HeadLine2Grammar{}";
    }
}
