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
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.view.View;

import com.yydcdut.rxmarkdown.view.ForwardingDrawable;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuyidong on 16/5/16.
 */
public class CustomImageSpan extends DynamicDrawableSpan {

    private static Pattern sImageUrlPattern = Pattern.compile("^(.*?)/(\\d+)\\*(\\d+)$");
    private static final int URL_$$$$ = -1;
    private static final int URL_HTTP = 0;
    private static final int URL_FILE = 1;

    private static final String URL_HEADER_HTTP = "http://";
    private static final String URL_HEADER_FILE = "file://";

    private String mImageUri;
    private Drawable mPlaceHolder;
    private Drawable mFinalDrawable;
    private final ForwardingDrawable mActualDrawable;
    private boolean mIsAttached;
    private View mAttachedView;
    private boolean mIsRequestSubmitted = false;

    private static Drawable createEmptyDrawable(int width, int height) {
        ColorDrawable d = new ColorDrawable(Color.TRANSPARENT);
        d.setBounds(0, 0, width, height);
        return d;
    }

    public CustomImageSpan(String uri, int width, int height) {
        this(uri, createEmptyDrawable(getSize(uri, width, height)[0], getSize(uri, width, height)[1]));
    }

    public CustomImageSpan(String uri, Drawable placeHolder) {
        super(ALIGN_BOTTOM);
        getUrl(uri);
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
                String url = params[0];
                int type = judgeUrl(url);
                Drawable drawable = null;
                switch (type) {
                    case URL_HTTP:
                        drawable = getDrawableFromNet(getUrl(url));
                        break;
                    case URL_FILE:
                        drawable = getDrawableFromLocal(getUrl(url));
                        break;
                    case URL_$$$$:
                    default:
                        return null;
                }

                return drawable;
            }

            @Override
            protected void onPostExecute(Drawable drawable) {
                setImageWithIntrinsicBounds(drawable);
            }

        };
        asyncTask.execute(mImageUri);
    }

    private void setImageWithIntrinsicBounds(@NonNull Drawable drawable) {
        if (mFinalDrawable != drawable && drawable != null) {
            mActualDrawable.setCurrent(drawable);
            mFinalDrawable = drawable;
        }
    }

    private BitmapDrawable createBitmapDrawable(Bitmap bitmap) {
        BitmapDrawable drawable;
        if (mAttachedView != null) {
            final Context context = mAttachedView.getContext();
            drawable = new BitmapDrawable(context.getResources(), bitmap);
        } else {
            drawable = new BitmapDrawable(null, bitmap);
        }
        return drawable;
    }

    private Drawable getDrawableFromLocal(String url) {
        if (mAttachedView == null) {
            return null;
        }
        Context context = mAttachedView.getContext();
        if (context == null) {
            return null;
        }
        String path = url.substring(URL_HEADER_FILE.length() + 1, url.length());
        InputStream inputStream = null;
        Drawable drawable = null;
        try {
            inputStream = new FileInputStream(path);
            byte[] bytes = getBytes(inputStream);
            if (bytes != null) {
                drawable = getDrawable(bytes);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeStream(inputStream);
        }
        return drawable;
    }

    private Drawable getDrawableFromNet(@NonNull String http) {
        byte[] bytes = net(http);
        if (bytes == null) {
            return null;
        }
        return getDrawable(bytes);
    }

    private static int calculate(@NonNull BitmapFactory.Options options, int expectWidth, int expectHeight) {
        int sampleSize = 1;
        while (options.outHeight / sampleSize > expectWidth || options.outWidth / sampleSize > expectHeight) {
            sampleSize = sampleSize << 1;
        }
        return sampleSize;
    }

    private static byte[] net(@NonNull String http) {
        HttpURLConnection httpURLConnection = null;
        ByteArrayOutputStream out = null;
        byte[] bytes = null;
        try {
            URL url = new URL(http);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            bytes = getBytes(in);
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

    private static byte[] getBytes(@NonNull InputStream inputStream) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] bytes = null;
        try {
            int i;
            while ((i = inputStream.read()) != -1) {
                out.write(i);
            }
            bytes = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private Drawable getDrawable(@NonNull byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        BitmapFactory.Options calculateOptions = new BitmapFactory.Options();
        calculateOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, calculateOptions);
        int expectWidth = mActualDrawable.getIntrinsicWidth();
        int expectHeight = mActualDrawable.getIntrinsicHeight();
        int sampleSize = 1;
        if (expectWidth >= 0 && expectHeight >= 0) {
            sampleSize = calculate(calculateOptions, expectWidth, expectHeight);
        } else if (mPlaceHolder.getBounds().width() >= 0 && mPlaceHolder.getBounds().height() >= 0) {
            Rect rect = mPlaceHolder.getBounds();
            sampleSize = calculate(calculateOptions, rect.width(), rect.height());
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        Drawable drawable = createBitmapDrawable(bitmap);
        return drawable;
    }

    private static void closeStream(@Nullable Closeable closeable) {
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
        mActualDrawable.setCurrent(mPlaceHolder);
    }

    private static int judgeUrl(@NonNull String url) {
        if (TextUtils.isEmpty(url)) {
            return URL_$$$$;
        }
        if (url.toLowerCase().startsWith(URL_HEADER_HTTP)) {
            return URL_HTTP;
        } else if (url.toLowerCase().startsWith(URL_HEADER_FILE)) {
            return URL_FILE;
        }
        return URL_$$$$;
    }

    @NonNull
    private static int[] getSize(String sourceUrl, int defaultWidth, int defaultHeight) {
        Matcher m = sImageUrlPattern.matcher(sourceUrl);
        int[] size = new int[]{defaultWidth, defaultHeight};
        if (m.find()) {
            if (TextUtils.isDigitsOnly(m.group(2))) {
                size[0] = Integer.valueOf(m.group(2));
            }
            if (TextUtils.isDigitsOnly(m.group(3))) {
                size[1] = Integer.valueOf(m.group(3));
            }
        }
        return size;
    }

    @NonNull
    private static String getUrl(String sourceUrl) {
        Matcher m = sImageUrlPattern.matcher(sourceUrl);
        if (m.find()) {
            return m.group(1);
        }
        return sourceUrl;
    }
}
