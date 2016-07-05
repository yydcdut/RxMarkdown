package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;

import com.yydcdut.rxmarkdown.RxMDConfiguration;

import java.util.regex.Pattern;

import static com.yydcdut.rxmarkdown.grammar.android.BackslashGrammar.KEY_BACKSLASH;

/**
 * Created by yuyidong on 16/5/13.
 */
class InlineCodeGrammar extends AbsAndroidGrammar {
    protected static final String KEY_INLINE_CODE = "`";

    protected static final String KEY_BACKSLASH_VALUE = KEY_BACKSLASH + KEY_INLINE_CODE;

    private int mColor;

    InlineCodeGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
        mColor = rxMDConfiguration.getInlineCodeBgColor();
    }

    @Override
    boolean isMatch(@NonNull String text) {
        if (!text.contains(KEY_INLINE_CODE)) {
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
            ssb.replace(index, index + KEY_BACKSLASH_VALUE.length(), BackslashGrammar.KEY_ENCODE);
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
            index = text.indexOf(BackslashGrammar.KEY_ENCODE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + BackslashGrammar.KEY_ENCODE.length(), KEY_BACKSLASH_VALUE);
        }
        return ssb;
    }

    @NonNull
    private SpannableStringBuilder complex(@NonNull String text, @NonNull SpannableStringBuilder ssb) {
        SpannableStringBuilder tmp = new SpannableStringBuilder();
        String tmpTotal = text;
        while (true) {
            int positionHeader = findPosition(tmpTotal, ssb, tmp);
            if (positionHeader == -1) {
                tmp.append(tmpTotal.substring(0, tmpTotal.length()));
                break;
            }
            tmp.append(tmpTotal.substring(0, positionHeader));
            int index = tmp.length();
            tmpTotal = tmpTotal.substring(positionHeader + KEY_INLINE_CODE.length(), tmpTotal.length());
            int positionFooter = findPosition(tmpTotal, ssb, tmp);
            if (positionFooter != -1) {
                ssb.delete(tmp.length(), tmp.length() + KEY_INLINE_CODE.length());
                tmp.append(tmpTotal.substring(0, positionFooter));
                ssb.setSpan(new BackgroundColorSpan(mColor), index, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.delete(tmp.length(), tmp.length() + KEY_INLINE_CODE.length());
            } else {
                tmp.append(KEY_INLINE_CODE);
                tmp.append(tmpTotal.substring(0, tmpTotal.length()));
                break;
            }
            tmpTotal = tmpTotal.substring(positionFooter + KEY_INLINE_CODE.length(), tmpTotal.length());
        }
        return ssb;
    }

    private int findPosition(@NonNull String tmpTotal, @NonNull SpannableStringBuilder ssb, @NonNull SpannableStringBuilder tmp) {
        String tmpTmpTotal = tmpTotal;
        int position = tmpTmpTotal.indexOf(KEY_INLINE_CODE);
        if (position == -1) {
            return -1;
        } else {
            if (checkInHyperLink(ssb, tmp.length() + position, KEY_INLINE_CODE.length()) ||
                    checkInImage(ssb, tmp.length() + position, KEY_INLINE_CODE.length())) {//key是否在HyperLink或者CustomImage中
                StringBuilder sb = new StringBuilder(tmpTmpTotal.substring(0, position))
                        .append("$").append(tmpTmpTotal.substring(position + KEY_INLINE_CODE.length(), tmpTmpTotal.length()));
                return findPosition(sb.toString(), ssb, tmp);
            } else {
                return position;
            }
        }
    }

}
