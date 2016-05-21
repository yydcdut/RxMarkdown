package com.yydcdut.rxmarkdown.grammar.android;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.yydcdut.rxmarkdown.span.CustomTodoSpan;

/**
 * Created by yuyidong on 16/5/17.
 * Key 与 UnOrderListGrammar 有关联
 */
class TodoGrammar extends AbsAndroidGrammar {
    private static final String KEY = "- [ ] ";

    @Override
    boolean isMatch(@NonNull String text) {
        return text.startsWith(KEY);
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        ssb.delete(0, KEY.length());
        ssb.setSpan(new CustomTodoSpan(Color.DKGRAY), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }
}
