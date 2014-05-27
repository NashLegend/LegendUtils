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

import android.graphics.Bitmap;

/**
 * 方法线程安全否
 * 
 * @author NashLegend
 * 
 */
public class FileUtil {

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
	 * 提取文件缩略图
	 * 
	 * @return
	 */
	public static Bitmap extractFileThumbnail() {
		return null;
	}
}
