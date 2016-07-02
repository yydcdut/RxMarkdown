package com.yydcdut.rxmarkdown.grammar.edit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.edit.EditToken;
import com.yydcdut.rxmarkdown.grammar.IGrammar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyidong on 16/7/2.
 */
class NormalGrammar implements IGrammar {

    NormalGrammar(@Nullable RxMDConfiguration rxMDConfiguration) {
    }

    @Override
    public boolean isMatch(@NonNull CharSequence charSequence) {
        return false;
    }

    @NonNull
    @Override
    public CharSequence format(@NonNull CharSequence charSequence) {
        return null;
    }

    @NonNull
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        return new ArrayList<>();
    }
}
