package com.yydcdut.rxmarkdown.config;

import android.graphics.Color;

import com.yydcdut.rxmarkdown.callback.OnLinkClickCallback;

/**
 * Created by yuyidong on 2018/4/25.
 */
public class Link {
    public int color = Color.RED;
    public boolean underline = true;
    public OnLinkClickCallback callback;
}
