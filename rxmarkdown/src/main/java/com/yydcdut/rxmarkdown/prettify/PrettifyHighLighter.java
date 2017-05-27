package com.yydcdut.rxmarkdown.prettify;

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

    public SpannableStringBuilder highLight(String fileExtension, SpannableStringBuilder sourceCode) {
        List<ParseResult> results = mParser.parse(fileExtension, sourceCode.toString());
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
        map.put("typ", 0xff87cefa);
        map.put("kwd", 0xff00ff00);
        map.put("lit", 0xffffff00);
        map.put("com", 0xff999999);
        map.put("str", 0xffff4500);
        map.put("pun", 0xffeeeeee);
        map.put("pln", 0xff000000);
        return map;
    }
}
