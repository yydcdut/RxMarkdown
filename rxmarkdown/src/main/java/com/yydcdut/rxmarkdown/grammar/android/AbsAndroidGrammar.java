package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.LeadingMarginSpan;

import com.yydcdut.rxmarkdown.grammar.IGrammar;

/**
 * Created by yuyidong on 16/5/13.
 */
abstract class AbsAndroidGrammar implements IGrammar {
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

    protected static void marginSSBLeft(SpannableStringBuilder ssb, int every) {
        ssb.setSpan(new LeadingMarginSpan.Standard(every), 0, ssb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }
}
