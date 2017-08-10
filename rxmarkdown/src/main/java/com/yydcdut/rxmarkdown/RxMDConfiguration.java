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
package com.yydcdut.rxmarkdown;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.callback.BlockquoteBackgroundNestedColorFetcher;
import com.yydcdut.rxmarkdown.callback.OnLinkClickCallback;
import com.yydcdut.rxmarkdown.loader.DefaultLoader;
import com.yydcdut.rxmarkdown.loader.RxMDImageLoader;
import com.yydcdut.rxmarkdown.theme.Theme;
import com.yydcdut.rxmarkdown.theme.ThemeDefault;

/**
 * The display configuration of RxMarkdown
 * <p>
 * Created by yuyidong on 16/6/22.
 */
public class RxMDConfiguration {
    private final int[] mDefaultImageSize;
    @ColorInt
    private final int mBlockQuotesColor;
    private final float mHeader1RelativeSize;
    private final float mHeader2RelativeSize;
    private final float mHeader3RelativeSize;
    private final float mHeader4RelativeSize;
    private final float mHeader5RelativeSize;
    private final float mHeader6RelativeSize;
    private final float mBlockQuoteRelativeSize;
    @ColorInt
    private final int mHorizontalRulesColor;
    private int mHorizontalRulesHeight;
    @ColorInt
    private final int mInlineCodeBgColor;
    @ColorInt
    @Deprecated
    private final int mCodeBgColor;
    private final Theme mTheme;
    @ColorInt
    private final int mTodoColor;
    @ColorInt
    private final int mTodoDoneColor;
    @ColorInt
    private final int mBlockQuoteBgColor;
    private final BlockquoteBackgroundNestedColorFetcher mColorFetcher;
    @ColorInt
    private final int mUnOrderListColor;
    @ColorInt
    private final int mLinkColor;
    private final boolean mIsLinkUnderline;
    private final boolean mIsAppendNewlineAfterLastLine;
    private final RxMDImageLoader mRxMDImageLoader;
    private final OnLinkClickCallback mOnLinkClickCallback;


    private final boolean mIsDebug;

    /**
     * Constructor
     *
     * @param defaultImageSize             default image size
     * @param blockQuotesColor             block quotes color
     * @param header1RelativeSize          header1 relative size
     * @param header2RelativeSize          header2 relative size
     * @param header3RelativeSize          header3 relative size
     * @param header4RelativeSize          header4 relative size
     * @param header5RelativeSize          header5 relative size
     * @param header6RelativeSize          header6 relative size
     * @param blockQuoteRelativeSize       blockQuote relative size
     * @param horizontalRulesColor         horizontal rules color
     * @param horizontalRulesHeight        horizontal rules height
     * @param inlineCodeBgColor            inline code bg color
     * @param codeBgColor                  code bg color
     * @param theme                        theme
     * @param todoColor                    to do color
     * @param todoDoneColor                to do done color
     * @param unOrderListColor             unorder list color
     * @param blockQuoteBgColor            blockQuote background color
     * @param linkColor                    link color
     * @param isLinkUnderline              link underline
     * @param rxMDImageLoader              loader
     * @param onLinkClickCallback          lick click callback
     * @param isDebug                      debug
     * @param isAppendNewlineAfterLastLine add newline after last line
     * @param colorFetcher                 the nested blockQuote color callback
     */
    private RxMDConfiguration(int[] defaultImageSize, int blockQuotesColor,
                              float header1RelativeSize, float header2RelativeSize,
                              float header3RelativeSize, float header4RelativeSize,
                              float header5RelativeSize, float header6RelativeSize,
                              float blockQuoteRelativeSize, int horizontalRulesColor, int horizontalRulesHeight,
                              int inlineCodeBgColor, int codeBgColor, Theme theme,
                              int todoColor, int todoDoneColor, int unOrderListColor,
                              int blockQuoteBgColor, int linkColor, boolean isLinkUnderline,
                              RxMDImageLoader rxMDImageLoader, OnLinkClickCallback onLinkClickCallback,
                              boolean isDebug, boolean isAppendNewlineAfterLastLine, BlockquoteBackgroundNestedColorFetcher colorFetcher) {
        mDefaultImageSize = defaultImageSize;
        mBlockQuotesColor = blockQuotesColor;
        mHeader1RelativeSize = header1RelativeSize;
        mHeader2RelativeSize = header2RelativeSize;
        mHeader3RelativeSize = header3RelativeSize;
        mHeader4RelativeSize = header4RelativeSize;
        mHeader5RelativeSize = header5RelativeSize;
        mHeader6RelativeSize = header6RelativeSize;
        mBlockQuoteRelativeSize = blockQuoteRelativeSize;
        mHorizontalRulesColor = horizontalRulesColor;
        mHorizontalRulesHeight = horizontalRulesHeight;
        mInlineCodeBgColor = inlineCodeBgColor;
        mCodeBgColor = codeBgColor;
        mTheme = theme;
        mTodoColor = todoColor;
        mTodoDoneColor = todoDoneColor;
        mUnOrderListColor = unOrderListColor;
        mBlockQuoteBgColor = blockQuoteBgColor;
        mLinkColor = linkColor;
        mIsLinkUnderline = isLinkUnderline;
        mRxMDImageLoader = rxMDImageLoader;
        mOnLinkClickCallback = onLinkClickCallback;
        mIsDebug = isDebug;
        mIsAppendNewlineAfterLastLine = isAppendNewlineAfterLastLine;
        mColorFetcher = colorFetcher;
    }

