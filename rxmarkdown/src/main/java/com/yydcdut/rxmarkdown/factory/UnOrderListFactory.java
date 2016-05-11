package com.yydcdut.rxmarkdown.factory;

import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.grammar.UnOrderListGrammar;
import com.yydcdut.rxmarkdown.html.IHGrammar;

/**
 * Created by yuyidong on 16/5/11.
 */
public class UnOrderListFactory implements IGrammarFactory {
    @Override
    public IGrammar getGrammar() {
        return new UnOrderListGrammar();
    }

    @Override
    public IHGrammar getHtmlGrammar() {
        return null;
    }
}
