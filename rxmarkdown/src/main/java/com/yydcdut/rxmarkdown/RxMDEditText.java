package com.yydcdut.rxmarkdown;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.yydcdut.rxmarkdown.factory.EditFactory;

import java.util.ArrayList;

/**
 * Created by yuyidong on 16/5/20.
 */
public class RxMDEditText extends EditText {

    private ArrayList<TextWatcher> mListeners;

    public RxMDEditText(Context context) {
        super(context);
        init();
    }

    public RxMDEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RxMDEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        super.addTextChangedListener(mEditTextWatcher);
        Editable editable = getText();
        if (!TextUtils.isEmpty(editable)) {
            mEditTextWatcher.beforeTextChanged("", 0, 0, editable.length());
            mEditTextWatcher.onTextChanged(editable, 0, 0, editable.length());
            mEditTextWatcher.afterTextChanged(editable);
        }
    }

    private TextWatcher mEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int before, int after) {
            sendBeforeTextChanged(s, start, before, after);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int after) {
            sendOnTextChanged(s, start, before, after);
        }

        @Override
        public void afterTextChanged(final Editable s) {
            removeTextChangedListener(mEditTextWatcher);
            format();
            addTextChangedListener(mEditTextWatcher);
            sendAfterTextChanged(s);
        }
    };

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        if (watcher == mEditTextWatcher) {
            super.addTextChangedListener(mEditTextWatcher);
        } else {
            if (mListeners == null) {
                mListeners = new ArrayList<>();
            }
            mListeners.add(watcher);
        }
    }

    @Override
    public void removeTextChangedListener(TextWatcher watcher) {
        if (watcher == mEditTextWatcher) {
            super.removeTextChangedListener(mEditTextWatcher);
        } else {
            if (mListeners != null) {
                int i = mListeners.indexOf(watcher);
                if (i >= 0) {
                    mListeners.remove(i);
                }
            }
        }
    }

    private void sendBeforeTextChanged(CharSequence s, int start, int before, int after) {
        if (mListeners != null) {
            final ArrayList<TextWatcher> list = mListeners;
            final int count = list.size();
            for (int i = 0; i < count; i++) {
                list.get(i).beforeTextChanged(s, start, before, after);
            }
        }
    }

    private void sendOnTextChanged(CharSequence s, int start, int before, int after) {
        if (mListeners != null) {
            final ArrayList<TextWatcher> list = mListeners;
            final int count = list.size();
            for (int i = 0; i < count; i++) {
                list.get(i).onTextChanged(s, start, before, after);
            }
        }
    }

    private void sendAfterTextChanged(Editable s) {
        if (mListeners != null) {
            final ArrayList<TextWatcher> list = mListeners;
            final int count = list.size();
            for (int i = 0; i < count; i++) {
                list.get(i).afterTextChanged(s);
            }
        }
    }

    private RxMDConfiguration mRxMDConfiguration;

    public void setConfig(RxMDConfiguration rxMDConfiguration) {
        mRxMDConfiguration = rxMDConfiguration;
    }

    private void format() {
        long begin = System.currentTimeMillis();
        Editable editable = getText();
        int selectionEnd = getSelectionEnd();
        int selectionStart = getSelectionStart();
        EditFactory editFactory = EditFactory.create();
        editFactory.init(mRxMDConfiguration);
        setText(editFactory.parse(editable));
        setSelection(selectionStart, selectionEnd);
        Log.i("yuyidong", "finish-->" + (System.currentTimeMillis() - begin));
    }
}
