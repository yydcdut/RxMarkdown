package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.URLSpan;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.edit.EditToken;
import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.span.MDCodeSpan;
import com.yydcdut.rxmarkdown.span.MDImageSpan;

import java.util.List;

/**
 * Created by yuyidong on 16/5/13.
 */
abstract class AbsAndroidGrammar implements IGrammar {

    public AbsAndroidGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
    }

    @Override
    public boolean isMatch(@NonNull CharSequence charSequence) {
        SpannableStringBuilder ssb = null;
        if (charSequence instanceof SpannableStringBuilder) {
            ssb = (SpannableStringBuilder) charSequence;
        } else {
            return false;
        }
        if (ssb.getSpans(0, ssb.length(), MDCodeSpan.class).length > 0) {
            return false;
        }
        if (TextUtils.isEmpty(charSequence)) {
            return false;
        }
        return isMatch(charSequence.toString());
    }

    @NonNull
    @Override
    public CharSequence format(@NonNull CharSequence charSequence) {
        SpannableStringBuilder ssb = null;
        if (charSequence instanceof SpannableStringBuilder) {
            ssb = (SpannableStringBuilder) charSequence;
        } else {
            return charSequence;
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

    protected boolean checkInInlineCode(SpannableStringBuilder ssb, int position, int keyLength) {
        BackgroundColorSpan[] spans = ssb.getSpans(position, position + keyLength, BackgroundColorSpan.class);
        if (spans.length == 0) {
            return false;
        } else {
            return true;
        }
    }

    protected boolean checkInHyperLink(SpannableStringBuilder ssb, int position, int keyLength) {
        URLSpan[] spans = ssb.getSpans(position, position + keyLength, URLSpan.class);
        if (spans.length == 0) {
            return false;
        } else {
            return true;
        }
    }

    protected boolean checkInImage(SpannableStringBuilder ssb, int position, int keyLength) {
        MDImageSpan[] spans = ssb.getSpans(position, position + keyLength, MDImageSpan.class);
        if (spans.length == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Nullable
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        return null;
    }

}
