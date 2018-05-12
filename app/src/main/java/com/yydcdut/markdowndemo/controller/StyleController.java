package com.yydcdut.markdowndemo.controller;

import android.text.Editable;
import android.widget.Toast;

import com.yydcdut.markdown.MarkdownEditText;
import com.yydcdut.markdown.syntax.SyntaxKey;

/**
 * Created by yuyidong on 16/7/13.
 */
public class StyleController {
    private MarkdownEditText mRxMDEditText;

    public StyleController(MarkdownEditText rxMDEditText) {
        mRxMDEditText = rxMDEditText;
    }

    public void doBold() {
        int start = mRxMDEditText.getSelectionStart();
        int end = mRxMDEditText.getSelectionEnd();
        if (start == end) {
            mRxMDEditText.getText().insert(start, "****");
            mRxMDEditText.setSelection(start + 2, end + 2);
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
        if (start == end) {
            mRxMDEditText.getText().insert(start, "**");
            mRxMDEditText.setSelection(start + 1, end + 1);
        } else if (end - start > 2) {//选中了4个以上
            int position0 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), start) + 1;
            int position00 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), end) + 1;
            if (position0 != position00) {
                Toast.makeText(mRxMDEditText.getContext(), "无法操作多行", Toast.LENGTH_SHORT).show();
                return;
            }
            Editable editable = mRxMDEditText.getText();
            if (SyntaxKey.KEY_BOLD_ASTERISK_SINGLE.equals(editable.subSequence(start, start + SyntaxKey.KEY_BOLD_ASTERISK_SINGLE.length()).toString()) &&
                    SyntaxKey.KEY_BOLD_ASTERISK_SINGLE.equals(editable.subSequence(end - SyntaxKey.KEY_BOLD_ASTERISK_SINGLE.length(), end).toString())) {
                mRxMDEditText.getText().delete(end - SyntaxKey.KEY_BOLD_ASTERISK_SINGLE.length(), end);
                mRxMDEditText.getText().delete(start, start + SyntaxKey.KEY_BOLD_ASTERISK_SINGLE.length());
                mRxMDEditText.setSelection(start, end - SyntaxKey.KEY_BOLD_ASTERISK_SINGLE.length() * 2);
            } else {
                mRxMDEditText.getText().insert(end, SyntaxKey.KEY_BOLD_ASTERISK_SINGLE);
                mRxMDEditText.getText().insert(start, SyntaxKey.KEY_BOLD_ASTERISK_SINGLE);
                mRxMDEditText.setSelection(start, end + SyntaxKey.KEY_BOLD_ASTERISK_SINGLE.length() * 2);
            }
        } else {
            mRxMDEditText.getText().insert(end, SyntaxKey.KEY_BOLD_ASTERISK_SINGLE);
            mRxMDEditText.getText().insert(start, SyntaxKey.KEY_BOLD_ASTERISK_SINGLE);
            mRxMDEditText.setSelection(start, end + SyntaxKey.KEY_BOLD_ASTERISK_SINGLE.length() * 2);
        }
    }
}