    /**
     * get image default size
     *
     * @return the array size is 2, [width, height]
     */
    public final int[] getDefaultImageSize() {
        return mDefaultImageSize;
    }

    /**
     * get block quote color
     *
     * @return the color
     */
    public final int getBlockQuotesColor() {
        return mBlockQuotesColor;
    }

    /**
     * get header1 relative size
     *
     * @return the size relative to current text
     */
    public final float getHeader1RelativeSize() {
        return mHeader1RelativeSize;
    }

    /**
     * get header2 relative size
     *
     * @return the size relative to current text
     */
    public final float getHeader2RelativeSize() {
        return mHeader2RelativeSize;
    }

    /**
     * get header3 relative size
     *
     * @return the size relative to current text
     */
    public final float getHeader3RelativeSize() {
        return mHeader3RelativeSize;
    }

    /**
     * get header4 relative size
     *
     * @return the size relative to current text
     */
    public final float getHeader4RelativeSize() {
        return mHeader4RelativeSize;
    }

    /**
     * get header5 relative size
     *
     * @return the size relative to current text
     */
    public final float getHeader5RelativeSize() {
        return mHeader5RelativeSize;
    }

    /**
     * get header6 relative size
     *
     * @return the size relative to current text
     */
    public final float getHeader6RelativeSize() {
        return mHeader6RelativeSize;
    }

    /**
     * get blockquote relative size
     *
     * @return the size relative to current text
     */
    public final float getBlockQuoteRelativeSize() {
        return mBlockQuoteRelativeSize;
    }

    /**
     * get horizontal rules color
     *
     * @return the color
     */
    public final int getHorizontalRulesColor() {
        return mHorizontalRulesColor;
    }

    /**
     * get horizontal rules height
     *
     * @return the height of horizontal rules
     */
    public int getHorizontalRulesHeight() {
        return mHorizontalRulesHeight;
    }

    /**
     * get inline code background color
     *
     * @return the color
     */
    public final int getInlineCodeBgColor() {
        return mInlineCodeBgColor;
    }

    /**
     * get code background color
     *
     * @return the color
     */
    @Deprecated
    public final int getCodeBgColor() {
        return mCodeBgColor;
    }

    /**
     * get code theme
     *
     * @return the theme
     */
    public Theme getTheme() {
        return mTheme;
    }

    /**
     * get to do color
     *
     * @return the color
     */
    public final int getTodoColor() {
        return mTodoColor;
    }

