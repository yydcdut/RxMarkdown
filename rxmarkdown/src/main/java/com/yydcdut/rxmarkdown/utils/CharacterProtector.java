package com.yydcdut.rxmarkdown.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Random;

/**
 * Created by yuyidong on 2018/4/29.
 */
public class CharacterProtector {
    private static final String GOOD_CHARS = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
    private static String KEY_ENCODE = null;
    private static String KEY_ENCODE_1 = null;
    private static String KEY_ENCODE_2 = null;
    private static String KEY_ENCODE_3 = null;
    private static String KEY_ENCODE_4 = null;

    private static String longRandomString() {
        StringBuilder sb = new StringBuilder();
        final int CHAR_MAX = GOOD_CHARS.length();
        for (int i = 0; i < 20; i++) {
            sb.append(GOOD_CHARS.charAt(new Random().nextInt(CHAR_MAX)));
        }
        return sb.toString();
    }

    public static String getKeyEncode() {
        if (KEY_ENCODE == null) {
            KEY_ENCODE = longRandomString();
            if (checkEquals(KEY_ENCODE, new String[]{KEY_ENCODE_1, KEY_ENCODE_2, KEY_ENCODE_3, KEY_ENCODE_4})) {
                KEY_ENCODE = null;
                return getKeyEncode();
            }
        }
        return KEY_ENCODE;
    }

    public static String getKeyEncode1() {
        if (KEY_ENCODE_1 == null) {
            KEY_ENCODE_1 = longRandomString();
            if (checkEquals(KEY_ENCODE_1, new String[]{KEY_ENCODE, KEY_ENCODE_2, KEY_ENCODE_3, KEY_ENCODE_4})) {
                KEY_ENCODE_1 = null;
                return getKeyEncode1();
            }
        }
        return KEY_ENCODE_1;
    }

    public static String getKeyEncode2() {
        if (KEY_ENCODE_2 == null) {
            KEY_ENCODE_2 = longRandomString();
            if (checkEquals(KEY_ENCODE_2, new String[]{KEY_ENCODE, KEY_ENCODE_1, KEY_ENCODE_3, KEY_ENCODE_4})) {
                KEY_ENCODE_2 = null;
                return getKeyEncode2();
            }
        }
        return KEY_ENCODE_2;
    }

    public static String getKeyEncode3() {
        if (KEY_ENCODE_3 == null) {
            KEY_ENCODE_3 = longRandomString();
            if (checkEquals(KEY_ENCODE_3, new String[]{KEY_ENCODE, KEY_ENCODE_1, KEY_ENCODE_2, KEY_ENCODE_4})) {
                KEY_ENCODE_3 = null;
                return getKeyEncode3();
            }
        }
        return KEY_ENCODE_3;
    }

    public static String getKeyEncode4() {
        if (KEY_ENCODE_4 == null) {
            KEY_ENCODE_4 = longRandomString();
            if (checkEquals(KEY_ENCODE_4, new String[]{KEY_ENCODE, KEY_ENCODE_1, KEY_ENCODE_2, KEY_ENCODE_3})) {
                KEY_ENCODE_4 = null;
                return getKeyEncode4();
            }
        }
        return KEY_ENCODE_4;
    }

    private static boolean checkEquals(@NonNull String result, @NonNull String[] compareList) {
        final int count = compareList.length;
        for (int i = 0; i < count; i++) {
            if (TextUtils.equals(result, compareList[i])) {
                return true;
            }
        }
        return false;
    }

}
