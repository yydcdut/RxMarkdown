package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;

import com.yydcdut.rxmarkdown.edit.EditToken;
import com.yydcdut.rxmarkdown.grammar.IGrammar;

import java.util.List;

/**
 * Created by yuyidong on 16/6/29.
 */
abstract class GrammarAdapter implements IGrammar {

    @Nullable
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        return null;
    }
}
