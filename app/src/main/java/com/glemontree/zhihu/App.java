package com.glemontree.zhihu;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Administrator on 2016/12/16.
 */

public class App extends Application{
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .denyCacheImageMultipleSizesInMemory() // 设置拒绝缓存在内存中，默认为允许
                .threadPriority(Thread.NORM_PRIORITY - 2) // 设置图片加载线程的优先级，默认为Thread.NORM_PRIORITY-1
                .tasksProcessingOrder(QueueProcessingType.LIFO) // 设置图片加载和显示队列处理的类型
                .build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(this);
    }
}
