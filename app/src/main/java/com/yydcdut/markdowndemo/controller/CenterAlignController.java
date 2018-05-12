package com.yydcdut.markdowndemo.controller;

import android.text.Editable;
import android.widget.Toast;

import com.yydcdut.markdown.MarkdownEditText;

/**
 * Created by yuyidong on 16/7/13.
 */
public class CenterAlignController {
    private MarkdownEditText mRxMDEditText;

    public CenterAlignController(MarkdownEditText rxMDEditText) {
        mRxMDEditText = rxMDEditText;
    }

    public void doCenter() {
        int start = mRxMDEditText.getSelectionStart();
        int end = mRxMDEditText.getSelectionEnd();
        int position0 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), start) + 1;
        int position1 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), end) + 1;
        if (position0 == position1) {
            int position2 = Utils.findNextNewLineChar(mRxMDEditText.getText(), end);
            if (position2 == -1) {
                position2 = mRxMDEditText.length();
            }
            Editable editable = mRxMDEditText.getText();
            if ("[".equals(editable.subSequence(position0, position0 + 1).toString()) &&
                    "]".equals(editable.subSequence(position2 - 1, position2).toString())) {
                mRxMDEditText.getText().delete(position2 - 1, position2);
                mRxMDEditText.getText().delete(position0, position0 + 1);
            } else {
                mRxMDEditText.getText().insert(position2, "]");
                mRxMDEditText.getText().insert(position0, "[");
            }
        } else {
            Toast.makeText(mRxMDEditText.getContext(), "无法操作多行", Toast.LENGTH_SHORT).show();
        }
    }
}
