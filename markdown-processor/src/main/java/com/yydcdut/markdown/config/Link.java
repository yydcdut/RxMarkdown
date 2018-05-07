package com.yydcdut.markdown.config;

import android.graphics.Color;

import com.yydcdut.markdown.callback.OnLinkClickCallback;

/**
 * Created by yuyidong on 2018/4/25.
 */
public class Link {
    public int color = Color.RED;
    public boolean underline = true;
    public OnLinkClickCallback callback;
}
