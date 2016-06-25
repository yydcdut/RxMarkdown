package com.yydcdut.rxmarkdown;

import android.util.Log;

import com.yydcdut.rxmarkdown.factory.AbsGrammarFactory;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yuyidong on 16/5/3.
 */
public class RxMarkdown {
    private String mContent;
    private AbsGrammarFactory mAbsGrammarFactory;
    private Configuration mConfiguration;

    private RxMarkdown(String content) {
        mContent = content;
    }

    public static RxMarkdown with(String content) {
        return new RxMarkdown(content);
    }

    public RxMarkdown config(Configuration configuration) {
        mConfiguration = configuration;
        return this;
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
                            if (mConfiguration == null) {
                                mConfiguration = new Configuration.Builder().build();
                            }
                            mAbsGrammarFactory.init(mConfiguration);
                            long time = System.currentTimeMillis();
                            CharSequence charSequence = mAbsGrammarFactory.parse(s);
                            Log.i("yuyidong", "spend time --->" + (System.currentTimeMillis() - time));
                            return charSequence;
                        }
                        return s;
                    }
                });
    }


}