    /**
     * get done color
     *
     * @return the color
     */
    public final int getTodoDoneColor() {
        return mTodoDoneColor;
    }

    /**
     * get unorder list color
     *
     * @return the color
     */
    public final int getUnOrderListColor() {
        return mUnOrderListColor;
    }

    /**
     * get blockquote background color
     *
     * @return the color
     */
    public final int getBlockQuoteBgColor() {
        return mBlockQuoteBgColor;
    }

    /**
     * get link color
     *
     * @return the color
     */
    public int getLinkColor() {
        return mLinkColor;
    }

    /**
     * whether link underline
     *
     * @return whether link underline
     */
    public boolean isLinkUnderline() {
        return mIsLinkUnderline;
    }

    /**
     * get loader
     *
     * @return {@link RxMDImageLoader}
     */
    public RxMDImageLoader getRxMDImageLoader() {
        return mRxMDImageLoader;
    }

    /**
     * get link click callback
     *
     * @return {@link OnLinkClickCallback}
     */
    public OnLinkClickCallback getOnLinkClickCallback() {
        return mOnLinkClickCallback;
    }

    /**
     * whether is debug or not
     *
     * @return TRUE:debug
     */
    public boolean isDebug() {
        return mIsDebug;
    }


    /**
     * whether to append a newline character to the last line of the parsed text
     *
     * @return <code>true</code> if newline should be appended
     */
    public boolean isAppendNewlineAfterLastLine() {
        return mIsAppendNewlineAfterLastLine;
    }

    /**
     * Get the color fetcher for background colors for nested blockquote levels.
     * If not set, the standard background color will be used.
     *
     * @return the fetcher
     */
    public BlockquoteBackgroundNestedColorFetcher getBlockQuoteBackgroundNestedColorFetcher() {
        return mColorFetcher;
    }

    /**
     * the build of configuration
     */
    public static class Builder {

        private int[] mDefaultImageSize;

        @ColorInt
        private int mBlockQuotesColor;

        private float mHeader1RelativeSize;
        private float mHeader2RelativeSize;
        private float mHeader3RelativeSize;
        private float mHeader4RelativeSize;
        private float mHeader5RelativeSize;
        private float mHeader6RelativeSize;

        private float mBlockQuoteRelativeSize;

        @ColorInt
        private int mHorizontalRulesColor;
        private int mHorizontalRulesHeight;

        @ColorInt
        private int mInlineCodeBgColor;

        @ColorInt
        @Deprecated
        private int mCodeBgColor;

        private Theme mTheme;

        @ColorInt
        private int mTodoColor;

        @ColorInt
        private int mTodoDoneColor;

        @ColorInt
        private int mUnOrderListColor;

        @ColorInt
        private int mBlockQuoteBgColor;

        @ColorInt
        private int mLinkColor;
        private boolean mIsLinkUnderline;

        private RxMDImageLoader mRxMDImageLoader;

        private OnLinkClickCallback mOnLinkClickCallback;

        private boolean mIsDebug;

        private boolean mIsAppendNewlineAfterLastLine;

        private BlockquoteBackgroundNestedColorFetcher mColorFetcher = null;


        /**
         * Constructor
         *
         * @param context Context
         */
        public Builder(@NonNull Context context) {
            mDefaultImageSize = new int[]{100, 100};
            mBlockQuotesColor = Color.LTGRAY;
            mHeader1RelativeSize = 1.6f;
            mHeader2RelativeSize = 1.5f;
            mHeader3RelativeSize = 1.4f;
            mHeader4RelativeSize = 1.3f;
            mHeader5RelativeSize = 1.2f;
            mHeader6RelativeSize = 1.1f;
            mBlockQuoteRelativeSize = 1f;
            mHorizontalRulesColor = Color.LTGRAY;
            mHorizontalRulesHeight = -1;
            mInlineCodeBgColor = Color.LTGRAY;
            mCodeBgColor = Color.LTGRAY;
            mTheme = new ThemeDefault();
            mTodoColor = Color.DKGRAY;
            mTodoDoneColor = Color.DKGRAY;
            mUnOrderListColor = Color.BLACK;
            mBlockQuoteBgColor = Color.TRANSPARENT;
            mLinkColor = Color.RED;
            mIsLinkUnderline = true;
            mRxMDImageLoader = new DefaultLoader(context);
            mOnLinkClickCallback = null;
            mIsAppendNewlineAfterLastLine = true;
        }

