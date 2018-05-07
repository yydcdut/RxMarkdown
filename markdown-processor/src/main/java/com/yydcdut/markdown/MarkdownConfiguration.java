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
package com.yydcdut.markdown;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.yydcdut.markdown.callback.OnLinkClickCallback;
import com.yydcdut.markdown.config.BlockQuote;
import com.yydcdut.markdown.config.Code;
import com.yydcdut.markdown.config.Header;
import com.yydcdut.markdown.config.HorizontalRule;
import com.yydcdut.markdown.config.Image;
import com.yydcdut.markdown.config.Link;
import com.yydcdut.markdown.config.Todo;
import com.yydcdut.markdown.config.UnOrderList;
import com.yydcdut.markdown.loader.MDImageLoader;
import com.yydcdut.markdown.theme.Theme;
import com.yydcdut.markdown.theme.ThemeDefault;

import java.util.List;

/**
 * The display configuration of RxMarkdown
 * <p>
 * Created by yuyidong on 16/6/22.
 */
public class MarkdownConfiguration {
    private final Header header;
    private final BlockQuote blockQuote;
    private final HorizontalRule horizontalRule;
    private final Code code;
    private final Theme theme;
    private final Todo todo;
    private final UnOrderList unOrderList;
    private final Link link;
    private final Image image;

    /**
     * Constructor
     *
     * @param header         Header size style
     * @param blockQuote     block quote style
     * @param horizontalRule horizontal rule style
     * @param code           code style
     * @param theme          code block theme
     * @param \t\o\d\o       \t\o\d\o and done style
     * @param unOrderList    unorder list style
     * @param link           link style
     * @param image          image style
     */
    protected MarkdownConfiguration(Header header, BlockQuote blockQuote, HorizontalRule horizontalRule,
                                    Code code, Theme theme, Todo todo, UnOrderList unOrderList, Link link, Image image) {
        this.header = header;
        this.blockQuote = blockQuote;
        this.horizontalRule = horizontalRule;
        this.code = code;
        this.theme = theme;
        this.todo = todo;
        this.unOrderList = unOrderList;
        this.link = link;
        this.image = image;
    }

    /**
     * get header1 relative size
     *
     * @return the size relative to current text
     */
    public final float getHeader1RelativeSize() {
        return header.h1;
    }

    /**
     * get header2 relative size
     *
     * @return the size relative to current text
     */
    public final float getHeader2RelativeSize() {
        return header.h2;
    }

    /**
     * get header3 relative size
     *
     * @return the size relative to current text
     */
    public final float getHeader3RelativeSize() {
        return header.h3;
    }

    /**
     * get header4 relative size
     *
     * @return the size relative to current text
     */
    public final float getHeader4RelativeSize() {
        return header.h4;
    }

    /**
     * get header5 relative size
     *
     * @return the size relative to current text
     */
    public final float getHeader5RelativeSize() {
        return header.h5;
    }

    /**
     * get header6 relative size
     *
     * @return the size relative to current text
     */
    public final float getHeader6RelativeSize() {
        return header.h6;
    }

    /**
     * get block quote line color
     *
     * @return the color
     */
    public final int getBlockQuotesLineColor() {
        return blockQuote.lineColor;
    }

    /**
     * get block quote relative size
     *
     * @return the size relative to current text
     */
    public final float getBlockQuoteRelativeSize() {
        return blockQuote.size;
    }

    /**
     * get block quote background color
     *
     * @return the color list (including nest background color)
     */
    public final List<Integer> getBlockQuoteBgColor() {
        return blockQuote.bgColorList;
    }

    /**
     * get horizontal rules color
     *
     * @return the color
     */
    public final int getHorizontalRulesColor() {
        return horizontalRule.color;
    }

    /**
     * get horizontal rules height
     *
     * @return the height of horizontal rules
     */
    public int getHorizontalRulesHeight() {
        return horizontalRule.height;
    }

    /**
     * get code font color
     *
     * @return the font color
     */
    public final int getCodeFontColor() {
        return code.color;
    }

