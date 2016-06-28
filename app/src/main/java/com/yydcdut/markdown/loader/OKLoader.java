package com.yydcdut.markdown.loader;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yydcdut.rxmarkdown.loader.RxMDImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yuyidong on 16/6/27.
 */
public class OKLoader implements RxMDImageLoader {

    @Nullable
    @Override
    public byte[] loadSync(@NonNull String url) {
        if (url.toLowerCase().startsWith("http://")) {
            byte[] bytes = null;
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();
                InputStream is = response.body().byteStream();
                bytes = getBytes(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bytes;
        } else if (url.toLowerCase().startsWith("file://")) {
            return local(url);
        } else {
            return null;
        }
    }

    @Nullable
    private static byte[] local(@NonNull String url) {
        String path = url.substring("file://".length() + 1, url.length());
        InputStream inputStream = null;
        byte[] bytes = null;
        try {
            inputStream = new FileInputStream(path);
            bytes = getBytes(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeStream(inputStream);
        }
        return bytes;
    }

    @Nullable
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
