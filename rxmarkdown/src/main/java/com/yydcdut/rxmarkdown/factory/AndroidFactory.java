package com.yydcdut.rxmarkdown.factory;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;

import com.yydcdut.rxmarkdown.chain.IChain;
import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.grammar.android.AndroidInstanceFactory;

/**
 * Created by yuyidong on 16/5/12.
 */
public class AndroidFactory extends AbsGrammarFactory {
    public AndroidFactory() {
        super();
    }

    public static AndroidFactory create() {
        return new AndroidFactory();
    }

    @Override
    protected IGrammar getHorizontalRulesGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_HORIZONTAL_RULES);
    }

    @Override
    protected IGrammar getBlockQuotesGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_BLOCK_QUOTES);
    }

    @Override
    protected IGrammar getTodoGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_TODO);
    }

    @Override
    protected IGrammar getTodoDoneGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_TODO_DONE);
    }

    @Override
    protected IGrammar getOrderListGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_ORDER_LIST);
    }

    @Override
    protected IGrammar getUnOrderListGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_UNORDER_LIST);
    }

    @Override
    protected IGrammar getCenterAlignGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_CENTER_ALIGN);
    }

    @Override
    protected IGrammar getHeaderGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_HEADER_LINE);
    }

    @Override
    protected IGrammar getBoldGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_BOLD);
    }

    @Override
    protected IGrammar getItalicGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_ITALIC);
    }

    @Override
    protected IGrammar getInlineCodeGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_INLINE_CODE);
    }

    @Override
    protected IGrammar getStrikeThroughGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_STRIKE_THROUGH);
    }

    @Override
    protected IGrammar getFootnoteGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar((AndroidInstanceFactory.GRAMMAR_FOOTNOTE));
    }

    @Override
    protected IGrammar getImageGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_IMAGE);
    }

    @Override
    protected IGrammar getHyperLinkGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_HYPERLINK);
    }

    @Override
    protected IGrammar getCodeGrammar() {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_CODE);
    }

    @NonNull
    @Override
    public CharSequence parse(@NonNull CharSequence charSequence) {
        SpannableStringBuilder ssb = parseByLine(mLineChain, charSequence.toString());
        return parseTotal(mTotalChain, ssb);
    }

    private static CharSequence parseTotal(IChain totalChain, SpannableStringBuilder ssb) {
        totalChain.handleGrammar(ssb);
        return ssb;
    }

    private static SpannableStringBuilder parseByLine(IChain lineChain, String content) {
        String[] lines = content.split("\n");
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        for (String line : lines) {
            SpannableStringBuilder lineSSB = new SpannableStringBuilder(line);
            lineChain.handleGrammar(lineSSB);
            lineSSB.append("\n");
            ssb.append(lineSSB);
        }
        return ssb;
    }

}
