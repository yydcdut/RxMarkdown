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

import com.yydcdut.rxmarkdown.syntax.SyntaxFactory;
import com.yydcdut.rxmarkdown.syntax.edit.EditFactory;
import com.yydcdut.rxmarkdown.syntax.text.TextFactory;

import rx.Observable;

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
    private SyntaxFactory mSyntaxFactory;
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
     * @param syntaxFactory {@link TextFactory} and {@link EditFactory}
     * @return this(RxMarkdown)
     */
    public RxMarkdown factory(SyntaxFactory syntaxFactory) {
        mSyntaxFactory = syntaxFactory;
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
                    .map(s -> {
                        if (mSyntaxFactory != null) {
                            RxMDConfiguration config = getRxMDConfiguration();
                            CharSequence charSequence = mSyntaxFactory.parse(s, config);
                            return charSequence;
                        }
                        return s;
                    });
        } else {
            return Observable.just(mRxMDEditText)
                    .map(rxMDEditText -> {
                        if (mSyntaxFactory == null) {
                            return rxMDEditText.getText();
                        }
                        rxMDEditText.setFactoryAndConfig(mSyntaxFactory, getRxMDConfiguration());
                        return rxMDEditText.getText();
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
