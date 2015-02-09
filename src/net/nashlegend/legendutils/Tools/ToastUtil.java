package net.nashlegend.legendutils.Tools;

import android.content.Context;
import android.widget.Toast;

/**
 * showToast为单例，连续使用不会出现toast长时间呆在屏幕上的情况。
 * makeTextAndShow普通的Toast，将makeText和show连接起来。
 * 
 * @author NashLegend
 */
public class ToastUtil {

	public static Toast toast;

	public ToastUtil() {
		// TODO 自动生成的构造函数存根
	}

	/**
	 * 单例，连续使用不会出现toast长时间呆在屏幕上的情况，duration为Toast.LENGTH_SHORT
	 * 
	 * @param context
	 * @param text
	 */
	public static void showToast(Context context, String text) {
		if (toast != null) {
			toast.cancel();
		}
		toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.show();
	}

	/**
	 * 单例，连续使用不会出现toast长时间呆在屏幕上的情况，使用string资源，duration为Toast.LENGTH_SHORT
	 * 
	 * @param context
	 * @param text
	 */
	public static void showToast(Context context, int resId) {
		if (toast != null) {
			toast.cancel();
		}
		toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
		toast.show();
	}

	/**
	 * 单例，连续使用不会出现toast长时间呆在屏幕上的情况，duration为自定义
	 * 
	 * @param context
	 * @param text
	 * @param duration
	 */
	public static void showToast(Context context, String text, int duration) {
		if (toast != null) {
			toast.cancel();
		}
		toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	/**
	 * 单例，连续使用不会出现toast长时间呆在屏幕上的情况，使用string资源，duration为自定义
	 * 
	 * @param context
	 * @param text
	 * @param duration
	 */
	public static void showToast(Context context, int resId, int duration) {
		if (toast != null) {
			toast.cancel();
		}
		toast = Toast.makeText(context, resId, duration);
		toast.show();
	}

	/**
	 * 普通的Toast，将makeText和show连接起来，duration为Toast.LENGTH_SHORT
	 * 
	 * @param context
	 * @param text
	 */
	public static void makeTextAndShow(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 普通的Toast，将makeText和show连接起来，使用string资源，duration为Toast.LENGTH_SHORT
	 * 
	 * @param context
	 * @param resId
	 */
	public static void makeTextAndShow(Context context, int resId) {
		Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 普通的Toast，将makeText和show连接起来，duration为自定义
	 * 
	 * @param context
	 * @param text
	 * @param duration
	 */
	public static void makeTextAndShow(Context context, String text,
			int duration) {
		Toast.makeText(context, text, duration).show();
	}

	/**
	 * 普通的Toast，将makeText和show连接起来，使用string资源，duration为自定义
	 * 
	 * @param context
	 * @param resId
	 * @param duration
	 */
	public static void makeTextAndShow(Context context, int resId, int duration) {
		Toast.makeText(context, resId, duration).show();
	}

}
