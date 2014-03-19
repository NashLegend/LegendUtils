package com.example.legendutils;

import android.util.Log;

public class LogUtil {

	public static boolean DEBUG = true;
	private static String tag = "";
	private static String iTag = "";
	private static String eTag = "";
	private static String dTag = "";
	private static String vTag = "";
	private static String wTag = "";

	// //////////////////////////////////////////////////////////

	public static void i(String msg) {
		if (DEBUG) {
			Log.i(iTag, msg);
		}
	}

	public static void i(String msg, Throwable tr) {
		if (DEBUG) {
			Log.i(iTag, msg, tr);
		}
	}

	public static void i(String tag, String msg) {
		if (DEBUG) {
			Log.i(tag, msg);
		}
	}

	public static void i(String tag, String msg, Throwable tr) {
		if (DEBUG) {
			Log.i(tag, msg, tr);
		}
	}

	// /////////////////////////////////////////////////////////

	public static void e(String msg) {
		if (DEBUG) {
			Log.e(eTag, msg);
		}
	}

	public static void e(String msg, Throwable tr) {
		if (DEBUG) {
			Log.e(eTag, msg, tr);
		}
	}

	public static void e(String tag, String msg) {
		if (DEBUG) {
			Log.e(tag, msg);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (DEBUG) {
			Log.e(tag, msg, tr);
		}
	}

	// //////////////////////////////////////////////////////////

	public static void d(String msg) {
		if (DEBUG) {
			Log.d(dTag, msg);
		}
	}

	public static void d(String msg, Throwable tr) {
		if (DEBUG) {
			Log.d(dTag, msg, tr);
		}
	}

	public static void d(String tag, String msg) {
		if (DEBUG) {
			Log.d(tag, msg);
		}
	}

	public static void d(String tag, String msg, Throwable tr) {
		if (DEBUG) {
			Log.d(tag, msg, tr);
		}
	}

	// ///////////////////////////////////////////////////////////

	public static void v(String msg) {
		if (DEBUG) {
			Log.v(vTag, msg);
		}
	}

	public static void v(String msg, Throwable tr) {
		if (DEBUG) {
			Log.v(vTag, msg, tr);
		}
	}

	public static void v(String tag, String msg) {
		if (DEBUG) {
			Log.v(tag, msg);
		}
	}

	public static void v(String tag, String msg, Throwable tr) {
		if (DEBUG) {
			Log.v(tag, msg, tr);
		}
	}

	// //////////////////////////////////////////////////////////////

	public static void w(String msg) {
		if (DEBUG) {
			Log.w(wTag, msg);
		}
	}

	public static void w(String msg, Throwable tr) {
		if (DEBUG) {
			Log.w(wTag, msg, tr);
		}
	}

	public static void w(String tag, String msg) {
		if (DEBUG) {
			Log.w(tag, msg);
		}
	}

	public static void w(String tag, String msg, Throwable tr) {
		if (DEBUG) {
			Log.w(tag, msg, tr);
		}
	}

	public static String getTag() {
		return tag;
	}

	public static void setTag(String tag) {
		LogUtil.tag = tag;
		seteTag(tag);
		setiTag(tag);
		setdTag(tag);
		setvTag(tag);
		setwTag(tag);
	}

	public static String getiTag() {
		return iTag;
	}

	public static void setiTag(String iTag) {
		LogUtil.iTag = iTag;
	}

	public static String geteTag() {
		return eTag;
	}

	public static void seteTag(String eTag) {
		LogUtil.eTag = eTag;
	}

	public static String getdTag() {
		return dTag;
	}

	public static void setdTag(String dTag) {
		LogUtil.dTag = dTag;
	}

	public static String getvTag() {
		return vTag;
	}

	public static void setvTag(String vTag) {
		LogUtil.vTag = vTag;
	}

	public static String getwTag() {
		return wTag;
	}

	public static void setwTag(String wTag) {
		LogUtil.wTag = wTag;
	}
}
