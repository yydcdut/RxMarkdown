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
package com.yydcdut.markdown;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.yydcdut.markdown.live.LivePrepare;
import com.yydcdut.markdown.span.MDImageSpan;
import com.yydcdut.markdown.syntax.SyntaxFactory;
import com.yydcdut.markdown.syntax.text.TextFactory;

import java.util.ArrayList;

/**
 * RxMDEditText, live preview.
 * <p>
 * Created by yuyidong on 16/5/20.
 */
public class MarkdownEditText extends EditText implements Handler.Callback {
    private static final String TAG = MarkdownEditText.class.getName();

    private static final int MSG_BEFORE_TEXT_CHANGED = 1;
    private static final int MSG_ON_TEXT_CHANGED = 2;
    private static final int MSG_AFTER_TEXT_CHANGED = 3;
    private static final int MSG_INIT_FORMAT = 4;
    private static final int MSG_FORMAT_BEFORE_TEXT_CHANGED = 5;
    private static final int MSG_FORMAT_ON_TEXT_CHANGED = 6;
    private Handler mHandler;

    private static final String BUNDLE_CHAR_SEQUENCE = "bundle_char_sequence";
    private static final String BUNDLE_START = "bundle_start";
    private static final String BUNDLE_BEFORE = "bundle_before";
    private static final String BUNDLE_AFTER = "bundle_after";

    private SyntaxFactory mGrammarFactory;
    private MarkdownConfiguration mMarkdownConfiguration;

    private ArrayList<TextWatcher> mListeners;
    private EditTextWatcher mEditTextWatcher;

    private boolean mHasImageInText;
    private boolean mInitFormat;

    private LivePrepare mLivePrepare;

    /**
     * Constructor
     *
     * @param context {@link EditText}
     */
    public MarkdownEditText(Context context) {
        super(context);
        init();
    }

    /**
     * Constructor
     *
     * @param context {@link EditText}
     * @param attrs   {@link EditText}
     */
    public MarkdownEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Constructor
     *
     * @param context      {@link EditText}
     * @param attrs        {@link EditText}
     * @param defStyleAttr {@link EditText}
     */
    public MarkdownEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mEditTextWatcher = new EditTextWatcher();
        mHandler = new Handler(this);
        mLivePrepare = new LivePrepare(this, mEditTextWatcher);
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

    public class EditTextWatcher implements TextWatcher {

        public void doBeforeTextChanged(CharSequence s, int start, int before, int after) {
            if (isMainThread()) {
                sendBeforeTextChanged(s, start, before, after);
            } else {
                sendMessage(MSG_BEFORE_TEXT_CHANGED, s, start, before, after);
            }
        }

        public void doOnTextChanged(CharSequence s, int start, int before, int after) {
            if (isMainThread()) {
                sendOnTextChanged(s, start, before, after);
            } else {
                sendMessage(MSG_ON_TEXT_CHANGED, s, start, before, after);
            }
        }

