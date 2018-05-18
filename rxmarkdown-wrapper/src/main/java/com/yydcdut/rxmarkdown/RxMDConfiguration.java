package com.yydcdut.rxmarkdown;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.callback.OnLinkClickCallback;
import com.yydcdut.markdown.callback.OnTodoClickCallback;
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

/**
 * Created by yuyidong on 2018/5/6.
 */
public class RxMDConfiguration extends MarkdownConfiguration {
    /**
     * Constructor
     *
     * @param header         Header size style
     * @param blockQuote     block quote style
     * @param horizontalRule horizontal rule style
     * @param code           code style
     * @param theme          code block theme
     * @param todo
     * @param unOrderList    unorder list style
     * @param link           link style
     * @param image          image style
     */
    private RxMDConfiguration(Header header, BlockQuote blockQuote, HorizontalRule horizontalRule, Code code, Theme theme, Todo todo, UnOrderList unOrderList, Link link, Image image) {
        super(header, blockQuote, horizontalRule, code, theme, todo, unOrderList, link, image);
    }

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
         * set _todo(done) click callback
         *
         * @param onTodoClickCallback OnTodoClickCallback, invoked when clicking _todo or done syntax
         * @return self
         */
        public Builder setOnTodoClickCallback(OnTodoClickCallback onTodoClickCallback) {
            todo.onTodoClickCallback = onTodoClickCallback;
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
        public RxMDConfiguration build() {
            return new RxMDConfiguration(header, blockQuote, horizontalRule, code, theme, todo, unOrderList, link, image);
        }
    }


}
