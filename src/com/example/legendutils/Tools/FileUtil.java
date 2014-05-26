package com.example.legendutils.Tools;

import java.io.File;

public class FileUtil {

	interface FileOperationListener {

		public void onComplete();

		public void onError();

	}

	public static boolean copy2File(File sourceFile, File destFile) {
		return false;
	}

	public static Runnable copy2FileAsync(File sourceFile, File destFile) {
		return null;
	}

	public static boolean copy2Directory(File sourceFile, File destFile) {
		return false;
	}

	public static Runnable copy2DirectoryAsync(File sourceFile, File destFile) {
		return null;
	}

	public static boolean move2File(File sourceFile, File destFile) {
		return false;
	}

	public static Runnable move2FileAsync(File sourceFile, File destFile) {
		return null;
	}

	public static boolean move2Directory(File sourceFile, File destFile) {
		return false;
	}

	public static Runnable move2DirectoryAsync(File sourceFile, File destFile) {
		return null;
	}

	public static boolean delete(File file) {
		return false;
	}

	public static Runnable deleteAsync(File file) {
		return null;
	}

}