        public void doAfterTextChanged(final Editable s) {
            if (isMainThread()) {
                sendAfterTextChanged(getText());
            } else {
                sendMessage(MSG_AFTER_TEXT_CHANGED, s, 0, 0, 0);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int before, int after) {
            if (isMainThread()) {
                sendBeforeTextChanged(s, start, before, after);
            } else {
                sendMessage(MSG_BEFORE_TEXT_CHANGED, s, start, before, after);
            }
            if (mInitFormat) {
                return;
            }
            if (isMainThread()) {
                beforeTextChanged4Controller(s, start, before, after);
            } else {
                sendMessage(MSG_FORMAT_BEFORE_TEXT_CHANGED, s, start, before, after);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int after) {
            if (isMainThread()) {
                sendOnTextChanged(s, start, before, after);
            } else {
                sendMessage(MSG_ON_TEXT_CHANGED, s, start, before, after);
            }
            if (mInitFormat) {
                return;
            }
            if (isMainThread()) {
                onTextChanged4Controller(s, start, before, after);
            } else {
                sendMessage(MSG_FORMAT_ON_TEXT_CHANGED, s, start, before, after);
            }
        }

        @Override
        public void afterTextChanged(final Editable s) {
            if (mInitFormat) {
                CharSequence charSequence = format();
                if (isMainThread()) {
                    setEditableText(charSequence);
                } else {
                    sendMessage(MSG_INIT_FORMAT, charSequence, 0, 0, 0);
                }
                mInitFormat = false;
            }
            if (isMainThread()) {
                sendAfterTextChanged(getText());
            } else {
                sendMessage(MSG_AFTER_TEXT_CHANGED, s, 0, 0, 0);
            }
        }
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        if (watcher == mEditTextWatcher) {
            super.addTextChangedListener(watcher);
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
            super.removeTextChangedListener(watcher);
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

    private void beforeTextChanged4Controller(CharSequence s, int start, int before, int after) {
        mLivePrepare.beforeTextChanged(s, start, before, after);
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

    private void onTextChanged4Controller(CharSequence s, int start, int before, int after) {
        mLivePrepare.onTextChanged(s, start, before, after);
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

    public void setFactoryAndConfig(@NonNull SyntaxFactory syntaxFactory,
                                    @NonNull MarkdownConfiguration markdownConfiguration) {
        mGrammarFactory = syntaxFactory;
        mMarkdownConfiguration = markdownConfiguration;
        mLivePrepare.config(markdownConfiguration);
        super.addTextChangedListener(mEditTextWatcher);
        Editable editable = getText();
        if (!TextUtils.isEmpty(editable)) {
            mInitFormat = true;
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
        CharSequence charSequence = mGrammarFactory.parse(editable, mMarkdownConfiguration);
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
            case MSG_INIT_FORMAT:
                Bundle bundle3 = msg.getData();
                CharSequence s3 = bundle3.getCharSequence(BUNDLE_CHAR_SEQUENCE);
                setEditableText(s3);
                break;
            case MSG_FORMAT_BEFORE_TEXT_CHANGED:
                Bundle bundle4 = msg.getData();
                CharSequence s4 = bundle4.getCharSequence(BUNDLE_CHAR_SEQUENCE);
                int start4 = bundle4.getInt(BUNDLE_START);
                int before4 = bundle4.getInt(BUNDLE_BEFORE);
                int after4 = bundle4.getInt(BUNDLE_AFTER);
                beforeTextChanged4Controller(s4, start4, before4, after4);
                break;
            case MSG_FORMAT_ON_TEXT_CHANGED:
                Bundle bundle5 = msg.getData();
                CharSequence s5 = bundle5.getCharSequence(BUNDLE_CHAR_SEQUENCE);
                int start5 = bundle5.getInt(BUNDLE_START);
                int before5 = bundle5.getInt(BUNDLE_BEFORE);
                int after5 = bundle5.getInt(BUNDLE_AFTER);
                onTextChanged4Controller(s5, start5, before5, after5);
                break;
            default:
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
        if (mLivePrepare != null) {
            mLivePrepare.onSelectionChanged(selStart, selEnd);
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (mGrammarFactory instanceof TextFactory) {
            if (mHasImageInText) {
                onDetach();
                mHasImageInText = false;
            }
            if (text instanceof Spanned) {
                MDImageSpan[] spans = ((Spanned) text).getSpans(0, text.length(), MDImageSpan.class);
                mHasImageInText = spans.length > 0;
                for (MDImageSpan image : spans) {
                    image.onAttach(this);
                }
            }
        }
        super.setText(text, type);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onAttach();
    }

    final void onAttach() {
        MDImageSpan[] images = getImages();
        for (MDImageSpan image : images) {
            image.onAttach(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onDetach();
    }

    @Override
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        onDetach();
    }

    @Override
    public void invalidateDrawable(Drawable dr) {
        if (mGrammarFactory instanceof TextFactory && mHasImageInText) {
            invalidate();
        } else {
            super.invalidateDrawable(dr);
        }
    }

    private void onDetach() {
        if (mGrammarFactory instanceof TextFactory) {
            MDImageSpan[] images = getImages();
            for (MDImageSpan image : images) {
                Drawable drawable = image.getDrawable();
                if (drawable != null) {
                    unscheduleDrawable(drawable);
                }
                image.onDetach();
            }
        }
    }

    private MDImageSpan[] getImages() {
        if (mGrammarFactory instanceof TextFactory && mHasImageInText && length() > 0) {
            return (getText()).getSpans(0, length(), MDImageSpan.class);
        }
        return new MDImageSpan[0];
    }
}
