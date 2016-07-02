package com.yydcdut.rxmarkdown.grammar;

import android.support.annotation.NonNull;
import android.text.Editable;

import com.yydcdut.rxmarkdown.Grammar;
import com.yydcdut.rxmarkdown.edit.EditToken;

import java.util.List;

/**
 * Created by yuyidong on 16/5/3.
 */
public interface IGrammar extends Grammar {

    boolean isMatch(@NonNull CharSequence charSequence);

    @NonNull
    CharSequence format(@NonNull CharSequence charSequence);

    @NonNull
    List<EditToken> format(@NonNull Editable editable);
}
