package com.yydcdut.rxmarkdown.factory;

import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.grammar.NormalGrammar;
import com.yydcdut.rxmarkdown.html.IHGrammar;

/**
 * Created by yuyidong on 16/5/11.
 */
public class NormalFactory implements IGrammarFactory {
    @Override
    public IGrammar getGrammar() {
        return new NormalGrammar();
    }

    @Override
    public IHGrammar getHtmlGrammar() {
        return null;
    }
}
