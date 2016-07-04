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

    private boolean shouldFormat = false;

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
            if (shouldFormat) {
                return;
            }
            shouldFormat = shouldFormat4BeforeTextChanged(s, start, before, after);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int after) {
            sendOnTextChanged(s, start, before, after);
            if (shouldFormat) {
                return;
            }
            shouldFormat = shouldFormat4OnTextChanged(s, start, before, after);
        }

        @Override
        public void afterTextChanged(final Editable s) {
            if (shouldFormat) {
                removeTextChangedListener(mEditTextWatcher);
                format();
                addTextChangedListener(mEditTextWatcher);
                shouldFormat = false;
            }
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
                    || ("#".equals(beforeString) || "#".equals(afterString))//*11*ss** --> **ss**
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
                    || ("#".equals(beforeString) || "#".equals(afterString))//**ss** --> *11*ss**
                    || ("~".equals(beforeString) || "~".equals(afterString))//~~ss~~ --> ~11~ss~~
                    || ("`".equals(beforeString) || "`".equals(afterString))) {//``` --> `1``(``1`)(```1)(1```)
                return true;
            }
        }
        return false;
    }

    public void clear() {
        removeTextChangedListener(mEditTextWatcher);
        Editable editable = getText();
        int selectionEnd = getSelectionEnd();
        int selectionStart = getSelectionStart();
        setText(editable.toString());
        setSelection(selectionStart, selectionEnd);
    }
}
