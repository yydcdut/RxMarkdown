package com.yydcdut.rxmarkdown.loader;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

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

/**
 * Created by yuyidong on 16/6/27.
 */
public class DefaultLoader implements RxMDImageLoader {
    private static final int URL_$$$$ = -1;
    private static final int URL_HTTP = 0;
    private static final int URL_FILE = 1;

    private static final String URL_HEADER_HTTP = "http://";
    private static final String URL_HEADER_FILE = "file://";

    @Nullable
    @Override
    public byte[] loadSync(@NonNull String url) {
        int type = judgeUrl(url);
        byte[] bytes = null;
        switch (type) {
            case URL_HTTP:
                return net(url);
            case URL_FILE:
                return local(url);
            case URL_$$$$:
            default:
                return bytes;
        }
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

    @Nullable
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

    @Nullable
    private static byte[] local(@NonNull String url) {
        String path = url.substring(URL_HEADER_FILE.length() + 1, url.length());
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
