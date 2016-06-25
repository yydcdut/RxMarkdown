package com.yydcdut.rxmarkdown.factory;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;

import com.yydcdut.rxmarkdown.Configuration;
import com.yydcdut.rxmarkdown.chain.IChain;
import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.grammar.android.AndroidInstanceFactory;

/**
 * Created by yuyidong on 16/5/12.
 */
public class AndroidFactory extends AbsGrammarFactory {
    private AndroidFactory() {
        super();
    }

    public static AndroidFactory create() {
        return new AndroidFactory();
    }

    @Override
    protected IGrammar getHorizontalRulesGrammar(@NonNull Configuration configuration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_HORIZONTAL_RULES, configuration);
    }

    @Override
    protected IGrammar getBlockQuotesGrammar(@NonNull Configuration configuration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_BLOCK_QUOTES, configuration);
    }

    @Override
    protected IGrammar getTodoGrammar(@NonNull Configuration configuration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_TODO, configuration);
    }

    @Override
    protected IGrammar getTodoDoneGrammar(@NonNull Configuration configuration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_TODO_DONE, configuration);
    }

    @Override
    protected IGrammar getOrderListGrammar(@NonNull Configuration configuration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_ORDER_LIST, configuration);
    }

    @Override
    protected IGrammar getUnOrderListGrammar(@NonNull Configuration configuration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_UNORDER_LIST, configuration);
    }

    @Override
    protected IGrammar getCenterAlignGrammar(@NonNull Configuration configuration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_CENTER_ALIGN, configuration);
    }

    @Override
    protected IGrammar getHeaderGrammar(@NonNull Configuration configuration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_HEADER_LINE, configuration);
    }

    @Override
    protected IGrammar getBoldGrammar(@NonNull Configuration configuration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_BOLD, configuration);
    }

    @Override
    protected IGrammar getItalicGrammar(@NonNull Configuration configuration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_ITALIC, configuration);
    }

    @Override
    protected IGrammar getInlineCodeGrammar(@NonNull Configuration configuration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_INLINE_CODE, configuration);
    }

    @Override
    protected IGrammar getStrikeThroughGrammar(@NonNull Configuration configuration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_STRIKE_THROUGH, configuration);
    }

    @Override
    protected IGrammar getFootnoteGrammar(@NonNull Configuration configuration) {
        return AndroidInstanceFactory.getAndroidGrammar((AndroidInstanceFactory.GRAMMAR_FOOTNOTE), configuration);
    }

    @Override
    protected IGrammar getImageGrammar(@NonNull Configuration configuration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_IMAGE, configuration);
    }

    @Override
    protected IGrammar getHyperLinkGrammar(@NonNull Configuration configuration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_HYPERLINK, configuration);
    }

    @Override
    protected IGrammar getCodeGrammar(@NonNull Configuration configuration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_CODE, configuration);
    }

    @Override
    protected IGrammar getBackslashGrammar(@NonNull Configuration configuration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_BACKSLASH, configuration);
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
