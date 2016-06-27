package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

import com.yydcdut.rxmarkdown.RxMDConfiguration;

/**
 * Created by yuyidong on 16/5/20.
 */
class HeaderGrammar extends AbsAndroidGrammar {
    private static final String KEY_0 = "# ";
    private static final String KEY_1 = "## ";
    private static final String KEY_2 = "### ";
    private static final String KEY_3 = "#### ";
    private static final String KEY_4 = "##### ";
    private static final String KEY_5 = "###### ";

    private float mHeader1RelativeSize;
    private float mHeader2RelativeSize;
    private float mHeader3RelativeSize;
    private float mHeader4RelativeSize;
    private float mHeader5RelativeSize;
    private float mHeader6RelativeSize;

    HeaderGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
        mHeader1RelativeSize = rxMDConfiguration.getHeader1RelativeSize();
        mHeader2RelativeSize = rxMDConfiguration.getHeader2RelativeSize();
        mHeader3RelativeSize = rxMDConfiguration.getHeader3RelativeSize();
        mHeader4RelativeSize = rxMDConfiguration.getHeader4RelativeSize();
        mHeader5RelativeSize = rxMDConfiguration.getHeader5RelativeSize();
        mHeader6RelativeSize = rxMDConfiguration.getHeader6RelativeSize();
    }

    @Override
    boolean isMatch(@NonNull String text) {
        return text.startsWith(KEY_0) ||
                text.startsWith(KEY_1) ||
                text.startsWith(KEY_2) ||
                text.startsWith(KEY_3) ||
                text.startsWith(KEY_4) ||
                text.startsWith(KEY_5);
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        String text = ssb.toString();
        if (text.startsWith(KEY_5)) {
            ssb.delete(0, KEY_5.length());
            ssb.setSpan(new RelativeSizeSpan(mHeader6RelativeSize), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (text.startsWith(KEY_4)) {
            ssb.delete(0, KEY_4.length());
            ssb.setSpan(new RelativeSizeSpan(mHeader5RelativeSize), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (text.startsWith(KEY_3)) {
            ssb.delete(0, KEY_3.length());
            ssb.setSpan(new RelativeSizeSpan(mHeader4RelativeSize), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (text.startsWith(KEY_2)) {
            ssb.delete(0, KEY_2.length());
            ssb.setSpan(new RelativeSizeSpan(mHeader3RelativeSize), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (text.startsWith(KEY_1)) {
            ssb.delete(0, KEY_1.length());
            ssb.setSpan(new RelativeSizeSpan(mHeader2RelativeSize), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (text.startsWith(KEY_0)) {
            ssb.delete(0, KEY_0.length());
            ssb.setSpan(new RelativeSizeSpan(mHeader1RelativeSize), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        marginSSBLeft(ssb, 10);
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }
}
