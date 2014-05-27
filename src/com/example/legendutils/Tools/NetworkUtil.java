package com.example.legendutils.Tools;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

	/**
	 * 返回当前是否有网络连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasNetwork(Context context) {
		final ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo networkInfo = connectivityManager
				.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isAvailable());
	}

	/**
	 * 返回当前网络状态
	 * 
	 * @param context
	 * @return
	 */
	public static int getNetworkState(Context context) {
		final ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo wifiNetWorkInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		final NetworkInfo mobileNetWorkInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (wifiNetWorkInfo != null && wifiNetWorkInfo.isAvailable()) {
			return ConnectivityManager.TYPE_WIFI;
		} else if (mobileNetWorkInfo != null && mobileNetWorkInfo.isAvailable()) {
			return ConnectivityManager.TYPE_MOBILE;
		} else {
			return -1;
		}
	}

	/**
	 * 判断是否具有wifi连接
	 * 
	 * @param context
	 * @return
	 */
	public static final boolean hasWifiConnection(Context context) {
		final ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo networkInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return (networkInfo != null && networkInfo.isAvailable());
	}

	/**
	 * 判断是否具有移动网络连接
	 * 
	 * @param context
	 * @return
	 */
	public static final boolean hasMobileConnection(Context context) {
		final ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo networkInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		return (networkInfo != null && networkInfo.isAvailable());
	}

	/**
	 * 打开网络连接设置界面
	 * 
	 * @param context
	 */
	public static void openNetworkSetting(Context context) {
		context.startActivity(new Intent(
				android.provider.Settings.ACTION_WIRELESS_SETTINGS));
	}

}
