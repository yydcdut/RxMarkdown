package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import com.yydcdut.rxmarkdown.grammar.IGrammar;

/**
 * Created by yuyidong on 16/5/13.
 */
public abstract class AbsAndroidGrammar implements IGrammar {
    @Override
    public boolean isMatch(@NonNull CharSequence charSequence) {
        String text = charSequence + "";
        return isMatch(text);
    }

    @NonNull
    @Override
    public CharSequence format(@NonNull CharSequence charSequence) {
        SpannableStringBuilder ssb = null;
        if (TextUtils.isEmpty(charSequence)) {
            ssb = new SpannableStringBuilder("");
        } else if (charSequence instanceof SpannableStringBuilder) {
            ssb = (SpannableStringBuilder) charSequence;
        }
        return format(ssb);
    }

    abstract boolean isMatch(@NonNull String text);

    abstract SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb);
}
