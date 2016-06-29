package com.yydcdut.rxmarkdown;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.yydcdut.rxmarkdown.edit.EditProcessor;

import java.util.ArrayList;

/**
 * Created by yuyidong on 16/5/20.
 */
public class RxMDEditText extends EditText {

    private ArrayList<TextWatcher> mListeners;

    private EditProcessor mEditProcessor;

    public RxMDEditText(Context context) {
        super(context);
        super.addTextChangedListener(mEditTextWatcher);
        mEditProcessor = new EditProcessor();
    }

    public RxMDEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.addTextChangedListener(mEditTextWatcher);
        mEditProcessor = new EditProcessor();
    }

    public RxMDEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.addTextChangedListener(mEditTextWatcher);
        mEditProcessor = new EditProcessor();
    }

    private TextWatcher mEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            mEditProcessor.checkAddOrDelete4BeforeTextChanged(start, count, after);
            sendBeforeTextChanged(s, start, count, after);
            Log.i("yuyidong", " start " + start + " count " + count + " after " + after + "  " + s.toString());
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mEditProcessor.checkAddOrDelete4OnTextChanged(start, before, count);
            sendOnTextChanged(s, start, before, count);
            Log.i("yuyidong", " start " + start + " before " + before + " count " + count + "  " + s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            RxMDEditText.this.removeTextChangedListener(this);
            mEditProcessor.format(s);
            RxMDEditText.this.addTextChangedListener(this);
            doAfterTextChanged(s);
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

    private void sendBeforeTextChanged(CharSequence s, int start, int count, int after) {
        if (mListeners != null) {
            final ArrayList<TextWatcher> list = mListeners;
            final int count4List = list.size();
            for (int i = 0; i < count4List; i++) {
                list.get(i).beforeTextChanged(s, start, count, after);
            }
        }
    }

    private void sendOnTextChanged(CharSequence s, int start, int before, int count) {
        if (mListeners != null) {
            final ArrayList<TextWatcher> list = mListeners;
            final int count4List = list.size();
            for (int i = 0; i < count4List; i++) {
                list.get(i).onTextChanged(s, start, before, count);
            }
        }
    }

    private void doAfterTextChanged(Editable s) {
        if (mListeners != null) {
            final ArrayList<TextWatcher> list = mListeners;
            final int count = list.size();
            for (int i = 0; i < count; i++) {
                list.get(i).afterTextChanged(s);
            }
        }
    }
}
