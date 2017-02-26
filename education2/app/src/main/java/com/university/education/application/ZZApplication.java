package com.university.education.application;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;

public class ZZApplication extends Application{
	// public final String IMAGE_SAVE_PATH = "/yuebao/images/";

	// public static final String HTTP_HEAD = "http://school.tongyi1.com/";
	private static final String TAG = "JPush";
	public static ArrayList<Activity> arrayList;


	@Override
	public void onCreate() {
//		arrayList = new ArrayList();
//		// TODO Auto-generated method stub
//		super.onCreate();
//		EMOptions options = new EMOptions();
//		// 默认添加好友时，是不需要验证的，改成需要验证
//		options.setAcceptInvitationAlways(false);
//		options.setAutoLogin(false);
//		//初始化
//		EMClient.getInstance().init(this, options);
//		//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
////		EMClient.getInstance().setDebugMode(true);

		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
//		JPushInterface.setDebugMode(false); // 设置开启日志,发布时请关闭日志
//		JPushInterface.init(this); // 初始化 JPush
//
//		DisplayImageOptions defaultOptions = new DisplayImageOptions
//				.Builder()
//				.showImageForEmptyUri(R.drawable.loading_image)
//				.showImageOnFail(R.drawable.loading_image)
//				.cacheInMemory(true)
//				.cacheOnDisc(true)
//				.build();
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration
//				.Builder(getApplicationContext())
//				.defaultDisplayImageOptions(defaultOptions)
//				.discCacheSize(50 * 1024 * 1024)//
//				.discCacheFileCount(100)//缓存一百张图片
//				.writeDebugLogs()
//				.build();
//		ImageLoader.getInstance().init(config);
//		new Thread(new Runnable() {
//            @Override
//            public void run() {
//                FaceConversionUtil.getInstace().getFileText(getApplicationContext());
//            }
//        }).start();

	}


}