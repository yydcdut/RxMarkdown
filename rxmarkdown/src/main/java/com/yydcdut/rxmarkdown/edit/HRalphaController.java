package com.yydcdut.rxmarkdown.edit;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.yydcdut.rxmarkdown.RxMDEditText;
import com.yydcdut.rxmarkdown.span.MDHorizontalRulesSpan;

/**
 * RxMDEditText, horizontal rules controller.
 * When editText's selection is on horizontal rules, The transparency of words is normal.
 * When editText's selection is not on horizontal rules, The transparency of words is 20%.
 * <p>
 * Created by yuyidong on 16/7/8.
 */
public class HRAlphaController {
    private RxMDEditText mRxMDEditText;

    public HRAlphaController(@NonNull RxMDEditText rxMDEditText) {
        mRxMDEditText = rxMDEditText;
    }

    public void onSelectionChanged(int selStart, int selEnd) {
        setAllHorizontalRulesTextColor();
        removeCurrentHorizontalRulesTextColor(selStart, selEnd);
    }

    private void setAllHorizontalRulesTextColor() {
        Editable editable = mRxMDEditText.getText();
        MDHorizontalRulesSpan[] spans = editable.getSpans(0, editable.length(), MDHorizontalRulesSpan.class);
        if (spans.length > 0) {
            for (MDHorizontalRulesSpan span : spans) {
                int start = editable.getSpanStart(span);
                int end = editable.getSpanEnd(span);
                if (!existForegroundColorSpan(start, end)) {
                    int textColor = mRxMDEditText.getCurrentTextColor();
                    editable.setSpan(new ForegroundColorSpan(
                                    Color.argb(51,
                                            Color.red(textColor),
                                            Color.green(textColor),
                                            Color.blue(textColor))),
                            start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
    }

    private void removeCurrentHorizontalRulesTextColor(int selStart, int selEnd) {
        Editable editable = mRxMDEditText.getText();
        MDHorizontalRulesSpan[] spans = editable.getSpans(selStart, selEnd, MDHorizontalRulesSpan.class);
        if (spans.length > 0) {
            for (MDHorizontalRulesSpan span : spans) {
                int start = editable.getSpanStart(span);
                int end = editable.getSpanEnd(span);
                ForegroundColorSpan[] foregroundColorSpans = editable.getSpans(start, end, ForegroundColorSpan.class);
                for (ForegroundColorSpan foregroundColorSpan : foregroundColorSpans) {
                    editable.removeSpan(foregroundColorSpan);
                }
            }
        }
    }

    private boolean existForegroundColorSpan(int start, int end) {
        ForegroundColorSpan[] foregroundColorSpans = mRxMDEditText.getText().getSpans(start, end, ForegroundColorSpan.class);
        return foregroundColorSpans != null ? foregroundColorSpans.length == 0 ? false : true : false;
    }
}
