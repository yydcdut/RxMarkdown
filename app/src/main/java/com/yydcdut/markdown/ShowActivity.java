package com.yydcdut.markdown;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.yydcdut.markdown.loader.OKLoader;
import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.RxMDTextView;
import com.yydcdut.rxmarkdown.RxMarkdown;
import com.yydcdut.rxmarkdown.factory.AndroidFactory;
import com.yydcdut.rxmarkdown.loader.RxMDImageLoader;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

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
        rxMDImageLoader = new OKLoader();
//        rxMDImageLoader = new UILLoader(this);

        RxMDConfiguration rxMDConfiguration = new RxMDConfiguration.Builder()
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
                .build();
        final long beginTime = System.currentTimeMillis();
        RxMarkdown.with(content)
                .config(rxMDConfiguration)
                .factory(AndroidFactory.create())
                .intoObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CharSequence>() {
                    @Override
                    public void onCompleted() {
                        Snackbar.make(textView, "onCompleted " + (System.currentTimeMillis() - beginTime) + "ms", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(textView, "onError  " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        e.printStackTrace();
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
