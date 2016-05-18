package com.yydcdut.rxmarkdown.grammar.android;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BulletSpan;

/**
 * Created by yuyidong on 16/5/4.
 */
class OrderListGrammar extends AbsAndroidGrammar {
    private static final char DOT = '.';

    private static final String KEY_BACKSLASH_VALUE = KEY_BACKSLASH + DOT;

    @Override
    public boolean isMatch(@NonNull String text) {
        if (text.length() < 3) {
            return false;
        }
        if (TextUtils.isDigitsOnly(text.charAt(0) + "")) {
            int dotPosition = 1;
            for (int i = 1; i < text.length(); i++) {
                char c = text.charAt(i);
                if (TextUtils.isDigitsOnly(c + "")) {
                    continue;
                } else {
                    dotPosition = i;
                    break;
                }
            }
            char dot = text.charAt(dotPosition);
            if (dot == DOT) {
                if (text.charAt(dotPosition + 1) == ' ') {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        int index = -1;
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(KEY_BACKSLASH_VALUE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + KEY_BACKSLASH_VALUE.length(), KEY_ENCODE);
        }
        return ssb;
    }

    @NonNull
    @Override
    public SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        if (ssb == null) {
            return new SpannableStringBuilder("");
        }
        String text = ssb.toString();
        if (TextUtils.isEmpty(text)) {
            return ssb;
        }
        if (!isMatch(text)) {
            return ssb;
        }
        ssb.setSpan(new BulletSpan(10, Color.TRANSPARENT), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        int index = -1;
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(KEY_ENCODE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + KEY_ENCODE.length(), KEY_BACKSLASH_VALUE);
        }
        return ssb;
    }

    @Override
    public String toString() {
        return "OrderListGrammar{}";
    }
}
