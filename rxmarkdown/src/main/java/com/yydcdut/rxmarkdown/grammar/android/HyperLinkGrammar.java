package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.URLSpan;

import java.util.regex.Pattern;

/**
 * Created by yuyidong on 16/5/14.
 */
class HyperLinkGrammar extends AbsAndroidGrammar {
    private static final String KEY_0 = "[";
    private static final String KEY_1 = "](";
    private static final String KEY_2 = ")";

    private static final String PLACE_HOLDER = " ";

    private static final String KEY_BACKSLASH_VALUE_0 = KEY_BACKSLASH + "[";
    private static final String KEY_BACKSLASH_VALUE_1 = KEY_BACKSLASH + "]";
    private static final String KEY_BACKSLASH_VALUE_2 = KEY_BACKSLASH + "(";
    private static final String KEY_BACKSLASH_VALUE_3 = KEY_BACKSLASH + "]";

    @Override
    boolean isMatch(@NonNull String text) {
        if (!(text.contains(KEY_0) && text.contains(KEY_1) && text.contains(KEY_2))) {
            return false;
        }
        Pattern pattern = Pattern.compile(".*[\\[]{1}.*[\\](]{1}.*[)]{1}.*");
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
        int index3 = -1;
        while (true) {
            String text = ssb.toString();
            index3 = text.indexOf(KEY_BACKSLASH_VALUE_3);
            if (index3 == -1) {
                break;
            }
            ssb.replace(index3, index3 + KEY_BACKSLASH_VALUE_3.length(), KEY_ENCODE_3);
        }
        return ssb;
    }

    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        String text = ssb.toString();
        if (!isMatch(text)) {
            return ssb;
        }
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
        int index3 = -1;
        while (true) {
            String text = ssb.toString();
            index3 = text.indexOf(KEY_ENCODE_3);
            if (index3 == -1) {
                break;
            }
            ssb.replace(index3, index3 + KEY_ENCODE_3.length(), KEY_BACKSLASH_VALUE_3);
        }
        return ssb;
    }

    private SpannableStringBuilder complex(String text, SpannableStringBuilder ssb) {
        SpannableStringBuilder tmp = new SpannableStringBuilder();
        String tmpTotal = text;
        while (true) {
            int position4Key0 = tmpTotal.indexOf(KEY_0);
            int position4Key1 = tmpTotal.indexOf(KEY_1);
            int position4Key2 = tmpTotal.indexOf(KEY_2);
            if (position4Key0 == -1 || position4Key1 == -1 || position4Key2 == -1) {
                break;
            }
            if (position4Key0 < position4Key1 && position4Key1 < position4Key2) {
                //处理aa[bb[b](cccc)dddd
                int tmpCenter = tmpTotal.indexOf(KEY_1);
                String tmpLeft = tmpTotal.substring(0, tmpCenter);
                //正常流程
                int positionHeader = tmpLeft.lastIndexOf(KEY_0);
                tmp.append(tmpTotal.substring(0, positionHeader));
                int index = tmp.length();
                tmpTotal = tmpTotal.substring(positionHeader + KEY_0.length(), tmpTotal.length());
                int positionCenter = tmpTotal.indexOf(KEY_1);
                ssb.delete(tmp.length(), tmp.length() + KEY_0.length());
                tmp.append(tmpTotal.substring(0, positionCenter));
                tmpTotal = tmpTotal.substring(positionCenter + KEY_1.length(), tmpTotal.length());
                int positionFooter = tmpTotal.indexOf(KEY_2);
                String link = tmpTotal.substring(0, positionFooter);
                ssb.setSpan(new URLSpan(link), index, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.delete(tmp.length(), tmp.length() + KEY_1.length() + link.length() + KEY_2.length());
                tmpTotal = tmpTotal.substring(positionFooter + KEY_2.length(), tmpTotal.length());
            } else if (position4Key0 < position4Key1 && position4Key0 < position4Key2 && position4Key2 < position4Key1) {
                //111[22)22](33333)
                tmpTotal = replaceFirstOne(tmpTotal, KEY_2, PLACE_HOLDER);
            } else if (position4Key1 < position4Key0 && position4Key1 < position4Key2) {
                //](在最前面的情况 111](2222[333)4444  1111](2222)3333[4444
                tmp.append(tmpTotal.substring(0, position4Key1 + KEY_1.length()));
                tmpTotal = tmpTotal.substring(position4Key1 + KEY_1.length(), tmpTotal.length());
            } else if (position4Key2 < position4Key0 && position4Key2 < position4Key1) {
                //)在最前面的情况 111)2222](333[4444  1111)2222[3333](4444
                tmp.append(tmpTotal.substring(0, position4Key2 + KEY_2.length()));
                tmpTotal = tmpTotal.substring(position4Key2 + KEY_2.length(), tmpTotal.length());
            }
        }
        return ssb;
    }

    private String replaceFirstOne(@NonNull String content, @NonNull String target, @NonNull String replacement) {
        if (target == null) {
            throw new NullPointerException("target == null");
        }
        if (replacement == null) {
            throw new NullPointerException("replacement == null");
        }
        int matchStart = content.indexOf(target, 0);
        if (matchStart == -1) {
            return content;
        }
        int targetLength = target.length();
        if (targetLength == 0) {
            int resultLength = content.length() + (content.length() + 1) * replacement.length();
            StringBuilder result = new StringBuilder(resultLength);
            result.append(replacement);
            for (int i = 0; i != content.length(); ++i) {
                result.append(content.charAt(i));
                result.append(replacement);
            }
            return result.toString();
        }
        StringBuilder result = new StringBuilder(content.length());
        for (int i = 0; i < matchStart; ++i) {
            result.append(content.charAt(i));
        }
        result.append(replacement);
        int over = matchStart + targetLength;
        for (int i = over; i < content.length(); ++i) {
            result.append(content.charAt(i));
        }
        return result.toString();
    }
}
