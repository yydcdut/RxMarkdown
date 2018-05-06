package com.yydcdut.rxmarkdown.utils;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;

import com.yydcdut.rxmarkdown.live.EditToken;
import com.yydcdut.rxmarkdown.span.MDCodeBlockSpan;
import com.yydcdut.rxmarkdown.span.MDImageSpan;
import com.yydcdut.rxmarkdown.syntax.SyntaxKey;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuyidong on 2018/4/29.
 */
public class SyntaxUtils {

    /**
     * parse bold and italic
     *
     * @param key {@link SyntaxKey#KEY_BOLD_ASTERISK} or {@link SyntaxKey#KEY_BOLD_UNDERLINE} or
     *            {@link SyntaxKey#KEY_ITALIC_ASTERISK} or {@link SyntaxKey#KEY_ITALIC_UNDERLINE}
     * @param ssb the original content
     * @return the content after parsing
     */
    public static SpannableStringBuilder parseBoldAndItalic(@NonNull String key, @NonNull SpannableStringBuilder ssb, @NonNull Object whatSpan) {
        String text = ssb.toString();
        int keyLength = key.length();
        SpannableStringBuilder tmp = new SpannableStringBuilder();
        String tmpTotal = text;
        while (true) {
            int positionHeader = SyntaxUtils.findPosition(key, tmpTotal, ssb, tmp);
            if (positionHeader == -1) {
                tmp.append(tmpTotal.substring(0, tmpTotal.length()));
                break;
            }
            tmp.append(tmpTotal.substring(0, positionHeader));
            int index = tmp.length();
            tmpTotal = tmpTotal.substring(positionHeader + keyLength, tmpTotal.length());
            int positionFooter = SyntaxUtils.findPosition(key, tmpTotal, ssb, tmp);
            if (positionFooter != -1) {
                ssb.delete(tmp.length(), tmp.length() + keyLength);
                tmp.append(tmpTotal.substring(0, positionFooter));
                ssb.setSpan(whatSpan, index, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.delete(tmp.length(), tmp.length() + keyLength);
            } else {
                tmp.append(key);
                tmp.append(tmpTotal.substring(0, tmpTotal.length()));
                break;
            }
            tmpTotal = tmpTotal.substring(positionFooter + keyLength, tmpTotal.length());
        }
        return ssb;
    }

    /**
     * find the position of next key
     * ignore the key and key in (inline) code syntax,
     *
     * @param tmpTotal the original content, the class type is {@link String}
     * @param ssb      the original content, the class type is {@link SpannableStringBuilder}
     * @param tmp      the content that has parsed
     * @return the next position of key
     */
    private static int findPosition(@NonNull String key, @NonNull String tmpTotal, @NonNull SpannableStringBuilder ssb, @NonNull SpannableStringBuilder tmp) {
        String tmpTmpTotal = tmpTotal;
        int position = tmpTmpTotal.indexOf(key);
        if (position == -1) {
            return -1;
        } else {
            if (existCodeSyntax(ssb, tmp.length() + position, key.length())) {//key是否在code中
                StringBuilder sb = new StringBuilder(tmpTmpTotal.substring(0, position))
                        .append("$$").append(tmpTmpTotal.substring(position + key.length(), tmpTmpTotal.length()));
                return findPosition(key, sb.toString(), ssb, tmp);
            } else {
                return position;
            }
        }
    }

    /**
     * check whether contains (inline) code syntax
     *
     * @param ssb       the content
     * @param position  start position
     * @param keyLength the checking words' length
     * @return TRUE: contains
     */
    public static boolean existCodeSyntax(SpannableStringBuilder ssb, int position, int keyLength) {
        TypefaceSpan[] spans = ssb.getSpans(position, position + keyLength, TypefaceSpan.class);
        return spans.length != 0;
    }

    /**
     * check whether contains hyper link syntax
     *
     * @param ssb       the content
     * @param position  start position
     * @param keyLength the checking words' length
     * @return TRUE: contains
     */
    public static boolean existHyperLinkSyntax(SpannableStringBuilder ssb, int position, int keyLength) {
        URLSpan[] spans = ssb.getSpans(position, position + keyLength, URLSpan.class);
        return spans.length != 0;
    }

    /**
     * check whether contains image syntax
     *
     * @param ssb       the content
     * @param position  start position
     * @param keyLength the checking words' length
     * @return TRUE: contains
     */
    public static boolean existImageSyntax(SpannableStringBuilder ssb, int position, int keyLength) {
        MDImageSpan[] spans = ssb.getSpans(position, position + keyLength, MDImageSpan.class);
        return spans.length != 0;
    }

    /**
     * check whether exists code block span
     *
     * @param ssb   the text
     * @param start the start position
     * @param end   the end position
     * @return if exists, return true
     */
    public static boolean existCodeBlockSpan(@NonNull SpannableStringBuilder ssb, int start, int end) {
        MDCodeBlockSpan[] mdCodeBlockSpans = ssb.getSpans(start, end, MDCodeBlockSpan.class);
        return mdCodeBlockSpans != null && mdCodeBlockSpans.length > 0;
    }

    public static List<EditToken> parse(@NonNull Editable editable, @NonNull String pattern, String ignoreText, OnWhatSpanCallback callback) {
        StringBuilder content = new StringBuilder(editable.toString().replace(ignoreText, Utils.getPlaceHolder(ignoreText)));
        return parse(content, pattern, callback);
    }

    @NonNull
    public static List<EditToken> parse(@NonNull Editable editable, @NonNull String pattern, OnWhatSpanCallback callback) {
        StringBuilder content = new StringBuilder(editable);
        return parse(content, pattern, callback);
    }

    public static List<EditToken> parse(@NonNull StringBuilder content, @NonNull String pattern, OnWhatSpanCallback callback) {
        List<EditToken> editTokenList = new ArrayList<>();
        Matcher m = Pattern.compile(pattern).matcher(content);
        List<String> matchList = new ArrayList<>();//找到的
        while (m.find()) {
            matchList.add(m.group());
        }
        for (String match : matchList) {
            int index = content.indexOf(match);
            int length = match.length();
            editTokenList.add(new EditToken(callback.whatSpan(), index, index + length));
            content.replace(index, index + length, Utils.getPlaceHolder(match));
        }
        return editTokenList;
    }

    public interface OnWhatSpanCallback {
        Object whatSpan();
    }

    public static boolean isNeedFormat(String key, String string, String beforeString, String afterString) {
        return string.contains(key) || key.equals(beforeString) || key.equals(afterString);
    }
}