    /**
     * get code background color
     *
     * @return the background color
     */
    public final int getCodeBgColor() {
        return code.bgColor;
    }

    /**
     * get code block theme
     *
     * @return the theme
     */
    public Theme getTheme() {
        return theme;
    }

    /**
     * get to do color
     *
     * @return the color
     */
    public final int getTodoColor() {
        return todo.todoColor;
    }

    /**
     * get done color
     *
     * @return the color
     */
    public final int getTodoDoneColor() {
        return todo.doneColor;
    }

    /**
     * get unorder list point color
     *
     * @return the color
     */
    public final int getUnOrderListColor() {
        return unOrderList.color;
    }

    /**
     * get link font color
     *
     * @return the color
     */
    public int getLinkFontColor() {
        return link.color;
    }

    /**
     * whether show link underline
     *
     * @return whether show link underline
     */
    public boolean isShowLinkUnderline() {
        return link.underline;
    }

    /**
     * get link click callback
     *
     * @return {@link OnLinkClickCallback}
     */
    public OnLinkClickCallback getOnLinkClickCallback() {
        return link.callback;
    }

    /**
     * get loader
     *
     * @return {@link MDImageLoader}
     */
    public MDImageLoader getRxMDImageLoader() {
        return image.loader;
    }

    /**
     * get image default size
     *
     * @return the array size is 2, [width, height]
     */
    public final int[] getDefaultImageSize() {
        return image.defaultSize;
    }

    /**
     * the build of configuration
     */
    public static class Builder {

        private Header header;
        private BlockQuote blockQuote;
        private HorizontalRule horizontalRule;
        private Code code;
        private Theme theme;
        private Todo todo;
        private UnOrderList unOrderList;
        private Link link;
        private Image image;

        /**
         * Constructor
         *
         * @param context Context
         */
        public Builder(@NonNull Context context) {
            header = new Header();
            blockQuote = new BlockQuote();
            horizontalRule = new HorizontalRule();
            code = new Code();
            theme = new ThemeDefault();
            todo = new Todo();
            unOrderList = new UnOrderList();
            link = new Link();
            image = new Image(context);
        }

        /**
         * set header h1 relative size
         *
         * @param header1RelativeSize relative to current text size
         * @return self
         */
        public Builder setHeader1RelativeSize(float header1RelativeSize) {
            header.h1 = header1RelativeSize;
            return this;
        }

        /**
         * set header h2 relative size
         *
         * @param header2RelativeSize relative to current text size
         * @return self
         */
        public Builder setHeader2RelativeSize(float header2RelativeSize) {
            header.h2 = header2RelativeSize;
            return this;
        }

        /**
         * set header h3 relative size
         *
         * @param header3RelativeSize relative to current text size
         * @return self
         */
        public Builder setHeader3RelativeSize(float header3RelativeSize) {
            header.h3 = header3RelativeSize;
            return this;
        }

        /**
         * set header h4 relative size
         *
         * @param header4RelativeSize relative to current text size
         * @return self
         */
        public Builder setHeader4RelativeSize(float header4RelativeSize) {
            header.h4 = header4RelativeSize;
            return this;
        }

        /**
         * set header h5 relative size
         *
         * @param header5RelativeSize relative to current text size
         * @return self
         */
        public Builder setHeader5RelativeSize(float header5RelativeSize) {
            header.h5 = header5RelativeSize;
            return this;
        }

        /**
         * set header h6 relative size
         *
         * @param header6RelativeSize relative to current text size
         * @return self
         */
        public Builder setHeader6RelativeSize(float header6RelativeSize) {
            header.h6 = header6RelativeSize;
            return this;
        }

        /**
         * set block quote line color
         *
         * @param lineColor the color
         * @return self
         */
        public Builder setBlockQuotesLineColor(@ColorInt int lineColor) {
            blockQuote.lineColor = lineColor;
            return this;
        }

