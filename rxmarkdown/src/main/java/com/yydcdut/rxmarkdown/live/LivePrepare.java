package com.yydcdut.rxmarkdown.live;

import android.support.annotation.NonNull;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.RxMDEditText;

import java.util.ArrayList;

/**
 * Created by yuyidong on 2017/6/7.
 */
public class LivePrepare {
    private boolean isConfig;
    private ArrayList<IEditLive> mEditControllerList;

    public LivePrepare(RxMDEditText rxMDEditText, RxMDEditText.EditTextWatcher editTextWatcher) {
        prepare(rxMDEditText, editTextWatcher);
    }

    private void prepare(RxMDEditText rxMDEditText, RxMDEditText.EditTextWatcher editTextWatcher) {
        mEditControllerList = new ArrayList<>();
        mEditControllerList.add(new BlockQuotesLive());
        mEditControllerList.add(new StyleLive());
        mEditControllerList.add(new CenterAlignLive());
        mEditControllerList.add(new HeaderLive());
        mEditControllerList.add(new HorizontalRulesLive(rxMDEditText));
        mEditControllerList.add(new InlineCodeLive());
        mEditControllerList.add(new StrikeThroughLive());
        mEditControllerList.add(new ListLive(rxMDEditText, editTextWatcher));
        mEditControllerList.add(new CodeLive());
    }

    public void config(@NonNull RxMDConfiguration rxMDConfiguration) {
        if (rxMDConfiguration != null) {
            isConfig = true;
        }
        for (IEditLive controller : mEditControllerList) {
            controller.setRxMDConfiguration(rxMDConfiguration);
        }
    }

    public void onSelectionChanged(int selStart, int selEnd) {
        if (!isConfig) {
            return;
        }
        for (IEditLive iEditLive : mEditControllerList) {
            iEditLive.onSelectionChanged(selStart, selEnd);
        }
    }

    public void beforeTextChanged(CharSequence s, int start, int before, int after) {
        if (!isConfig) {
            return;
        }
        for (IEditLive iEditLive : mEditControllerList) {
            iEditLive.beforeTextChanged(s, start, before, after);
        }
    }

    public void onTextChanged(CharSequence s, int start, int before, int after) {
        if (!isConfig) {
            return;
        }
        for (IEditLive iEditLive : mEditControllerList) {
            iEditLive.onTextChanged(s, start, before, after);
        }
    }

}
