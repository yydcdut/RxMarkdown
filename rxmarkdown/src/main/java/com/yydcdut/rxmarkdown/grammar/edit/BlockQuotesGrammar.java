package com.yydcdut.rxmarkdown.grammar.edit;

import android.support.annotation.NonNull;
import android.text.Editable;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.edit.EditToken;
import com.yydcdut.rxmarkdown.span.MDQuoteSpan;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuyidong on 16/6/30.
 */
class BlockQuotesGrammar extends EditGrammarAdapter {
    private int mColor;

    BlockQuotesGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
        mColor = rxMDConfiguration.getBlockQuotesColor();
    }

    @NonNull
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        List<EditToken> editTokenList = new ArrayList<>();
        StringBuilder content = new StringBuilder(editable);
        Pattern p = Pattern.compile("^(> )(.*?)$", Pattern.MULTILINE);
        Matcher m = p.matcher(content);
        List<String> matchList = new ArrayList<>();//找到的
        while (m.find()) {
            matchList.add(m.group());
        }
        for (String match : matchList) {
            int nested = calculateNested(match);
            if (nested == 0) {
                return editTokenList;
            }
            int index = content.indexOf(match);
            int length = match.length();
            editTokenList.add(new EditToken(new MDQuoteSpan(mColor, nested, true), index, index + length));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                sb.append(" ");
            }
            StringBuilder placeHolder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                placeHolder.append(" ");
            }
            content.replace(index, index + length, placeHolder.toString());
        }
        return editTokenList;
    }

    /**
     * 有一个 "> " 就算嵌套一层
     *
     * @param text
     * @return
     */
    private static int calculateNested(@NonNull String text) {
        int nested = 0;
        while (true) {
            if ((nested + 1) * KEY_BLOCK_QUOTES.length() > text.length()) {
                break;
            }
            String sub = text.substring(nested * KEY_BLOCK_QUOTES.length(), (nested + 1) * KEY_BLOCK_QUOTES.length());
            if (!KEY_BLOCK_QUOTES.equals(sub)) {
                break;
            }
            ++nested;
        }
        return nested;
    }
}
