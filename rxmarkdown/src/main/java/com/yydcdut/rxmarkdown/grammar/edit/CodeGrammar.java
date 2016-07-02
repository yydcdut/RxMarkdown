package com.yydcdut.rxmarkdown.grammar.edit;

import android.support.annotation.NonNull;
import android.text.Editable;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.edit.EditToken;
import com.yydcdut.rxmarkdown.span.MDCodeSpan;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuyidong on 16/6/30.
 */
class CodeGrammar extends EditGrammarAdapter {
    private int mColor;

    CodeGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
        mColor = rxMDConfiguration.getCodeBgColor();
    }

    @NonNull
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        List<EditToken> editTokenList = new ArrayList<>();
        StringBuilder content = new StringBuilder(editable);
        Pattern p = Pattern.compile("^```$", Pattern.MULTILINE);
        Matcher m = p.matcher(content);
        List<String> matchList = new ArrayList<>();//找到的
        while (m.find()) {
            matchList.add(m.group());
        }
        int size = matchList.size() % 2 == 0 ? matchList.size() : matchList.size() - 1;
        int index = 0;
        for (int i = 0; i < size; i++) {
            String match = matchList.get(i);
            if (i % 2 == 0) {
                index = content.indexOf(match);
                char c4 = content.charAt(index + 3);
                int length = match.length();
                content.replace(index, index + length, getPlaceHolder(getPlaceHolder(match)));
                if (c4 != '\n') {
                    index = 0;
                    i--;
                    continue;
                }
            } else {
                int currentIndex = content.indexOf(match);
                int length = match.length();
                content.replace(currentIndex, currentIndex + length, getPlaceHolder(getPlaceHolder(match)));
                char c4 = content.charAt(currentIndex + 3);
                if (c4 != '\n') {
                    i--;
                    continue;
                } else {
                    editTokenList.add(new EditToken(new MDCodeSpan(mColor), index, currentIndex + length));
                }
            }
        }
        return editTokenList;
    }


}
