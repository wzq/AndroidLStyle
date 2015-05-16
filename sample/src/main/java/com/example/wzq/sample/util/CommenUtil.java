package com.example.wzq.sample.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.DisplayMetrics;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by wzq on 15/4/14.
 */
public class CommenUtil {
	public static DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageOnLoading(android.R.color.transparent) // 设置图片在下载期间显示的图片
			.showImageForEmptyUri(android.R.color.transparent)// 设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(android.R.color.transparent) // 设置图片加载/解码过程中错误时候显示的图片
			.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
			.cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
			.considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
			//.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
			.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
			// .decodingOptions(android.graphics.BitmapFactory.Options
			// decodingOptions)//设置图片的解码配置
			// .delayBeforeLoading(int delayInMillis)//int
			// delayInMillis为你设置的下载前的延迟时间
			// 设置图片加入缓存前，对bitmap进行设置
			// .preProcessor(BitmapProcessor preProcessor)
			// .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
			// .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
			// .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
			.build();// 构建完成
	
	
	public static Point getScreenMetrics(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		int w_screen = dm.widthPixels;
		int h_screen = dm.heightPixels;
		return new Point(w_screen, h_screen);

	}
	
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
	
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
