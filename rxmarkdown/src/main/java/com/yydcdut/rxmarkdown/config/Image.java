package com.yydcdut.rxmarkdown.config;

import android.content.Context;

import com.yydcdut.rxmarkdown.loader.DefaultLoader;
import com.yydcdut.rxmarkdown.loader.RxMDImageLoader;

/**
 * Created by yuyidong on 2018/4/25.
 */
public class Image {

    public RxMDImageLoader loader;
    public int[] defaultSize = new int[]{100, 100};

    public Image(Context context) {
        loader = new DefaultLoader(context);
    }
}
