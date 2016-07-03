package com.yydcdut.rxmarkdown;

import android.support.annotation.NonNull;
import android.util.Log;

import com.yydcdut.rxmarkdown.factory.AbsGrammarFactory;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by yuyidong on 16/5/3.
 */
public class RxMarkdown {
    private static final String TAG = "yuyidong_RxMarkdown";
    private String mContent;
    private RxMDEditText mRxMDEditText;
    private AbsGrammarFactory mAbsGrammarFactory;
    private RxMDConfiguration mRxMDConfiguration;

    private RxMarkdown(String content) {
        mContent = content;
    }

    private RxMarkdown(RxMDEditText rxMDEditText) {
        mRxMDEditText = rxMDEditText;
    }

    public static RxMarkdown with(String content) {
        return new RxMarkdown(content);
    }

    public static RxMarkdown live(RxMDEditText rxMDEditText) {
        return new RxMarkdown(rxMDEditText);
    }

    public RxMarkdown config(RxMDConfiguration rxMDConfiguration) {
        mRxMDConfiguration = rxMDConfiguration;
        return this;
    }

    public RxMarkdown factory(AbsGrammarFactory absGrammarFactory) {
        mAbsGrammarFactory = absGrammarFactory;
        return this;
    }

    public Observable<CharSequence> intoObservable() {
        if (mContent != null) {
            return Observable.just(mContent)
                    .map(new Func1<String, CharSequence>() {
                        @Override
                        public CharSequence call(String s) {
                            if (mAbsGrammarFactory != null) {
                                RxMDConfiguration config = getRxMDConfiguration();
                                mAbsGrammarFactory.init(config);
                                long time = System.currentTimeMillis();
                                CharSequence charSequence = mAbsGrammarFactory.parse(s);
                                if (config.isDebug()) {
                                    Log.i(TAG, "spend time --->" + (System.currentTimeMillis() - time));
                                }
                                return charSequence;
                            }
                            return s;
                        }
                    });
        } else {
            return Observable.just(mRxMDEditText)
                    .map(new Func1<RxMDEditText, CharSequence>() {
                        @Override
                        public CharSequence call(RxMDEditText rxMDEditText) {
                            if (mAbsGrammarFactory == null) {
                                return rxMDEditText.getText();
                            }
                            rxMDEditText.setFactoryAndConfig(mAbsGrammarFactory, getRxMDConfiguration());
                            return rxMDEditText.getText();
                        }
                    });
        }
    }

    @NonNull
    private RxMDConfiguration getRxMDConfiguration() {
        if (mRxMDConfiguration == null) {
            mRxMDConfiguration = new RxMDConfiguration.Builder().build();
        }
        return mRxMDConfiguration;
    }

}
