package com.example.legendutils.Tools;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class TesticonActivity extends Activity {
	/** Called when the activity is first created. */
	PackageManager pm;
	private ImageView icon = null;
	Drawable activityIcon = null;
	Context mContext = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		icon = new ImageView(mContext);
		ApplicationInfo app = parsePackageName("/sdcard/UC.apk");
		System.out.println("appName=" + app.packageName);
		mContext = TesticonActivity.this;
		// 已经安装的程序获取icon
		// Intent launchIntentForPackage =
		// TesticonActivity.this.getPackageManager().getLaunchIntentForPackage(app.packageName);
		// //obj.getmPackageName()是包名
		// try {
		// activityIcon =
		// TesticonActivity.this.getPackageManager().getActivityIcon(launchIntentForPackage);
		// } catch (NameNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// 未安装的程序通过apk文件获取icon
		icon.setBackgroundDrawable(getApkIcon("/sdcard/UC.apk"));
	}

	private ApplicationInfo parsePackageName(String path) {
		pm = TesticonActivity.this.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(path,
				PackageManager.GET_ACTIVITIES);
		ApplicationInfo appInfo = null;
		if (info != null) {
			appInfo = info.applicationInfo;
			return appInfo;
		} else {
			return null;
		}
	}

	public Drawable getApkIcon(String Path) {
		// 未安装的程序通过apk文件获取icon
		String apkPath = Path; // apk 文件所在的路径
		String PATH_PackageParser = "android.content.pm.PackageParser";
		String PATH_AssetManager = "android.content.res.AssetManager";
		try {
			Class<?> pkgParserCls = Class.forName(PATH_PackageParser);
			Class<?>[] typeArgs = { String.class };
			Constructor<?> pkgParserCt = pkgParserCls.getConstructor(typeArgs);
			Object[] valueArgs = { apkPath };
			Object pkgParser = pkgParserCt.newInstance(valueArgs);
			DisplayMetrics metrics = new DisplayMetrics();
			metrics.setToDefaults();
			typeArgs = new Class<?>[] { File.class, String.class,
					DisplayMetrics.class, int.class };
			Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod(
					"parsePackage", typeArgs);
			valueArgs = new Object[] { new File(apkPath), apkPath, metrics, 0 };
			Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser,
					valueArgs);
			Field appInfoFld = pkgParserPkg.getClass().getDeclaredField(
					"applicationInfo");
			ApplicationInfo info = (ApplicationInfo) appInfoFld
					.get(pkgParserPkg);

			Class<?> assetMagCls = Class.forName(PATH_AssetManager);
			Object assetMag = assetMagCls.newInstance();
			typeArgs = new Class[1];
			typeArgs[0] = String.class;
			Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod(
					"addAssetPath", typeArgs);
			valueArgs = new Object[1];
			valueArgs[0] = apkPath;
			assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);
			Resources res = mContext.getResources();
			typeArgs = new Class[3];
			typeArgs[0] = assetMag.getClass();
			typeArgs[1] = res.getDisplayMetrics().getClass();
			typeArgs[2] = res.getConfiguration().getClass();
			Constructor<Resources> resCt = Resources.class
					.getConstructor(typeArgs);
			valueArgs = new Object[3];
			valueArgs[0] = assetMag;
			valueArgs[1] = res.getDisplayMetrics();
			valueArgs[2] = res.getConfiguration();
			res = (Resources) resCt.newInstance(valueArgs);
			if (info != null) {
				if (info.icon != 0) {
					Drawable icon = res.getDrawable(info.icon);
					return icon;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}