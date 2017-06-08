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
import com.yydcdut.rxmarkdown.syntax.SyntaxKey;

/**
 * The implementation of syntax for back slash.
 * syntax:
 * "\"
 * <p>
 * Created by yuyidong on 16/6/20.
 */
class BackslashSyntax extends TextSyntaxAdapter {

    public BackslashSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
    }

    @Override
    boolean isMatch(@NonNull String text) {
        if (text.contains(SyntaxKey.KEY_BOLD_BACKSLASH_VALUE) ||
                text.contains(SyntaxKey.KEY_BOLD_BACKSLASH_VALUE_1) ||
                text.contains(SyntaxKey.KEY_CENTER_ALIGN_BACKSLASH_VALUE_RIGHT) ||
                text.contains(SyntaxKey.KEY_FOOTNOTE_BACKSLASH_VALUE_LEFT) ||
                text.contains(SyntaxKey.KEY_FOOTNOTE_BACKSLASH_VALUE_RIGHT) ||
                text.contains(SyntaxKey.KEY_HYPER_LINK_BACKSLASH_VALUE_LEFT) ||
                text.contains(SyntaxKey.KEY_HYPER_LINK_BACKSLASH_VALUE_MIDDLE) ||
                text.contains(SyntaxKey.KEY_HYPER_LINK_BACKSLASH_VALUE_RIGHT) ||
                text.contains(SyntaxKey.KEY_IMAGE_BACKSLASH_VALUE_LEFT) ||
                text.contains(SyntaxKey.KEY_IMAGE_BACKSLASH_VALUE_MIDDLE) ||
                text.contains(SyntaxKey.KEY_IMAGE_BACKSLASH_VALUE_RIGHT) ||
                text.contains(SyntaxKey.KEY_INLINE_BACKSLASH_VALUE) ||
                text.contains(SyntaxKey.KEY_ITALIC_BACKSLASH_VALUE) ||
                text.contains(SyntaxKey.KEY_ITALIC_BACKSLASH_VALUE_1) ||
                text.contains(SyntaxKey.KEY_STRIKE_BACKSLASH_VALUE)) {
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
            index = text.indexOf(SyntaxKey.KEY_BOLD_BACKSLASH_VALUE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + SyntaxKey.KEY_BOLD_BACKSLASH_VALUE.length(), "*");
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(SyntaxKey.KEY_BOLD_BACKSLASH_VALUE_1);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + SyntaxKey.KEY_BOLD_BACKSLASH_VALUE_1.length(), "_");
        }
        //----------  BoldSyntax  ----------
        //----------  CenterAlignSyntax  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(SyntaxKey.KEY_CENTER_ALIGN_BACKSLASH_VALUE_RIGHT);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + SyntaxKey.KEY_CENTER_ALIGN_BACKSLASH_VALUE_RIGHT.length(), SyntaxKey.KEY_CENTER_ALIGN_RIGHT);
        }
        //----------  CenterAlignSyntax  ----------
        //----------  FootnoteSyntax  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(SyntaxKey.KEY_FOOTNOTE_LEFT);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + SyntaxKey.KEY_FOOTNOTE_LEFT.length(), "[");
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(SyntaxKey.KEY_FOOTNOTE_BACKSLASH_VALUE_RIGHT);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + SyntaxKey.KEY_FOOTNOTE_BACKSLASH_VALUE_RIGHT.length(), SyntaxKey.KEY_FOOTNOTE_RIGHT);
        }
        //----------  FootnoteSyntax  ----------
        //----------  HyperLinkSyntax  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(SyntaxKey.KEY_HYPER_LINK_BACKSLASH_VALUE_LEFT);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + SyntaxKey.KEY_HYPER_LINK_BACKSLASH_VALUE_LEFT.length(), SyntaxKey.KEY_HYPER_LINK_LEFT);
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(SyntaxKey.KEY_HYPER_LINK_BACKSLASH_VALUE_MIDDLE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + SyntaxKey.KEY_HYPER_LINK_BACKSLASH_VALUE_MIDDLE.length(), "]");
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(SyntaxKey.KEY_HYPER_LINK_BACKSLASH_VALUE_RIGHT);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + SyntaxKey.KEY_HYPER_LINK_BACKSLASH_VALUE_RIGHT.length(), SyntaxKey.KEY_HYPER_LINK_RIGHT);
        }
        //----------  HyperLinkSyntax  ----------
        //----------  ImageSyntax  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(SyntaxKey.KEY_IMAGE_BACKSLASH_VALUE_LEFT);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + SyntaxKey.KEY_IMAGE_BACKSLASH_VALUE_LEFT.length(), "!");
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(SyntaxKey.KEY_IMAGE_BACKSLASH_VALUE_MIDDLE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + SyntaxKey.KEY_IMAGE_BACKSLASH_VALUE_MIDDLE.length(), "]");
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(SyntaxKey.KEY_IMAGE_BACKSLASH_VALUE_RIGHT);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + SyntaxKey.KEY_IMAGE_BACKSLASH_VALUE_RIGHT.length(), SyntaxKey.KEY_IMAGE_RIGHT);
        }
        //----------  ImageSyntax  ----------
        //----------  InlineCodeSyntax  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(SyntaxKey.KEY_INLINE_BACKSLASH_VALUE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + SyntaxKey.KEY_INLINE_BACKSLASH_VALUE.length(), SyntaxKey.KEY_INLINE_CODE);
        }
        //----------  InlineCodeSyntax  ----------
        //----------  ItalicSyntax  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(SyntaxKey.KEY_ITALIC_BACKSLASH_VALUE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + SyntaxKey.KEY_ITALIC_BACKSLASH_VALUE.length(), SyntaxKey.KEY_ITALIC);
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(SyntaxKey.KEY_ITALIC_BACKSLASH_VALUE_1);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + SyntaxKey.KEY_ITALIC_BACKSLASH_VALUE_1.length(), SyntaxKey.KEY_ITALIC_1);
        }
        //----------  ItalicSyntax  ----------
        //----------  StrikeThroughSyntax  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(SyntaxKey.KEY_STRIKE_BACKSLASH_VALUE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + SyntaxKey.KEY_STRIKE_BACKSLASH_VALUE.length(), "~");
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
