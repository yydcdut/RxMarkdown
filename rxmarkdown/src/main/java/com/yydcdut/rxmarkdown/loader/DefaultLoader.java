package com.yydcdut.rxmarkdown.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yuyidong on 16/6/27.
 */
public class DefaultLoader implements RxMDImageLoader {

    private Context mContext;

    public DefaultLoader(Context context) {
        mContext = context;
    }

    @Nullable
    @Override
    public byte[] loadSync(@NonNull String url) throws IOException {
        byte[] bytes = null;
        switch (Scheme.ofUri(url)) {
            case HTTP:
            case HTTPS:
                return http(url);
            case FILE:
                return sdCard(url);
            case ASSETS:
                return assets(url);
            case DRAWABLE:
                return drawable(url);
            case UNKNOWN:
            default:
                return bytes;
        }
    }

    @Nullable
    private static byte[] http(@NonNull String http) throws IOException {
        HttpURLConnection httpURLConnection = null;
        ByteArrayOutputStream out = null;
        byte[] bytes = null;
        try {
            URL url = new URL(http);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            bytes = getBytes(in);
        } finally {
            closeStream(out);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return bytes;
    }

    @Nullable
    private static byte[] sdCard(@NonNull String url) throws IOException {
        String path = Scheme.FILE.crop(url);
        InputStream inputStream = new FileInputStream(path);
        byte[] bytes = getBytes(inputStream);
        closeStream(inputStream);
        return bytes;
    }

    @Nullable
    private byte[] assets(@NonNull String url) throws IOException {
        String filePath = Scheme.ASSETS.crop(url);
        InputStream inputStream = mContext.getAssets().open(filePath);
        byte[] bytes = getBytes(inputStream);
        closeStream(inputStream);
        return bytes;
    }

    @Nullable
    private byte[] drawable(@NonNull String url) throws IOException {
        String drawableIdString = Scheme.DRAWABLE.crop(url);
        int drawableId = Integer.parseInt(drawableIdString);
        InputStream inputStream = mContext.getResources().openRawResource(drawableId);
        byte[] bytes = getBytes(inputStream);
        closeStream(inputStream);
        return bytes;
    }

    @Nullable
    private static byte[] getBytes(@NonNull InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int i;
        while ((i = inputStream.read()) != -1) {
            out.write(i);
        }
        byte[] bytes = out.toByteArray();
        return bytes;
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
}