        /**
         * set image default size
         *
         * @param width  the default width to display
         * @param height the default height to display
         * @return self
         */
        public Builder setDefaultImageSize(int width, int height) {
            mDefaultImageSize = new int[]{width, height};
            return this;
        }

        /**
         * set block quote color
         *
         * @param blockQuotesColor the color
         * @return self
         */
        public Builder setBlockQuotesColor(@ColorInt int blockQuotesColor) {
            mBlockQuotesColor = blockQuotesColor;
            return this;
        }

        /**
         * set header h1 relative size
         *
         * @param header1RelativeSize relative to current text size
         * @return self
         */
        public Builder setHeader1RelativeSize(float header1RelativeSize) {
            mHeader1RelativeSize = header1RelativeSize;
            return this;
        }

        /**
         * set header h2 relative size
         *
         * @param header2RelativeSize relative to current text size
         * @return self
         */
        public Builder setHeader2RelativeSize(float header2RelativeSize) {
            mHeader2RelativeSize = header2RelativeSize;
            return this;
        }

        /**
         * set header h3 relative size
         *
         * @param header3RelativeSize relative to current text size
         * @return self
         */
        public Builder setHeader3RelativeSize(float header3RelativeSize) {
            mHeader3RelativeSize = header3RelativeSize;
            return this;
        }

        /**
         * set header h4 relative size
         *
         * @param header4RelativeSize relative to current text size
         * @return self
         */
        public Builder setHeader4RelativeSize(float header4RelativeSize) {
            mHeader4RelativeSize = header4RelativeSize;
            return this;
        }

        /**
         * set header h5 relative size
         *
         * @param header5RelativeSize relative to current text size
         * @return self
         */
        public Builder setHeader5RelativeSize(float header5RelativeSize) {
            mHeader5RelativeSize = header5RelativeSize;
            return this;
        }

        /**
         * set header h6 relative size
         *
         * @param header6RelativeSize relative to current text size
         * @return self
         */
        public Builder setHeader6RelativeSize(float header6RelativeSize) {
            mHeader6RelativeSize = header6RelativeSize;
            return this;
        }

        /**
         * set the blockquote relative size compared to standard size
         *
         * @param blockQuoteRelativeSize the size
         * @return self
         */
        public Builder setBlockQuoteRelativeSize(float blockQuoteRelativeSize) {
            mBlockQuoteRelativeSize = blockQuoteRelativeSize;
            return this;
        }

        /**
         * set horizontal rules color
         *
         * @param horizontalRulesColor the color
         * @return self
         */
        public Builder setHorizontalRulesColor(@ColorInt int horizontalRulesColor) {
            mHorizontalRulesColor = horizontalRulesColor;
            return this;
        }

        /**
         * set horizontal rules height
         *
         * @param horizontalRulesHeight horizontal rules height
         * @return self
         */
        public Builder setHorizontalRulesHeight(int horizontalRulesHeight) {
            mHorizontalRulesHeight = horizontalRulesHeight;
            return this;
        }

        /**
         * set inline code background color
         *
         * @param inlineCodeBgColor the color
         * @return self
         */
        public Builder setInlineCodeBgColor(@ColorInt int inlineCodeBgColor) {
            mInlineCodeBgColor = inlineCodeBgColor;
            return this;
        }

        /**
         * set code background color
         * {@link #setTheme(Theme)}
         *
         * @param codeBgColor the color
         * @return self
         */
        @Deprecated
        public Builder setCodeBgColor(@ColorInt int codeBgColor) {
            mCodeBgColor = codeBgColor;
            return this;
        }

