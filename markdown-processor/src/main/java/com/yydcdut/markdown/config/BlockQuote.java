package com.yydcdut.markdown.config;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyidong on 2018/4/24.
 */
public class BlockQuote {
    public float size = 1.0f;
    public int lineColor = Color.LTGRAY;
    public List<Integer> bgColorList = new ArrayList<Integer>() {{
        add(Color.TRANSPARENT);
    }};
}
