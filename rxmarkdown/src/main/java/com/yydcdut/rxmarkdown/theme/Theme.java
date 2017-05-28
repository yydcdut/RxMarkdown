package com.yydcdut.rxmarkdown.theme;

import android.support.annotation.ColorInt;

/**
 * Created by yuyidong on 2017/5/27.
 */
public interface Theme {
    String CODE_TYP = "typ";
    String CODE_KWD = "kwd";
    String CODE_LIT = "lit";
    String CODE_COM = "com";
    String CODE_STR = "str";
    String CODE_PUN = "pun";
    String CODE_TAG = "tag";
    String CODE_PLN = "pln";
    String CODE_DEC = "dec";
    String CODE_ATN = "atn";
    String CODE_ATV = "atv";
    String CODE_OPN = "opn";
    String CODE_CLO = "clo";
    String CODE_VAR = "var";
    String CODE_FUN = "fun";
    String CODE_NOCODE = "nocode";

    @ColorInt
    int getBackgroundColor();

    @ColorInt
    int getTypColor();

    @ColorInt
    int getKwdColor();

    @ColorInt
    int getLitColor();

    @ColorInt
    int getComColor();

    @ColorInt
    int getStrColor();

    @ColorInt
    int getPunColor();

    @ColorInt
    int getTagColor();

    @ColorInt
    int getPlnColor();

    @ColorInt
    int getDecColor();

    @ColorInt
    int getAtnColor();

    @ColorInt
    int getAtvColor();

    @ColorInt
    int getOpnColor();

    @ColorInt
    int getCloColor();

    @ColorInt
    int getVarColor();

    @ColorInt
    int getFunColor();

    @ColorInt
    int getNocodeColor();
}
