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
package com.yydcdut.markdown.loader;

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
 * The default loader, if user doesn't set loader.
 * <p>
 * Created by yuyidong on 16/6/27.
 */
public class DefaultLoader implements MDImageLoader {

    private Context mContext;

    /**
     * Constructor
     *
     * @param context Context
     */
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
        URL url = new URL(http);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setReadTimeout(5000);
        InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
        byte[] bytes = getBytes(in);
        httpURLConnection.disconnect();
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
        return out.toByteArray();
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
