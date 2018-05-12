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

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.MarkdownEditText;
import com.yydcdut.markdown.syntax.SyntaxFactory;
import com.yydcdut.markdown.syntax.edit.EditFactory;
import com.yydcdut.markdown.syntax.text.TextFactory;

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
    private MarkdownEditText mMarkdownEditText;
    private Context mContext;
    private SyntaxFactory mSyntaxFactory;
    private MarkdownConfiguration mMarkdownConfiguration;

    private RxMarkdown(String content, Context context) {
        mContent = content;
        mContext = context;
    }

    private RxMarkdown(MarkdownEditText MarkdownEditText) {
        mMarkdownEditText = MarkdownEditText;
        mContext = mMarkdownEditText.getContext();
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
     * Live preview in {@link MarkdownEditText}.
     *
     * @param MarkdownEditText RxMDEditText
     * @return RxMarkdown object
     */
    public static RxMarkdown live(MarkdownEditText MarkdownEditText) {
        return new RxMarkdown(MarkdownEditText);
    }

    /**
     * set configuration
     *
     * @param markdownConfiguration {@link MarkdownConfiguration}
     * @return this(RxMarkdown)
     */
    public RxMarkdown config(MarkdownConfiguration markdownConfiguration) {
        mMarkdownConfiguration = markdownConfiguration;
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
                    .map(new Func1<String, CharSequence>() {
                        @Override
                        public CharSequence call(String s) {
                            if (mSyntaxFactory != null) {
                                MarkdownConfiguration config = getMarkdownConfiguration();
                                CharSequence charSequence = mSyntaxFactory.parse(s, config);
                                return charSequence;
                            }
                            return s;
                        }
                    });
        } else {
            return Observable.just(mMarkdownEditText)
                    .map(new Func1<MarkdownEditText, CharSequence>() {
                        @Override
                        public CharSequence call(MarkdownEditText markdownEditText) {
                            if (mSyntaxFactory == null) {
                                return markdownEditText.getText();
                            }
                            markdownEditText.setFactoryAndConfig(mSyntaxFactory, getMarkdownConfiguration());
                            return markdownEditText.getText();
                        }
                    });
        }
    }

    /**
     * get configuration
     * if user didn't set configuration, return default.
     *
     * @return {@link MarkdownConfiguration}
     */
    @NonNull
    private MarkdownConfiguration getMarkdownConfiguration() {
        if (mMarkdownConfiguration == null) {
            mMarkdownConfiguration = new MarkdownConfiguration.Builder(mContext).build();
        }
        return mMarkdownConfiguration;
    }

}
