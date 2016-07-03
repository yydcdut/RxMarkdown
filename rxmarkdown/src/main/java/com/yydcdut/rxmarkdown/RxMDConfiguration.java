package com.yydcdut.rxmarkdown;

import android.graphics.Color;
import android.support.annotation.ColorInt;

import com.yydcdut.rxmarkdown.loader.DefaultLoader;
import com.yydcdut.rxmarkdown.loader.RxMDImageLoader;

/**
 * Created by yuyidong on 16/6/22.
 */
public class RxMDConfiguration {
    private final int[] defaultImageSize;
    @ColorInt
    private final int blockQuotesColor;
    private final float header1RelativeSize;
    private final float header2RelativeSize;
    private final float header3RelativeSize;
    private final float header4RelativeSize;
    private final float header5RelativeSize;
    private final float header6RelativeSize;
    @ColorInt
    private final int horizontalRulesColor;
    @ColorInt
    private final int inlineCodeBgColor;
    @ColorInt
    private final int codeBgColor;
    @ColorInt
    private final int todoColor;
    @ColorInt
    private final int todoDoneColor;
    @ColorInt
    private int unOrderListColor;

    private RxMDImageLoader rxMDImageLoader;

    private boolean isDebug = true;

    public RxMDConfiguration(int[] defaultImageSize, int blockQuotesColor,
                             float header1RelativeSize, float header2RelativeSize,
                             float header3RelativeSize, float header4RelativeSize,
                             float header5RelativeSize, float header6RelativeSize,
                             int horizontalRulesColor, int inlineCodeBgColor, int codeBgColor,
                             int todoColor, int todoDoneColor, int unOrderListColor,
                             RxMDImageLoader rxMDImageLoader, boolean isDebug) {
        this.defaultImageSize = defaultImageSize;
        this.blockQuotesColor = blockQuotesColor;
        this.header1RelativeSize = header1RelativeSize;
        this.header2RelativeSize = header2RelativeSize;
        this.header3RelativeSize = header3RelativeSize;
        this.header4RelativeSize = header4RelativeSize;
        this.header5RelativeSize = header5RelativeSize;
        this.header6RelativeSize = header6RelativeSize;
        this.horizontalRulesColor = horizontalRulesColor;
        this.inlineCodeBgColor = inlineCodeBgColor;
        this.codeBgColor = codeBgColor;
        this.todoColor = todoColor;
        this.todoDoneColor = todoDoneColor;
        this.unOrderListColor = unOrderListColor;
        this.rxMDImageLoader = rxMDImageLoader;
        this.isDebug = isDebug;
    }

    public final int[] getDefaultImageSize() {
        return defaultImageSize;
    }

    public final int getBlockQuotesColor() {
        return blockQuotesColor;
    }

    public final float getHeader1RelativeSize() {
        return header1RelativeSize;
    }

    public final float getHeader2RelativeSize() {
        return header2RelativeSize;
    }

    public final float getHeader3RelativeSize() {
        return header3RelativeSize;
    }

    public final float getHeader4RelativeSize() {
        return header4RelativeSize;
    }

    public final float getHeader5RelativeSize() {
        return header5RelativeSize;
    }

    public final float getHeader6RelativeSize() {
        return header6RelativeSize;
    }

    public final int getHorizontalRulesColor() {
        return horizontalRulesColor;
    }

    public final int getInlineCodeBgColor() {
        return inlineCodeBgColor;
    }

    public final int getCodeBgColor() {
        return codeBgColor;
    }

    public final int getTodoColor() {
        return todoColor;
    }

    public final int getTodoDoneColor() {
        return todoDoneColor;
    }

    public final int getUnOrderListColor() {
        return unOrderListColor;
    }

