package com.yydcdut.rxmarkdown.utils;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.LeadingMarginSpan;

/**
 * Created by yuyidong on 16/5/11.
 */
public class MDUtils {
    public static void marginSSBLeft(SpannableStringBuilder ssb, int every) {
        ssb.setSpan(new LeadingMarginSpan.Standard(every), 0, ssb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }
}
