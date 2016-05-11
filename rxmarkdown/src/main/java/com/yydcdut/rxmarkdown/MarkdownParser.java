package com.yydcdut.rxmarkdown;

import android.text.SpannableStringBuilder;

import com.yydcdut.rxmarkdown.chain.GrammarMultiChains;
import com.yydcdut.rxmarkdown.chain.GrammarSingleChain;
import com.yydcdut.rxmarkdown.chain.IResponsibilityChain;
import com.yydcdut.rxmarkdown.chain.MultiGrammarsChain;
import com.yydcdut.rxmarkdown.factory.BlockQutesFactory;
import com.yydcdut.rxmarkdown.factory.BoldFactory;
import com.yydcdut.rxmarkdown.factory.CenterAlignFactory;
import com.yydcdut.rxmarkdown.factory.Header1Factory;
import com.yydcdut.rxmarkdown.factory.Header2Factory;
import com.yydcdut.rxmarkdown.factory.Header3Factory;
import com.yydcdut.rxmarkdown.factory.ItalicFactory;
import com.yydcdut.rxmarkdown.factory.OrderListFactory;
import com.yydcdut.rxmarkdown.factory.UnOrderListFactory;

/**
 * Created by yuyidong on 16/5/3.
 */
public class MarkdownParser {
    IResponsibilityChain mChain = null;

    {
        mChain = new GrammarSingleChain(new BlockQutesFactory().getGrammar());
        IResponsibilityChain orderListChain = new GrammarSingleChain(new OrderListFactory().getGrammar());
        IResponsibilityChain unOrderListChain = new GrammarSingleChain(new UnOrderListFactory().getGrammar());
        IResponsibilityChain centerAlignChain = new GrammarMultiChains(new CenterAlignFactory().getGrammar());
        IResponsibilityChain headerLine3Chain = new GrammarMultiChains(new Header3Factory().getGrammar());
        IResponsibilityChain headerLine2Chain = new GrammarMultiChains(new Header2Factory().getGrammar());
        IResponsibilityChain headerLine1Chain = new GrammarMultiChains(new Header1Factory().getGrammar());
        IResponsibilityChain multiChain = new MultiGrammarsChain(
                new BoldFactory().getGrammar(),
                new ItalicFactory().getGrammar());
        mChain.setNextHandleGrammar(orderListChain);
        orderListChain.setNextHandleGrammar(unOrderListChain);
        unOrderListChain.setNextHandleGrammar(centerAlignChain);
        centerAlignChain.addNextHandleGrammar(headerLine3Chain);
        centerAlignChain.addNextHandleGrammar(multiChain);
        headerLine3Chain.addNextHandleGrammar(headerLine2Chain);
        headerLine3Chain.addNextHandleGrammar(multiChain);
        headerLine2Chain.addNextHandleGrammar(headerLine1Chain);
        headerLine2Chain.addNextHandleGrammar(multiChain);
        headerLine1Chain.addNextHandleGrammar(multiChain);
    }

    public MarkdownParser() {
    }

    public SpannableStringBuilder parse(String content) {
        String[] lines = content.split("\n");
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        for (String line : lines) {
            SpannableStringBuilder lineSSB = new SpannableStringBuilder(line);
            mChain.handleGrammar(lineSSB);
            ssb.append(lineSSB);
            ssb.append("\n");
        }
        return ssb;
    }

}
