package com.yydcdut.rxmarkdown.grammar.edit;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.style.RelativeSizeSpan;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.edit.EditToken;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuyidong on 16/6/30.
 */
class HeaderGrammar extends EditGrammarAdapter {
    /**
     * {@link com.yydcdut.rxmarkdown.grammar.android.CenterAlignGrammar#KEY_0_CENTER_ALIGN}
     */
    private static final String KEY_0_CENTER_ALIGN = "[";
    /**
     * {@link com.yydcdut.rxmarkdown.grammar.android.HeaderGrammar#KEY_0_HEADER}
     */
    private static final String KEY_0_HEADER = "# ";
    /**
     * {@link com.yydcdut.rxmarkdown.grammar.android.HeaderGrammar#KEY_1_HEADER}
     */
    private static final String KEY_1_HEADER = "## ";
    /**
     * {@link com.yydcdut.rxmarkdown.grammar.android.HeaderGrammar#KEY_2_HEADER}
     */
    private static final String KEY_2_HEADER = "### ";
    /**
     * {@link com.yydcdut.rxmarkdown.grammar.android.HeaderGrammar#KEY_3_HEADER}
     */
    private static final String KEY_3_HEADER = "#### ";
    /**
     * {@link com.yydcdut.rxmarkdown.grammar.android.HeaderGrammar#KEY_4_HEADER}
     */
    private static final String KEY_4_HEADER = "##### ";
    /**
     * {@link com.yydcdut.rxmarkdown.grammar.android.HeaderGrammar#KEY_5_HEADER}
     */
    private static final String KEY_5_HEADER = "###### ";


    HeaderGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
    }

    @NonNull
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        List<EditToken> editTokenList = new ArrayList<>();
        StringBuilder content = new StringBuilder(editable);
        Pattern p = Pattern.compile("^(#{1,6})(.*?)$", Pattern.MULTILINE);
        Matcher m = p.matcher(content);
        List<String> matchList = new ArrayList<>();//找到的
        while (m.find()) {
            matchList.add(m.group());
        }
        Pattern p2 = Pattern.compile("^\\[(#{1,6}(.*?)\\]$)", Pattern.MULTILINE);
        Matcher m2 = p2.matcher(content);
        while (m2.find()) {
            matchList.add(m2.group());
        }
        for (String match : matchList) {
            int index = content.indexOf(match);
            int length = match.length();
            editTokenList.add(new EditToken(getSpan(match), index, index + length));
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

    private Object getSpan(String match) {
        if (match.startsWith(KEY_0_CENTER_ALIGN)) {
            if (match.contains(KEY_5_HEADER)) {
                return new RelativeSizeSpan(1.1f);
            } else if (match.contains(KEY_4_HEADER)) {
                return new RelativeSizeSpan(1.2f);
            } else if (match.contains(KEY_3_HEADER)) {
                return new RelativeSizeSpan(1.3f);
            } else if (match.contains(KEY_2_HEADER)) {
                return new RelativeSizeSpan(1.4f);
            } else if (match.contains(KEY_1_HEADER)) {
                return new RelativeSizeSpan(1.5f);
            } else if (match.contains(KEY_0_HEADER)) {
                return new RelativeSizeSpan(1.6f);
            }
        } else if (match.startsWith(KEY_5_HEADER)) {
            return new RelativeSizeSpan(1.1f);
        } else if (match.startsWith(KEY_4_HEADER)) {
            return new RelativeSizeSpan(1.2f);
        } else if (match.startsWith(KEY_3_HEADER)) {
            return new RelativeSizeSpan(1.3f);
        } else if (match.startsWith(KEY_2_HEADER)) {
            return new RelativeSizeSpan(1.4f);
        } else if (match.startsWith(KEY_1_HEADER)) {
            return new RelativeSizeSpan(1.5f);
        } else if (match.startsWith(KEY_0_HEADER)) {
            return new RelativeSizeSpan(1.6f);
        } else {
            return new RelativeSizeSpan(1.0f);
        }
        return new RelativeSizeSpan(1.0f);
    }
}
