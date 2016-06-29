package com.yydcdut.rxmarkdown.edit;

import android.graphics.Typeface;
import android.text.Editable;
import android.text.Spannable;
import android.text.style.StyleSpan;

/**
 * Created by yuyidong on 16/6/28.
 */

public class EditProcessor {

    private int mRememberChangeStart;
    private int mRememberChangeCount;

    private static final int ADD = 1;
    private static final int DELETE = 2;
    private static final int NOTHING = 0;
    private int mOperation = NOTHING;

    public int checkAddOrDelete4BeforeTextChanged(int start, int count, int after) {
        mRememberChangeStart = start;
        if (after - count > 0) {//ADD
            mOperation = ADD;
            mRememberChangeCount = after;
        } else if (after - count < 0) {//DELETE
            mOperation = DELETE;
            mRememberChangeCount = count;
        } else {
            mOperation = NOTHING;
            mRememberChangeCount = 0;
        }
        return mOperation;
    }

    public int checkAddOrDelete4OnTextChanged(int start, int before, int count) {
        if (mRememberChangeStart != start) {
            throw new IllegalArgumentException("Check  mRememberChangeStart != start" +
                    "  mRememberChangeStart-->" + mRememberChangeStart +
                    "  start-->" + start);
        }
        if (count - before > 0) {//ADD
            if (mOperation != ADD) {
                throw new IllegalArgumentException("Check  mOperation != ADD  mOperation-->" + mOperation);
            }
            if (mRememberChangeCount != count) {
                throw new IllegalArgumentException("Check  mRememberChangeCount != count " +
                        "  mRememberChangeStart-->" + mRememberChangeStart +
                        "  start-->" + start);
            }
        } else if (count - before < 0) {//DELETE
            if (mOperation != DELETE) {
                throw new IllegalArgumentException("Check  mOperation != DELETE  mOperation-->" + mOperation);
            }
            if (mRememberChangeCount != before) {
                throw new IllegalArgumentException("Check  mRememberChangeCount != count " +
                        "  mRememberChangeStart-->" + mRememberChangeStart +
                        "  start-->" + start);
            }
        } else {
            if (mOperation != NOTHING) {
                throw new IllegalArgumentException("Check mOperation != NOTHING  mOperation-->" + mOperation);
            }
            if (mRememberChangeCount != 0) {
                throw new IllegalArgumentException("Check  mRememberChangeCount != count " +
                        "  mRememberChangeStart-->" + mRememberChangeStart);
            }
        }
        return mOperation;
    }

    public void format(Editable editable) {
        editable.setSpan(new StyleSpan(Typeface.BOLD), 0, editable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
