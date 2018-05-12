package com.yydcdut.markdowndemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.MarkdownEditText;
import com.yydcdut.markdown.MarkdownProcessor;
import com.yydcdut.markdown.MarkdownTextView;
import com.yydcdut.markdown.syntax.edit.EditFactory;
import com.yydcdut.markdown.syntax.text.TextFactory;
import com.yydcdut.markdowndemo.loader.OKLoader;
import com.yydcdut.markdowndemo.view.HorizontalEditScrollView;
import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.RxMDEditText;
import com.yydcdut.rxmarkdown.RxMDTextView;
import com.yydcdut.rxmarkdown.RxMarkdown;

import rx.Subscriber;

/**
 * Created by yuyidong on 16/7/23.
 */
public class CompareActivity extends AppCompatActivity implements TextWatcher {
    private RxMDEditText mRxMDEditText;
    private RxMDTextView mRxMDTextView;
    private RxMDConfiguration mRxMDConfiguration;

    private MarkdownTextView mMarkdownTextView;
    private MarkdownEditText mMarkdownEditText;
    private MarkdownProcessor mMarkdownProcessor;

    private boolean isRx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Compare");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HorizontalEditScrollView horizontalEditScrollView = (HorizontalEditScrollView) findViewById(R.id.scroll_edit);
        isRx = getIntent().getBooleanExtra("is_rx", false);

        mRxMDEditText = (RxMDEditText) findViewById(R.id.edit_rx);
        mRxMDEditText.addTextChangedListener(this);
        mRxMDEditText.setText(Const.MD_SAMPLE);
        mRxMDTextView = (RxMDTextView) findViewById(R.id.txt_md_show_rx);

        mMarkdownEditText = (MarkdownEditText) findViewById(R.id.edit_rx);
        mMarkdownEditText.addTextChangedListener(this);
        mMarkdownTextView = (MarkdownTextView) findViewById(R.id.txt_md_show);
        mMarkdownEditText.setText(Const.MD_SAMPLE);

        if (isRx) {
            rxMarkdown(horizontalEditScrollView);
            mRxMDEditText.setVisibility(View.VISIBLE);
            mRxMDTextView.setVisibility(View.VISIBLE);
        } else {
            markdown(horizontalEditScrollView);
            mMarkdownEditText.setVisibility(View.VISIBLE);
            mMarkdownTextView.setVisibility(View.VISIBLE);
        }
    }

    private void rxMarkdown(HorizontalEditScrollView horizontalEditScrollView) {
        mRxMDConfiguration = new RxMDConfiguration.Builder(this)
                .setDefaultImageSize(400, 400)
                .setBlockQuotesLineColor(0xff33b5e5)
                .setHeader1RelativeSize(1.6f)
                .setHeader2RelativeSize(1.5f)
                .setHeader3RelativeSize(1.4f)
                .setHeader4RelativeSize(1.3f)
                .setHeader5RelativeSize(1.2f)
                .setHeader6RelativeSize(1.1f)
                .setHorizontalRulesColor(0xffaa66cc)
                .setCodeBgColor(0x33CCCCCC)
                .setTodoColor(0xff669900)
                .setTodoDoneColor(0xffff4444)
                .setUnOrderListColor(0xffffbb33)
                .setRxMDImageLoader(new OKLoader(this))
                .showLinkUnderline(true)
                .setLinkFontColor(0xff00ddff)
                .build();
        horizontalEditScrollView.setEditTextAndConfig(mRxMDEditText, mRxMDConfiguration);
        RxMarkdown.live(mRxMDEditText)
                .config(mRxMDConfiguration)
                .factory(EditFactory.create())
                .intoObservable()
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void markdown(HorizontalEditScrollView horizontalEditScrollView) {
        MarkdownConfiguration markdownConfiguration = new MarkdownConfiguration.Builder(this)
                .setDefaultImageSize(400, 400)
                .setBlockQuotesLineColor(0xff33b5e5)
                .setHeader1RelativeSize(1.6f)
                .setHeader2RelativeSize(1.5f)
                .setHeader3RelativeSize(1.4f)
                .setHeader4RelativeSize(1.3f)
                .setHeader5RelativeSize(1.2f)
                .setHeader6RelativeSize(1.1f)
                .setHorizontalRulesColor(0xffaa66cc)
                .setCodeBgColor(0x33CCCCCC)
                .setTodoColor(0xff669900)
                .setTodoDoneColor(0xffff4444)
                .setUnOrderListColor(0xffffbb33)
                .setRxMDImageLoader(new OKLoader(this))
                .showLinkUnderline(true)
                .setLinkFontColor(0xff00ddff)
                .build();
        horizontalEditScrollView.setEditTextAndConfig(mMarkdownEditText, markdownConfiguration);
        mMarkdownProcessor = new MarkdownProcessor(this);
        mMarkdownProcessor.config(markdownConfiguration);
        mMarkdownProcessor.factory(EditFactory.create());
        mMarkdownProcessor.live(mMarkdownEditText);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (isRx) {
            RxMarkdown.with(s.toString(), this)
                    .config(mRxMDConfiguration)
                    .factory(TextFactory.create())
                    .intoObservable()
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<CharSequence>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(CharSequence charSequence) {
                            mRxMDTextView.setText(charSequence, TextView.BufferType.SPANNABLE);
                        }
                    });
        } else {
            mMarkdownProcessor.factory(TextFactory.create());
            mMarkdownTextView.setText(mMarkdownProcessor.parse(s.toString()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
