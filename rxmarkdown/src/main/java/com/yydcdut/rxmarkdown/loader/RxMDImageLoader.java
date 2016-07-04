package com.yydcdut.rxmarkdown.loader;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.Locale;

/**
 * Created by yuyidong on 16/6/27.
 */
public interface RxMDImageLoader {
    @Nullable
    byte[] loadSync(@NonNull String url) throws IOException;

    enum Scheme {
        HTTP("http"), HTTPS("https"), FILE("file"), ASSETS("assets"), DRAWABLE("drawable"), UNKNOWN("");

        private String scheme;
        private String uriPrefix;

        Scheme(String scheme) {
            this.scheme = scheme;
            uriPrefix = scheme + "://";
        }

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

        public String wrap(String path) {
            return uriPrefix + path;
        }

        public String crop(String uri) {
            if (!belongsTo(uri)) {
                throw new IllegalArgumentException(String.format("URI [%1$s] doesn't have expected scheme [%2$s]", uri, scheme));
            }
            return uri.substring(uriPrefix.length());
        }
    }
}
