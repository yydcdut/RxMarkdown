package com.yydcdut.rxmarkdown.utils;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyidong on 2017/6/6.
 */
public class Utils {

    public static List<Pair<Integer, Integer>> find(@NonNull String text, @NonNull String KEY_CODE) {
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        String[] lines = text.split("\n");
        int start = -1;
        int end = -1;
        int currentLength = 0;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].startsWith(KEY_CODE)) {
                if (start == -1) {
                    start = currentLength;
                } else if (end == -1 && TextUtils.equals(lines[i], KEY_CODE)) {
                    end = currentLength;
                }
                if (start != -1 && end != -1) {
                    list.add(new Pair<>(start, end));
                    start = -1;
                    end = -1;
                }
            }
            currentLength += lines[i].length() + "\n".length();
        }
        return list;
    }

    public static int findNextNewLineChar(SpannableStringBuilder ssb, int start) {//todo utils
        for (int i = start; i < ssb.length(); i++) {
            if (ssb.charAt(i) == '\n') {
                return i;
            }
        }
        return -1;
    }

    public static List<Integer> getMiddleNewLineCharPosition(SpannableStringBuilder ssb, int start, int end) {//todo utils
        List<Integer> list = new ArrayList<>();
        for (int i = start; i < end; i++) {
            if (ssb.charAt(i) == '\n') {
                list.add(i);
            }
        }
        return list;
    }

}
