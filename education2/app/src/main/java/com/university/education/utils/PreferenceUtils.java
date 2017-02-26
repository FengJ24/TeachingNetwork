package com.university.education.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * sharedPreference工具类
 * 
 * @author jian
 * 
 */
public class PreferenceUtils {

	private static SharedPreferences sharedPreferences;

	/*
	 * 得到sharedPreferences的对象
	 */
	private static void getSharedPreferences(Context context) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences("config",
					Context.MODE_PRIVATE);
		}
	}

	/*
	 * 设置sp的内容
	 */
	public static void putString(Context context, String name, String value) {
		getSharedPreferences(context);
		sharedPreferences.edit().putString(name, value).commit();
	}

	/*
	 * 取出sp中的内容
	 */
	public static String getString(Context context, String name, String defValue) {
		getSharedPreferences(context);
		return sharedPreferences.getString(name, defValue);
	}

	/*
	 * 取出sp中的内容
	 */
	public static String getString(Context context, String name) {
		getSharedPreferences(context);
		return sharedPreferences.getString(name, "");
	}

	/*
	 * 设置sp的内容,值是布尔类型
	 */
	public static void putBoolean(Context context, String name, Boolean value) {
		getSharedPreferences(context);
		sharedPreferences.edit().putBoolean(name, value).commit();
	}

	/*
	 * 取出sp中的内容
	 */
	public static Boolean getBoolean(Context context, String name,
			Boolean defValue) {
		getSharedPreferences(context);
		return sharedPreferences.getBoolean(name, defValue);
	}

	/*
	 * 取出sp中的内容
	 */
	public static Boolean getBoolean(Context context, String name) {
		getSharedPreferences(context);
		return getBoolean(context, name, true);

	}
	/*
	 * 设置sp的内容,值是布尔类型
	 */
	public static void putInt(Context context, String name, int value) {
		getSharedPreferences(context);
		sharedPreferences.edit().putInt(name, value).commit();
	}
	
	/*
	 * 取出sp中的内容
	 */
	public static int getInt(Context context, String name,
			int defValue) {
		getSharedPreferences(context);
		return sharedPreferences.getInt(name, defValue);
	}
	
	/*
	 * 取出sp中的内容
	 */
	public static int getInt(Context context, String name) {
		getSharedPreferences(context);
		return getInt(context, name, -1);
		
	}

	public static void remove(Context context, String name) {
		getSharedPreferences(context);
		sharedPreferences.edit().remove(name).commit();

	}
}
