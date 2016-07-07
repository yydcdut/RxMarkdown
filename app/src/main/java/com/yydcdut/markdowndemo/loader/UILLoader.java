package com.yydcdut.markdowndemo.loader;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yydcdut.rxmarkdown.loader.RxMDImageLoader;

import java.io.ByteArrayOutputStream;

/**
 * Created by yuyidong on 16/6/28.
 */

public class UILLoader implements RxMDImageLoader {
    private static boolean sIsInit;

    public UILLoader(Context context) {
        if (!sIsInit) {
            init(context);
            sIsInit = true;
        }
    }

    @Override
    public byte[] loadSync(String url) {
        Bitmap bitmap = ImageLoader.getInstance().loadImageSync(url);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    private void init(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs();

        ImageLoader.getInstance().init(config.build());
    }
}
