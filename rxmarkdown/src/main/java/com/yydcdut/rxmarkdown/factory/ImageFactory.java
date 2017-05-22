package com.yydcdut.rxmarkdown.factory;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.grammar.android.ImageGrammar;
import com.yydcdut.rxmarkdown.grammar.edit.NormalGrammar;

/**
 * Created by yuyidong on 2017/5/21.
 */
public class ImageFactory extends SyntaxFactory {
    public ImageFactory(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
    }

    @Override
    public IGrammar createTextSyntax() {
        return new ImageGrammar(mConfiguration);
    }

    @Override
    public IGrammar createEditSyntax() {
        return new NormalGrammar(mConfiguration);
    }
}
