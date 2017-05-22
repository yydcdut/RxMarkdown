package com.yydcdut.rxmarkdown.factory;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.grammar.IGrammar;

/**
 * Created by yuyidong on 2017/5/20.
 */
public abstract class SyntaxFactory {

    protected RxMDConfiguration mConfiguration;

    public SyntaxFactory(@NonNull RxMDConfiguration rxMDConfiguration) {
        this.mConfiguration = rxMDConfiguration;
    }

    public abstract IGrammar createTextSyntax();

    public abstract IGrammar createEditSyntax();
}
