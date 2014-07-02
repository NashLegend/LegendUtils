package com.example.legendutils.Tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import android.R.integer;
import android.os.AsyncTask;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipUtil {
	public static boolean zip(File file, File destFile) {
		return zip(file, destFile, "");
	}

	public static boolean zip(File file, File destFile, String pwd) {
		File[] files = { file };
		return zip(files, destFile, pwd);
	}

	public static boolean zip(File[] files, File destFile) {
		return zip(files, destFile, "");
	}

	public static boolean zip(File[] files, File destFile, String pwd) {
		try {
			ZipFile zipFile = new ZipFile(destFile);
			ArrayList<File> Files2Add = new ArrayList<File>();
			for (int i = 0; i < files.length; i++) {
				Files2Add.add(files[i]);
			}
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			if (pwd != null && pwd.length() > 0) {
				parameters.setEncryptFiles(true);
				parameters
						.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
				parameters.setPassword(pwd);
			}
			for (Iterator<File> iterator = Files2Add.iterator(); iterator
					.hasNext();) {
				File file = (File) iterator.next();
				if (file.isFile()) {
					zipFile.addFile(file, parameters);
				} else if (file.isDirectory()) {
					zipFile.addFolder(file, parameters);
				}

			}
		} catch (ZipException e) {
			return false;
		}
		return true;
	}

	public static boolean isZipfileValid(File file) throws IOException {
		try {
			ZipFile zipFile = new ZipFile(file);
			return zipFile.isValidZipFile();
		} catch (ZipException e) {
			throw new IOException(e.getMessage());
		}
	}

	public static boolean isZipfileEncrypted(File file) throws IOException {
		try {
			ZipFile zipFile = new ZipFile(file);
			return zipFile.isEncrypted();
		} catch (ZipException e) {
			throw new IOException(e.getMessage());
		}
	}

	public static boolean unZip(File file, String destPath) {
		return unZip(file, destPath, "");
	}

	public static boolean unZip(File file, String destPath, String pwd) {
		try {
			ZipFile zipFile = new ZipFile(file);
			if (zipFile.isEncrypted()) {
				if (pwd == null || pwd.equals("")) {
					return false;
				}
				zipFile.setPassword(pwd);
			}
			zipFile.extractAll(destPath);
		} catch (ZipException e) {
			return false;
		}
		return true;
	}

	/**
	 * @param sourceFile
	 * @param destFile
	 * @param listener
	 */
	public static void zipAsync(final File[] files, final File destFile,
			final String pwd, final ZipOperationListener listener) {

		class ZipTask extends AsyncTask<String, Integer, ProgressMonitor> {

			@Override
			protected ProgressMonitor doInBackground(String... params) {
				int pro = -1;
				try {
					ZipFile zipFile = new ZipFile(destFile);
					zipFile.setRunInThread(true);
					ArrayList<File> Files2Add = new ArrayList<File>();
					for (int i = 0; i < files.length; i++) {
						Files2Add.add(files[i]);
					}
					ZipParameters parameters = new ZipParameters();
					parameters
							.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
					parameters
							.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
					if (pwd != null && pwd.length() > 0) {
						parameters.setEncryptFiles(true);
						parameters
								.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
						parameters.setPassword(pwd);
					}
					for (Iterator<File> iterator = Files2Add.iterator(); iterator
							.hasNext();) {
						File file = (File) iterator.next();
						if (file.isFile()) {
							zipFile.addFile(file, parameters);
						} else if (file.isDirectory()) {
							zipFile.addFolder(file, parameters);
						}
					}
					ProgressMonitor progressMonitor = zipFile
							.getProgressMonitor();
					if (listener != null) {
						while (progressMonitor.getState() == ProgressMonitor.STATE_BUSY) {
							int currentprog = progressMonitor.getPercentDone();
							if (pro != currentprog) {
								publishProgress(progressMonitor
										.getPercentDone());
							}
							pro = currentprog;
						}
					} else {
						while (progressMonitor.getState() == ProgressMonitor.STATE_BUSY) {
							// do nothing
						}
					}

					return progressMonitor;

				} catch (ZipException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onProgressUpdate(Integer... values) {
				listener.onProgress(values[0]);
				super.onProgressUpdate(values);
			}

			@Override
			protected void onPostExecute(ProgressMonitor progressMonitor) {
				super.onPostExecute(progressMonitor);
				if (listener != null) {

					if (progressMonitor == null) {
						listener.onError("");
					} else {
						switch (progressMonitor.getResult()) {
						case ProgressMonitor.RESULT_ERROR:
							if (progressMonitor.getException() != null) {
								listener.onError(progressMonitor.getException()
										.getMessage());
							} else {
								listener.onError("An error occurred without any exception");
							}
							break;
						case ProgressMonitor.RESULT_CANCELLED:
							listener.onCancelled();
							break;
						case ProgressMonitor.RESULT_SUCCESS:
							listener.onComplete();
							break;
						case ProgressMonitor.RESULT_WORKING:
							listener.onWorking();
							break;

						default:
							break;
						}
					}
				}
			}
		}

		ZipTask task = new ZipTask();
		task.execute("");
	}

	public static interface ZipOperationListener {

		public void onComplete();

		public void onProgress(int progress);

		public void onError(String message);

		public void onCancelled();

		public void onWorking();

	}
}