    public RxMDImageLoader getRxMDImageLoader() {
        return rxMDImageLoader;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public static class Builder {

        private int[] defaultImageSize;

        @ColorInt
        private int blockQuotesColor;

        private float header1RelativeSize;
        private float header2RelativeSize;
        private float header3RelativeSize;
        private float header4RelativeSize;
        private float header5RelativeSize;
        private float header6RelativeSize;

        @ColorInt
        private int horizontalRulesColor;

        @ColorInt
        private int inlineCodeBgColor;

        @ColorInt
        private int codeBgColor;

        @ColorInt
        private int todoColor;

        @ColorInt
        private int todoDoneColor;

        @ColorInt
        private int unOrderListColor;

        private RxMDImageLoader rxMDImageLoader;

        private boolean isDebug = true;

        public Builder() {
            defaultImageSize = new int[]{100, 100};
            blockQuotesColor = Color.LTGRAY;
            header1RelativeSize = 1.6f;
            header2RelativeSize = 1.5f;
            header3RelativeSize = 1.4f;
            header4RelativeSize = 1.3f;
            header5RelativeSize = 1.2f;
            header6RelativeSize = 1.1f;
            horizontalRulesColor = Color.LTGRAY;
            inlineCodeBgColor = Color.LTGRAY;
            codeBgColor = Color.LTGRAY;
            todoColor = Color.DKGRAY;
            todoDoneColor = Color.DKGRAY;
            unOrderListColor = Color.BLACK;
            rxMDImageLoader = new DefaultLoader();
        }

        public Builder setDefaultImageSize(int width, int height) {
            defaultImageSize = new int[]{width, height};
            return this;
        }

        public Builder setBlockQuotesColor(@ColorInt int blockQuotesColor) {
            this.blockQuotesColor = blockQuotesColor;
            return this;
        }

        public Builder setHeader1RelativeSize(float header1RelativeSize) {
            this.header1RelativeSize = header1RelativeSize;
            return this;
        }

        public Builder setHeader2RelativeSize(float header2RelativeSize) {
            this.header2RelativeSize = header2RelativeSize;
            return this;
        }

        public Builder setHeader3RelativeSize(float header3RelativeSize) {
            this.header3RelativeSize = header3RelativeSize;
            return this;
        }

        public Builder setHeader4RelativeSize(float header4RelativeSize) {
            this.header4RelativeSize = header4RelativeSize;
            return this;
        }

        public Builder setHeader5RelativeSize(float header5RelativeSize) {
            this.header5RelativeSize = header5RelativeSize;
            return this;
        }

        public Builder setHeader6RelativeSize(float header6RelativeSize) {
            this.header6RelativeSize = header6RelativeSize;
            return this;
        }

        public Builder setHorizontalRulesColor(@ColorInt int horizontalRulesColor) {
            this.horizontalRulesColor = horizontalRulesColor;
            return this;
        }

        public Builder setInlineCodeBgColor(@ColorInt int inlineCodeBgColor) {
            this.inlineCodeBgColor = inlineCodeBgColor;
            return this;
        }

        public Builder setCodeBgColor(@ColorInt int codeBgColor) {
            this.codeBgColor = codeBgColor;
            return this;
        }

        public Builder setTodoDoneColor(@ColorInt int todoDoneColor) {
            this.todoDoneColor = todoDoneColor;
            return this;
        }

        public Builder setTodoColor(@ColorInt int todoColor) {
            this.todoColor = todoColor;
            return this;
        }

        public Builder setUnOrderListColor(int unOrderListColor) {
            this.unOrderListColor = unOrderListColor;
            return this;
        }

        public Builder setRxMDImageLoader(RxMDImageLoader rxMDImageLoader) {
            this.rxMDImageLoader = rxMDImageLoader;
            return this;
        }

        public Builder setDebug(boolean debug) {
            isDebug = debug;
            return this;
        }

        public RxMDConfiguration build() {
            return new RxMDConfiguration(defaultImageSize, blockQuotesColor,
                    header1RelativeSize,
                    header2RelativeSize,
                    header3RelativeSize,
                    header4RelativeSize,
                    header5RelativeSize,
                    header6RelativeSize,
                    horizontalRulesColor, inlineCodeBgColor, codeBgColor,
                    todoColor, todoDoneColor, unOrderListColor, rxMDImageLoader,
                    isDebug);
        }
    }
}
