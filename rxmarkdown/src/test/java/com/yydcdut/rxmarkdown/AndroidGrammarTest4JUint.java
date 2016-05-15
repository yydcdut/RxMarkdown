package com.yydcdut.rxmarkdown;

import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.grammar.android.AndroidInstanceFactory;

import org.junit.Test;


import static org.junit.Assert.assertTrue;

/**
 * Created by yuyidong on 16/5/14.
 */
public class androidGrammarTest4Juint {
    @Test
    public void hyperLink_isCorrect() throws Exception {
        String testString = "[asdasd]([(dddd)ssss";
        IGrammar hyperLinkGrammar = AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_HYPERLINK);
        boolean b = hyperLinkGrammar.isMatch(testString);
        assertTrue(b);
    }
}
