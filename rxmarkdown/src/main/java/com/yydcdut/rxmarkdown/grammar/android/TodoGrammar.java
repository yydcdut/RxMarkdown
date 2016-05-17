package com.yydcdut.rxmarkdown.grammar.android;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;

import com.yydcdut.rxmarkdown.span.CustomTodoSpan;

/**
 * Created by yuyidong on 16/5/17.
 */
public class TodoGrammar extends AbsAndroidGrammar {
    private static final String KEY = "- [ ] ";

    @Override
    boolean isMatch(@NonNull String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        return text.startsWith(KEY);
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
        ssb.delete(0, KEY.length());
        ssb.setSpan(new CustomTodoSpan(Color.DKGRAY), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }
}
