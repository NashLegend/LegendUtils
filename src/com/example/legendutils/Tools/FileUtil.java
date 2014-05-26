package com.example.legendutils.Tools;

import java.io.File;
import java.util.UUID;

public class FileUtil {

	interface FileOperationListener {

		public void onComplete();

		public void onError();

	}

	public static boolean copy2File(File sourceFile, File destFile) {
		if (sourceFile == null || destFile == null) {
			throw new NullPointerException(sourceFile == null ? "sourceFile"
					: "destFile" + " cannot be null");
		}
		// destFile在创建完成之前isDirectory返回的是false
		if (!sourceFile.exists()) {
			throw new NullPointerException("sourceFile does not exist");
		}

		// 如果根本就是一个文件，直接返回true
		if (sourceFile.equals(destFile)) {
			// 没有变化
			return true;
		}

		if (destFile.exists()) {
			// 如果destFile存在，则删除之
			if (!delete(destFile)) {
				// 如果删除失败，则直接返回false
				return false;
			}
		}

		File parentFile = destFile.getParentFile();
		if (parentFile != null) {
			// 如果目录不存在则创建目录
			if (!parentFile.exists()) {
				if (!parentFile.mkdirs()) {
					return false;
				}
			}
		} else {
			// 返回null有两种可能，一是路径不对，二是deskFile文件是根目录，这两个都是不允许的
			throw new NullPointerException(
					"file not valid bacause it has no parent");
		}

		// TODO , 开始复制
		if (sourceFile.isFile()) {

		} else {

		}
		// TODO
		return false;
	}

	/**
	 * 复制文件而不是文件夹
	 * 
	 * @return
	 */
	public static boolean copy2SilgleFile() {
		return false;
	}

	public static Runnable copy2FileAsync(File sourceFile, File destFile) {
		return null;
	}

	public static boolean copy2Directory(File sourceFile, File destFile) {
		if (sourceFile == null || destFile == null) {
			throw new NullPointerException(sourceFile == null ? "sourceFile"
					: "destFile" + " cannot be null");
		}
		// 如果一个文件不存在，则isDirectory必然为false，而当mkdirs后isDirectory就是true了
		if (destFile.exists()) {
			// 如果destFile存在且是个文件则直接删除，然后创建目录
			if (!destFile.isDirectory()) {
				if (!delete(destFile)) {
					return false;
				}
				if (!destFile.mkdirs()) {
					return false;
				}
			}
		} else {
			// 如果destFile不存在则创建文件夹，创建失败则返回false，有可能是没有权限也有可能是路径不对
			if (!destFile.mkdirs()) {
				return false;
			}
		}

		// 走到这一步说明已经存在了destFile
		File finalFile = new File(destFile.getAbsolutePath(),
				sourceFile.getName());
		return copy2File(sourceFile, finalFile);
	}

	public static Runnable copy2DirectoryAsync(File sourceFile, File destFile) {
		return null;
	}

	public static boolean move2File(File sourceFile, File destFile) {
		if (sourceFile == null || destFile == null) {
			throw new NullPointerException(sourceFile == null ? "sourceFile"
					: "destFile" + " cannot be null");
		}

		// destFile在创建完成之前isDirectory返回的是false
		if (!sourceFile.exists()) {
			throw new NullPointerException("sourceFile does not exist");
		}

		// 如果根本就是一个文件，直接返回true
		if (sourceFile.equals(destFile)) {
			// 没有变化
			return true;
		}

		if (destFile.exists()) {
			// 如果destFile存在，则删除之
			if (!delete(destFile)) {
				// 如果删除失败，则直接返回false
				return false;
			}
		}

		File parentFile = destFile.getParentFile();
		if (parentFile != null) {
			// 如果目录不存在则创建目录
			if (!parentFile.exists()) {
				if (!parentFile.mkdirs()) {
					return false;
				}
			}
		} else {
			// 返回null有两种可能，一是路径不对，二是deskFile文件是根目录，这两个都是不允许的
			throw new NullPointerException(
					"file not valid bacause it has no parent");
		}

		// 开始移动
		return sourceFile.renameTo(destFile);
		// return moveFileOverride(sourceFile, destFile);
	}

	/**
	 * renameTo本来就会覆盖文件，所以本方法用不着。。。
	 * 
	 * @param sourceFile
	 * @param destFile
	 * @return
	 */
	public static boolean moveFileOverride(File sourceFile, File destFile) {
		// 覆盖文件,如果文件已经存在，则先将destFile重命名为一个临时文件，以备移动失败时恢复 TODO
		File existedFile = null;
		if (destFile.exists()) {
			existedFile = new File(destFile.getAbsolutePath() + "_backup_"
					+ UUID.randomUUID().toString());
			// 保存临时文件
			if (destFile.renameTo(existedFile)) {
				// 保存临时文件成功
			} else {
				// 如果保存临时文件失败，则直接删除？ TODO
				existedFile = null;
				if (!destFile.delete()) {
					return false;
				}
			}
		}
		// 开始移动文件，如果移动失败，则恢复以前的destFile（如果以前的过的话）
		if (sourceFile.renameTo(destFile)) {
			// 移动成功
			if (existedFile != null && existedFile.exists()) {
				existedFile.delete();
			}
			return true;
		} else {
			// 移动失败不会导致源文件自身丢失
			if (existedFile != null && existedFile.exists()) {
				// destFile和existedFile不会共存，所以destFile应该已经不存在了
				// 但是假如还存在，那它应该还是以前的destFile，真多事
				if (destFile.exists()) {
					existedFile.delete();
				} else {
					existedFile.renameTo(destFile);
				}
			}
			return false;
		}
	}

	public static Runnable move2FileAsync(File sourceFile, File destFile) {
		return null;
	}

	public static boolean move2Directory(File sourceFile, File destFile) {
		if (sourceFile == null || destFile == null) {
			throw new NullPointerException(sourceFile == null ? "sourceFile"
					: "destFile" + " cannot be null");
		}

		// 如果一个文件不存在，则isDirectory必然为false，而当mkdirs后isDirectory就是true了
		if (destFile.exists()) {
			// 如果destFile存在且是个文件则直接删除，然后创建目录，创建失败则返回false，有可能是没有权限也有可能是路径不对
			if (!destFile.isDirectory()) {
				if (!delete(destFile)) {
					return false;
				}
				if (!destFile.mkdirs()) {
					return false;
				}
			}
		} else {
			// 如果destFile不存在则创建文件夹，创建失败则返回false，有可能是没有权限也有可能是路径不对
			if (!destFile.mkdirs()) {
				return false;
			}
		}

		// 走到这一步说明已经存在了destFile并且deskFile是目录
		File finalFile = new File(destFile.getAbsolutePath(),
				sourceFile.getName());
		return move2File(sourceFile, finalFile);
	}

	public static Runnable move2DirectoryAsync(File sourceFile, File destFile) {
		return null;
	}

	public static boolean delete(File file) {
		if (file == null) {
			throw new NullPointerException("file is null");
		}
		if (file.isDirectory()) {
			return file.delete();
		}

		return deleteFolder(file);
	}

	public static boolean deleteFolder(File file) {
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
						if (!delete(sFile)) {
							return false;
						}
					}
				}
				return true;
			}
		} else {
			throw new ClassCastException("file is not a directory");
		}

	}

	public static Runnable deleteAsync(File file) {
		return null;
	}

}
