package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;

import com.yydcdut.rxmarkdown.Configuration;

/**
 * Created by yuyidong on 16/6/20.
 */
public class BackslashGrammar extends AbsAndroidGrammar {
    protected static final String KEY_BACKSLASH = "\\";

    protected static final String KEY_ENCODE = "@$7DF16dgf%jy@po&";
    protected static final String KEY_ENCODE_1 = "%4usyHDlL&@D%";
    protected static final String KEY_ENCODE_2 = "&YDhs@h4sF&%kLsx63sd@";
    protected static final String KEY_ENCODE_3 = "%hsyRjh34l%$2@";
    protected static final String KEY_ENCODE_4 = "&@da&U56ec%k$QW@";

    public BackslashGrammar(@NonNull Configuration configuration) {
        super(configuration);
    }

    @Override
    boolean isMatch(@NonNull String text) {
        if (text.contains(BoldGrammar.KEY_BACKSLASH_VALUE) ||
                text.contains(CenterAlignGrammar.KEY_BACKSLASH_VALUE_1) ||
                text.contains(FootnoteGrammar.KEY_BACKSLASH_VALUE_0) ||
                text.contains(FootnoteGrammar.KEY_BACKSLASH_VALUE_2) ||
                text.contains(HyperLinkGrammar.KEY_BACKSLASH_VALUE_0) ||
                text.contains(HyperLinkGrammar.KEY_BACKSLASH_VALUE_1) ||
                text.contains(HyperLinkGrammar.KEY_BACKSLASH_VALUE_3) ||
                text.contains(ImageGrammar.KEY_BACKSLASH_VALUE_0) ||
                text.contains(ImageGrammar.KEY_BACKSLASH_VALUE_2) ||
                text.contains(ImageGrammar.KEY_BACKSLASH_VALUE_4) ||
                text.contains(InlineCodeGrammar.KEY_BACKSLASH_VALUE) ||
                text.contains(ItalicGrammar.KEY_BACKSLASH_VALUE) ||
                text.contains(StrikeThroughGrammar.KEY_BACKSLASH_VALUE)) {
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
        int index = -1;
        //----------  BoldGrammar  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(BoldGrammar.KEY_BACKSLASH_VALUE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + BoldGrammar.KEY_BACKSLASH_VALUE.length(), "*");
        }
        //----------  BoldGrammar  ----------
        //----------  CenterAlignGrammar  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(CenterAlignGrammar.KEY_BACKSLASH_VALUE_1);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + CenterAlignGrammar.KEY_BACKSLASH_VALUE_1.length(), CenterAlignGrammar.KEY1);
        }
        //----------  CenterAlignGrammar  ----------
        //----------  FootnoteGrammar  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(FootnoteGrammar.KEY_BACKSLASH_VALUE_0);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + FootnoteGrammar.KEY_BACKSLASH_VALUE_0.length(), "[");
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(FootnoteGrammar.KEY_BACKSLASH_VALUE_2);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + FootnoteGrammar.KEY_BACKSLASH_VALUE_2.length(), FootnoteGrammar.KEY_END);
        }
        //----------  FootnoteGrammar  ----------
        //----------  HyperLinkGrammar  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(HyperLinkGrammar.KEY_BACKSLASH_VALUE_0);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + HyperLinkGrammar.KEY_BACKSLASH_VALUE_0.length(), HyperLinkGrammar.KEY_0);
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(HyperLinkGrammar.KEY_BACKSLASH_VALUE_1);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + HyperLinkGrammar.KEY_BACKSLASH_VALUE_1.length(), "]");
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(HyperLinkGrammar.KEY_BACKSLASH_VALUE_3);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + HyperLinkGrammar.KEY_BACKSLASH_VALUE_3.length(), HyperLinkGrammar.KEY_2);
        }
        //----------  HyperLinkGrammar  ----------
        //----------  ImageGrammar  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(ImageGrammar.KEY_BACKSLASH_VALUE_0);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + ImageGrammar.KEY_BACKSLASH_VALUE_0.length(), "!");
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(ImageGrammar.KEY_BACKSLASH_VALUE_2);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + ImageGrammar.KEY_BACKSLASH_VALUE_2.length(), "]");
        }
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(ImageGrammar.KEY_BACKSLASH_VALUE_4);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + ImageGrammar.KEY_BACKSLASH_VALUE_4.length(), ImageGrammar.KEY_2);
        }
        //----------  ImageGrammar  ----------
        //----------  InlineCodeGrammar  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(InlineCodeGrammar.KEY_BACKSLASH_VALUE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + InlineCodeGrammar.KEY_BACKSLASH_VALUE.length(), InlineCodeGrammar.KEY);
        }
        //----------  InlineCodeGrammar  ----------
        //----------  ItalicGrammar  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(ItalicGrammar.KEY_BACKSLASH_VALUE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + ItalicGrammar.KEY_BACKSLASH_VALUE.length(), ItalicGrammar.KEY);
        }
        //----------  ItalicGrammar  ----------
        //----------  StrikeThroughGrammar  ----------
        while (true) {
            String text = ssb.toString();
            index = text.indexOf(StrikeThroughGrammar.KEY_BACKSLASH_VALUE);
            if (index == -1) {
                break;
            }
            ssb.replace(index, index + StrikeThroughGrammar.KEY_BACKSLASH_VALUE.length(), "~");
        }
        //----------  StrikeThroughGrammar  ----------
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }
}
