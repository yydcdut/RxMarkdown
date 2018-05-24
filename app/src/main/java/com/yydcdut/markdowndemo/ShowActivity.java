package com.yydcdut.markdowndemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.MarkdownProcessor;
import com.yydcdut.markdown.MarkdownTextView;
import com.yydcdut.markdown.callback.OnLinkClickCallback;
import com.yydcdut.markdown.callback.OnTodoClickCallback;
import com.yydcdut.markdown.loader.MDImageLoader;
import com.yydcdut.markdown.syntax.text.TextFactory;
import com.yydcdut.markdown.theme.ThemeSunburst;
import com.yydcdut.markdowndemo.loader.OKLoader;
import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.RxMDTextView;
import com.yydcdut.rxmarkdown.RxMarkdown;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yuyidong on 16/5/11.
 */
public class ShowActivity extends AppCompatActivity {

    public static final String EXTRA_CONTENT = "extra_content";
    public static final String EXTRA_RX = "is_rx";

    public static void startShowActivity(Activity activity, String content, boolean isRx) {
        Intent intent = new Intent(activity, ShowActivity.class);
        intent.putExtra(EXTRA_CONTENT, content);
        intent.putExtra(EXTRA_RX, isRx);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Show");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RxMDTextView rxMDTextView = (RxMDTextView) findViewById(R.id.txt_md_show_rx);
        rxMDTextView.setMovementMethod(LinkMovementMethod.getInstance());
        MarkdownTextView markdownTextView = (MarkdownTextView) findViewById(R.id.txt_md_show);
        markdownTextView.setMovementMethod(LinkMovementMethod.getInstance());
        String content = getIntent().getStringExtra(EXTRA_CONTENT);
        boolean isRx = getIntent().getBooleanExtra(EXTRA_RX, false);
        if (TextUtils.isEmpty(content)) {
            Snackbar.make(rxMDTextView, "No Text", Snackbar.LENGTH_SHORT).show();
            return;
        }
        MDImageLoader mdImageLoader = null;
        mdImageLoader = new OKLoader(this);
//        mdImageLoader = new UILLoader(this);
        if (isRx) {
            rxMDTextView.setVisibility(View.VISIBLE);
            rxMarkdown(rxMDTextView, content, mdImageLoader);
        } else {
            markdownTextView.setVisibility(View.VISIBLE);
            markdown(markdownTextView, content, mdImageLoader);
        }
    }

    private Toast mToast;

    private void rxMarkdown(final TextView textView, String content, MDImageLoader imageLoader) {
        RxMDConfiguration rxMDConfiguration = new RxMDConfiguration.Builder(this)
                .setDefaultImageSize(50, 50)
                .setBlockQuotesLineColor(0xff33b5e5)
                .setHeader1RelativeSize(1.6f)
                .setHeader2RelativeSize(1.5f)
                .setHeader3RelativeSize(1.4f)
                .setHeader4RelativeSize(1.3f)
                .setHeader5RelativeSize(1.2f)
                .setHeader6RelativeSize(1.1f)
                .setHorizontalRulesColor(0xff99cc00)
                .setCodeBgColor(0xffff4444)
                .setTodoColor(0xffaa66cc)
                .setTodoDoneColor(0xffff8800)
                .setUnOrderListColor(0xff00ddff)
                .setRxMDImageLoader(imageLoader)
                .setHorizontalRulesHeight(1)
                .setLinkFontColor(Color.BLUE)
                .showLinkUnderline(false)
                .setTheme(new ThemeSunburst())
                .setOnLinkClickCallback(new OnLinkClickCallback() {
                    @Override
                    public void onLinkClicked(View view, String link) {
                        toast(link);
                    }
                })
                .setOnTodoClickCallback(new OnTodoClickCallback() {
                    @Override
                    public CharSequence onTodoClicked(View view, String line, int lineNumber) {
                        toast("line:" + line + "\n" + "line number:" + lineNumber);
                        return textView.getText();
                    }
                })
                .build();
        RxMarkdown.with(content, this)
                .config(rxMDConfiguration)
                .factory(TextFactory.create())
                .intoObservable()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CharSequence>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        textView.setText(charSequence, TextView.BufferType.SPANNABLE);
                    }
                });
    }

    private void markdown(final TextView textView, String content, MDImageLoader imageLoader) {
        MarkdownConfiguration markdownConfiguration = new MarkdownConfiguration.Builder(this)
                .setDefaultImageSize(50, 50)
                .setBlockQuotesLineColor(0xff33b5e5)
                .setHeader1RelativeSize(1.6f)
                .setHeader2RelativeSize(1.5f)
                .setHeader3RelativeSize(1.4f)
                .setHeader4RelativeSize(1.3f)
                .setHeader5RelativeSize(1.2f)
                .setHeader6RelativeSize(1.1f)
                .setHorizontalRulesColor(0xff99cc00)
                .setCodeBgColor(0xffff4444)
                .setTodoColor(0xffaa66cc)
                .setTodoDoneColor(0xffff8800)
                .setUnOrderListColor(0xff00ddff)
                .setRxMDImageLoader(imageLoader)
                .setHorizontalRulesHeight(1)
                .setLinkFontColor(Color.BLUE)
                .showLinkUnderline(false)
                .setTheme(new ThemeSunburst())
                .setOnLinkClickCallback(new OnLinkClickCallback() {
                    @Override
                    public void onLinkClicked(View view, String link) {
                        toast(link);
                    }
                })
                .setOnTodoClickCallback(new OnTodoClickCallback() {
                    @Override
                    public CharSequence onTodoClicked(View view, String line, int lineNumber) {
                        toast("line:" + line + "\n" + "line number:" + lineNumber);
                        return textView.getText();
                    }
                })
                .build();
        MarkdownProcessor processor = new MarkdownProcessor(this);
        processor.factory(TextFactory.create());
        processor.config(markdownConfiguration);
        textView.setText(processor.parse(content));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(ShowActivity.this, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
