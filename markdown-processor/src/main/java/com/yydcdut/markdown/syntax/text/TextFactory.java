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
package com.yydcdut.markdown.syntax.text;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.chain.ISpecialChain;
import com.yydcdut.markdown.chain.MultiSyntaxChain;
import com.yydcdut.markdown.chain.SyntaxChain;
import com.yydcdut.markdown.chain.SyntaxDoElseChain;
import com.yydcdut.markdown.chain.SyntaxMultiChains;
import com.yydcdut.markdown.syntax.Syntax;
import com.yydcdut.markdown.syntax.SyntaxFactory;
import com.yydcdut.markdown.utils.TextHelper;

/**
 * This factory's purpose is parsing content <b>correctly</b>, as the same time, it destroys the integrity of the content.
 * This factory will delete the key words of markdown syntax in content.
 * So, hope that it will be used in TextView, not in EditText.
 * <p>
 * Created by yuyidong on 16/5/12.
 */
public class TextFactory implements SyntaxFactory {
    private static final String NEWLINE = "\n";
    private MarkdownConfiguration mMarkdownConfiguration;
    private ISpecialChain mLineChain;
    private ISpecialChain mTotalChain;

    private TextFactory() {
    }

    /**
     * get AndroidFactory object
     *
     * @return {@link SyntaxFactory}
     */
    public static SyntaxFactory create() {
        return new TextFactory();
    }

    @Override
    public Syntax getHorizontalRulesSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new HorizontalRulesSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getBlockQuotesSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new BlockQuotesSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getTodoSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new TodoSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getTodoDoneSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new TodoDoneSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getOrderListSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new OrderListSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getUnOrderListSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new UnOrderListSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getListSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new ListSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getCenterAlignSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new CenterAlignSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getHeaderSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new HeaderSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getBoldSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new BoldSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getItalicSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new ItalicSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getCodeSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new CodeSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getStrikeThroughSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new StrikeThroughSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getFootnoteSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new FootnoteSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getImageSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new ImageSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getHyperLinkSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new HyperLinkSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getCodeBlockSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new CodeBlockSyntax(markdownConfiguration);
    }

    @Override
    public Syntax getBackslashSyntax(@NonNull MarkdownConfiguration markdownConfiguration) {
        return new BackslashSyntax(markdownConfiguration);
    }

    private void init(@NonNull MarkdownConfiguration markdownConfiguration) {
        mMarkdownConfiguration = markdownConfiguration;
        mTotalChain = new MultiSyntaxChain(
                getCodeBlockSyntax(markdownConfiguration),
                getListSyntax(markdownConfiguration));

//        getUnOrderListSyntax(markdownConfiguration),
//                getOrderListSyntax(markdownConfiguration));
        mLineChain = new SyntaxChain(getHorizontalRulesSyntax(markdownConfiguration));
        SyntaxDoElseChain blockQuitesChain = new SyntaxDoElseChain(getBlockQuotesSyntax(markdownConfiguration));
        SyntaxDoElseChain todoChain = new SyntaxDoElseChain(getTodoSyntax(markdownConfiguration));
        SyntaxDoElseChain todoDoneChain = new SyntaxDoElseChain(getTodoDoneSyntax(markdownConfiguration));
        SyntaxMultiChains centerAlignChain = new SyntaxMultiChains(getCenterAlignSyntax(markdownConfiguration));
        SyntaxMultiChains headerChain = new SyntaxMultiChains(getHeaderSyntax(markdownConfiguration));
        MultiSyntaxChain multiChain = new MultiSyntaxChain(
                getImageSyntax(markdownConfiguration),
                getHyperLinkSyntax(markdownConfiguration),
                getCodeSyntax(markdownConfiguration),
                getBoldSyntax(markdownConfiguration),
                getItalicSyntax(markdownConfiguration),
                getStrikeThroughSyntax(markdownConfiguration),
                getFootnoteSyntax(markdownConfiguration));
        SyntaxChain backslashChain = new SyntaxChain(getBackslashSyntax(markdownConfiguration));

        mLineChain.setNextHandleSyntax(blockQuitesChain);

        blockQuitesChain.setNextHandleSyntax(todoChain);
        blockQuitesChain.addNextHandleSyntax(multiChain);

        todoChain.setNextHandleSyntax(todoDoneChain);
        todoChain.addNextHandleSyntax(multiChain);

        todoDoneChain.setNextHandleSyntax(centerAlignChain);
        todoDoneChain.addNextHandleSyntax(multiChain);

        centerAlignChain.addNextHandleSyntax(headerChain);
        centerAlignChain.addNextHandleSyntax(multiChain);

        multiChain.setNextHandleSyntax(backslashChain);
    }

    @NonNull
    @Override
    public CharSequence parse(@NonNull CharSequence charSequence, @NonNull MarkdownConfiguration markdownConfiguration) {
        if (markdownConfiguration == null) {
            return charSequence;
        }
        if (mTotalChain == null || mLineChain == null || mMarkdownConfiguration == null || mMarkdownConfiguration != markdownConfiguration) {
            init(markdownConfiguration);
        }
        CharSequence standardLines = standardizeLineEndings(charSequence);
        SpannableStringBuilder ssb = new SpannableStringBuilder(standardLines);
        ssb = parseTotal(mTotalChain, ssb);
        ssb = parseByLine(mLineChain, ssb);
        return ssb;
    }

    private SpannableStringBuilder parseTotal(ISpecialChain totalChain, SpannableStringBuilder ssb) {
        totalChain.handleSyntax(ssb, 0);
        return ssb;
    }

    private SpannableStringBuilder parseByLine(ISpecialChain lineChain, SpannableStringBuilder content) {
        String text = content.toString();
        String[] lines = text.split(NEWLINE);
        final int linesCount = lines.length;
        SpannableStringBuilder[] ssbLines = new SpannableStringBuilder[linesCount];
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        final int newLineLength = NEWLINE.length();
        int index = 0;
        for (int line = 0; line < linesCount; line++) {
            int lineLength = lines[line].length();
            ssbLines[line] = (SpannableStringBuilder) content.subSequence(TextHelper.safePosition(index, content), TextHelper.safePosition(index + lineLength, content));
            lineChain.handleSyntax(ssbLines[line], line);
            index += lineLength;
            if (line < linesCount - 1) {
                ssbLines[line].append(NEWLINE);
                index += newLineLength;
            }
            ssb.append(ssbLines[line]);
        }
        return ssb;
    }

    private CharSequence standardizeLineEndings(CharSequence charSequence) {
        if (charSequence instanceof String || charSequence instanceof StringBuilder || charSequence instanceof StringBuffer) {
            return TextHelper.standardizeLineEndings(TextHelper.standardizeLineEndings(new StringBuilder(charSequence), "\\r\\n", "\n"),
                    "\\r", "\n").toString();
        } else if (charSequence instanceof Spannable) {
            SpannableStringBuilder ssb = new SpannableStringBuilder(charSequence);
            TextHelper.standardizeLineEndings(ssb, "\r\n", "\n");
            TextHelper.standardizeLineEndings(ssb, "\r", "\n");
            return ssb;
        }
        return charSequence;
    }
}
