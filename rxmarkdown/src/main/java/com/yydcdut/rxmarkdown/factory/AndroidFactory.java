package com.yydcdut.rxmarkdown.factory;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.chain.GrammarDoElseChain;
import com.yydcdut.rxmarkdown.chain.GrammarMultiChains;
import com.yydcdut.rxmarkdown.chain.GrammarSingleChain;
import com.yydcdut.rxmarkdown.chain.IChain;
import com.yydcdut.rxmarkdown.chain.MultiGrammarsChain;
import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.grammar.android.AndroidInstanceFactory;

/**
 * Created by yuyidong on 16/5/12.
 */
public class AndroidFactory extends AbsGrammarFactory {
    protected IChain mLineChain;
    protected IChain mTotalChain;

    private AndroidFactory() {
        super();
    }

    public static AndroidFactory create() {
        return new AndroidFactory();
    }

    @Override
    protected IGrammar getHorizontalRulesGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_HORIZONTAL_RULES, rxMDConfiguration);
    }

    @Override
    protected IGrammar getBlockQuotesGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_BLOCK_QUOTES, rxMDConfiguration);
    }

    @Override
    protected IGrammar getTodoGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_TODO, rxMDConfiguration);
    }

    @Override
    protected IGrammar getTodoDoneGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_TODO_DONE, rxMDConfiguration);
    }

    @Override
    protected IGrammar getOrderListGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_ORDER_LIST, rxMDConfiguration);
    }

    @Override
    protected IGrammar getUnOrderListGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_UNORDER_LIST, rxMDConfiguration);
    }

    @Override
    protected IGrammar getCenterAlignGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_CENTER_ALIGN, rxMDConfiguration);
    }

    @Override
    protected IGrammar getHeaderGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_HEADER_LINE, rxMDConfiguration);
    }

    @Override
    protected IGrammar getBoldGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_BOLD, rxMDConfiguration);
    }

    @Override
    protected IGrammar getItalicGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_ITALIC, rxMDConfiguration);
    }

    @Override
    protected IGrammar getInlineCodeGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_INLINE_CODE, rxMDConfiguration);
    }

    @Override
    protected IGrammar getStrikeThroughGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_STRIKE_THROUGH, rxMDConfiguration);
    }

    @Override
    protected IGrammar getFootnoteGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return AndroidInstanceFactory.getAndroidGrammar((AndroidInstanceFactory.GRAMMAR_FOOTNOTE), rxMDConfiguration);
    }

    @Override
    protected IGrammar getImageGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_IMAGE, rxMDConfiguration);
    }

    @Override
    protected IGrammar getHyperLinkGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_HYPERLINK, rxMDConfiguration);
    }

    @Override
    protected IGrammar getCodeGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_CODE, rxMDConfiguration);
    }

    @Override
    protected IGrammar getBackslashGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_BACKSLASH, rxMDConfiguration);
    }

    @Override
    public void init(@NonNull RxMDConfiguration rxMDConfiguration) {
        super.init(rxMDConfiguration);
        mTotalChain = new MultiGrammarsChain(
                getCodeGrammar(rxMDConfiguration),
                getUnOrderListGrammar(rxMDConfiguration),
                getOrderListGrammar(rxMDConfiguration));
        mLineChain = new GrammarSingleChain(getHorizontalRulesGrammar(rxMDConfiguration));
        GrammarDoElseChain blockQuitesChain = new GrammarDoElseChain(getBlockQuotesGrammar(rxMDConfiguration));
        GrammarDoElseChain todoChain = new GrammarDoElseChain(getTodoGrammar(rxMDConfiguration));
        GrammarDoElseChain todoDoneChain = new GrammarDoElseChain(getTodoDoneGrammar(rxMDConfiguration));
        GrammarMultiChains centerAlignChain = new GrammarMultiChains(getCenterAlignGrammar(rxMDConfiguration));
        GrammarMultiChains headerChain = new GrammarMultiChains(getHeaderGrammar(rxMDConfiguration));
        MultiGrammarsChain multiChain = new MultiGrammarsChain(
                getImageGrammar(rxMDConfiguration),
                getHyperLinkGrammar(rxMDConfiguration),
                getInlineCodeGrammar(rxMDConfiguration),
                getBoldGrammar(rxMDConfiguration),
                getItalicGrammar(rxMDConfiguration),
                getStrikeThroughGrammar(rxMDConfiguration),
                getFootnoteGrammar(rxMDConfiguration));
        GrammarSingleChain backslashChain = new GrammarSingleChain(getBackslashGrammar(rxMDConfiguration));

        mLineChain.setNextHandleGrammar(blockQuitesChain);

        blockQuitesChain.setNextHandleGrammar(todoChain);
        blockQuitesChain.addNextHandleGrammar(multiChain);

        todoChain.setNextHandleGrammar(todoDoneChain);
        todoChain.addNextHandleGrammar(multiChain);

        todoDoneChain.setNextHandleGrammar(centerAlignChain);
        todoDoneChain.addNextHandleGrammar(multiChain);

        centerAlignChain.addNextHandleGrammar(headerChain);
        centerAlignChain.addNextHandleGrammar(multiChain);

        multiChain.setNextHandleGrammar(backslashChain);
    }

    @NonNull
    @Override
    public CharSequence parse(@NonNull CharSequence charSequence) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(charSequence);
        ssb = parseTotal(mTotalChain, ssb);
        ssb = parseByLine(mLineChain, ssb);
        return ssb;
    }

    private static SpannableStringBuilder parseTotal(IChain totalChain, SpannableStringBuilder ssb) {
        totalChain.handleGrammar(ssb);
        return ssb;
    }

    private static SpannableStringBuilder parseByLine(IChain lineChain, SpannableStringBuilder content) {
        String text = content.toString();
        String[] lines = text.split("\n");
        SpannableStringBuilder[] ssbLines = new SpannableStringBuilder[lines.length];
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        int index = 0;
        for (int i = 0; i < lines.length; i++) {
            ssbLines[i] = (SpannableStringBuilder) content.subSequence(index, index + lines[i].length());
            lineChain.handleGrammar(ssbLines[i]);
            ssbLines[i].append("\n");
            ssb.append(ssbLines[i]);
            index = index + (lines[i] + "\n").length();
        }
        return ssb;
    }

}
