package com.yydcdut.rxmarkdown.factory;

import com.yydcdut.rxmarkdown.grammar.Header3Grammar;
import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.html.IHGrammar;

/**
 * Created by yuyidong on 16/5/11.
 */
public class Header3Factory implements IGrammarFactory {
    @Override
    public IGrammar getGrammar() {
        return new Header3Grammar();
    }

    @Override
    public IHGrammar getHtmlGrammar() {
        return null;
    }
}
