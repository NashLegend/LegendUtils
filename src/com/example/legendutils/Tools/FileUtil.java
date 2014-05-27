package com.example.legendutils.Tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.provider.MediaStore.Video.Thumbnails;

/**
 * 方法线程安全不
 * 
 * @author NashLegend
 * 
 */
public class FileUtil {

	/**
	 * 普通文件
	 */
	public static final int FILE_TYPE_UNKNOWN = 0;
	/**
	 * 文件夹
	 */
	public static final int FILE_TYPE_FOLDER = 1;
	/**
	 * 声音类型的文件
	 */
	public static final int FILE_TYPE_SOUND = 2;
	/**
	 * 图像类型的文件
	 */
	public static final int FILE_TYPE_IMAGE = 3;
	/**
	 * 视频类型的文件
	 */
	public static final int FILE_TYPE_VIDEO = 4;
	/**
	 * APK文件
	 */
	public static final int FILE_TYPE_APK = 5;
	/**
	 * TXT文件
	 */
	public static final int FILE_TYPE_TXT = 6;
	/**
	 * ZIP文件
	 */
	public static final int FILE_TYPE_ZIP = 7;

	// 类型当然不全……
	public static final String[] soundSuffixArray = { "mp3", "wav" };
	public static final String[] imageSuffixArray = { "jpg", "jpeg", "png",
			"bmp", "gif" };
	public static final String[] videoSuffixArray = { "mp4", "avi", "rmvb",
			"flv", "mkv", "wmv", };
	public static final String[] apkSuffixArray = { "apk" };
	public static final String[] txtSuffixArray = { "txt", "xml" };
	public static final String[] zipSuffixArray = { "zip", "rar", "gz", "7z" };

	interface FileOperationListener {

		public void onComplete();

		public void onProgress();

		public void onError();

	}

	/**
	 * @param sourceFile
	 * @param destFile
	 * @return
	 */
	public static boolean copy2File(File sourceFile, File destFile) {
		if (ensureSourceAndDestFileValid(sourceFile, destFile)) {
			if (sourceFile.isFile()) {
				if (sourceFile.equals(destFile)) {
					return true;
				}
				return copy2SingleFile(sourceFile, destFile);
			} else {
				return copy2SingleFolder(sourceFile, destFile);
			}
		} else {
			return false;
		}
	}

	public static boolean copy2Directory(File sourceFile, File destFile) {
		if (sourceFile == null || destFile == null) {
			throw new NullPointerException(sourceFile == null ? "sourceFile"
					: "destFile" + " cannot be null");
		}
		if (!sourceFile.exists()) {
			throw new NullPointerException("sourceFile does not exist");
		}
		if (ensureFileIsDirectory(destFile)) {
			File finalFile = new File(destFile.getAbsolutePath(),
					sourceFile.getName());
			return copy2File(sourceFile, finalFile);
		} else {
			return false;
		}

	}

	/**
	 * 将sourceFile文件移动成为destFile
	 * 
	 * @param sourceFile
	 * @param destFile
	 * @return
	 */
	public static boolean move2File(File sourceFile, File destFile) {
		if (ensureSourceAndDestFileValid(sourceFile, destFile)) {
			// 这里不必检查是否sourceFile和destFile是同一个文件，renameTo自然会返回true
			return sourceFile.renameTo(destFile);
		} else {
			return false;
		}
	}

	/**
	 * 将文件转移到某个目录，文件名不变
	 * 
	 * @param sourceFile
	 * @param destFile
	 * @return
	 */
	public static boolean move2Directory(File sourceFile, File destFile) {
		if (sourceFile == null || destFile == null) {
			throw new NullPointerException(sourceFile == null ? "sourceFile"
					: "destFile" + " cannot be null");
		}
		if (!sourceFile.exists()) {
			throw new NullPointerException("sourceFile does not exist");
		}
		if (ensureFileIsDirectory(destFile)) {
			// 走到这一步说明已经存在了destFile并且deskFile是目录
			File finalFile = new File(destFile.getAbsolutePath(),
					sourceFile.getName());
			return move2File(sourceFile, finalFile);
		} else {
			return false;
		}
	}

