package com.yydcdut.rxmarkdown.factory;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.grammar.IGrammar;

/**
 * Created by yuyidong on 2017/5/21.
 */
public class StrikeThroughFactory extends SyntaxFactory {
    public StrikeThroughFactory(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
    }

    @Override
    public IGrammar createTextSyntax() {
        return new com.yydcdut.rxmarkdown.grammar.android.StrikeThroughGrammar(mConfiguration);
    }

    @Override
    public IGrammar createEditSyntax() {
        return new com.yydcdut.rxmarkdown.grammar.edit.StrikeThroughGrammar(mConfiguration);
    }
}
