package com.yydcdut.rxmarkdown.state;

import android.text.Editable;

import com.yydcdut.rxmarkdown.edit.EditToken;

import java.util.List;

/**
 * Created by yuyidong on 2017/5/21.
 */
public interface Parser {

    CharSequence parse(Syntax syntax, CharSequence charSequence);

    List<EditToken> parse(Syntax syntax, Editable editable);

}