        /**
         * set the block quote background color (including nest background color)
         *
         * @param bgColor     the background color
         * @param nestBgColor the nest background color
         * @return self
         */
        public Builder setBlockQuotesBgColor(int bgColor, int... nestBgColor) {
            blockQuote.bgColorList.set(0, bgColor);
            if (nestBgColor != null && nestBgColor.length > 0) {
                final int count = nestBgColor.length;
                for (int i = 0; i < count; i++) {
                    blockQuote.bgColorList.add(nestBgColor[i]);
                }
            }
            return this;
        }

        /**
         * set the block quote relative size compared to standard size
         *
         * @param blockQuotesRelativeSize the size
         * @return self
         */
        public Builder setBlockQuotesRelativeSize(float blockQuotesRelativeSize) {
            blockQuote.size = blockQuotesRelativeSize;
            return this;
        }

        /**
         * set horizontal rules color
         *
         * @param horizontalRulesColor the color
         * @return self
         */
        public Builder setHorizontalRulesColor(@ColorInt int horizontalRulesColor) {
            horizontalRule.color = horizontalRulesColor;
            return this;
        }

        /**
         * set horizontal rules height
         *
         * @param horizontalRulesHeight horizontal rules height
         * @return self
         */
        public Builder setHorizontalRulesHeight(int horizontalRulesHeight) {
            horizontalRule.height = horizontalRulesHeight;
            return this;
        }

        /**
         * set code background color
         *
         * @param fontColor the color
         * @return self
         */
        public Builder setCodeFontColor(@ColorInt int fontColor) {
            code.color = fontColor;
            return this;
        }

        /**
         * set inline code background color
         *
         * @param codeBgColor the color
         * @return self
         */
        public Builder setCodeBgColor(@ColorInt int codeBgColor) {
            code.bgColor = codeBgColor;
            return this;
        }

        /**
         * set code theme
         *
         * @param theme the code style
         * @return self
         */
        public Builder setTheme(Theme theme) {
            this.theme = theme;
            return this;
        }

        /**
         * set to do color
         *
         * @param todoColor the color
         * @return self
         */
        public Builder setTodoColor(@ColorInt int todoColor) {
            todo.todoColor = todoColor;
            return this;
        }

        /**
         * set done color
         *
         * @param todoDoneColor the color
         * @return self
         */
        public Builder setTodoDoneColor(@ColorInt int todoDoneColor) {
            todo.doneColor = todoDoneColor;
            return this;
        }

        /**
         * set unorder list point color
         *
         * @param unOrderListColor the color
         * @return self
         */
        public Builder setUnOrderListColor(int unOrderListColor) {
            unOrderList.color = unOrderListColor;
            return this;
        }


        /**
         * set link font color
         *
         * @param linkFontColor the color
         * @return self
         */
        public Builder setLinkFontColor(int linkFontColor) {
            link.color = linkFontColor;
            return this;
        }

        /**
         * is link underline
         *
         * @param show boolean, whether show link underline
         * @return self
         */
        public Builder showLinkUnderline(boolean show) {
            link.underline = show;
            return this;
        }

        /**
         * set link click callback
         *
         * @param onLinkClickCallback OnLinkClickCallback, invoked when clicking the link text
         * @return self
         */
        public Builder setOnLinkClickCallback(OnLinkClickCallback onLinkClickCallback) {
            link.callback = onLinkClickCallback;
            return this;
        }

        /**
         * set loader
         *
         * @param MDImageLoader the loader
         * @return self
         */
        public Builder setRxMDImageLoader(MDImageLoader MDImageLoader) {
            image.loader = MDImageLoader;
            return this;
        }

        /**
         * set image default size
         *
         * @param width  the default width to display
         * @param height the default height to display
         * @return self
         */
        public Builder setDefaultImageSize(int width, int height) {
            image.defaultSize = new int[]{width, height};
            return this;
        }

        /**
         * get RxMDConfiguration
         *
         * @return RxMDConfiguration
         */
        public MarkdownConfiguration build() {
            return new MarkdownConfiguration(header, blockQuote, horizontalRule, code, theme, todo, unOrderList, link, image);
        }
    }
}
