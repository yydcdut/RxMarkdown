package com.yydcdut.rxmarkdown.operator;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.loader.RxMDImageLoader;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by yuyidong on 2017/7/17.
 */
public class ImageLoadSyncOperator implements Observable.Operator<byte[], String> {
    private RxMDImageLoader mRxMDImageLoader;

    public ImageLoadSyncOperator(RxMDImageLoader rxMDImageLoader) {
        mRxMDImageLoader = rxMDImageLoader;
    }

    @Override
    public Subscriber<? super String> call(final Subscriber<? super byte[]> subscriber) {
        return new Subscriber<String>() {
            @Override
            public void onCompleted() {
                subscriber.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }

            @Override
            public void onNext(String url) {
                try {
                    byte[] bytes = mRxMDImageLoader.loadSync(getUrl(url));
                    subscriber.onNext(bytes);
                } catch (IOException e) {
                    subscriber.onError(new IOException(e));
                }
            }
        };
    }

    @NonNull
    private static String getUrl(String sourceUrl) {
        Matcher m = Pattern.compile("^(.*?)/(\\d+)\\$(\\d+)$").matcher(sourceUrl);
        if (m.find()) {
            return m.group(1);
        }
        return sourceUrl;
    }
}
