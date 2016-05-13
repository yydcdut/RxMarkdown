package com.yydcdut.rxmarkdown.grammar.android;

import android.text.Spannable;
import android.text.SpannableStringBuilder;

/**
 * Created by yuyidong on 16/5/13.
 */
abstract class LiteGrammar extends AbsAndroidGrammar {

    protected static SpannableStringBuilder complex(String text, SpannableStringBuilder ssb, final String KEY, Object style) {
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
                ssb.setSpan(style, index, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
