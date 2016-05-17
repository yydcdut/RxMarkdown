package com.yydcdut.rxmarkdown;

import android.text.SpannableStringBuilder;
import android.widget.TextView;

import com.yydcdut.rxmarkdown.chain.IChain;
import com.yydcdut.rxmarkdown.factory.AbsGrammarFactory;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yuyidong on 16/5/3.
 */
public class RxMarkdown {
    /**
     * 内容
     */
    private String mContent;
    /**
     * 语法工厂
     */
    private AbsGrammarFactory mAbsGrammarFactory;

    private float mMeasureText = 0f;

    private RxMarkdown(String content) {
        mContent = content;
    }

    public static RxMarkdown with(String content) {
        return new RxMarkdown(content);
    }

    public RxMarkdown factory(AbsGrammarFactory absGrammarFactory) {
        mAbsGrammarFactory = absGrammarFactory;
        return this;
    }

    public RxMarkdown measure(TextView textView) {
        mMeasureText = textView.getPaint().measureText(" ");
        return this;
    }

    public Observable<CharSequence> intoObservable() {
        return Observable.just(mContent)
                .subscribeOn(Schedulers.computation())
                .map(new Func1<String, CharSequence>() {
                    @Override
                    public CharSequence call(String s) {
                        if (mAbsGrammarFactory != null) {
                            SpannableStringBuilder ssb = parseByLine(mAbsGrammarFactory.getLineChain(), mContent);
                            parseTotal(mAbsGrammarFactory.getTotalChain(), ssb);
                            return ssb;
                        }
                        return s;
                    }
                });
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
