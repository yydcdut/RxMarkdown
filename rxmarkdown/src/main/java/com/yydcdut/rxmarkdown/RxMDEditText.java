package com.yydcdut.rxmarkdown;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
public class RxMDEditText extends EditText implements Handler.Callback {
    private static final String TAG = "yuyidong_RxMDEditText";

    private static final int MSG_BEFORE_TEXT_CHANGED = 1;
    private static final int MSG_ON_TEXT_CHANGED = 2;
    private static final int MSG_AFTER_TEXT_CHANGED = 3;
    private static final int MSG_FORMAT = 4;
    private Handler mHandler;

    private static final String BUNDLE_CHAR_SEQUENCE = "bundle_char_sequence";
    private static final String BUNDLE_START = "bundle_start";
    private static final String BUNDLE_BEFORE = "bundle_before";
    private static final String BUNDLE_AFTER = "bundle_after";

    private AbsGrammarFactory mGrammarFactory;
    private RxMDConfiguration mRxMDConfiguration;

    private ArrayList<TextWatcher> mListeners;

    private boolean shouldFormat = false;

    public RxMDEditText(Context context) {
        super(context);
        mHandler = new Handler(this);
    }

    public RxMDEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHandler = new Handler(this);
    }

    public RxMDEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHandler = new Handler(this);
    }

    private TextWatcher mEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int before, int after) {
            if (isMainThread()) {
                sendBeforeTextChanged(s, start, before, after);
            } else {
                sendMessage(MSG_BEFORE_TEXT_CHANGED, s, start, before, after);
            }
            if (shouldFormat) {
                return;
            }
            shouldFormat = shouldFormat4BeforeTextChanged(s, start, before, after);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int after) {
            if (isMainThread()) {
                sendOnTextChanged(s, start, before, after);
            } else {
                sendMessage(MSG_ON_TEXT_CHANGED, s, start, before, after);
            }
            if (shouldFormat) {
                return;
            }
            shouldFormat = shouldFormat4OnTextChanged(s, start, before, after);
        }

        @Override
        public void afterTextChanged(final Editable s) {
            if (shouldFormat) {
                CharSequence charSequence = format();
                if (isMainThread()) {
                    setEditableText(charSequence);
                } else {
                    sendMessage(MSG_FORMAT, charSequence, 0, 0, 0);
                }
                shouldFormat = false;
            }
            if (!isMainThread()) {
                sendMessage(MSG_AFTER_TEXT_CHANGED, s, 0, 0, 0);
            } else {
                sendAfterTextChanged(getText());
            }
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
            shouldFormat = true;
            mEditTextWatcher.beforeTextChanged("", 0, 0, editable.length());
            mEditTextWatcher.onTextChanged(editable, 0, 0, editable.length());
            mEditTextWatcher.afterTextChanged(editable);
        }
    }

    private CharSequence format() {
        if (mGrammarFactory == null) {
            return getText();
        }
        Editable editable = getText();
        long begin = System.currentTimeMillis();
        CharSequence charSequence = mGrammarFactory.parse(editable);
        if (mRxMDConfiguration.isDebug()) {
            Log.i(TAG, "finish-->" + (System.currentTimeMillis() - begin));
        }
        return charSequence;
    }

    private void setEditableText(CharSequence charSequence) {
        int selectionEnd = getSelectionEnd();
        int selectionStart = getSelectionStart();
        removeTextChangedListener(mEditTextWatcher);
        setText(charSequence);
        addTextChangedListener(mEditTextWatcher);
        setSelection(selectionStart, selectionEnd);
    }

    private boolean shouldFormat4BeforeTextChanged(CharSequence s, int start, int before, int after) {
        if (before != 0) {
            String deleteString = s.subSequence(start, start + before).toString();
            String beforeString = null;
            String afterString = null;
            if (start > 0) {
                beforeString = s.subSequence(start - 1, start).toString();
            }
            if (start + before + 1 <= s.length()) {
                afterString = s.subSequence(start + before, start + before + 1).toString();
            }
            if (deleteString.contains("*")
                    || deleteString.contains("#")
                    || deleteString.contains("~")
                    || deleteString.contains(">")
                    || deleteString.contains("[")
                    || deleteString.contains("]")
                    || deleteString.contains("`")
                    || (deleteString.startsWith(" ") && ("#".equals(beforeString) || ">".equals(beforeString)))
                    || ("#".equals(beforeString) || "#".equals(afterString))//#12# ss(##12 ss) --> ## ss
                    || ("*".equals(beforeString) || "*".equals(afterString))//*11*ss** --> **ss**
                    || ("~".equals(beforeString) || "~".equals(afterString))//~11~ss~~ --> ~~ss~~
                    || ("`".equals(beforeString) || "`".equals(afterString))) {//`1``(``1`)(```1)(1```) --> ```

                return true;
            }
        }
        return false;
    }

    private boolean shouldFormat4OnTextChanged(CharSequence s, int start, int before, int after) {
        if (after != 0) {
            String addString;
            String beforeString = null;
            String afterString = null;
            if (after - before >= 0) {
                addString = s.subSequence(start, start + (after - before)).toString();
                if (start + (after - before) + 1 <= s.length()) {
                    afterString = s.subSequence(start + (after - before), start + (after - before) + 1).toString();
                }
            } else {
                addString = s.subSequence(start, start + (before - after)).toString();
                if (start + (after - before) + 1 <= s.length()) {
                    afterString = s.subSequence(start + (before - after), start + (before - after) + 1).toString();
                }
            }
            if (start > 0) {
                beforeString = s.subSequence(start - 1, start).toString();
            }
            if (addString.contains("*")
                    || addString.contains("#")
                    || addString.contains("~")
                    || addString.contains(">")
                    || addString.contains("[")
                    || addString.contains("]")
                    || addString.contains("`")
                    || (addString.startsWith(" ") && ("#".equals(beforeString) || ">".equals(beforeString)))
                    || ("#".equals(beforeString) || "#".equals(afterString))//## ss --> #12# ss(##12 ss)
                    || ("*".equals(beforeString) || "*".equals(afterString))//**ss** --> *11*ss**
                    || ("~".equals(beforeString) || "~".equals(afterString))//~~ss~~ --> ~11~ss~~
                    || ("`".equals(beforeString) || "`".equals(afterString))) {//``` --> `1``(``1`)(```1)(1```)
                return true;
            }
        }
        return false;
    }

    private boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    public void clear() {
        removeTextChangedListener(mEditTextWatcher);
        Editable editable = getText();
        int selectionEnd = getSelectionEnd();
        int selectionStart = getSelectionStart();
        setText(editable.toString());
        setSelection(selectionStart, selectionEnd);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_BEFORE_TEXT_CHANGED:
                Bundle bundle = msg.getData();
                CharSequence s = bundle.getCharSequence(BUNDLE_CHAR_SEQUENCE);
                int start = bundle.getInt(BUNDLE_START);
                int before = bundle.getInt(BUNDLE_BEFORE);
                int after = bundle.getInt(BUNDLE_AFTER);
                sendBeforeTextChanged(s, start, before, after);
                break;
            case MSG_ON_TEXT_CHANGED:
                Bundle bundle_ = msg.getData();
                CharSequence s_ = bundle_.getCharSequence(BUNDLE_CHAR_SEQUENCE);
                int start_ = bundle_.getInt(BUNDLE_START);
                int before_ = bundle_.getInt(BUNDLE_BEFORE);
                int after_ = bundle_.getInt(BUNDLE_AFTER);
                sendOnTextChanged(s_, start_, before_, after_);
                break;
            case MSG_AFTER_TEXT_CHANGED:
                Bundle bundle$ = msg.getData();
                CharSequence s$ = bundle$.getCharSequence(BUNDLE_CHAR_SEQUENCE);
                if (s$ instanceof Editable) {
                    sendAfterTextChanged((Editable) s$);
                } else {
                    sendAfterTextChanged(getText());
                }
                break;
            case MSG_FORMAT:
                Bundle $bundle = msg.getData();
                CharSequence $s = $bundle.getCharSequence(BUNDLE_CHAR_SEQUENCE);
                setEditableText($s);
                break;
        }
        return false;
    }

    public void sendMessage(int what, CharSequence s, int start, int before, int after) {
        Message message = mHandler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putCharSequence(BUNDLE_CHAR_SEQUENCE, s);
        bundle.putInt(BUNDLE_START, start);
        bundle.putInt(BUNDLE_BEFORE, before);
        bundle.putInt(BUNDLE_AFTER, after);
        message.what = what;
        message.setData(bundle);
        mHandler.sendMessage(message);
    }
}
