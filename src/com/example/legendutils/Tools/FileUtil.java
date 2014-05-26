package com.example.legendutils.Tools;

import java.io.File;
import java.util.UUID;

public class FileUtil {

	interface FileOperationListener {

		public void onComplete();

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
				// 如果根本就是同一个文件，直接返回true
				if (sourceFile.equals(destFile)) {
					return true;
				}
				return copy2SilgleFile(sourceFile, destFile);
			} else {
				return copy2SingleFolder(sourceFile, destFile);
			}
		} else {
			return false;
		}
	}

	/**
	 * 复制单个文件而不是文件夹。私有静态方法，由copy2File(File sourceFile, File destFile)调用。
	 * 
	 * 在调用前就已经确保参数合法，不必检查，sourceFile和destFile也一定不是同一个文件
	 * 
	 * @return
	 */
	private static boolean copy2SingleFolder(File sourceFile, File destFile) {
		// 如果根本就是同一个文件，直接返回true
		return false;
	}

	/**
	 * 复制单个文件而不是文件夹。私有静态方法，由copy2File或者copy2SingleFolder调用。
	 * 
	 * 在调用前就已经确保参数合法，不必进行检查,sourceFile和destFile也一定不是同一个文件
	 * 
	 * @return
	 */
	private static boolean copy2SilgleFile(File sourceFile, File destFile) {
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
		if (!sourceFile.exists()) {
			throw new NullPointerException("sourceFile does not exist");
		}
		if (ensureFileIsDirectory(destFile)) {
			// 走到这一步说明已经存在了destFile
			File finalFile = new File(destFile.getAbsolutePath(),
					sourceFile.getName());
			return copy2File(sourceFile, finalFile);
		} else {
			return false;
		}

	}

	public static Runnable copy2DirectoryAsync(File sourceFile, File destFile) {
		return null;
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

	public static Runnable move2FileAsync(File sourceFile, File destFile) {
		return null;
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
			// 如果destFile存在，则删除之
			if (!delete(destFile)) {
				// 如果删除失败，则直接返回false
				return false;
			}
		}

		return ensureFileIsDirectory(destFile.getParentFile());
	}

	/**
	 * renameTo本来就会覆盖文件，所以本方法用不着，简直就是无用功
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

}
