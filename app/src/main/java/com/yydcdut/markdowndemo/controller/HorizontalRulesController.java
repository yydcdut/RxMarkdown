package com.yydcdut.markdowndemo.controller;

import android.text.Editable;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.RxMDEditText;
import com.yydcdut.rxmarkdown.span.MDHorizontalRulesSpan;

/**
 * Created by yuyidong on 16/7/13.
 */
public class HorizontalRulesController {
    private RxMDEditText mRxMDEditText;
    private RxMDConfiguration mRxMDConfiguration;

    public HorizontalRulesController(RxMDEditText rxMDEditText, RxMDConfiguration rxMDConfiguration) {
        mRxMDEditText = rxMDEditText;
        mRxMDConfiguration = rxMDConfiguration;
    }

    public void doHorizontalRules() {
        int start = mRxMDEditText.getSelectionStart();
        int end = mRxMDEditText.getSelectionEnd();
        if (start == end) {//整个段落
            MDHorizontalRulesSpan mdHorizontalRulesSpan = Utils.getSpans(mRxMDEditText, start, end, MDHorizontalRulesSpan.class);
            if (mdHorizontalRulesSpan != null) {
                Editable editable = mRxMDEditText.getText();
                int spanStart = editable.getSpanStart(mdHorizontalRulesSpan);
                int spanEnd = editable.getSpanEnd(mdHorizontalRulesSpan);
                mRxMDEditText.getText().removeSpan(mdHorizontalRulesSpan);
                mRxMDEditText.getText().delete(spanStart, spanEnd);
            } else {
                char c0 = mRxMDEditText.getText().charAt(start - 1);
                char c1 = mRxMDEditText.getText().charAt(end + 1);
                StringBuilder sb = new StringBuilder();
                if (c0 != '\n') {
                    sb.append("\n");
                }
                sb.append("---");
                if (c1 != '\n') {
                    sb.append("\n");
                }
                mRxMDEditText.getText().insert(start, sb.toString());
            }
        }
    }
}
