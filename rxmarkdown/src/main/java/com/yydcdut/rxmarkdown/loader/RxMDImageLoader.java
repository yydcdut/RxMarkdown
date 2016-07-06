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
package com.yydcdut.rxmarkdown.loader;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.Locale;

/**
 * For image grammar, to load image from network or local.
 * <p>
 * Created by yuyidong on 16/6/27.
 */
public interface RxMDImageLoader {

    /**
     * load in thread
     *
     * @param url the url
     * @return the image byte array
     * @throws IOException the IOException
     */
    @Nullable
    byte[] loadSync(@NonNull String url) throws IOException;

    /**
     * Scheme of Url
     */
    enum Scheme {
        HTTP("http"), HTTPS("https"), FILE("file"), ASSETS("assets"), DRAWABLE("drawable"), UNKNOWN("");

        private String scheme;
        private String uriPrefix;

        Scheme(String scheme) {
            this.scheme = scheme;
            uriPrefix = scheme + "://";
        }

        /**
         * get the scheme
         *
         * @param uri the url
         * @return the scheme of url
         */
        public static Scheme ofUri(String uri) {
            if (uri != null) {
                for (Scheme s : values()) {
                    if (s.belongsTo(uri)) {
                        return s;
                    }
                }
            }
            return UNKNOWN;
        }

        private boolean belongsTo(String uri) {
            return uri.toLowerCase(Locale.US).startsWith(uriPrefix);
        }

        /**
         * get url,filter scheme
         *
         * @param uri url
         * @return the url without scheme
         */
        public String crop(String uri) {
            if (!belongsTo(uri)) {
                throw new IllegalArgumentException(String.format("URI [%1$s] doesn't have expected scheme [%2$s]", uri, scheme));
            }
            return uri.substring(uriPrefix.length());
        }
    }
}
