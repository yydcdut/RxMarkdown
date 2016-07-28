/*
 * Copyright (C) 2016 yydcdut (yuyidong2015@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.callback.OnLinkClickCallback;
import com.yydcdut.rxmarkdown.span.MDURLSpan;

import java.util.regex.Pattern;

import static com.yydcdut.rxmarkdown.grammar.android.BackslashGrammar.KEY_BACKSLASH;

/**
 * The implementation of grammar for hyper link.
 * Grammar:
 * "[content](http://link.html)"
 * <p>
 * Created by yuyidong on 16/5/14.
 */
class HyperLinkGrammar extends AbsAndroidGrammar {

    protected static final String KEY_0_HYPER_LINK = "[";
    protected static final String KEY_1_HYPER_LINK = "](";
    protected static final String KEY_2_HYPER_LINK = ")";

    private static final String PLACE_HOLDER = " ";

    protected static final String KEY_BACKSLASH_VALUE_0 = KEY_BACKSLASH + "[";
    protected static final String KEY_BACKSLASH_VALUE_1 = KEY_BACKSLASH + "]";
    protected static final String KEY_BACKSLASH_VALUE_3 = KEY_BACKSLASH + ")";

    private int mColor;
    private boolean isUnderLine;
    private OnLinkClickCallback mOnLinkClickCallback;

    HyperLinkGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
        mColor = rxMDConfiguration.getLinkColor();
        isUnderLine = rxMDConfiguration.isLinkUnderline();
        mOnLinkClickCallback = rxMDConfiguration.getOnLinkClickCallback();
    }

    @Override
    boolean isMatch(@NonNull String text) {
        if (!(text.contains(KEY_0_HYPER_LINK) && text.contains(KEY_1_HYPER_LINK) && text.contains(KEY_2_HYPER_LINK))) {
            return false;
        }
        Pattern pattern = Pattern.compile(".*[\\[]{1}.*[\\](]{1}.*[)]{1}.*");
        return pattern.matcher(text).matches();
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        int index0;
        while (true) {
            String text = ssb.toString();
            index0 = text.indexOf(KEY_BACKSLASH_VALUE_0);
            if (index0 == -1) {
                break;
            }
            ssb.replace(index0, index0 + KEY_BACKSLASH_VALUE_0.length(), BackslashGrammar.KEY_ENCODE);
        }
        int index1;
        while (true) {
            String text = ssb.toString();
            index1 = text.indexOf(KEY_BACKSLASH_VALUE_1);
            if (index1 == -1) {
                break;
            }
            ssb.replace(index1, index1 + KEY_BACKSLASH_VALUE_1.length(), BackslashGrammar.KEY_ENCODE_1);
        }
        int index3;
        while (true) {
            String text = ssb.toString();
            index3 = text.indexOf(KEY_BACKSLASH_VALUE_3);
            if (index3 == -1) {
                break;
            }
            ssb.replace(index3, index3 + KEY_BACKSLASH_VALUE_3.length(), BackslashGrammar.KEY_ENCODE_3);
        }
        return ssb;
    }

    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        String text = ssb.toString();
        return parse(text, ssb);
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        int index0;
        while (true) {
            String text = ssb.toString();
            index0 = text.indexOf(BackslashGrammar.KEY_ENCODE);
            if (index0 == -1) {
                break;
            }
            ssb.replace(index0, index0 + BackslashGrammar.KEY_ENCODE.length(), KEY_BACKSLASH_VALUE_0);
        }
        int index1;
        while (true) {
            String text = ssb.toString();
            index1 = text.indexOf(BackslashGrammar.KEY_ENCODE_1);
            if (index1 == -1) {
                break;
            }
            ssb.replace(index1, index1 + BackslashGrammar.KEY_ENCODE_1.length(), KEY_BACKSLASH_VALUE_1);
        }
        int index3;
        while (true) {
            String text = ssb.toString();
            index3 = text.indexOf(BackslashGrammar.KEY_ENCODE_3);
            if (index3 == -1) {
                break;
            }
            ssb.replace(index3, index3 + BackslashGrammar.KEY_ENCODE_3.length(), KEY_BACKSLASH_VALUE_3);
        }
        return ssb;
    }

    /**
     * parse
     *
     * @param text the original content,the class type is {@link String}
     * @param ssb  the original content,the class type is {@link SpannableStringBuilder}
     * @return the content after parsing
     */
    @NonNull
    private SpannableStringBuilder parse(@NonNull String text, @NonNull SpannableStringBuilder ssb) {
        SpannableStringBuilder tmp = new SpannableStringBuilder();
        String tmpTotal = text;
        while (true) {
            int position4Key0 = tmpTotal.indexOf(KEY_0_HYPER_LINK);
            int position4Key1 = tmpTotal.indexOf(KEY_1_HYPER_LINK);
            int position4Key2 = tmpTotal.indexOf(KEY_2_HYPER_LINK);
            if (position4Key0 == -1 || position4Key1 == -1 || position4Key2 == -1) {
                break;
            }
            if (position4Key0 < position4Key1 && position4Key1 < position4Key2) {
                //处理aa[bb[b](cccc)dddd
                int tmpCenter = tmpTotal.indexOf(KEY_1_HYPER_LINK);
                String tmpLeft = tmpTotal.substring(0, tmpCenter);
                //正常流程
                int positionHeader = tmpLeft.lastIndexOf(KEY_0_HYPER_LINK);
                tmp.append(tmpTotal.substring(0, positionHeader));
                int index = tmp.length();
                tmpTotal = tmpTotal.substring(positionHeader + KEY_0_HYPER_LINK.length(), tmpTotal.length());
                int positionCenter = tmpTotal.indexOf(KEY_1_HYPER_LINK);
                ssb.delete(tmp.length(), tmp.length() + KEY_0_HYPER_LINK.length());
                tmp.append(tmpTotal.substring(0, positionCenter));
                tmpTotal = tmpTotal.substring(positionCenter + KEY_1_HYPER_LINK.length(), tmpTotal.length());
                int positionFooter = tmpTotal.indexOf(KEY_2_HYPER_LINK);
                String link = tmpTotal.substring(0, positionFooter);
                ssb.setSpan(new MDURLSpan(link, mColor, isUnderLine, mOnLinkClickCallback), index, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.delete(tmp.length(), tmp.length() + KEY_1_HYPER_LINK.length() + link.length() + KEY_2_HYPER_LINK.length());
                tmpTotal = tmpTotal.substring(positionFooter + KEY_2_HYPER_LINK.length(), tmpTotal.length());
            } else if (position4Key0 < position4Key1 && position4Key0 < position4Key2 && position4Key2 < position4Key1) {
                //111[22)22](33333)
                tmpTotal = replaceFirstOne(tmpTotal, KEY_2_HYPER_LINK, PLACE_HOLDER);
            } else if (position4Key1 < position4Key0 && position4Key1 < position4Key2) {
                //](在最前面的情况 111](2222[333)4444  1111](2222)3333[4444
                tmp.append(tmpTotal.substring(0, position4Key1 + KEY_1_HYPER_LINK.length()));
                tmpTotal = tmpTotal.substring(position4Key1 + KEY_1_HYPER_LINK.length(), tmpTotal.length());
            } else if (position4Key2 < position4Key0 && position4Key2 < position4Key1) {
                //)在最前面的情况 111)2222](333[4444  1111)2222[3333](4444
                tmp.append(tmpTotal.substring(0, position4Key2 + KEY_2_HYPER_LINK.length()));
                tmpTotal = tmpTotal.substring(position4Key2 + KEY_2_HYPER_LINK.length(), tmpTotal.length());
            }
        }
        return ssb;
    }

    /**
     * replace the key words
     *
     * @param content     the original content
     * @param target      the key words
     * @param replacement the replacement string
     * @return
     */
    @NonNull
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
