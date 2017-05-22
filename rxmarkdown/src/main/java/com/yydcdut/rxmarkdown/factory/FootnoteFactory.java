package com.yydcdut.rxmarkdown.factory;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.grammar.android.FootnoteGrammar;
import com.yydcdut.rxmarkdown.grammar.edit.NormalGrammar;

/**
 * Created by yuyidong on 2017/5/21.
 */
public class FootnoteFactory extends SyntaxFactory {

    public FootnoteFactory(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
    }

    @Override
    public IGrammar createTextSyntax() {
        return new FootnoteGrammar(mConfiguration);
    }

    @Override
    public IGrammar createEditSyntax() {
        return new NormalGrammar(mConfiguration);
    }
}
