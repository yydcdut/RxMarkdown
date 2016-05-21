package com.yydcdut.rxmarkdown.grammar.android;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.yydcdut.rxmarkdown.span.CustomQuoteSpan;

/**
 * Created by yuyidong on 16/5/4.
 */
class BlockQuotesGrammar extends AbsAndroidGrammar {
    private static final String KEY = "> ";

    @Override
    public boolean isMatch(@NonNull String text) {
        if (!text.startsWith(KEY)) {
            return false;
        }
        return true;
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    @NonNull
    @Override
    public SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        int nested = calculateNested(ssb.toString());
        if (nested == 0) {
            return ssb;
        }
        ssb = ssb.delete(0, nested * KEY.length() - 1);
        ssb.setSpan(new CustomQuoteSpan(Color.LTGRAY, nested), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        marginSSBLeft(ssb, 20);
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    @Override
    public String toString() {
        return "BlockQuotesGrammar{}";
    }

    /**
     * 有一个 "> " 就算嵌套一层
     *
     * @param text
     * @return
     */
    private int calculateNested(@NonNull String text) {
        int nested = 0;
        while (true) {
            if ((nested + 1) * KEY.length() > text.length()) {
                break;
            }
            String sub = text.substring(nested * KEY.length(), (nested + 1) * KEY.length());
            if (!KEY.equals(sub)) {
                break;
            }
            ++nested;
        }
        return nested;

    }
}
