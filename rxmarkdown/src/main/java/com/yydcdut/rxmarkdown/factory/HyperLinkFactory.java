package com.yydcdut.rxmarkdown.factory;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.grammar.android.HyperLinkGrammar;
import com.yydcdut.rxmarkdown.grammar.edit.NormalGrammar;

/**
 * Created by yuyidong on 2017/5/21.
 */
public class HyperLinkFactory extends SyntaxFactory {
    public HyperLinkFactory(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
    }

    @Override
    public IGrammar createTextSyntax() {
        return new HyperLinkGrammar(mConfiguration);
    }

    @Override
    public IGrammar createEditSyntax() {
        return new NormalGrammar(mConfiguration);
    }
}
