package com.example.legendutils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Window;

public class DisplayUtils {

	public DisplayUtils() {
		// TODO 自动生成的构造函数存根
	}

	/**
	 * 获取手机屏幕高度,以px为单位
	 * 
	 * @param activity
	 * @return
	 */
	public static int getScreenHeight(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}

	/**
	 * 获取手机屏幕宽度，以px为单位
	 * 
	 * @param activity
	 * @return
	 */
	public static int getScreenWidth(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;
	}

	/**
	 * 返回程序window宽度
	 * 
	 * @return
	 */
	public int getWindowWidth(Activity activity) {
		return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT)
				.getWidth();
	}

	/**
	 * 返回程序window高度，不包括通知栏和标题栏
	 * 
	 * @return
	 */
	public int getWindowHeight(Activity activity) {
		return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT)
				.getHeight();
	}

	/**
	 * 单位转换，将dip转换为px
	 * 
	 * @param dp
	 * @param context
	 * @return
	 */
	public static int dip2px(float dp, Context context) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}
	

    /**
     * 单位转换，将px转换为dip
     * @param px
     * @param context
     * @return
     */
    public static int px2dip(float px, Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
	}

}
