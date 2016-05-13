package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;

import java.util.regex.Pattern;

/**
 * Created by yuyidong on 16/5/13.
 */
class StrikeThroughGrammar extends LiteGrammar {
    private static final String KEY = "~~";

    @Override
    public boolean isMatch(@NonNull String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        if (!text.contains(KEY)) {
            return false;
        }
        Pattern pattern = Pattern.compile(".*[~]{2}.*[~]{2}.*");
        return pattern.matcher(text).matches();
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
        if (!text.contains(KEY)) {
            return ssb;
        }
        if (!isMatch(text)) {
            return ssb;
        }
        return complex(text, ssb, KEY, new StrikethroughSpan());
    }

}
