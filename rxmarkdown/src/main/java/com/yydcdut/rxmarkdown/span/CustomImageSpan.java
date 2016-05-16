package com.yydcdut.rxmarkdown.span;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.style.DynamicDrawableSpan;
import android.view.View;

import com.yydcdut.rxmarkdown.view.ForwardingDrawable;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yuyidong on 16/5/16.
 */
public class CustomImageSpan extends DynamicDrawableSpan {
    private String mImageUri;
    private Drawable mPlaceHolder;
    private Drawable mFinalDrawable;
    private final ForwardingDrawable mActualDrawable;
    private boolean mIsAttached;
    private View mAttachedView;
    private boolean mIsRequestSubmitted = false;

    private static Drawable createEmptyDrawable() {
        ColorDrawable d = new ColorDrawable(Color.TRANSPARENT);
        d.setBounds(0, 0, 100, 100);
        return d;
    }

    public CustomImageSpan(String uri) {
        this(uri, createEmptyDrawable());
    }

    public CustomImageSpan(String uri, Drawable placeHolder) {
        super(ALIGN_BOTTOM);
        mImageUri = uri;
        mPlaceHolder = placeHolder;
        mActualDrawable = new ForwardingDrawable(mPlaceHolder);
        Rect bounds = mPlaceHolder.getBounds();
        if (bounds.right == 0 || bounds.bottom == 0) {
            mActualDrawable.setBounds(0, 0, mPlaceHolder.getIntrinsicWidth(), mPlaceHolder.getIntrinsicHeight());
        } else {
            mActualDrawable.setBounds(bounds);
        }
    }

    @Override
    public Drawable getDrawable() {
        return mActualDrawable;
    }

    public void onAttach(@NonNull View view) {
        mIsAttached = true;
        if (mAttachedView != view) {
            mActualDrawable.setCallback(null);
            if (mAttachedView != null) {
                throw new IllegalStateException("has been attached to view:" + mAttachedView);
            }
            mAttachedView = view;
            mActualDrawable.setCallback(mAttachedView);
        }
        if (!mIsRequestSubmitted) {
            submitRequest();
        }
    }

    private void submitRequest() {
        mIsRequestSubmitted = true;
        AsyncTask<String, Void, Drawable> asyncTask = new AsyncTask<String, Void, Drawable>() {

            @Override
            protected Drawable doInBackground(String... params) {
                String http = params[0];
                byte[] bytes = net(http);
                if (bytes == null) {
                    return null;
                }
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Drawable drawable = createBitmapDrawable(bitmap);
                return drawable;
            }

            @Override
            protected void onPostExecute(Drawable drawable) {
                setImageWithIntrinsicBounds(drawable);
            }

            protected BitmapDrawable createBitmapDrawable(Bitmap bitmap) {
                BitmapDrawable drawable;
                if (mAttachedView != null) {
                    final Context context = mAttachedView.getContext();
                    drawable = new BitmapDrawable(context.getResources(), bitmap);
                } else {
                    drawable = new BitmapDrawable(null, bitmap);
                }
                return drawable;
            }
        };
        asyncTask.execute(mImageUri);
    }

    private void setImageWithIntrinsicBounds(Drawable drawable) {
        if (mFinalDrawable != drawable && drawable != null) {
            mActualDrawable.setCurrent(drawable);
            mFinalDrawable = drawable;
        }
    }

    private static byte[] net(String http) {
        HttpURLConnection httpURLConnection = null;
        ByteArrayOutputStream out = null;
        byte[] bytes = null;
        try {
            URL url = new URL(http);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            out = new ByteArrayOutputStream();
            int i;
            while ((i = in.read()) != -1) {
                out.write(i);
            }
            bytes = out.toByteArray();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(out);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return bytes;
    }

    private static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onDetach() {
        if (!mIsAttached) {
            return;
        }
        mActualDrawable.setCallback(null);
        mAttachedView = null;
        reset();
    }

    private void reset() {
        mActualDrawable.setCurrent(mPlaceHolder);
    }

}
