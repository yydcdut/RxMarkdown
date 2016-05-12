package com.yydcdut.rxmarkdown.widget;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by yuyidong on 16/5/12.
 */
public class TextWrapper {
    private TextView mTextView;
    private EditText mEditText;

    public TextWrapper(TextView textView) {
        mTextView = textView;
    }

    public TextWrapper(EditText editText) {
        mEditText = editText;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public EditText getEditText() {
        return mEditText;
    }
}
