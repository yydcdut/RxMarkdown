package com.yydcdut.rxmarkdown;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.yydcdut.rxmarkdown.factory.AbsGrammarFactory;

import java.util.ArrayList;

/**
 * Created by yuyidong on 16/5/20.
 */
public class RxMDEditText extends EditText {
    private static final String TAG = "yuyidong_RxMDEditText";

    private AbsGrammarFactory mGrammarFactory;
    private RxMDConfiguration mRxMDConfiguration;

    private ArrayList<TextWatcher> mListeners;

    public RxMDEditText(Context context) {
        super(context);
    }

    public RxMDEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RxMDEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

    protected void setFactoryAndConfig(@NonNull AbsGrammarFactory absGrammarFactory,
                                       @NonNull RxMDConfiguration rxMDConfiguration) {
        mGrammarFactory = absGrammarFactory;
        mGrammarFactory.init(rxMDConfiguration);
        mRxMDConfiguration = rxMDConfiguration;
        super.addTextChangedListener(mEditTextWatcher);
        Editable editable = getText();
        if (!TextUtils.isEmpty(editable)) {
            mEditTextWatcher.beforeTextChanged("", 0, 0, editable.length());
            mEditTextWatcher.onTextChanged(editable, 0, 0, editable.length());
            mEditTextWatcher.afterTextChanged(editable);
        }
    }

    private CharSequence format() {
        if (mGrammarFactory == null) {
            return getText();
        }
        long begin = System.currentTimeMillis();
        Editable editable = getText();
        int selectionEnd = getSelectionEnd();
        int selectionStart = getSelectionStart();
        setText(mGrammarFactory.parse(editable));
        setSelection(selectionStart, selectionEnd);
        if (mRxMDConfiguration.isDebug()) {
            Log.i(TAG, "finish-->" + (System.currentTimeMillis() - begin));
        }
        return getText();
    }
}
