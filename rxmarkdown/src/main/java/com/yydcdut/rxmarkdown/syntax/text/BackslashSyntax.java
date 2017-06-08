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
package com.yydcdut.rxmarkdown.syntax.text;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;

import com.yydcdut.rxmarkdown.RxMDConfiguration;

/**
 * The implementation of syntax for back slash.
 * syntax:
 * "\"
 * <p>
 * Created by yuyidong on 16/6/20.
 */
class BackslashSyntax extends TextSyntaxAdapter {

    protected static final String KEY_BACKSLASH = "\\";

    protected static final String KEY_ENCODE = "@%7DF16dgf%jy@po&";
    protected static final String KEY_ENCODE_1 = "%4usyHDlL&@D%";
    protected static final String KEY_ENCODE_2 = "&YDhs@h4sF&%kLsx63sd@";
    protected static final String KEY_ENCODE_3 = "%hsyRjh34l%%2@";
    protected static final String KEY_ENCODE_4 = "&@da&U56ec%k%QW@";

    public BackslashSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
    }

    @Override
    boolean isMatch(@NonNull String text) {
        if (text.contains(BoldSyntax.KEY_BACKSLASH_VALUE) ||
                text.contains(BoldSyntax.KEY_BACKSLASH_VALUE_1) ||
                text.contains(CenterAlignSyntax.KEY_BACKSLASH_VALUE_1) ||
                text.contains(FootnoteSyntax.KEY_BACKSLASH_VALUE_0) ||
                text.contains(FootnoteSyntax.KEY_BACKSLASH_VALUE_2) ||
                text.contains(HyperLinkSyntax.KEY_BACKSLASH_VALUE_0) ||
                text.contains(HyperLinkSyntax.KEY_BACKSLASH_VALUE_1) ||
                text.contains(HyperLinkSyntax.KEY_BACKSLASH_VALUE_3) ||
                text.contains(ImageSyntax.KEY_BACKSLASH_VALUE_0) ||
                text.contains(ImageSyntax.KEY_BACKSLASH_VALUE_2) ||
                text.contains(ImageSyntax.KEY_BACKSLASH_VALUE_4) ||
                text.contains(InlineCodeSyntax.KEY_BACKSLASH_VALUE) ||
                text.contains(ItalicSyntax.KEY_BACKSLASH_VALUE) ||
                text.contains(ItalicSyntax.KEY_BACKSLASH_VALUE_1) ||
                text.contains(StrikeThroughSyntax.KEY_BACKSLASH_VALUE)) {
            return true;
        } else {
            return false;
        }
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        int index;
        //----------  BoldSyntax  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(BoldSyntax.KEY_BACKSLASH_VALUE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + BoldSyntax.KEY_BACKSLASH_VALUE.length(), "*");
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(BoldSyntax.KEY_BACKSLASH_VALUE_1);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + BoldSyntax.KEY_BACKSLASH_VALUE_1.length(), "_");
        }
        //----------  BoldSyntax  ----------
        //----------  CenterAlignSyntax  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(CenterAlignSyntax.KEY_BACKSLASH_VALUE_1);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + CenterAlignSyntax.KEY_BACKSLASH_VALUE_1.length(), CenterAlignSyntax.KEY_1_CENTER_ALIGN);
        }
        //----------  CenterAlignSyntax  ----------
        //----------  FootnoteSyntax  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(FootnoteSyntax.KEY_BACKSLASH_VALUE_0);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + FootnoteSyntax.KEY_BACKSLASH_VALUE_0.length(), "[");
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(FootnoteSyntax.KEY_BACKSLASH_VALUE_2);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + FootnoteSyntax.KEY_BACKSLASH_VALUE_2.length(), FootnoteSyntax.KEY_1_FOOTNOTE);
        }
        //----------  FootnoteSyntax  ----------
        //----------  HyperLinkSyntax  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(HyperLinkSyntax.KEY_BACKSLASH_VALUE_0);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + HyperLinkSyntax.KEY_BACKSLASH_VALUE_0.length(), HyperLinkSyntax.KEY_0_HYPER_LINK);
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(HyperLinkSyntax.KEY_BACKSLASH_VALUE_1);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + HyperLinkSyntax.KEY_BACKSLASH_VALUE_1.length(), "]");
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(HyperLinkSyntax.KEY_BACKSLASH_VALUE_3);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + HyperLinkSyntax.KEY_BACKSLASH_VALUE_3.length(), HyperLinkSyntax.KEY_2_HYPER_LINK);
        }
        //----------  HyperLinkSyntax  ----------
        //----------  ImageSyntax  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(ImageSyntax.KEY_BACKSLASH_VALUE_0);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + ImageSyntax.KEY_BACKSLASH_VALUE_0.length(), "!");
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(ImageSyntax.KEY_BACKSLASH_VALUE_2);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + ImageSyntax.KEY_BACKSLASH_VALUE_2.length(), "]");
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(ImageSyntax.KEY_BACKSLASH_VALUE_4);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + ImageSyntax.KEY_BACKSLASH_VALUE_4.length(), ImageSyntax.KEY_2_IMAGE);
        }
        //----------  ImageSyntax  ----------
        //----------  InlineCodeSyntax  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(InlineCodeSyntax.KEY_BACKSLASH_VALUE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + InlineCodeSyntax.KEY_BACKSLASH_VALUE.length(), InlineCodeSyntax.KEY_INLINE_CODE);
        }
        //----------  InlineCodeSyntax  ----------
        //----------  ItalicSyntax  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(ItalicSyntax.KEY_BACKSLASH_VALUE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + ItalicSyntax.KEY_BACKSLASH_VALUE.length(), ItalicSyntax.KEY_ITALIC);
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(ItalicSyntax.KEY_BACKSLASH_VALUE_1);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + ItalicSyntax.KEY_BACKSLASH_VALUE_1.length(), ItalicSyntax.KEY_ITALIC_1);
        }
        //----------  ItalicSyntax  ----------
        //----------  StrikeThroughSyntax  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(StrikeThroughSyntax.KEY_BACKSLASH_VALUE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + StrikeThroughSyntax.KEY_BACKSLASH_VALUE.length(), "~");
        }
        //----------  StrikeThroughSyntax  ----------
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }
}
