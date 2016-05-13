package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.SuperscriptSpan;

import java.util.regex.Pattern;

/**
 * Created by yuyidong on 16/5/13.
 */
class SuperscriptGrammar extends AbsAndroidGrammar {
    private static final String KEY_BEGIN = "[^";
    private static final String KEY_END = "]";

    @Override
    boolean isMatch(@NonNull String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        if (!(text.contains(KEY_BEGIN) && text.contains(KEY_END))) {
            return false;
        }
        Pattern pattern = Pattern.compile(".*[\\[\\^].*[]].*");
        return pattern.matcher(text).matches();
    }

    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        if (ssb == null) {
            return new SpannableStringBuilder("");
        }
        String text = ssb.toString();
        if (TextUtils.isEmpty(text)) {
            return ssb;
        }
        if (!(text.contains(KEY_BEGIN) && text.contains(KEY_END))) {
            return ssb;
        }
        if (!isMatch(text)) {
            return ssb;
        }
        return complex(text, ssb);
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
