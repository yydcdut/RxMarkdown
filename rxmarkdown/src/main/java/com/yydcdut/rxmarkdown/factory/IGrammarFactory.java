package com.yydcdut.rxmarkdown.factory;

import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.html.IHGrammar;

/**
 * Created by yuyidong on 16/5/11.
 */
public interface IGrammarFactory {
    IGrammar getGrammar();

    IHGrammar getHtmlGrammar();
}
