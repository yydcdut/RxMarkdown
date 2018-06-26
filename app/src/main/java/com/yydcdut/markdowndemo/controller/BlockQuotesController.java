package com.yydcdut.markdowndemo.controller;

import android.text.Editable;
import android.widget.Toast;

import com.yydcdut.markdown.MarkdownEditText;

/**
 * Created by yuyidong on 16/7/15.
 */
public class BlockQuotesController {
    private MarkdownEditText mRxMDEditText;

    public BlockQuotesController(MarkdownEditText rxMDEditText) {
        mRxMDEditText = rxMDEditText;
    }

    public void doBlockQuotes() {
        int start = mRxMDEditText.getSelectionStart();
        int end = mRxMDEditText.getSelectionEnd();
        if (start == end) {
            int position0 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), start) + 1;
            Editable editable = mRxMDEditText.getText();
            if ("> ".equals(editable.subSequence(Utils.safePosition(position0, editable), Utils.safePosition(position0 + "> ".length(), editable)).toString())) {
                mRxMDEditText.getText().delete(position0, position0 + "> ".length());
                return;
            }

            mRxMDEditText.getText().insert(position0, "> ");
        } else {
            int position0 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), start) + 1;
            int position1 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), end) + 1;
            if (position0 == position1) {
                Editable editable = mRxMDEditText.getText();
                int selectedStart = mRxMDEditText.getSelectionStart();
                int selectedEnd = mRxMDEditText.getSelectionEnd();
                if (selectedStart >= "> ".length() && ("> ".equals(editable.subSequence(Utils.safePosition(selectedStart - "> ".length(), editable), Utils.safePosition(selectedStart, editable)).toString())) &&
                        ((selectedStart > "\n> ".length() && editable.charAt(selectedStart - 3) == '\n') || selectedStart < "\n> ".length()) || (
                        selectedStart > "> > ".length() && "> > ".equals(editable.subSequence(Utils.safePosition(selectedStart - "> > ".length(), editable), Utils.safePosition(selectedStart, editable)).toString()))) {
                    mRxMDEditText.getText().delete(selectedStart - "> ".length(), selectedStart);
                    mRxMDEditText.setSelection(selectedStart - "> ".length(), selectedEnd - "> ".length());
                    return;
                }

                if ((selectedStart > 0 && editable.charAt(selectedStart - 1) == '\n') || selectedStart == 0) {
                    if (selectedEnd < editable.length() && editable.charAt(selectedEnd) != '\n') {
                        mRxMDEditText.getText().insert(selectedEnd, "\n");
                    }
                    mRxMDEditText.getText().insert(selectedStart, "> ");
                    mRxMDEditText.setSelection(selectedStart + "> ".length(), selectedEnd + "> ".length());
                } else {
                    if (selectedEnd + 1 < editable.length() && editable.charAt(selectedEnd + 1) != '\n') {
                        mRxMDEditText.getText().insert(selectedEnd, "\n");
                    }
                    mRxMDEditText.getText().insert(selectedStart, "\n> ");
                    mRxMDEditText.setSelection(selectedStart + "\n> ".length(), selectedEnd + "\n> ".length());
                }
            } else {
                Toast.makeText(mRxMDEditText.getContext(), "无法操作多行", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void addNestedBlockQuotes() {
        int start = mRxMDEditText.getSelectionStart();
        int end = mRxMDEditText.getSelectionEnd();
        if (start == end) {
            int position0 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), start) + 1;
            mRxMDEditText.getText().insert(position0, "> ");
        } else {
            int position0 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), start) + 1;
            int position1 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), end) + 1;
            if (position0 == position1) {
                int selectedStart = mRxMDEditText.getSelectionStart();
                int selectedEnd = mRxMDEditText.getSelectionEnd();
                Editable editable = mRxMDEditText.getText();
                if (selectedStart >= "> ".length()
                        && ("> ".equals(editable.subSequence(Utils.safePosition(selectedStart - "> ".length(), editable), Utils.safePosition(selectedStart, editable)).toString())) &&
                        ((selectedStart > "\n> ".length() && editable.charAt(selectedStart - 3) == '\n') || selectedStart < "\n> ".length()) || (
                        selectedStart > "> > ".length() && "> > ".equals(editable.subSequence(Utils.safePosition(selectedStart - "> > ".length(), editable), Utils.safePosition(selectedStart, editable)).toString()))) {
                    mRxMDEditText.getText().insert(selectedStart, "> ");
                    mRxMDEditText.setSelection(selectedStart + "> ".length(), selectedEnd + "> ".length());
                    return;
                }

                if ((selectedStart > 0 && editable.charAt(selectedStart - 1) == '\n') || selectedStart == 0) {
                    if (selectedEnd < editable.length() && editable.charAt(selectedEnd) != '\n') {
                        mRxMDEditText.getText().insert(selectedEnd, "\n");
                    }
                    mRxMDEditText.getText().insert(selectedStart, "> ");
                    mRxMDEditText.setSelection(selectedStart + "> ".length(), selectedEnd + "> ".length());
                } else {
                    if (selectedEnd + 1 < editable.length() && editable.charAt(selectedEnd + 1) != '\n') {
                        mRxMDEditText.getText().insert(selectedEnd, "\n");
                    }
                    mRxMDEditText.getText().insert(selectedStart, "\n> ");
                    mRxMDEditText.setSelection(selectedStart + "\n> ".length(), selectedEnd + "\n> ".length());
                }
            } else {
                Toast.makeText(mRxMDEditText.getContext(), "无法操作多行", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
