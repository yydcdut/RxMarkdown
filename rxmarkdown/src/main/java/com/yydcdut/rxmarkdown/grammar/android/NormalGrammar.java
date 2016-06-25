package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;

import com.yydcdut.rxmarkdown.Configuration;

/**
 * Created by yuyidong on 16/5/4.
 */
class NormalGrammar extends AbsAndroidGrammar {

    public NormalGrammar(@NonNull Configuration configuration) {
        super(configuration);
    }

    @Override
    public boolean isMatch(@NonNull String text) {
        return false;
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    @NonNull
    @Override
    public SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    @Override
    public String toString() {
        return "NormalGrammar{}";
    }
}
