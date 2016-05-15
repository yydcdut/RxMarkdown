package com.yydcdut.rxmarkdown.grammar.android;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;

import com.yydcdut.rxmarkdown.span.CustomQuoteSpan;

/**
 * Created by yuyidong on 16/5/4.
 */
class BlockQuotesGrammar extends AbsAndroidGrammar {
    private static final String KEY = "> ";

    @Override
    public boolean isMatch(@NonNull String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        if (!text.startsWith(KEY)) {
            return false;
        }
        return true;
    }

    @NonNull
    @Override
    public SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
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
        ssb.delete(0, KEY.length() - 1);
        ssb.setSpan(new CustomQuoteSpan(Color.LTGRAY), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        marginSSBLeft(ssb, 20);
        return ssb;
    }

    @Override
    public String toString() {
        return "BlockQuotesGrammar{}";
    }
}
