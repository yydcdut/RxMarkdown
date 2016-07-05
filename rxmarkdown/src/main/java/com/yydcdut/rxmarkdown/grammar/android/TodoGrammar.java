package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.span.MDTodoSpan;

/**
 * Created by yuyidong on 16/5/17.
 * Key 与 UnOrderListGrammar 有关联
 */
class TodoGrammar extends AbsAndroidGrammar {
    protected static final String KEY_TODO = "- [ ] ";

    private int mColor;

    TodoGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
        mColor = rxMDConfiguration.getTodoColor();
    }

    @Override
    boolean isMatch(@NonNull String text) {
        return text.startsWith(KEY_TODO);
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        ssb.delete(0, KEY_TODO.length());
        ssb.setSpan(new MDTodoSpan(mColor), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }
}
