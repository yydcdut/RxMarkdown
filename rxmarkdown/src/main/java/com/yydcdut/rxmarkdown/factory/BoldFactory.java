package com.yydcdut.rxmarkdown.factory;

import com.yydcdut.rxmarkdown.grammar.BoldGrammar;
import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.html.IHGrammar;

/**
 * Created by yuyidong on 16/5/11.
 */
public class BoldFactory implements IGrammarFactory {
    @Override
    public IGrammar getGrammar() {
        return new BoldGrammar();
    }

    @Override
    public IHGrammar getHtmlGrammar() {
        return null;
    }
}
