package com.yydcdut.markdown;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.yydcdut.rxmarkdown.MarkdownParser;
import com.yydcdut.rxmarkdown.factory.AndroidFactory;
import com.yydcdut.rxmarkdown.widget.TextWrapper;

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
        final TextView textView = (TextView) findViewById(R.id.txt_md_show);
        String content = getIntent().getStringExtra(EXTRA_CONTENT);
        if (TextUtils.isEmpty(content)) {
            Snackbar.make(textView, "No Text", Snackbar.LENGTH_SHORT).show();
            return;
        }
        MarkdownParser markdownParser = new MarkdownParser.Builder(content)
                .addFormatFactory(AndroidFactory.create())
                .addView(new TextWrapper(textView))
                .build();
        markdownParser.intoObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SpannableStringBuilder>() {
                    @Override
                    public void onCompleted() {
                        Snackbar.make(textView, "onCompleted", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(textView, "onError  " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(SpannableStringBuilder spannableStringBuilder) {
                        Snackbar.make(textView, "onNext", Snackbar.LENGTH_SHORT).show();
                        textView.setText(spannableStringBuilder, TextView.BufferType.SPANNABLE);
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
