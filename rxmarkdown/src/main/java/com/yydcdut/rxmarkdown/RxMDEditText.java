/*
 * Copyright (C) 2016 yydcdut (yuyidong2015@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.yydcdut.rxmarkdown;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.yydcdut.rxmarkdown.factory.AbsGrammarFactory;
import com.yydcdut.rxmarkdown.span.MDHorizontalRulesSpan;

import java.util.ArrayList;

/**
 * RxMDEditText, live preview.
 * <p>
 * Created by yuyidong on 16/5/20.
 */
public class RxMDEditText extends EditText implements Handler.Callback {
    private static final String TAG = RxMDEditText.class.getName();

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

    /**
     * Constructor
     *
     * @param context {@link EditText}
     */
    public RxMDEditText(Context context) {
        super(context);
        mHandler = new Handler(this);
    }

    /**
     * Constructor
     *
     * @param context {@link EditText}
     * @param attrs   {@link EditText}
     */
    public RxMDEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHandler = new Handler(this);
    }

    /**
     * Constructor
     *
     * @param context      {@link EditText}
     * @param attrs        {@link EditText}
     * @param defStyleAttr {@link EditText}
     */
    public RxMDEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHandler = new Handler(this);
    }

    /**
     * clear markdown format
     */
    public void clear() {
        removeTextChangedListener(mEditTextWatcher);
        Editable editable = getText();
        int selectionEnd = getSelectionEnd();
        int selectionStart = getSelectionStart();
        setText(editable.toString());
        setSelection(selectionStart, selectionEnd);
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
        CharSequence charSequence = mGrammarFactory.parse(editable, mRxMDConfiguration);
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
            if (deleteString.contains("*")//bold && italic
                    || deleteString.contains("#")//header
                    || deleteString.contains("~")//strike through
                    || deleteString.contains(">")//block quote
                    || deleteString.contains("[")//center align
                    || deleteString.contains("]")//center align
                    || deleteString.contains("`")//inline code && code
                    || (deleteString.startsWith(" ") && ("#".equals(beforeString) || ">".equals(beforeString)))//"> " && "## "
                    || ("#".equals(beforeString) || "#".equals(afterString))//#12# ss(##12 ss) --> ## ss
                    || ("*".equals(beforeString) || "*".equals(afterString))//*11*ss** --> **ss**
                    || ("~".equals(beforeString) || "~".equals(afterString))//~11~ss~~ --> ~~ss~~
                    || ("-".equals(beforeString) || "-".equals(afterString))//1---(-1--)(--1-)(---1) --> ---
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
            if (addString.contains("*")//bold && italic
                    || addString.contains("#")//header
                    || addString.contains("~")//strike through
                    || addString.contains(">")//block quote
                    || addString.contains("[")//center align
                    || addString.contains("]")//center align
                    || addString.contains("`")//inline code && code
                    || (addString.startsWith(" ") && ("#".equals(beforeString) || ">".equals(beforeString)))//"> " && "## "
                    || ("#".equals(beforeString) || "#".equals(afterString))//## ss --> #12# ss(##12 ss)
                    || ("*".equals(beforeString) || "*".equals(afterString))//**ss** --> *11*ss**
                    || ("~".equals(beforeString) || "~".equals(afterString))//~~ss~~ --> ~11~ss~~
                    || ("-".equals(beforeString) || "-".equals(afterString))//--- --> 1---(-1--)(--1-)(---1)
                    || ("`".equals(beforeString) || "`".equals(afterString))) {//``` --> `1``(``1`)(```1)(1```)
                return true;
            }
        }
        return false;
    }

    private boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_BEFORE_TEXT_CHANGED:
                Bundle bundle0 = msg.getData();
                CharSequence s0 = bundle0.getCharSequence(BUNDLE_CHAR_SEQUENCE);
                int start0 = bundle0.getInt(BUNDLE_START);
                int before0 = bundle0.getInt(BUNDLE_BEFORE);
                int after0 = bundle0.getInt(BUNDLE_AFTER);
                sendBeforeTextChanged(s0, start0, before0, after0);
                break;
            case MSG_ON_TEXT_CHANGED:
                Bundle bundle1 = msg.getData();
                CharSequence s1 = bundle1.getCharSequence(BUNDLE_CHAR_SEQUENCE);
                int start1 = bundle1.getInt(BUNDLE_START);
                int before1 = bundle1.getInt(BUNDLE_BEFORE);
                int after1 = bundle1.getInt(BUNDLE_AFTER);
                sendOnTextChanged(s1, start1, before1, after1);
                break;
            case MSG_AFTER_TEXT_CHANGED:
                Bundle bundle2 = msg.getData();
                CharSequence s2 = bundle2.getCharSequence(BUNDLE_CHAR_SEQUENCE);
                if (s2 instanceof Editable) {
                    sendAfterTextChanged((Editable) s2);
                } else {
                    sendAfterTextChanged(getText());
                }
                break;
            case MSG_FORMAT:
                Bundle bundle3 = msg.getData();
                CharSequence s3 = bundle3.getCharSequence(BUNDLE_CHAR_SEQUENCE);
                setEditableText(s3);
                break;
        }
        return false;
    }

    private void sendMessage(int what, CharSequence s, int start, int before, int after) {
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

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        setAllHorizontalRulesTextColor();
        removeCurrentHorizontalRulesTextColor(selStart, selEnd);
    }

    private void setAllHorizontalRulesTextColor() {
        MDHorizontalRulesSpan[] spans = getText().getSpans(0, getText().length(), MDHorizontalRulesSpan.class);
        if (spans.length > 0) {
            for (MDHorizontalRulesSpan span : spans) {
                int start = getText().getSpanStart(span);
                int end = getText().getSpanEnd(span);
                if (!existForegroundColorSpan(start, end)) {
                    int textColor = getCurrentTextColor();
                    getText().setSpan(new ForegroundColorSpan(
                                    Color.argb(51,
                                            Color.red(textColor),
                                            Color.green(textColor),
                                            Color.blue(textColor))),
                            start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
    }

    private void removeCurrentHorizontalRulesTextColor(int selStart, int selEnd) {
        MDHorizontalRulesSpan[] spans = getText().getSpans(selStart, selEnd, MDHorizontalRulesSpan.class);
        if (spans.length > 0) {
            for (MDHorizontalRulesSpan span : spans) {
                int start = getText().getSpanStart(span);
                int end = getText().getSpanEnd(span);
                ForegroundColorSpan[] foregroundColorSpans = getText().getSpans(start, end, ForegroundColorSpan.class);
                for (ForegroundColorSpan foregroundColorSpan : foregroundColorSpans) {
                    getText().removeSpan(foregroundColorSpan);
                }
            }
        }
    }

    private boolean existForegroundColorSpan(int start, int end) {
        ForegroundColorSpan[] foregroundColorSpans = getText().getSpans(start, end, ForegroundColorSpan.class);
        return foregroundColorSpans != null ? foregroundColorSpans.length == 0 ? false : true : false;
    }
}
