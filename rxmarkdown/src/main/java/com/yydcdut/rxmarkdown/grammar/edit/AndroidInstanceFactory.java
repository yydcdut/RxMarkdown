package com.yydcdut.rxmarkdown.grammar.edit;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.grammar.IFactory;
import com.yydcdut.rxmarkdown.grammar.IGrammar;

/**
 * Created by yuyidong on 16/7/2.
 */
public class AndroidInstanceFactory implements IFactory {

    public static IGrammar getAndroidGrammar(@IntRange(from = 0, to = 32) int grammar, @NonNull RxMDConfiguration rxMDConfiguration) {
        switch (grammar) {
//            case GRAMMAR_HORIZONTAL_RULES:
//                return new HorizontalRulesGrammar(rxMDConfiguration);
            case GRAMMAR_BLOCK_QUOTES:
                return new BlockQuotesGrammar(rxMDConfiguration);
            case GRAMMAR_CENTER_ALIGN:
                return new CenterAlignGrammar(rxMDConfiguration);
            case GRAMMAR_HEADER_LINE:
                return new HeaderGrammar(rxMDConfiguration);
            case GRAMMAR_BOLD:
                return new BoldGrammar(rxMDConfiguration);
            case GRAMMAR_ITALIC:
                return new ItalicGrammar(rxMDConfiguration);
            case GRAMMAR_INLINE_CODE:
                return new InlineCodeGrammar(rxMDConfiguration);
            case GRAMMAR_STRIKE_THROUGH:
                return new StrikeThroughGrammar(rxMDConfiguration);
//            case GRAMMAR_FOOTNOTE:
//                return new FootnoteGrammar(rxMDConfiguration);
//            case GRAMMAR_IMAGE:
//                return new ImageGrammar(rxMDConfiguration);
//            case GRAMMAR_HYPERLINK:
//                return new HyperLinkGrammar(rxMDConfiguration);
//            case GRAMMAR_TODO:
//                return new TodoGrammar(rxMDConfiguration);
//            case GRAMMAR_TODO_DONE:
//                return new TodoDoneGrammar(rxMDConfiguration);
            case GRAMMAR_CODE:
                return new CodeGrammar(rxMDConfiguration);
//            case GRAMMAR_UNORDER_LIST:
//                return new UnOrderListGrammar(rxMDConfiguration);
//            case GRAMMAR_ORDER_LIST:
//                return new OrderListGrammar(rxMDConfiguration);
//            case GRAMMAR_BACKSLASH:
//                return new BackslashGrammar(rxMDConfiguration);
            default:
                return new NormalGrammar(rxMDConfiguration);
        }
    }
}
