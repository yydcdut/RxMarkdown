package com.yydcdut.rxmarkdown.operator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.drawable.ForwardingDrawable;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by yuyidong on 2017/7/17.
 */
public class ByteArrayOperator implements Observable.Operator<Drawable, byte[]> {
    private Context mContext;
    private Drawable mPlaceHolder;
    private ForwardingDrawable mActualDrawable;

    public ByteArrayOperator(Context context, Drawable placeHolder, ForwardingDrawable actualDrawable) {
        mContext = context;
        mPlaceHolder = placeHolder;
        mActualDrawable = actualDrawable;
    }

    @Override
    public Subscriber<? super byte[]> call(final Subscriber<? super Drawable> subscriber) {
        return new Subscriber<byte[]>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(byte[] bytes) {
                if (bytes == null) {
                    subscriber.onNext(mPlaceHolder);
                } else {
                    subscriber.onNext(getDrawable(mContext, bytes));
                }
            }
        };
    }

    private Drawable getDrawable(Context context, @NonNull byte[] bytes) {
        BitmapFactory.Options calculateOptions = new BitmapFactory.Options();
        calculateOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, calculateOptions);
//        float needHeight = mActualDrawable.getBounds().width() * (((float) calculateOptions.outHeight) / ((float) calculateOptions.outWidth));
//        Drawable drawable = new ColorDrawable(Color.TRANSPARENT);
//        drawable.setBounds(0, 0, mActualDrawable.getBounds().width(), (int) needHeight);
//        mActualDrawable.refreshCurrent(drawable);
        int sampleSize = 1;
        if (mActualDrawable.getIntrinsicWidth() >= 0 && mActualDrawable.getIntrinsicHeight() >= 0) {
            sampleSize = calculate(calculateOptions, mActualDrawable.getIntrinsicWidth(), mActualDrawable.getIntrinsicHeight());
        } else if (mPlaceHolder.getBounds().width() >= 0 && mPlaceHolder.getBounds().height() >= 0) {
            Rect rect = mPlaceHolder.getBounds();
            sampleSize = calculate(calculateOptions, rect.width(), rect.height());
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        return createBitmapDrawable(context, bitmap);
    }

    private static int calculate(@NonNull BitmapFactory.Options options, int expectWidth, int expectHeight) {
        int sampleSize = 1;
        while (options.outHeight / sampleSize > expectWidth || options.outWidth / sampleSize > expectHeight) {
            sampleSize = sampleSize << 1;
        }
        return sampleSize;
    }

    private static BitmapDrawable createBitmapDrawable(Context context, Bitmap bitmap) {
        BitmapDrawable drawable;
        if (context != null) {
            drawable = new BitmapDrawable(context.getResources(), bitmap);
        } else {
            drawable = new BitmapDrawable(null, bitmap);
        }
        return drawable;
    }
}
