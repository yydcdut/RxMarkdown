package com.yydcdut.rxmarkdown.grammar.android;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.yydcdut.rxmarkdown.span.CustomTodoDoneSpan;

/**
 * Created by yuyidong on 16/5/17.
 */
public class TodoDoneGrammar extends AbsAndroidGrammar {
    private static final String KEY_0 = "- [x] ";
    private static final String KEY_1 = "- [X] ";

    private static final String KEY_BACKSLASH_VALUE_0 = KEY_BACKSLASH + "-";
    private static final String KEY_BACKSLASH_VALUE_1 = KEY_BACKSLASH + "[";
    private static final String KEY_BACKSLASH_VALUE_2 = KEY_BACKSLASH + "]";

    private static final int START_POSITION = 6;

    @Override
    boolean isMatch(@NonNull String text) {
        return text.startsWith(KEY_0) ||
                text.startsWith(KEY_1);
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        int index0 = -1;
        while (true) {
            String text = ssb.toString();
            index0 = text.indexOf(KEY_BACKSLASH_VALUE_0);
            if (index0 == -1) {
                break;
            }
            ssb.replace(index0, index0 + KEY_BACKSLASH_VALUE_0.length(), KEY_ENCODE);
        }
        int index1 = -1;
        while (true) {
            String text = ssb.toString();
            index1 = text.indexOf(KEY_BACKSLASH_VALUE_1);
            if (index1 == -1) {
                break;
            }
            ssb.replace(index1, index1 + KEY_BACKSLASH_VALUE_1.length(), KEY_ENCODE_1);
        }
        int index2 = -1;
        while (true) {
            String text = ssb.toString();
            index2 = text.indexOf(KEY_BACKSLASH_VALUE_2);
            if (index2 == -1) {
                break;
            }
            ssb.replace(index2, index2 + KEY_BACKSLASH_VALUE_2.length(), KEY_ENCODE_2);
        }
        return ssb;
    }

    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        String text = ssb.toString();
        if (!isMatch(text)) {
            return ssb;
        }
        ssb.delete(0, START_POSITION);
        ssb.setSpan(new CustomTodoDoneSpan(Color.DKGRAY), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        int index0 = -1;
        while (true) {
            String text = ssb.toString();
            index0 = text.indexOf(KEY_ENCODE);
            if (index0 == -1) {
                break;
            }
            ssb.replace(index0, index0 + KEY_ENCODE.length(), KEY_BACKSLASH_VALUE_0);
        }
        int index1 = -1;
        while (true) {
            String text = ssb.toString();
            index1 = text.indexOf(KEY_ENCODE_1);
            if (index1 == -1) {
                break;
            }
            ssb.replace(index1, index1 + KEY_ENCODE_1.length(), KEY_BACKSLASH_VALUE_1);
        }
        int index2 = -1;
        while (true) {
            String text = ssb.toString();
            index2 = text.indexOf(KEY_ENCODE_2);
            if (index2 == -1) {
                break;
            }
            ssb.replace(index2, index2 + KEY_ENCODE_2.length(), KEY_BACKSLASH_VALUE_2);
        }
        return ssb;
    }
}
