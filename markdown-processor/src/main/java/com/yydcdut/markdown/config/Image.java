package com.yydcdut.markdown.config;

import android.content.Context;

import com.yydcdut.markdown.loader.DefaultLoader;
import com.yydcdut.markdown.loader.MDImageLoader;

/**
 * Created by yuyidong on 2018/4/25.
 */
public class Image {

    public MDImageLoader loader;
    public int[] defaultSize = new int[]{100, 100};

    public Image(Context context) {
        loader = new DefaultLoader(context);
    }
}
