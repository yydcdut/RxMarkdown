package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.SuperscriptSpan;

import com.yydcdut.rxmarkdown.RxMDConfiguration;

import java.util.regex.Pattern;

import static com.yydcdut.rxmarkdown.grammar.android.BackslashGrammar.KEY_BACKSLASH;

/**
 * Created by yuyidong on 16/5/13.
 */
class FootnoteGrammar extends AbsAndroidGrammar {
    protected static final String KEY_0_FOOTNOTE = "[^";
    protected static final String KEY_1_FOOTNOTE = "]";

    protected static final String KEY_BACKSLASH_VALUE_0 = KEY_BACKSLASH + "[";
    protected static final String KEY_BACKSLASH_VALUE_2 = KEY_BACKSLASH + "]";

    FootnoteGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
    }

    @Override
    boolean isMatch(@NonNull String text) {
        if (!(text.contains(KEY_0_FOOTNOTE) && text.contains(KEY_1_FOOTNOTE))) {
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
            ssb.replace(index0, index0 + KEY_BACKSLASH_VALUE_0.length(), BackslashGrammar.KEY_ENCODE);
        }
        int index2 = -1;
        while (true) {
            String text = ssb.toString();
            index2 = text.indexOf(KEY_BACKSLASH_VALUE_2);
            if (index2 == -1) {
                break;
            }
            ssb.replace(index2, index2 + KEY_BACKSLASH_VALUE_2.length(), BackslashGrammar.KEY_ENCODE_2);
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
            index0 = text.indexOf(BackslashGrammar.KEY_ENCODE);
            if (index0 == -1) {
                break;
            }
            ssb.replace(index0, index0 + BackslashGrammar.KEY_ENCODE.length(), KEY_BACKSLASH_VALUE_0);
        }
        int index2 = -1;
        while (true) {
            String text = ssb.toString();
            index2 = text.indexOf(BackslashGrammar.KEY_ENCODE_2);
            if (index2 == -1) {
                break;
            }
            ssb.replace(index2, index2 + BackslashGrammar.KEY_ENCODE_2.length(), KEY_BACKSLASH_VALUE_2);
        }
        return ssb;
    }

    private SpannableStringBuilder complex(@NonNull String text, @NonNull SpannableStringBuilder ssb) {
        SpannableStringBuilder tmp = new SpannableStringBuilder();
        String tmpTotal = text;
        while (true) {
            int positionHeader = findBeginPosition(tmpTotal, ssb, tmp);
            if (positionHeader == -1) {
                tmp.append(tmpTotal.substring(0, tmpTotal.length()));
                break;
            }
            tmp.append(tmpTotal.substring(0, positionHeader));
            int index = tmp.length();
            tmpTotal = tmpTotal.substring(positionHeader + KEY_0_FOOTNOTE.length(), tmpTotal.length());
            int positionFooter = findEndPosition(tmpTotal, ssb, tmp);
            if (positionFooter != -1) {
                ssb.delete(tmp.length(), tmp.length() + KEY_0_FOOTNOTE.length());
                tmp.append(tmpTotal.substring(0, positionFooter));
                ssb.setSpan(new SuperscriptSpan(), index, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.delete(tmp.length(), tmp.length() + KEY_1_FOOTNOTE.length());
            } else {
                tmp.append(KEY_0_FOOTNOTE);
                tmp.append(tmpTotal.substring(0, tmpTotal.length()));
                break;
            }
            tmpTotal = tmpTotal.substring(positionFooter + KEY_0_FOOTNOTE.length(), tmpTotal.length());
        }
        return ssb;
    }

    private int findBeginPosition(@NonNull String tmpTotal, @NonNull SpannableStringBuilder ssb, @NonNull SpannableStringBuilder tmp) {
        String tmpTmpTotal = tmpTotal;
        int position = tmpTmpTotal.indexOf(KEY_0_FOOTNOTE);
        if (position == -1) {
            return -1;
        } else {
            if (checkInInlineCode(ssb, tmp.length() + position, KEY_0_FOOTNOTE.length())) {//key是否在inlineCode中
                StringBuilder sb = new StringBuilder(tmpTmpTotal.substring(0, position))
                        .append("$$").append(tmpTmpTotal.substring(position + KEY_0_FOOTNOTE.length(), tmpTmpTotal.length()));
                return findBeginPosition(sb.toString(), ssb, tmp);
            } else {
                return position;
            }
        }
    }

    private int findEndPosition(@NonNull String tmpTotal, @NonNull SpannableStringBuilder ssb, @NonNull SpannableStringBuilder tmp) {
        String tmpTmpTotal = tmpTotal;
        int position = tmpTmpTotal.indexOf(KEY_1_FOOTNOTE);
        if (position == -1) {
            return -1;
        } else {
            if (checkInInlineCode(ssb, tmp.length() + position, KEY_1_FOOTNOTE.length())) {//key是否在inlineCode中
                StringBuilder sb = new StringBuilder(tmpTmpTotal.substring(0, position))
                        .append("$").append(tmpTmpTotal.substring(position + KEY_1_FOOTNOTE.length(), tmpTmpTotal.length()));
                return findBeginPosition(sb.toString(), ssb, tmp);
            } else {
                return position;
            }
        }
    }
}
