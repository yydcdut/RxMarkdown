package com.yydcdut.markdowndemo.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yydcdut.rxmarkdown.loader.RxMDImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yuyidong on 16/6/27.
 */
public class OKLoader implements RxMDImageLoader {
    private Context mContext;

    public OKLoader(Context context) {
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
                return local(url);
            case ASSETS:
                return asserts(url);
            case DRAWABLE:
                return drawable(url);
            case UNKNOWN:
            default:
                return bytes;
        }
    }

    @Nullable
    private static byte[] http(@NonNull String url) throws IOException {
        ByteArrayOutputStream out = null;
        byte[] bytes = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        InputStream is = response.body().byteStream();
        bytes = getBytes(is);
        closeStream(out);
        return bytes;
    }

    @Nullable
    private static byte[] local(@NonNull String url) throws IOException {
        String path = Scheme.FILE.crop(url);
        InputStream inputStream = null;
        inputStream = new FileInputStream(path);
        byte[] bytes = getBytes(inputStream);
        closeStream(inputStream);
        return bytes;
    }

    @Nullable
    private static byte[] getBytes(@NonNull InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] bytes = null;
        int i;
        while ((i = inputStream.read()) != -1) {
            out.write(i);
        }
        bytes = out.toByteArray();
        closeStream(inputStream);
        return bytes;
    }

    @Nullable
    private byte[] asserts(@NonNull String url) throws IOException {
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