        /**
         * set code theme
         *
         * @param theme the code style
         * @return self
         */
        public Builder setTheme(Theme theme) {
            mTheme = theme;
            return this;
        }

        /**
         * set done color
         *
         * @param todoDoneColor the color
         * @return self
         */
        public Builder setTodoDoneColor(@ColorInt int todoDoneColor) {
            mTodoDoneColor = todoDoneColor;
            return this;
        }

        /**
         * set to do color
         *
         * @param todoColor the color
         * @return self
         */
        public Builder setTodoColor(@ColorInt int todoColor) {
            mTodoColor = todoColor;
            return this;
        }

        /**
         * set unorder list color
         *
         * @param unOrderListColor the color
         * @return self
         */
        public Builder setUnOrderListColor(int unOrderListColor) {
            mUnOrderListColor = unOrderListColor;
            return this;
        }

        /**
         * set the blockquote background color
         *
         * @param blockQuoteBgColor the color
         * @return self
         */
        public Builder setBlockQuoteBgColor(int blockQuoteBgColor) {
            mBlockQuoteBgColor = blockQuoteBgColor;
            return this;
        }

        /**
         * set link color
         *
         * @param linkColor the color
         * @return self
         */
        public Builder setLinkColor(int linkColor) {
            mLinkColor = linkColor;
            return this;
        }

        /**
         * is link underline
         *
         * @param linkUnderline boolean, whether link underline
         * @return self
         */
        public Builder setLinkUnderline(boolean linkUnderline) {
            mIsLinkUnderline = linkUnderline;
            return this;
        }

        /**
         * set loader
         *
         * @param rxMDImageLoader the loader
         * @return self
         */
        public Builder setRxMDImageLoader(RxMDImageLoader rxMDImageLoader) {
            mRxMDImageLoader = rxMDImageLoader;
            return this;
        }

        /**
         * set link click callback
         *
         * @param onLinkClickCallback OnLinkClickCallback, the callback
         * @return self
         */
        public Builder setOnLinkClickCallback(OnLinkClickCallback onLinkClickCallback) {
            mOnLinkClickCallback = onLinkClickCallback;
            return this;
        }

        /**
         * whether debug or not
         * default is true
         *
         * @param debug boolean
         * @return self
         */
        public Builder setDebug(boolean debug) {
            mIsDebug = debug;
            return this;
        }

        /**
         * whether to append a newline character to the last line of the parsed text
         *
         * @param append <code>true if newline should be appended</code>
         * @return self
         */
        public Builder setAppendNewlineAfterLastLine(boolean append) {
            mIsAppendNewlineAfterLastLine = append;
            return this;
        }

        /**
         * set nested block quote background color
         *
         * @param colorFetcher the callback
         * @return self
         */
        public Builder setBlockquoteBackgroundNestedColorFetcher(BlockquoteBackgroundNestedColorFetcher colorFetcher) {
            mColorFetcher = colorFetcher;
            return this;
        }

        /**
         * get RxMDConfiguration
         *
         * @return RxMDConfiguration
         */
        public RxMDConfiguration build() {
            return new RxMDConfiguration(
                    mDefaultImageSize, mBlockQuotesColor,
                    mHeader1RelativeSize,
                    mHeader2RelativeSize,
                    mHeader3RelativeSize,
                    mHeader4RelativeSize,
                    mHeader5RelativeSize,
                    mHeader6RelativeSize,
                    mBlockQuoteRelativeSize, mHorizontalRulesColor, mHorizontalRulesHeight,
                    mInlineCodeBgColor, mCodeBgColor, mTheme,
                    mTodoColor, mTodoDoneColor, mUnOrderListColor,
                    mBlockQuoteBgColor, mLinkColor, mIsLinkUnderline,
                    mRxMDImageLoader, mOnLinkClickCallback,
                    mIsDebug, mIsAppendNewlineAfterLastLine, mColorFetcher);
        }

    }
}
