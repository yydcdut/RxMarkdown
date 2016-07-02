package com.yydcdut.rxmarkdown.factory;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Spannable;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.edit.EditToken;
import com.yydcdut.rxmarkdown.grammar.IGrammar;
import com.yydcdut.rxmarkdown.grammar.edit.AndroidInstanceFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyidong on 16/7/2.
 */
public class EditFactory extends AbsGrammarFactory {

    private EditFactory() {
    }

    public static EditFactory create() {
        return new EditFactory();
    }

    @Override
    protected IGrammar getHorizontalRulesGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return null;
    }

    @Override
    protected IGrammar getBlockQuotesGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_BLOCK_QUOTES, rxMDConfiguration);
    }

    @Override
    protected IGrammar getTodoGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return null;
    }

    @Override
    protected IGrammar getTodoDoneGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return null;
    }

    @Override
    protected IGrammar getOrderListGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return null;
    }

    @Override
    protected IGrammar getUnOrderListGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return null;
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
        return null;
    }

    @Override
    protected IGrammar getImageGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return null;
    }

    @Override
    protected IGrammar getHyperLinkGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return null;
    }

    @Override
    protected IGrammar getCodeGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_CODE, rxMDConfiguration);
    }

    @Override
    protected IGrammar getBackslashGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        return null;
    }

    @NonNull
    @Override
    public CharSequence parse(@NonNull CharSequence charSequence) {
        if (!(charSequence instanceof Editable)) {
            return charSequence;
        }
        if (mRxMDConfiguration == null) {
            return charSequence;
        }
        Editable editable = (Editable) charSequence;
        List<EditToken> list = new ArrayList<>();
        list.addAll(getBoldGrammar(mRxMDConfiguration).format(editable));
        list.addAll(getItalicGrammar(mRxMDConfiguration).format(editable));
        list.addAll(getStrikeThroughGrammar(mRxMDConfiguration).format(editable));
        list.addAll(getInlineCodeGrammar(mRxMDConfiguration).format(editable));
        list.addAll(getCenterAlignGrammar(mRxMDConfiguration).format(editable));
        list.addAll(getHeaderGrammar(mRxMDConfiguration).format(editable));
        list.addAll(getBlockQuotesGrammar(mRxMDConfiguration).format(editable));
        list.addAll(getCodeGrammar(mRxMDConfiguration).format(editable));
        Editable newEditable = Editable.Factory.getInstance().newEditable(editable.toString());
        for (EditToken editToken : list) {
            newEditable.setSpan(editToken.getSpan(), editToken.getStart(), editToken.getEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return newEditable;
    }

}
