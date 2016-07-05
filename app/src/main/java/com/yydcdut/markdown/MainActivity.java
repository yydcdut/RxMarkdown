package com.yydcdut.markdown;

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

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.RxMDEditText;
import com.yydcdut.rxmarkdown.RxMarkdown;
import com.yydcdut.rxmarkdown.factory.EditFactory;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RxMDEditText mEditText;
    private AsyncTask mAsyncTask;
    private FloatingActionButton mFloatingActionButton;

    private Observable<CharSequence> mObservable;
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(this);

        mEditText = (RxMDEditText) findViewById(R.id.edit_md);
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
                .build();
        mEditText.setText(Const.MD_SAMPLE2);
        mObservable = RxMarkdown.live(mEditText)
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

        mAsyncTask = new DemoPictureAsyncTask().execute();
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
            return true;
        } else if (id == R.id.action_disable) {
            if (mSubscription != null) {
                mSubscription.unsubscribe();
                mSubscription = null;
                mEditText.clear();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (mAsyncTask != null && mAsyncTask.getStatus() == AsyncTask.Status.FINISHED) {
            ShowActivity.startShowActivity(this, mEditText.getText().toString());
        } else {
            Snackbar.make(v, "Wait....", Snackbar.LENGTH_SHORT).show();
        }
    }

    class DemoPictureAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            OutputStream outputStream = null;
            InputStream inputStream = null;
            try {
                outputStream = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + "tv_cheers.png");
                AssetManager assetManager = getAssets();
                inputStream = assetManager.open("tv_cheers.png");
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
}
