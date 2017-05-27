package com.yydcdut.rxmarkdown.prettify;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import prettify.PrettifyParser;
import syntaxhighlight.ParseResult;
import syntaxhighlight.Parser;

/**
 * Created by yuyidong on 2017/5/26.
 */
public class PrettifyHighLighter {
    private Map<String, Integer> mColorMap;
    private Parser mParser;

    public PrettifyHighLighter() {
        mColorMap = buildColorsMap();
        mParser = new PrettifyParser();
    }

    public SpannableStringBuilder highLight(String language, SpannableStringBuilder sourceCode) {
        List<ParseResult> results = mParser.parse(language, sourceCode.toString());
        for (ParseResult result : results) {
            String type = result.getStyleKeys().get(0);
            sourceCode.setSpan(new ForegroundColorSpan(getColor(type)), result.getOffset(), result.getOffset() + result.getLength(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return sourceCode;
    }

    private int getColor(String type) {
        return mColorMap.containsKey(type) ? mColorMap.get(type) : mColorMap.get("pln");
    }

    private static Map<String, Integer> buildColorsMap() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("typ", 0xff660066);
        map.put("kwd", 0xff000088);
        map.put("lit", 0xff006666);
        map.put("com", 0xff880000);
        map.put("str", 0xff008800);
        map.put("pun", 0xff666600);
        map.put("tag", 0xff000088);
        map.put("pln", 0xff000000);
        map.put("nocode", 0xff000000);
        map.put("dec", 0xff000000);
        map.put("atn", 0xff660066);
        map.put("atv", 0xff008800);
        map.put("opn", 0xff666600);
        map.put("clo", 0xff666600);
        map.put("var", 0xff660066);
        map.put("fun", Color.RED);
        return map;
    }
}