	public static boolean delete(File file) {
		if (file == null) {
			throw new NullPointerException("file is null");
		}
		if (file.isDirectory()) {
			return deleteFolder(file);
		}
		return file.delete();

	}

	private static boolean deleteFolder(File file) {
		if (file.isDirectory()) {
			File[] subFiles = file.listFiles();
			if (subFiles == null || subFiles.length == 0) {
				return file.delete();
			} else {
				for (int i = 0; i < subFiles.length; i++) {
					File sFile = subFiles[i];
					if (sFile.isDirectory()) {
						if (!deleteFolder(sFile)) {
							return false;
						}
					} else {
						if (!sFile.delete()) {
							return false;
						}
					}
				}
			}
			// 全部删除后还要再删除自己
			return file.delete();
		} else {
			throw new ClassCastException("file is not a directory");
		}
	}

	/**
	 * 复制单个文件而不是文件夹。私有静态方法，由copy2File(File sourceFile, File destFile)调用。
	 * 
	 * 在调用前就已经确保参数合法，不必检查，sourceFile和destFile也一定不是同一个文件
	 * 
	 * Folder To Folder
	 * 
	 * @return
	 */
	private static boolean copy2SingleFolder(File sourceFile, File destFile) {
		if (destFile.mkdirs()) {
			File[] files = sourceFile.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					File sourceSubFile = files[i];
					File destSubFile = new File(destFile.getAbsolutePath(),
							sourceSubFile.getName());
					if (sourceSubFile.isDirectory()) {
						if (!copy2SingleFolder(sourceSubFile, destSubFile)) {
							return false;
						}
					} else {
						if (!copy2SingleFile(sourceSubFile, destSubFile)) {
							return false;
						}

					}
				}
			}
		}
		return true;
	}

	/**
	 * 复制单个文件而不是文件夹。私有静态方法，由copy2File或者copy2SingleFolder调用。
	 * 
	 * 在调用前就已经确保参数合法，不必进行检查,destFile必然有父文件夹，sourceFile和destFile也一定不是同一个文件
	 * 
	 * File To File
	 * 
	 * @return
	 */
	private static boolean copy2SingleFile(File sourceFile, File destFile) {
		boolean copyOK = true;
		BufferedInputStream inputStream = null;
		BufferedOutputStream outputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(
					sourceFile));
			outputStream = new BufferedOutputStream(new FileOutputStream(
					destFile));
			byte[] buffer = new byte[1024 * 5];
			int len;
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			copyOK = false;
			e.printStackTrace();
		} catch (IOException e) {
			copyOK = false;
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (IOException e) {
				copyOK = false;
				e.printStackTrace();
			}
		}

		return copyOK;
	}

	/**
	 * 使用FileChannel复制文件，速度略快
	 * 
	 * @param sourceFile
	 * @param destFile
	 * @return
	 */
	@SuppressWarnings("unused")
	private static boolean copy2SingleFileByChannel(File sourceFile,
			File destFile) {
		boolean copyOK = true;
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputStream = new FileInputStream(sourceFile);
			outputStream = new FileOutputStream(destFile);
			inputChannel = inputStream.getChannel();
			outputChannel = outputStream.getChannel();
			inputChannel.transferTo(0, inputChannel.size(), outputChannel);
		} catch (Exception e) {
			copyOK = false;
		} finally {
			try {
				inputChannel.close();
				inputStream.close();
				outputChannel.close();
				outputStream.close();
			} catch (IOException e) {
				copyOK = false;
				e.printStackTrace();
			}
		}
		return copyOK;
	}

	/**
	 * 用于确定某个方法中父目录的存在性，确保destFile是个目录，如果不存在，则创建，如果存在但不是目录，则返回false。
	 * 
	 * @param destFile
	 * @return
	 */
	private static boolean ensureFileIsDirectory(File destFile) {
		if (destFile == null) {
			throw new NullPointerException("destFile cannot be null");
		}
		// 如果一个文件不存在，则isDirectory必然为false，而当mkdirs后isDirectory就是true了
		if (destFile.exists()) {
			// 如果destFile存在且是个文件则直接返回false
			if (!destFile.isDirectory()) {
				return false;
			}
		} else {
			// 如果destFile不存在则创建文件夹，创建失败则返回false，有可能是没有权限也有可能是路径不对
			// 也有可能destFile的父级目录却是个文件而不是目录 TODO
			if (!destFile.mkdirs()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 确保源文件和目标文件的合法性。
	 * 
	 * @param sourceFile
	 *            要复制或者移动的文件，必须存在。
	 * @param destFile
	 *            要移动到的目标文件（不是要移动到的目录！），不一定存在，若存在则删除，若其向上某一级得到的结果有可能是文件，则不合法，
	 *            因为这样此文件就不能创建，mkdirs不会成功 TODO
	 * @return 若返回true，则：destFile不存在，destFile的父级目录存在，只等复制过去
	 */
	private static boolean ensureSourceAndDestFileValid(File sourceFile,
			File destFile) {
		if (sourceFile == null || destFile == null) {
			throw new NullPointerException(sourceFile == null ? "sourceFile"
					: "destFile" + " cannot be null");
		}

		if (!sourceFile.exists()) {
			throw new NullPointerException("sourceFile does not exist");
		}

		if (destFile.exists()) {
			// 如果destFile存在，则删除之(文件夹是否应该合并 TODO)
			if (!delete(destFile)) {
				return false;
			}
		}

		return ensureFileIsDirectory(destFile.getParentFile());
	}

	private static boolean isArrayContains(String[] strs, String suffix) {
		if (strs == null || suffix == null) {
			return false;
		}
		for (int i = 0; i < strs.length; i++) {
			if (suffix.equals(strs[i])) {
				return true;
			}
		}
		return false;
	}

	// 异步操作

	public static Runnable copy2FileAsync(File sourceFile, File destFile) {
		return null;
	}

	public static Runnable copy2DirectoryAsync(File sourceFile, File destFile) {
		return null;
	}

	public static Runnable move2FileAsync(File sourceFile, File destFile) {
		return null;
	}

	public static Runnable move2DirectoryAsync(File sourceFile, File destFile) {
		return null;
	}

	public static Runnable deleteAsync(File file) {
		return null;
	}

	/**
	 * 提取文件缩略图，默认大小96 x 96. 如果没有指定context，将不会取到apk文件的缩略图
	 * 
	 * @param file
	 * @return
	 */
	public static Bitmap extractFileThumbnail(File file, Context context) {
		int type = getFileType(file);
		Bitmap thumb = null;
		switch (type) {
		case FILE_TYPE_IMAGE:// 获取图像文件缩略图
			thumb = getImageFileThumbnail(file, 96, 96);
			break;
		case FILE_TYPE_VIDEO:// 获取视频文件缩略图
			thumb = getVideoFileThumbnail(file, 96, 96);
			break;
		case FILE_TYPE_APK:// 获取apk文件缩略图
			thumb = getApkIcon(context, file.getAbsolutePath());
			break;

		default:
			break;
		}
		return thumb;
	}

	/**
	 * 提取文件缩略图,指定缩略图大小, 如果没有指定context，将不会取到apk文件的缩略图
	 * 
	 * @param file
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap extractFileThumbnail(File file, int width, int height,
			Context context) {
		int type = getFileType(file);
		Bitmap thumb = null;
		switch (type) {
		case FILE_TYPE_IMAGE:// 获取图像文件缩略图
			thumb = getImageFileThumbnail(file, width, height);
			break;
		case FILE_TYPE_VIDEO:// 获取视频文件缩略图
			thumb = getVideoFileThumbnail(file, width, height);
			break;
		case FILE_TYPE_APK:// 获取apk文件按指定尺寸缩放过的缩略图，
			thumb = getApkResizedIcon(context, file.getAbsolutePath(), width,
					height);
			break;

		default:
			break;
		}
		return thumb;
	}

	public static Bitmap getImageFileThumbnail(File file, int width, int height) {
		Bitmap bitmap = null;
		String path = file.getAbsolutePath();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 只获取这个图片的宽和高
		bitmap = BitmapFactory.decodeFile(path, options);
		options.inJustDecodeBounds = false;
		// 计算缩放比,出现错误的时候有可能为-1
		int h = options.outHeight;
		int w = options.outWidth;
		if (h > 0 && w > 0) {
			int beWidth = w / width;
			int beHeight = h / height;
			int be = 1;
			if (beWidth < beHeight) {
				be = beWidth;
			} else {
				be = beHeight;
			}
			if (be <= 0) {
				be = 1;
			}
			options.inSampleSize = be;
			// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
			bitmap = BitmapFactory.decodeFile(path, options);
			// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		}

		return bitmap;
	}

	public static Bitmap getVideoFileThumbnail(File file, int width, int height) {
		// MINI_KIND: 512 x 384 ； MICRO_KIND: 96 x 96
		Bitmap thumb = null;
		if (width > 96 || height > 96) {
			// 大于96 x 96，则取MINI_KIND的缩略图并在此基础上再次抽取
			thumb = ThumbnailUtils.extractThumbnail(ThumbnailUtils
					.createVideoThumbnail(file.getAbsolutePath(),
							Thumbnails.MINI_KIND), width, height,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		} else if (width == 96 && height == 96) {
			thumb = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(),
					Thumbnails.MICRO_KIND);
		} else {
			// 小于96 x 96，则取MICRO_KIND的缩略图并在此基础上再次抽取
			thumb = ThumbnailUtils.extractThumbnail(ThumbnailUtils
					.createVideoThumbnail(file.getAbsolutePath(),
							Thumbnails.MICRO_KIND), width, height,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		}

		return thumb;
	}

	/**
	 * 获取apk文件按指定尺寸缩放过的缩略图
	 * 
	 * @param context
	 * @param apkPath
	 * @return
	 */
	public static Bitmap getApkResizedIcon(Context context, String apkPath,
			int width, int height) {
		Bitmap thumb = getApkIcon(context, apkPath);
		if (thumb != null) {
			return ThumbnailUtils.extractThumbnail(thumb, width, height,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		}
		return thumb;
	}

	/**
	 * 获取apk文件缩略图将不会改变icon大小
	 * 
	 * @param context
	 * @param apkPath
	 * @return
	 */
	public static Bitmap getApkIcon(Context context, String apkPath) {
		Bitmap thumb = null;
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(apkPath,
				PackageManager.GET_ACTIVITIES);
		if (info != null) {
			ApplicationInfo appInfo = info.applicationInfo;
			appInfo.sourceDir = apkPath;
			appInfo.publicSourceDir = apkPath;
			try {
				thumb = ((BitmapDrawable) appInfo.loadIcon(pm)).getBitmap();
			} catch (OutOfMemoryError e) {

			}
		}
		return thumb;
	}

	/**
	 * 根据后缀取得文件类型,FILE_TYPE_IMAGE,FILE_TYPE_SOUND......
	 */
	public static int getFileType(File file) {
		if (file.isDirectory()) {
			return FILE_TYPE_FOLDER;
		} else {
			String suffix = getFileSuffix(file);
			if (isArrayContains(apkSuffixArray, suffix)) {
				return FILE_TYPE_APK;
			} else if (isArrayContains(imageSuffixArray, suffix)) {
				return FILE_TYPE_IMAGE;
			} else if (isArrayContains(soundSuffixArray, suffix)) {
				return FILE_TYPE_SOUND;
			} else if (isArrayContains(videoSuffixArray, suffix)) {
				return FILE_TYPE_VIDEO;
			} else if (isArrayContains(txtSuffixArray, suffix)) {
				return FILE_TYPE_TXT;
			} else if (isArrayContains(zipSuffixArray, suffix)) {
				return FILE_TYPE_ZIP;
			} else {
				return FILE_TYPE_UNKNOWN;
			}
		}
	}

	/**
	 * 获得文件后缀
	 */
	public static String getFileSuffix(File file) {
		String fileName = file.getName();
		String suffix = "";
		int offset = fileName.lastIndexOf(".");
		// -1则没有后缀。0,则表示是一个隐藏文件而没有后缀，offset == fileName.length() -
		// 1，表示"."是最后一个字符，没有后缀
		if (offset > 0 && offset < fileName.length() - 1) {
			suffix = fileName.substring(offset + 1);
		}
		return suffix;
	}
}
