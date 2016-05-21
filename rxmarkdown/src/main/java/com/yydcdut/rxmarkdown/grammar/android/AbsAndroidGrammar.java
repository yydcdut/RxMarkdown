package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.LeadingMarginSpan;
import android.util.Log;

import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.span.CustomCodeSpan;

/**
 * Created by yuyidong on 16/5/13.
 */
abstract class AbsAndroidGrammar implements IGrammar {
    protected static final String KEY_BACKSLASH = "\\";

    protected static final String KEY_ENCODE = "@$7DF16dgf%jy@po&";
    protected static final String KEY_ENCODE_1 = "%4usyHDlL&@D%";
    protected static final String KEY_ENCODE_2 = "&YDhs@h4sF&%kLsx63sd@";
    protected static final String KEY_ENCODE_3 = "%hsyRjh34l%$2@";
    protected static final String KEY_ENCODE_4 = "&@da&U56ec%k$QW@";

    @Override
    public boolean isMatch(@NonNull CharSequence charSequence) {
        SpannableStringBuilder ssb = null;
        if (charSequence instanceof SpannableStringBuilder) {
            ssb = (SpannableStringBuilder) charSequence;
        } else {
            Log.wtf("AbsAndroidGrammar", "charSequence 类型 " + charSequence.getClass().getName());
            throw new RuntimeException("AbsAndroidGrammar\ncharSequence 类型 " + charSequence.getClass().getName());
        }
        if (ssb.getSpans(0, ssb.length(), CustomCodeSpan.class).length > 0) {
            return false;
        }
        String text = null;
        if (TextUtils.isEmpty(charSequence)) {
            text = "";
        } else {
            text = charSequence + "";
        }

        return isMatch(text);
    }

    @NonNull
    @Override
    public CharSequence format(@NonNull CharSequence charSequence) {
        SpannableStringBuilder ssb = null;
        if (charSequence instanceof SpannableStringBuilder) {
            ssb = (SpannableStringBuilder) charSequence;
        } else {
            Log.wtf("AbsAndroidGrammar", "charSequence 类型 " + charSequence.getClass().getName());
            throw new RuntimeException("AbsAndroidGrammar\ncharSequence 类型 " + charSequence.getClass().getName());
        }
        ssb = encode(ssb);
        ssb = format(ssb);
        ssb = decode(ssb);
        return ssb;
    }

    abstract boolean isMatch(@NonNull String text);

    @NonNull
    abstract SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb);

    @NonNull
    abstract SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb);

    @NonNull
    abstract SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb);

    protected static void marginSSBLeft(SpannableStringBuilder ssb, int every) {
        ssb.setSpan(new LeadingMarginSpan.Standard(every), 0, ssb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

}
