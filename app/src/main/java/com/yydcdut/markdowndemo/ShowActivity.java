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

import com.yydcdut.markdowndemo.loader.OKLoader;
import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.RxMDTextView;
import com.yydcdut.rxmarkdown.RxMarkdown;
import com.yydcdut.rxmarkdown.callback.OnLinkClickCallback;
import com.yydcdut.rxmarkdown.grammar.android.TextFactory;
import com.yydcdut.rxmarkdown.loader.RxMDImageLoader;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yuyidong on 16/5/11.
 */
public class ShowActivity extends AppCompatActivity {

    public static final String EXTRA_CONTENT = "extra_content";

    public static void startShowActivity(Activity activity, String content) {
        Intent intent = new Intent(activity, ShowActivity.class);
        intent.putExtra(EXTRA_CONTENT, content);
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
        final RxMDTextView textView = (RxMDTextView) findViewById(R.id.txt_md_show);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        String content = getIntent().getStringExtra(EXTRA_CONTENT);
        if (TextUtils.isEmpty(content)) {
            Snackbar.make(textView, "No Text", Snackbar.LENGTH_SHORT).show();
            return;
        }
        textView.setText(content);
        RxMDImageLoader rxMDImageLoader = null;
        rxMDImageLoader = new OKLoader(this);
//        rxMDImageLoader = new UILLoader(this);

        RxMDConfiguration rxMDConfiguration = new RxMDConfiguration.Builder(this)
                .setDefaultImageSize(50, 50)
                .setBlockQuotesColor(0xff33b5e5)
                .setHeader1RelativeSize(2.2f)
                .setHeader2RelativeSize(2.0f)
                .setHeader3RelativeSize(1.8f)
                .setHeader4RelativeSize(1.6f)
                .setHeader5RelativeSize(1.4f)
                .setHeader6RelativeSize(1.2f)
                .setHorizontalRulesColor(0xff99cc00)
                .setInlineCodeBgColor(0xffff4444)
                .setCodeBgColor(0x33999999)
                .setTodoColor(0xffaa66cc)
                .setTodoDoneColor(0xffff8800)
                .setUnOrderListColor(0xff00ddff)
                .setRxMDImageLoader(rxMDImageLoader)
                .setHorizontalRulesHeight(1)
                .setLinkColor(Color.BLUE)
                .setLinkUnderline(false)
                .setOnLinkClickCallback(new OnLinkClickCallback() {
                    @Override
                    public void onLinkClicked(View view, String link) {
                        Toast.makeText(view.getContext(), link, Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        final long beginTime = System.currentTimeMillis();
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
                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        textView.setText(charSequence, TextView.BufferType.SPANNABLE);
                    }
                });
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
