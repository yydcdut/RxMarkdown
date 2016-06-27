package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;

import com.yydcdut.rxmarkdown.Configuration;

/**
 * Created by yuyidong on 16/5/4.
 */
class NormalGrammar extends AbsAndroidGrammar {

    NormalGrammar(@NonNull Configuration configuration) {
        super(configuration);
    }

    @Override
    boolean isMatch(@NonNull String text) {
        return false;
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }
}
