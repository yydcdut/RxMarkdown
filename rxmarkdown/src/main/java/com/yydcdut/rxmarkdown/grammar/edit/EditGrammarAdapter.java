package com.yydcdut.rxmarkdown.grammar.edit;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.grammar.IGrammar;

/**
 * Created by yuyidong on 16/6/29.
 */
abstract class EditGrammarAdapter implements IGrammar {

    EditGrammarAdapter(@NonNull RxMDConfiguration rxMDConfiguration) {
    }

    @Override
    public boolean isMatch(@NonNull CharSequence charSequence) {
        return true;
    }

    @NonNull
    @Override
    public CharSequence format(@NonNull CharSequence charSequence) {
        return null;
    }

    protected String getPlaceHolder(String matchString) {
        int length = matchString.length();
        StringBuilder placeHolder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            placeHolder.append(" ");
        }
        return placeHolder.toString();
    }
}
