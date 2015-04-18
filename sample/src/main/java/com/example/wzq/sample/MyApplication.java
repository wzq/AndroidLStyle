package com.example.wzq.sample;

import android.app.Application;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MyApplication extends Application {
		
	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoader();
	}
	
	private void initImageLoader(){
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
			.memoryCacheExtraOptions(480, 800)
			.threadPoolSize(5)
			.threadPriority(Thread.NORM_PRIORITY-2)
			.denyCacheImageMultipleSizesInMemory()
			.memoryCache(new WeakMemoryCache())
			.diskCacheSize(60*1024*1024)
			.tasksProcessingOrder(QueueProcessingType.LIFO)
			.build();
		ImageLoader.getInstance().init(config);
	}
}
