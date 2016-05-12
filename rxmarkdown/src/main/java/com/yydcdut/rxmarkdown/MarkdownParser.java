package com.yydcdut.rxmarkdown;

import android.text.SpannableStringBuilder;

import com.yydcdut.rxmarkdown.chain.IResponsibilityChain;
import com.yydcdut.rxmarkdown.factory.AbsGrammarFactory;
import com.yydcdut.rxmarkdown.widget.TextWrapper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yuyidong on 16/5/3.
 */
public class MarkdownParser {
    private String mContent;

    private List<AbsGrammarFactory> mAbsGrammarFactoryList;
    private List<TextWrapper> mTextWrapperList;

    private MarkdownParser(String content, List<AbsGrammarFactory> absGrammarFactoryList, List<TextWrapper> textWrapperList) {
        mContent = content;
        mAbsGrammarFactoryList = absGrammarFactoryList;
        mTextWrapperList = textWrapperList;
    }

    public Observable<SpannableStringBuilder> intoObservable() {
        return Observable.just(mContent)
                .subscribeOn(Schedulers.computation())
                .map(new Func1<String, SpannableStringBuilder>() {
                    @Override
                    public SpannableStringBuilder call(String s) {
                        if (mAbsGrammarFactoryList != null) {
                            AbsGrammarFactory absGrammarFactory = mAbsGrammarFactoryList.get(0);
                            IResponsibilityChain chain = absGrammarFactory.getChain();
                            return parse(chain);
                        }
                        return null;
                    }
                });
    }

    private SpannableStringBuilder parse(IResponsibilityChain chain) {
        String[] lines = mContent.split("\n");
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        for (String line : lines) {
            SpannableStringBuilder lineSSB = new SpannableStringBuilder(line);
            chain.handleGrammar(lineSSB);
            ssb.append(lineSSB);
            ssb.append("\n");
        }
        return ssb;
    }


    public static final class Builder {
        private String mContent;

        private List<AbsGrammarFactory> mAbsGrammarFactoryList;
        private List<TextWrapper> mTextWrapperList;

        public Builder(String content) {
            mContent = content;
        }

        public Builder addFormatFactory(AbsGrammarFactory absGrammarFactory) {
            if (mAbsGrammarFactoryList == null) {
                mAbsGrammarFactoryList = new ArrayList<>();
            }
            mAbsGrammarFactoryList.add(absGrammarFactory);
            return this;
        }

        public Builder addView(TextWrapper textWrapper) {
            if (mTextWrapperList == null) {
                mTextWrapperList = new ArrayList<>();
            }
            mTextWrapperList.add(textWrapper);
            return this;
        }

        public MarkdownParser build() {
            return new MarkdownParser(mContent, mAbsGrammarFactoryList, mTextWrapperList);
        }
    }


}
