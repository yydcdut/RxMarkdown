package com.yydcdut.markdowndemo;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.MarkdownEditText;
import com.yydcdut.markdown.MarkdownProcessor;
import com.yydcdut.markdown.syntax.edit.EditFactory;
import com.yydcdut.markdowndemo.view.EditScrollView;
import com.yydcdut.markdowndemo.view.HorizontalEditScrollView;
import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.RxMDEditText;
import com.yydcdut.rxmarkdown.RxMarkdown;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yuyidong on 16/7/23.
 */
public class EditActivity extends AppCompatActivity implements View.OnClickListener, EditScrollView.OnScrollChangedListener {
    private RxMDEditText mRxMDEditText;
    private MarkdownEditText mMarkdownEditText;

    private AsyncTask mAsyncTask;
    private FloatingActionButton mFloatingActionButton;

    private Observable<CharSequence> mObservable;
    private Subscription mSubscription;
    private HorizontalEditScrollView mHorizontalEditScrollView;
    private int mShortestDistance = -1;

    private MarkdownProcessor mMarkdownProcessor;

    private boolean isRx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Edit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(this);
        EditScrollView editScrollView = (EditScrollView) findViewById(R.id.edit_scroll);
        editScrollView.setOnScrollChangedListener(this);
        mRxMDEditText = (RxMDEditText) findViewById(R.id.edit_rx);
        mMarkdownEditText = (MarkdownEditText) findViewById(R.id.edit_md);
        mHorizontalEditScrollView = (HorizontalEditScrollView) findViewById(R.id.scroll_edit);
        isRx = getIntent().getBooleanExtra("is_rx", false);
        if (isRx) {
            mRxMDEditText.setVisibility(View.VISIBLE);
            rxMarkdown();
        } else {
            mMarkdownEditText.setVisibility(View.VISIBLE);
            markdown();
        }
        mAsyncTask = new EditActivity.DemoPictureAsyncTask().execute();
    }

    private void markdown() {
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
                .build();
        mHorizontalEditScrollView.setEditTextAndConfig(mMarkdownEditText, markdownConfiguration);
        mMarkdownEditText.setText(Const.MD_SAMPLE);
        mMarkdownProcessor = new MarkdownProcessor(this);
        mMarkdownProcessor.config(markdownConfiguration);
        mMarkdownProcessor.factory(EditFactory.create());
        mMarkdownProcessor.live(mMarkdownEditText);
    }

    private void rxMarkdown() {
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
                .build();
        mHorizontalEditScrollView.setEditTextAndConfig(mRxMDEditText, rxMDConfiguration);
        mRxMDEditText.setText(Const.MD_SAMPLE);
        mObservable = RxMarkdown.live(mRxMDEditText)
                .config(rxMDConfiguration)
                .factory(EditFactory.create())
                .intoObservable()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        final long time = System.currentTimeMillis();
        mSubscription = mObservable
                .subscribe(new Subscriber<CharSequence>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(mFloatingActionButton, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        Snackbar.make(mFloatingActionButton, (System.currentTimeMillis() - time) + "", Snackbar.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_enable) {
            if (isRx) {
                if (mSubscription != null) {
                    final long time = System.currentTimeMillis();
                    mSubscription = mObservable
                            .subscribe(new Subscriber<CharSequence>() {
                                @Override
                                public void onCompleted() {
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Snackbar.make(mFloatingActionButton, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }

                                @Override
                                public void onNext(CharSequence charSequence) {
                                    Snackbar.make(mFloatingActionButton, (System.currentTimeMillis() - time) + "", Snackbar.LENGTH_SHORT).show();
                                }
                            });
                }
            } else {
                if (mMarkdownProcessor != null) {
                    mMarkdownProcessor.live(mMarkdownEditText);
                }
            }
            return true;
        } else if (id == R.id.action_disable) {
            if (isRx) {
                if (mSubscription != null) {
                    mSubscription.unsubscribe();
                    mSubscription = null;
                    mRxMDEditText.clear();
                }
            } else {
                mMarkdownEditText.clear();
            }
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (mAsyncTask != null && mAsyncTask.getStatus() == AsyncTask.Status.FINISHED) {
            if (isRx) {
                ShowActivity.startShowActivity(this, mRxMDEditText.getText().toString(), isRx);
            } else {
                ShowActivity.startShowActivity(this, mMarkdownEditText.getText().toString(), isRx);
            }
        } else {
            Snackbar.make(v, "Wait....", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (mShortestDistance == -1) {
            mShortestDistance = mRxMDEditText.getLineHeight() * 3 / 2;
        }
        if (Math.abs(t - oldt) > mShortestDistance) {
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            if (imm != null && imm.isActive() && null != getCurrentFocus()) {
//                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
            mFloatingActionButton.setFocusable(true);
            mFloatingActionButton.setFocusableInTouchMode(true);
            mFloatingActionButton.requestFocus();
        }
    }

    class DemoPictureAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            OutputStream outputStream = null;
            InputStream inputStream = null;
            try {
                File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "rxMarkdown");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                outputStream = new FileOutputStream(dir.getAbsolutePath() + File.separator + "b.jpg");
                AssetManager assetManager = getAssets();
                inputStream = assetManager.open("b.jpg");
                byte[] buffer = new byte[1024];
                int read = 0;
                while ((read = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
                outputStream.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mHorizontalEditScrollView.handleResult(requestCode, resultCode, data);
    }
}
