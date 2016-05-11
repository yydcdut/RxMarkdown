package com.yydcdut.rxmarkdown.factory;

import com.yydcdut.rxmarkdown.grammar.Header1Grammar;
import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.html.IHGrammar;

/**
 * Created by yuyidong on 16/5/11.
 */
public class Header1Factory implements IGrammarFactory {
    @Override
    public IGrammar getGrammar() {
        return new Header1Grammar();
    }

    @Override
    public IHGrammar getHtmlGrammar() {
        return null;
    }
}
