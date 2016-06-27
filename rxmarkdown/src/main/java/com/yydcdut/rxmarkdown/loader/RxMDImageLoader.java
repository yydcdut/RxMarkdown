package com.yydcdut.rxmarkdown.loader;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by yuyidong on 16/6/27.
 */
public interface RxMDImageLoader {
    @Nullable
    byte[] loadSync(@NonNull String url);
}
