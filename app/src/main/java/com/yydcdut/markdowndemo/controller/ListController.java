package com.yydcdut.markdowndemo.controller;

import android.widget.Toast;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.RxMDEditText;
import com.yydcdut.rxmarkdown.span.MDUnOrderListSpan;

/**
 * Created by yuyidong on 16/7/15.
 */
public class ListController {
    private RxMDEditText mRxMDEditText;
    private RxMDConfiguration mRxMDConfiguration;

    public ListController(RxMDEditText rxMDEditText, RxMDConfiguration rxMDConfiguration) {
        mRxMDEditText = rxMDEditText;
        mRxMDConfiguration = rxMDConfiguration;
    }

    public void doUnOrderList() {
        int start = mRxMDEditText.getSelectionStart();
        int end = mRxMDEditText.getSelectionEnd();
        if (start == end) {
            MDUnOrderListSpan mdUnOrderListSpan = Utils.getSpans(mRxMDEditText, start, end, MDUnOrderListSpan.class);
            int position = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), start) + 1;
            if (mdUnOrderListSpan != null) {
                if (mdUnOrderListSpan.getNested() == 0) {
                    mRxMDEditText.getText().delete(position, position + "* ".length());
                    return;
                }
                mRxMDEditText.getText().delete(position, position + 1);
                return;
            }
            mRxMDEditText.getText().insert(position, "* ");
        } else {
            int position0 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), start) + 1;
            int position00 = Utils.findBeforeNewLineChar(mRxMDEditText.getText(), end) + 1;
            if (position0 != position00) {
                Toast.makeText(mRxMDEditText.getContext(), "无法操作多行", Toast.LENGTH_SHORT).show();
                return;
            }
            int selectedStart = mRxMDEditText.getSelectionStart();
            int selectedEnd = mRxMDEditText.getSelectionEnd();
            MDUnOrderListSpan mdUnOrderListSpan = Utils.getSpans(mRxMDEditText, start, end, MDUnOrderListSpan.class);
            if (mdUnOrderListSpan != null) {
                if (mdUnOrderListSpan.getNested() == 0) {
                    mRxMDEditText.getText().delete(position0, position0 + "* ".length());
                    mRxMDEditText.setSelection(selectedStart - "* ".length(), selectedEnd - "* ".length());
                    return;
                }
                mRxMDEditText.getText().delete(position0, position0 + 1);
                mRxMDEditText.setSelection(selectedStart - 1, selectedEnd - 1);
                return;
            }
            mRxMDEditText.getText().insert(position0, "* ");
            mRxMDEditText.setSelection(selectedStart + "* ".length(), selectedEnd + "* ".length());
        }
    }
}
