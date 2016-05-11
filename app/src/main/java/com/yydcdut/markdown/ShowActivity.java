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
        TextView mTextView = (TextView) findViewById(R.id.txt_md_show);
        String mContent = getIntent().getStringExtra(EXTRA_CONTENT);
        if (TextUtils.isEmpty(mContent)) {
            Snackbar.make(mTextView, "No Text", Snackbar.LENGTH_SHORT).show();
            return;
        }
        MarkdownParser markdownParser = new MarkdownParser();
        SpannableStringBuilder ssb = markdownParser.parse(mContent);
        mTextView.setText(ssb, TextView.BufferType.SPANNABLE);
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
