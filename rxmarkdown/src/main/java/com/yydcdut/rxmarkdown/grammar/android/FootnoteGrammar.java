package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.SuperscriptSpan;

import java.util.regex.Pattern;

/**
 * Created by yuyidong on 16/5/13.
 */
class FootnoteGrammar extends AbsAndroidGrammar {
    private static final String KEY_BEGIN = "[^";
    private static final String KEY_END = "]";

    private static final String KEY_BACKSLASH_VALUE_0 = KEY_BACKSLASH + "[";
    private static final String KEY_BACKSLASH_VALUE_2 = KEY_BACKSLASH + "]";

    @Override
    boolean isMatch(@NonNull String text) {
        if (!(text.contains(KEY_BEGIN) && text.contains(KEY_END))) {
            return false;
        }
        Pattern pattern = Pattern.compile(".*[\\[\\^].*[]].*");
        return pattern.matcher(text).matches();
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
        return complex(text, ssb);
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

    private SpannableStringBuilder complex(String text, SpannableStringBuilder ssb) {
        SpannableStringBuilder tmp = new SpannableStringBuilder();
        String tmpTotal = text;
        while (true) {
            int positionHeader = tmpTotal.indexOf(KEY_BEGIN);
            if (positionHeader == -1) {
                tmp.append(tmpTotal.substring(0, tmpTotal.length()));
                break;
            }
            tmp.append(tmpTotal.substring(0, positionHeader));
            int index = tmp.length();
            tmpTotal = tmpTotal.substring(positionHeader + KEY_BEGIN.length(), tmpTotal.length());
            int positionFooter = tmpTotal.indexOf(KEY_END);
            if (positionFooter != -1) {
                ssb.delete(tmp.length(), tmp.length() + KEY_BEGIN.length());
                tmp.append(tmpTotal.substring(0, positionFooter));
                ssb.setSpan(new SuperscriptSpan(), index, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.delete(tmp.length(), tmp.length() + KEY_END.length());
            } else {
                tmp.append(KEY_BEGIN);
                tmp.append(tmpTotal.substring(0, tmpTotal.length()));
                break;
            }
            tmpTotal = tmpTotal.substring(positionFooter + KEY_BEGIN.length(), tmpTotal.length());
        }
        return ssb;
    }

    @Override
    public String toString() {
        return "SuperScriptGrammarAbs{}";
    }
}
