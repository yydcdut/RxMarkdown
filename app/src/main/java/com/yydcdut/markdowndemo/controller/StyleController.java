package com.yydcdut.markdowndemo.controller;

import android.text.Editable;
import android.widget.Toast;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.RxMDEditText;

/**
 * Created by yuyidong on 16/7/13.
 */
public class StyleController {
    private RxMDEditText mRxMDEditText;
    private RxMDConfiguration mRxMDConfiguration;

    public StyleController(RxMDEditText rxMDEditText, RxMDConfiguration rxMDConfiguration) {
        mRxMDEditText = rxMDEditText;
        mRxMDConfiguration = rxMDConfiguration;
    }

    public void doBold() {
        int start = mRxMDEditText.getSelectionStart();
        int end = mRxMDEditText.getSelectionEnd();
        if (start == end) {//整个段落
            Toast.makeText(mRxMDEditText.getContext(), "没有选中文字", Toast.LENGTH_SHORT).show();
        } else if (end - start > 4) {//选中了4个以上
            int position0 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), start) + 1;
            int position00 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), end) + 1;
            if (position0 != position00) {
                Toast.makeText(mRxMDEditText.getContext(), "无法操作多行", Toast.LENGTH_SHORT).show();
                return;
            }
            Editable editable = mRxMDEditText.getText();
            if ("**".equals(editable.subSequence(start, start + "**".length()).toString()) &&
                    "**".equals(editable.subSequence(end - "**".length(), end).toString())) {
                mRxMDEditText.getText().delete(end - "**".length(), end);
                mRxMDEditText.getText().delete(start, start + "**".length());
                mRxMDEditText.setSelection(start, end - "**".length() * 2);
            } else {
                mRxMDEditText.getText().insert(end, "**");
                mRxMDEditText.getText().insert(start, "**");
                mRxMDEditText.setSelection(start, end + "**".length() * 2);
            }
        } else {
            mRxMDEditText.getText().insert(end, "**");
            mRxMDEditText.getText().insert(start, "**");
            mRxMDEditText.setSelection(start, end + "**".length() * 2);
        }
    }

    public void doItalic() {
        int start = mRxMDEditText.getSelectionStart();
        int end = mRxMDEditText.getSelectionEnd();
        if (start == end) {//整个段落
            Toast.makeText(mRxMDEditText.getContext(), "没有选中文字", Toast.LENGTH_SHORT).show();
        } else if (end - start > 2) {//选中了4个以上
            int position0 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), start) + 1;
            int position00 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), end) + 1;
            if (position0 != position00) {
                Toast.makeText(mRxMDEditText.getContext(), "无法操作多行", Toast.LENGTH_SHORT).show();
                return;
            }
            Editable editable = mRxMDEditText.getText();
            if ("*".equals(editable.subSequence(start, start + "*".length()).toString()) &&
                    "*".equals(editable.subSequence(end - "*".length(), end).toString())) {
                mRxMDEditText.getText().delete(end - "*".length(), end);
                mRxMDEditText.getText().delete(start, start + "*".length());
                mRxMDEditText.setSelection(start, end - "*".length() * 2);
            } else {
                mRxMDEditText.getText().insert(end, "*");
                mRxMDEditText.getText().insert(start, "*");
                mRxMDEditText.setSelection(start, end + "*".length() * 2);
            }
        } else {
            mRxMDEditText.getText().insert(end, "*");
            mRxMDEditText.getText().insert(start, "*");
            mRxMDEditText.setSelection(start, end + "*".length() * 2);
        }
    }
}
