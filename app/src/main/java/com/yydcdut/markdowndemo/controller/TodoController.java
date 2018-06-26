package com.yydcdut.markdowndemo.controller;

import android.text.Editable;
import android.widget.Toast;

import com.yydcdut.markdown.MarkdownEditText;

/**
 * Created by yuyidong on 16/7/13.
 */

public class TodoController {
    private MarkdownEditText mRxMDEditText;

    public TodoController(MarkdownEditText rxMDEditText) {
        mRxMDEditText = rxMDEditText;
    }

    public void doTodo() {
        int start = mRxMDEditText.getSelectionStart();
        int end = mRxMDEditText.getSelectionEnd();
        int position0 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), start) + 1;
        int position00 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), end) + 1;
        if (position0 != position00) {
            Toast.makeText(mRxMDEditText.getContext(), "无法操作多行", Toast.LENGTH_SHORT).show();
            return;
        }
        Editable editable = mRxMDEditText.getText();
        if ("- [ ] ".equals(editable.subSequence(Utils.safePosition(position0, editable), Utils.safePosition(position0 + "- [ ] ".length(), editable)).toString())) {
            editable.delete(position0, position0 + "- [ ] ".length());
        } else if ("- [x] ".equalsIgnoreCase(editable.subSequence(Utils.safePosition(position0, editable), Utils.safePosition(position0 + "- [ ] ".length(), editable)).toString())) {
            editable.delete(position0, position0 + "- [x] ".length());
            editable.insert(position0, "- [ ] ");
        } else {
            editable.insert(position0, "- [ ] ");
        }
    }

    public void doTodoDone() {
        int start = mRxMDEditText.getSelectionStart();
        int end = mRxMDEditText.getSelectionEnd();
        int position0 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), start) + 1;
        int position00 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), end) + 1;
        if (position0 != position00) {
            Toast.makeText(mRxMDEditText.getContext(), "无法操作多行", Toast.LENGTH_SHORT).show();
            return;
        }
        Editable editable = mRxMDEditText.getText();
        if ("- [x] ".equals(editable.subSequence(Utils.safePosition(position0, editable), Utils.safePosition(position0 + "- [x] ".length(), editable)).toString())) {
            mRxMDEditText.getText().delete(position0, position0 + "- [x] ".length());
        } else if ("- [ ] ".equalsIgnoreCase(editable.subSequence(Utils.safePosition(position0, editable), Utils.safePosition(position0 + "- [ ] ".length(), editable)).toString())) {
            editable.delete(position0, position0 + "- [ ] ".length());
            editable.insert(position0, "- [x] ");
        } else {
            editable.insert(position0, "- [x] ");
        }
    }
}
