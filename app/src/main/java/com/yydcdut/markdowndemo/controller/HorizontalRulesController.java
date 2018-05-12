package com.yydcdut.markdowndemo.controller;

import android.text.Editable;
import android.widget.Toast;

import com.yydcdut.markdown.MarkdownEditText;
import com.yydcdut.markdown.span.MDHorizontalRulesSpan;

/**
 * Created by yuyidong on 16/7/13.
 */
public class HorizontalRulesController {
    private MarkdownEditText mRxMDEditText;

    public HorizontalRulesController(MarkdownEditText rxMDEditText) {
        mRxMDEditText = rxMDEditText;
    }

    public void doHorizontalRules() {
        int start = mRxMDEditText.getSelectionStart();
        int end = mRxMDEditText.getSelectionEnd();
        int position0 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), start) + 1;
        int position00 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), end) + 1;
        if (position0 != position00) {
            Toast.makeText(mRxMDEditText.getContext(), "无法操作多行", Toast.LENGTH_SHORT).show();
            return;
        }
        MDHorizontalRulesSpan mdHorizontalRulesSpan = Utils.getSpans(mRxMDEditText, start, end, MDHorizontalRulesSpan.class);
        if (mdHorizontalRulesSpan != null) {
            Editable editable = mRxMDEditText.getText();
            int spanStart = editable.getSpanStart(mdHorizontalRulesSpan);
            int spanEnd = editable.getSpanEnd(mdHorizontalRulesSpan);
            mRxMDEditText.getText().removeSpan(mdHorizontalRulesSpan);
            mRxMDEditText.getText().delete(spanStart, spanEnd);
        } else {
            char c0 = mRxMDEditText.getText().charAt(start <= 0 ? 0 : start - 1);
            char c1 = mRxMDEditText.getText().charAt(end >= mRxMDEditText.length() - 1 ? mRxMDEditText.length() - 1 : end + 1);
            StringBuilder sb = new StringBuilder();
            if (c0 != '\n' && start != 0) {
                sb.append("\n");
            }
            sb.append("---");
            if (c1 != '\n' || end >= mRxMDEditText.length()) {
                sb.append("\n");
            }
            mRxMDEditText.getText().insert(start, sb.toString());
        }
    }
}
