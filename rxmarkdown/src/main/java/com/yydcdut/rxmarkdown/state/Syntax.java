package com.yydcdut.rxmarkdown.state;

import android.text.Editable;
import android.text.SpannableStringBuilder;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.edit.EditToken;
import com.yydcdut.rxmarkdown.factory.AbsGrammarFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyidong on 2017/5/21.
 */
public class Syntax {
    private RxMDConfiguration mRxMDConfiguration;
    private AbsGrammarFactory mFactory;
    private CharSequence mCharSequence;

    public Syntax(RxMDConfiguration rxMDConfiguration, AbsGrammarFactory factory, CharSequence charSequence) {
        mRxMDConfiguration = rxMDConfiguration;
        mFactory = factory;
        mCharSequence = charSequence;
    }

    private Parser mParser;

    protected void setParser(Parser parser) {
        mParser = parser;
    }

    protected RxMDConfiguration getRxMDConfiguration() {
        return mRxMDConfiguration;
    }

    protected AbsGrammarFactory getFactory() {
        return mFactory;
    }

    public void prase() {
        List<EditToken> list = new ArrayList<>();
        while (mParser != null) {
            if (mCharSequence instanceof SpannableStringBuilder) {
                mCharSequence = mParser.parse(this, mCharSequence);
            } else if (mCharSequence instanceof Editable) {
                list.addAll(mParser.parse(this, (Editable) mCharSequence));
            }
        }
    }
}
