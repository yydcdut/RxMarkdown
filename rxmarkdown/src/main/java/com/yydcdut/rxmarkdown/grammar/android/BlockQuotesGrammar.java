package com.yydcdut.rxmarkdown.grammar.android;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.text.style.QuoteSpan;

import com.yydcdut.rxmarkdown.grammar.IGrammar;

/**
 * Created by yuyidong on 16/5/4.
 */
class BlockQuotesGrammar implements IGrammar {
    private static final String KEY = "> ";

    @Override
    public boolean isMatch(@Nullable String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        if (!text.startsWith(KEY)) {
            return false;
        }
        return true;
    }

    @Nullable
    @Override
    public SpannableStringBuilder format(@Nullable SpannableStringBuilder ssb) {
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
//        ssb.delete(0, KEY.length() - 1);
        Drawable drawable = new ColorDrawable(Color.RED);
        drawable.setBounds(0, 0, 100, 100);
        ssb.setSpan(new ImageSpan(drawable, 10), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new QuoteSpan(Color.GRAY), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//        MDUtils.marginSSBLeft(ssb, 20);
        return ssb;
    }

    @Override
    public String toString() {
        return "BlockQuotesGrammar{}";
    }
}
