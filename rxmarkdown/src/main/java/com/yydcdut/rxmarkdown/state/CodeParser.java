package com.yydcdut.rxmarkdown.state;

import android.text.Editable;

import com.yydcdut.rxmarkdown.edit.EditToken;
import com.yydcdut.rxmarkdown.grammar.IGrammar;

import java.util.List;

/**
 * Created by yuyidong on 2017/5/21.
 */
public class CodeParser implements Parser {

    @Override
    public CharSequence parse(Syntax syntax, CharSequence charSequence) {
        IGrammar grammar = syntax.getFactory().getCodeGrammar(syntax.getRxMDConfiguration());
        syntax.setParser(new UnOrderListParser());
        if (grammar.isMatch(charSequence)) {
            return grammar.format(charSequence);
        } else {
            return charSequence;
        }
    }

    @Override
    public List<EditToken> parse(Syntax syntax, Editable editable) {
        return null;
    }
}
