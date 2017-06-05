/*
 * Copyright (C) 2016 yydcdut (yuyidong2015@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.yydcdut.rxmarkdown;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.yydcdut.rxmarkdown.factory.AbsGrammarFactory;
import com.yydcdut.rxmarkdown.syntax.edit.EditFactory;
import com.yydcdut.rxmarkdown.syntax.text.TextFactory;

import rx.Observable;
import rx.functions.Func1;

/**
 * RxMarkdown for TextView:
 * <p>
 * RxMarkdown.with(content, context)
 * .config(rxMDConfiguration)
 * .factory(AndroidFactory.create())
 * .intoObservable()
 * .subscribeOn(Schedulers.computation())
 * .observeOn(AndroidSchedulers.mainThread())
 * .subscribe();
 * <p>
 * RxMarkdown for EditText:
 * <p>
 * RxMarkdown.live(mEditText)
 * .config(rxMDConfiguration)
 * .factory(EditFactory.create())
 * .intoObservable()
 * .subscribeOn(Schedulers.computation())
 * .observeOn(AndroidSchedulers.mainThread());
 * .subscribe();
 * <p>
 * Created by yuyidong on 16/5/3.
 */
public class RxMarkdown {
    private static final String TAG = RxMarkdown.class.getName();
    private String mContent;
    private RxMDEditText mRxMDEditText;
    private Context mContext;
    private AbsGrammarFactory mAbsGrammarFactory;
    private RxMDConfiguration mRxMDConfiguration;

    private RxMarkdown(String content, Context context) {
        mContent = content;
        mContext = context;
    }

    private RxMarkdown(RxMDEditText rxMDEditText) {
        mRxMDEditText = rxMDEditText;
        mContext = mRxMDEditText.getContext();
    }

    /**
     * get RxMarkdown object
     *
     * @param content the markdown content
     * @param context {@link Context}
     * @return RxMarkdown object
     */
    public static RxMarkdown with(String content, Context context) {
        return new RxMarkdown(content, context);
    }

    /**
     * get RxMarkdown object.
     * Live preview in {@link RxMDEditText}.
     *
     * @param rxMDEditText RxMDEditText
     * @return RxMarkdown object
     */
    public static RxMarkdown live(RxMDEditText rxMDEditText) {
        return new RxMarkdown(rxMDEditText);
    }

    /**
     * set configuration
     *
     * @param rxMDConfiguration {@link RxMDConfiguration}
     * @return this(RxMarkdown)
     */
    public RxMarkdown config(RxMDConfiguration rxMDConfiguration) {
        mRxMDConfiguration = rxMDConfiguration;
        return this;
    }

    /**
     * set factory
     *
     * @param absGrammarFactory {@link TextFactory} and {@link EditFactory}
     * @return this(RxMarkdown)
     */
    public RxMarkdown factory(AbsGrammarFactory absGrammarFactory) {
        mAbsGrammarFactory = absGrammarFactory;
        return this;
    }

    /**
     * begin parsing
     *
     * @return RxJava Observable
     */
    public Observable<CharSequence> intoObservable() {
        if (mContent != null) {
            return Observable.just(mContent)
                    .map(new Func1<String, CharSequence>() {
                        @Override
                        public CharSequence call(String s) {
                            if (mAbsGrammarFactory != null) {
                                RxMDConfiguration config = getRxMDConfiguration();
                                long time = System.currentTimeMillis();
                                CharSequence charSequence = mAbsGrammarFactory.parse(s, config);
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

    /**
     * get configuration
     * if user didn't set configuration, return default.
     *
     * @return {@link RxMDConfiguration}
     */
    @NonNull
    private RxMDConfiguration getRxMDConfiguration() {
        if (mRxMDConfiguration == null) {
            mRxMDConfiguration = new RxMDConfiguration.Builder(mContext).build();
        }
        return mRxMDConfiguration;
    }

}
