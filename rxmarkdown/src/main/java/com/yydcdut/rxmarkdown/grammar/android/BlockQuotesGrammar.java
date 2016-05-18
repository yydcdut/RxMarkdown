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

    private static final String KEY_BACKSLASH_VALUE = KEY_BACKSLASH + KEY;

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
        int index = -1;
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(KEY_BACKSLASH_VALUE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + KEY_BACKSLASH_VALUE.length(), KEY_ENCODE);
        }
        return ssb;
    }

    @NonNull
    @Override
    public SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        if (!isMatch(ssb.toString())) {
            return ssb;
        }
        ssb.delete(0, KEY.length() - 1);
        ssb.setSpan(new CustomQuoteSpan(Color.LTGRAY), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        marginSSBLeft(ssb, 20);
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        int index = -1;
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(KEY_ENCODE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + KEY_ENCODE.length(), KEY_BACKSLASH_VALUE);
        }
        return ssb;
    }

    @Override
    public String toString() {
        return "BlockQuotesGrammar{}";
    }
}
