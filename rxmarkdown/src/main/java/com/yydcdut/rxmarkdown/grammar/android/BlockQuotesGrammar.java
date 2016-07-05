package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.span.MDQuoteSpan;

/**
 * Created by yuyidong on 16/5/4.
 */
class BlockQuotesGrammar extends AbsAndroidGrammar {
    private int mColor;

    BlockQuotesGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
        mColor = rxMDConfiguration.getBlockQuotesColor();
    }

    @Override
    boolean isMatch(@NonNull String text) {
        if (!text.startsWith(KEY_BLOCK_QUOTES)) {
            return false;
        }
        return true;
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        int nested = calculateNested(ssb.toString());
        if (nested == 0) {
            return ssb;
        }
        ssb.replace(0, nested * KEY_BLOCK_QUOTES.length() - 1, getHolder(nested));
        ssb.setSpan(new MDQuoteSpan(mColor, nested), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        marginSSBLeft(ssb, 20);
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    /**
     * 有一个 "> " 就算嵌套一层
     *
     * @param text
     * @return
     */
    private static int calculateNested(@NonNull String text) {
        int nested = 0;
        while (true) {
            if ((nested + 1) * KEY_BLOCK_QUOTES.length() > text.length()) {
                break;
            }
            String sub = text.substring(nested * KEY_BLOCK_QUOTES.length(), (nested + 1) * KEY_BLOCK_QUOTES.length());
            if (!KEY_BLOCK_QUOTES.equals(sub)) {
                break;
            }
            ++nested;
        }
        return nested;
    }

    private String getHolder(int nested) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < nested; i++) {
            stringBuilder.append("   ");
        }
        return stringBuilder.toString();
    }
}
