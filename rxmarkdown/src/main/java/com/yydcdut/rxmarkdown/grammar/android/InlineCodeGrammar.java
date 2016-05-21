package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;

import java.util.regex.Pattern;

/**
 * Created by yuyidong on 16/5/13.
 * Key 与 CodeGrammar 有关联
 */
class InlineCodeGrammar extends AbsAndroidGrammar {
    private static final String KEY = "`";

    private static final String KEY_BACKSLASH_VALUE = KEY_BACKSLASH + KEY;

    @Override
    boolean isMatch(@NonNull String text) {
        if (!text.contains(KEY)) {
            return false;
        }
        Pattern pattern = Pattern.compile(".*[`]{1}.*[`]{1}.*");
        return pattern.matcher(text).matches();
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

    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        String text = ssb.toString();
        return complex(text, ssb);
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

    private SpannableStringBuilder complex(String text, SpannableStringBuilder ssb) {
        if (text.startsWith(KEY) && text.length() == 3 &&
                KEY.equals(text.charAt(1) + "") && KEY.equals(text.charAt(2) + "")) {
            return ssb;
        }
        SpannableStringBuilder tmp = new SpannableStringBuilder();
        String tmpTotal = text;
        while (true) {
            int positionHeader = tmpTotal.indexOf(KEY);
            if (positionHeader == -1) {
                tmp.append(tmpTotal.substring(0, tmpTotal.length()));
                break;
            }
            tmp.append(tmpTotal.substring(0, positionHeader));
            int index = tmp.length();
            tmpTotal = tmpTotal.substring(positionHeader + KEY.length(), tmpTotal.length());
            int positionFooter = tmpTotal.indexOf(KEY);
            if (positionFooter != -1) {
                ssb.delete(tmp.length(), tmp.length() + KEY.length());
                tmp.append(tmpTotal.substring(0, positionFooter));
                ssb.setSpan(new BackgroundColorSpan(0xff0099cc), index, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.delete(tmp.length(), tmp.length() + KEY.length());
            } else {
                tmp.append(KEY);
                tmp.append(tmpTotal.substring(0, tmpTotal.length()));
                break;
            }
            tmpTotal = tmpTotal.substring(positionFooter + KEY.length(), tmpTotal.length());
        }
        return ssb;
    }
}
