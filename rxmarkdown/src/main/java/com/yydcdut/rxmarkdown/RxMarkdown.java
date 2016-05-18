package com.yydcdut.rxmarkdown;

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

    public Observable<CharSequence> intoObservable() {
        return Observable.just(mContent)
                .subscribeOn(Schedulers.computation())
                .map(new Func1<String, CharSequence>() {
                    @Override
                    public CharSequence call(String s) {
                        if (mAbsGrammarFactory != null) {
                            return mAbsGrammarFactory.parse(s);
                        }
                        return s;
                    }
                });
    }


}
